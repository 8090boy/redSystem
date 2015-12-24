package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.entity.User;
import controller.service.IServiceSerialNumber;
import controller.service.IServiceUser;
import controller.util.UtilVerify;
import controller.util.UtilRest;
import controller.util.ViewPages;

@Controller
public class ControllerLogin {

	@Autowired(required = false)
	private IServiceUser serviceUser;

	@Autowired(required = false)
	private IServiceSerialNumber serviceSerialNumber;

	/**
	 * 登录 需要手机号、密码 验证成功后转向不同级别的用户主页
	 * 
	 * @param mob
	 *            　手机
	 * @param pwd
	 *            　密码
	 * @return 视图
	 */
	@RequestMapping(value = "/go")
	public String LoginSys(String mob, String pwd, HttpServletRequest request, HttpServletResponse response) {

		if (mob == null || pwd == null) {
			return ViewPages.LOGIN;
		}

		if (!UtilVerify.isMobilesOrEmail(mob)) {
			System.out.println("手机号不对");
			return ViewPages.LOGIN;
		}

		User user = serviceUser.getForMob(mob);

		// 非法用户
		if (user == null || user.getUtype() < 1 || user.getPassWord() == "" || user.getPassWord() == null) {
			System.out.println(" 非法用户");
			return ViewPages.LOGIN;
		}
		// 密码不对
		if (!user.getPassWord().equals(pwd)) {
			System.out.println("密码不对");
			return ViewPages.LOGIN;
		}

		if (user.getUtype() == 1) { // 股东
			System.out.println(user.getUserName() + "股东登录!");
			return ViewPages.SHARE;
		} else if (user.getUtype() == 2) { // 公司
			System.out.println(user.getUserName() + "公司账户登录!");
			return ViewPages.COMP;
		}

		request.getSession().setAttribute("isLogin", user);
		System.out.println(user.getuMob() + "系统管理账户登录!");
		return ViewPages.ADMIN;

	}

	@RequestMapping(value = "/exit")
	public void exit(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("isLogin");
		UtilRest.restJson(1, response);
	}

}
