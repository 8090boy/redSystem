package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import controller.service.IServiceSerialNumber;
import controller.service.IServiceSysRules;
import controller.service.IServiceUser;
import controller.util.ConfigGlobal;
import controller.util.ViewPages;

@Controller
@RequestMapping(value = "/admin")
public class ControllerAdmin {

	@Autowired(required = false)
	private IServiceUser serviceUser;

	@Autowired(required = false)
	private IServiceSerialNumber serviceSerialNumber;

	@Autowired(required = false)
	private IServiceSysRules serviceSysRules;

	/**
	 * 系统管理 主页
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/S")
	public String manageSystem(HttpServletRequest request, HttpServletResponse response) {

		return ViewPages.ADMIN;
	}

	/**
	 * 系统初始化
	 * 
	 * @return
	 */
	@RequestMapping("/initSys")
	public synchronized String initSys(HttpServletRequest request) {
		// 清空所有序列号
		serviceSerialNumber.delAll();

		// 删除所有并添加最高权限系统账户
		serviceUser.delAll();
		serviceUser.getUserDefault();

		// 清空系统配置添加默认系统配置
		serviceSysRules.delAll();
		serviceSysRules.getSysRulesSingle();
		ConfigGlobal.setRules(serviceSysRules.getIsOK());
		return ViewPages.ADMIN;
	}

}
