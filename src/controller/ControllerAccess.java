package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import controller.entity.User;
import controller.service.IServiceSysRules;
import controller.service.IServiceUser;
import controller.util.ConfigGlobal;
import controller.util.IPSeeker;
import controller.util.UtilRest;

@Controller
public class ControllerAccess {

	@Autowired(required = false)
	private IServiceUser serviceUser;

	@Autowired(required = false)
	private IServiceSysRules serviceSysRules;

	// 购买商城专用，外部系统专用
	@RequestMapping(value = "/au")
	public void AddUser(String str, HttpServletRequest request, HttpServletResponse response) {
		String result = "0";
		if (str == null || str.getBytes().length < 20) {
			System.out.println("非法数据格式：" + str);
			UtilRest.restJson(result, response);
			return;
		}
		synchronized (this) {
			result = serviceUser.AddUser(request, str2User(str));
			serviceSysRules.update(ConfigGlobal.getRules());
			UtilRest.restJson(result, response);
		}
		System.out.println("返回系统：" + result);
	}

	// 字符串转化为user对象
	public User str2User(String str) {
		// 13678560099|马大鹏|611041314@qq.com|70969EDA7D5E8E2E4FCA75DF626FDC97|passWord123456|广东省深圳市|20
		// 手机|支付宝姓名|支付宝账户|用户加密问题|商城密码|用户注册IP地址|目前总积分
		System.out.println(str);
		String[] strArr = str.split("\\|");
		User usr = new User();
		usr.setUtype(0); // 普通会员
		usr.setuMob(strArr[0]); // 手机
		usr.setUserName(strArr[1]); // 支付宝姓名
		usr.setuAccount(strArr[2]); // 支付宝帐号
		usr.setuSQA(strArr[3]); // 加密问题
		usr.setPassWord(strArr[4]);// 商城密码
		String addr = IPSeeker.cityForIP(strArr[5].toString()); // 所在城市
		usr.setuAddress(addr); // 所在城市
		usr.setIntegral(Integer.parseInt(strArr[6])); // 积分
		return usr;
	}

}
