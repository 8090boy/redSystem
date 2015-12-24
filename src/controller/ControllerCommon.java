package controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import controller.service.IServiceUser;
import controller.util.UtilRest;
import controller.util.UtilVerify;

@Controller
@RequestMapping(value = "/common")
public class ControllerCommon {

	@Autowired(required = false)
	private IServiceUser serviceUser;

	@RequestMapping(value = "/mob")
	public void Monthly(String mob, HttpServletResponse response) {
		if (!UtilVerify.isMobileNO(mob)) {
			System.out.println(mob);
			UtilRest.restJson("0", response);
			return;
		}
		if (serviceUser.getForMob(mob) != null) {
			System.out.println("已经存在了");
			UtilRest.restJson("0", response);
			return;
		}
		UtilRest.restJson("0264", response);
		// 产生随机码
	}

}
