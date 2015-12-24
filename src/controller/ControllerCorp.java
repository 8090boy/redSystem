package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.entity.User;
import controller.service.IServiceRecommend;
import controller.service.IServiceSerialNumber;
import controller.service.IServiceUser;
import controller.util.ConfigGlobal;
import controller.util.UtilRest;
import controller.util.ViewPages;

@Controller
@RequestMapping(value = "/comp")
public class ControllerCorp {

	@Autowired(required = false)
	private IServiceUser serviceUser;
	@Autowired(required = false)
	private IServiceSerialNumber serviceSerialNumber;
	@Autowired(required = false)
	private IServiceRecommend serviceRecommend;

	// 报表 验证用户是否合法、根据用户级别、类别、时间提供报表
	@RequestMapping(value = "/RS")
	public void Monthly(int year, int month, HttpServletResponse response) {
		// 序列号报表
		int count = serviceSerialNumber.getCurrentCountFor(year, month);
		UtilRest.restJson(count, response);
	}

	/**
	 * 会员数据报表
	 * 
	 * @param year
	 *            某年
	 * @param month
	 *            某年
	 * @param city
	 *            某个城市
	 * @param response
	 */
	@RequestMapping(value = "/RU")
	public void baobiao(int year, int month, String city, HttpServletResponse response) {
		int cityCount = 0;
		String result = null;
		if (city != null && city != "") { // 所在城市会员总数
			if (city.equals("^[\u4E00-\u9FA5]+$")) {
				// 返回城市的会员总数
				cityCount = serviceUser.userCountForCity(city);
				result = "{cityCount:" + cityCount + "}";
			}
		} 
		if (year != 0 && month != 0) {
			int monthConut = serviceUser.userCountForCity(year, month);
			result += "," + "{" + month + ":" + monthConut + "}";
		}
		UtilRest.restJson(result, response);
	}

	@RequestMapping(value = "/RA")
	public void RA(  HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		User my = (User) request.getAttribute("isLogin");
		int myRecCount = 0;
		  int	myCity = 0;	// 返回会员总数
		if(my != null){
			myRecCount = serviceRecommend.getForVesting(my.getId()).size();
			myCity = serviceUser.userCountForCity(my.getuCity());
		}
		String snCount = "0";
		String uCount = "0";
		if( serviceSerialNumber.getAll() != null ){
			snCount = serviceSerialNumber.getAll().size() + ""; // 序列号总数
		}
		if( serviceUser.getAll() != null ){
			uCount = serviceUser.getAll().size() + ""; // 总会员数，包括股东，普通会员，违规会员
		}
		int endLayer = ConfigGlobal.getRules().getInitialPayment() ; // 已发放完成的层
		if( endLayer > 0  ){
			endLayer = endLayer - 1;
		}
		int userCount = serviceUser.getUserCountForType(0); // 普通会员总数
		int shareCount = serviceUser.getUserCountForType(1); // 股东总数
		int userWGCount = serviceUser.getUserCountForType(2); // 违规会员总数 
		int snCurrentMonth = serviceSerialNumber.getCurrentMonth(	); //当月SN序列号总数 
		result = "{\"snCount\":\"" + snCount + 
				"\", \"snCurrentMonth\":\"" + snCurrentMonth +
				"\", \"uCount\":\"" + uCount +
				"\", \"myCity\":\"" + myCity +
				"\",  \"endLayer\":\"" + endLayer + 
				"\",  \"userCount\":\"" + userCount + 
				"\",  \"shareCount\":\"" + shareCount + 
				"\",  \"myRecCount\":\"" + myRecCount + 
				"\",  \"userWGCount\":\"" + userWGCount +
				  "\"  }";
		UtilRest.restJson(result, response);
	}

	@RequestMapping(value = "/Main")
	public String ToPage() {
		return ViewPages.COMP;
	}

}
