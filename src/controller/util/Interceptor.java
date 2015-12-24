package controller.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import controller.entity.User;

public class Interceptor implements HandlerInterceptor {

	/**
	 * 在DispatcherServlet 处理完请求后被调用，可进行资源清理操作。
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
	 //	System.out.println("-----清理资源");

	}

	/**
	 * 在业务处理器完成后，但是DispatcherServlet向客户端返回请求前调用，在该方法中对用户请求request进行处理。
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		//	  System.out.println("处理完了");

	}

	/**
	 * 这个方法在业务处理器处理请求之前被调用，在该方法中对用户请求request进行处理。
	 *  如果本拦截器对请求进行拦截处理后要调用其他的拦截器，或业务处理，则返回true； 
	 *  如果程序员决定不需要再调用其他的组件去处理请求，则返回false。
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		User user = (User) arg0.getSession().getAttribute("isLogin");
		if (user != null) {
			return true;
		}
		arg1.sendRedirect("/login.html");
		return false;
	}

}
