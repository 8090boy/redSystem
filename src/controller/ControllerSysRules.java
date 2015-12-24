package controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import controller.entity.SysRules;
import controller.service.IServiceSysRules;
import controller.util.UtilRest;

@Controller
@RequestMapping(value = "/rules")
public class ControllerSysRules {

	@Resource(name = "serviceSysRules")
	private IServiceSysRules serviceSysRules;

	public void setServiceSysRules(IServiceSysRules serviceSysRules) {
		this.serviceSysRules = serviceSysRules;
	}

	@RequestMapping(value = "/set")
	public void setSysRules(int id, HttpServletRequest request, HttpServletResponse response) {

		if (id > 0) {
			// 更新旧的
			SysRules sysRulesOld = serviceSysRules.getIsOK();
			if (sysRulesOld != null) {
				sysRulesOld.setIsOK(0);
				serviceSysRules.update(sysRulesOld);
			}
			// 设置新的
			SysRules sr = serviceSysRules.getById(id + "");
			if (sr != null) {

				sr.setIsOK(1);
				serviceSysRules.update(sr);
				UtilRest.restJson(id, response);
				sr = null;
				return;
			}
		}
		UtilRest.restJson(0, response);
		return;
	}

	@RequestMapping(value = "/default")
	public void setDefault(HttpServletRequest request, HttpServletResponse response) {
		SysRules sysRules = serviceSysRules.getIsOK();
		if (sysRules == null) {
			UtilRest.restJson(0, response);
			return;
		}
		UtilRest.restJson(sysRules.getId(), response);
		return;
	}

	@RequestMapping(value = "/au")
	public void addRules(SysRules rules, HttpServletRequest request, HttpServletResponse response) {

		if (rules.getId() > 0  ) {
			System.out.println("rules update id==" + rules.getId());
			serviceSysRules.update(rules);
			UtilRest.restJson(true, response);
			return;
		} else { // 添加
			serviceSysRules.add(rules);
			UtilRest.restJson(true, response);
			return;
		}

	}

	@RequestMapping(value = "/del")
	public void del(String id, HttpServletRequest request, HttpServletResponse response) {
		serviceSysRules.del(id);
		UtilRest.restJson(1, response);
		return;

	}

	@RequestMapping(value = "/all")
	public void all(HttpServletRequest request, HttpServletResponse response) {
		String rest = "0";
		List<SysRules> sysRulesAll = serviceSysRules.getAll();
		if (sysRulesAll.size() > 0) {
			UtilRest.restJson(sysRulesAll, response);
			return;
		}
		UtilRest.restJson(rest, response);
	}

}