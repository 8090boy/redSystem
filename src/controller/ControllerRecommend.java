package controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

 
import controller.entity.Recommend;
import controller.entity.User;
import controller.service.IServiceRecommend;
import controller.service.IServiceUser;
import controller.util.UtilVerify;
import controller.util.UtilRest;
import controller.util.UtilDate;

@Controller
@RequestMapping("/recom")
public class ControllerRecommend {
	@Autowired(required = false)
	private IServiceRecommend serviceRecommend;

	@Autowired(required = false)
	private IServiceUser serviceUser;

	@RequestMapping(value = "myGroup")
	public void myGroupl(HttpServletRequest request, HttpServletResponse response) {
		User my = (User) request.getSession().getAttribute("isLogin");
		if (my != null) {
			UtilRest.restJson(serviceRecommend.getForVesting(my.getId()), response);
		}
	}

	@RequestMapping(value = "Add")
	public void addRec(String mob, HttpServletRequest request, HttpServletResponse response) {

		if (!UtilVerify.isMobileNO(mob)) {
			UtilRest.restJson("0", response);
			System.out.println("手机号不对！");
			return;
		}
		
		List<Recommend> recs = null;
		recs = serviceRecommend.getForMob(mob);
		if (recs != null) {
			UtilRest.restJson("1", response);
			System.out.println("已经推荐过了！");
			return;
		}

		User my = (User) request.getSession().getAttribute("isLogin");
		if (my == null) {
			System.out.println("我没有登录！");
			UtilRest.restJson("0", response);
			return;
		}
		
		

		User user = serviceUser.getForMob(mob); // 被推荐人信息
		if (user == null) { // 被推荐人不存在
			System.out.println("被推荐人不存在！");
			UtilRest.restJson("2", response);
			return;
		}
		System.out.println("user==" +  user.getuCity() );
		System.out.println("user==" +  user.getuMob() );
		System.out.println("user==" +  user.getUserName() );
		System.out.println("my.getId()==" +  my.getId()  );
		System.out.println("UtilDate.getCurrentDate()==" +  UtilDate.getCurrentDate() );

		Recommend rec = new Recommend();
		rec.setrCity(user.getuCity());
		rec.setrMob(mob);
		rec.setrVesting(my.getId());
		rec.setSort(UtilDate.getCurrentDate());
		
		try {
			serviceRecommend.add(rec);
			UtilRest.restJson("9", response);
		} catch (Exception e) {
			System.out.println("推荐错误！");
			UtilRest.restJson("0", response);
		}
	}

	@RequestMapping(value = "myCity")
	public void getAllForCity(HttpServletRequest request, HttpServletResponse response) {
		User my = (User) request.getSession().getAttribute("isLogin");
		if (my == null) {
			UtilRest.restJson("0", response);
			return;
		}

		UtilRest.restJson(serviceUser.getAllForCity(my.getuCity()), response);
	}

}
