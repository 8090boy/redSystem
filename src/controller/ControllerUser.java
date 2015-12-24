package controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import controller.entity.User;
import controller.service.IServiceUser;
import controller.util.UtilRest;

@Controller
@RequestMapping("/user")
public class ControllerUser {

	@Autowired(required = false)
	private IServiceUser serviceUser;

	//认为分配序列号
	@RequestMapping(value = "/addUser")
	public String AddUser(User user, HttpServletRequest request, HttpServletResponse response) {
		synchronized (this) {
			String result = serviceUser.AddUser(request,user);
			return result;
		}
	}


	@RequestMapping("/S")
	public void getAllShare(HttpServletResponse response) {
		UtilRest.restJson(serviceUser.getAllShare(), response);
	}

	@RequestMapping("/updateUser")
	public void updateUser(User user, HttpServletResponse response) {
		if (serviceUser.update(user)) {
			user = serviceUser.getForId(user.getId());
			UtilRest.restJson(user, response);
			return;
		}
		UtilRest.restJson(user, response);
	}

  
	@RequestMapping("/all")
	public void getAllUser(HttpServletResponse response) {
		List<User> users = serviceUser.getAll();
		if (users != null) {
			UtilRest.restJson(users, response);
			return;
		}
		UtilRest.restJson("error", response);
	}

	@RequestMapping("/Del")
	public void delUser(String id, HttpServletResponse response) {

		if (serviceUser.delForId(id)) {
			UtilRest.restJson(1, response);
			return;
		}
		UtilRest.restJson(0, response);
	}

	@RequestMapping("/my")
	public void getUser(String id, HttpServletResponse response) {
		if (id != null && id != "") {
			User user = serviceUser.getForId(id);
			UtilRest.restJson(user, response);
			return;
		}
		UtilRest.restJson("error", response);
	}

	@RequestMapping("/as")
	public void setShareholder(String id, int type, String mob, HttpServletResponse response) {

		if (type < 3 && mob != null) {
			Boolean abc = serviceUser.setUas(mob, type);
			UtilRest.restJson(abc.toString(), response);
		}
		if (id != null) {
			String abc = serviceUser.setShareholder(id);
			UtilRest.restJson(abc, response);
		}

	}

}
