package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.entity.User;
import controller.service.IServiceSerialNumber;
import controller.service.IServiceUser;
import controller.util.UtilRest;
import controller.util.ViewPages;

@Controller
@RequestMapping(value = "/share")
public class ControllerShareholders {

	@Autowired(required = false)
	private IServiceUser serviceUser;

	@Autowired(required = false)
	private IServiceSerialNumber serviceSerialNumber;

	/**
	 * 系统管理 主页
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/S")
	public String manageSystem(HttpServletRequest request, HttpServletResponse response) {
		return ViewPages.SHARE;
	}

	public void baobiao(HttpServletRequest request, HttpServletResponse response) {
		User userMy = (User) request.getSession().getAttribute("isLogin");
		String cityMy = userMy.getuCity();
		int cityCount = 0;
		String result = null;

		// 返回城市的会员总数
		cityCount = serviceUser.userCountForCity(cityMy);
		result = "{cityCount:" + cityCount + "}";

		UtilRest.restJson(result, response);

	}

	@RequestMapping(value = "/Main")
	public String ToPage() {
		System.out.println("股东页面");
		return ViewPages.SHARE;
	}

}
