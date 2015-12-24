package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.entity.SerialNumber;
import controller.entity.User;
import controller.service.IServiceSerialNumber;
import controller.service.IServiceSysRules;
import controller.service.IServiceUser;
import controller.util.ConfigGlobal;
import controller.util.UtilRest;
import controller.util.SendSms;
import controller.util.ViewPages;
import controller.util.alipay.AlipayNotify;
import controller.util.alipay.AlipaySubmit;

@Controller
@RequestMapping("/alipay")
public class ControllerPay {

	@Autowired(required = false)
	private IServiceSerialNumber serviceSerialNumber;

	@Autowired(required = false)
	private IServiceUser serviceUser;

	@Autowired(required = false)
	private IServiceSysRules serviceSysRules;

	/**
	 * 支付宝回传数据处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
			System.out.println("回传：" + name + "===" + valueStr);
		}

		PrintWriter out = null;
		response.setContentType("text/html");
		if (AlipayNotify.verify(params)) { // 验证成功
			String success_details = request.getParameter("success_details");
			exOKSN(success_details);
			try {
				out = response.getWriter();
				out.write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // 验证失败
			String fail_details = request.getParameter("fail_details");
			System.out.println("失败==" + fail_details);
			exErrorSN(fail_details);
			try {
				out = response.getWriter();
				out.write("fail");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		serviceSysRules.update(ConfigGlobal.getRules());
	}

	// 发放失败的序列号处理
	public Boolean exErrorSN(String fail_details) {
		String[] ERRsns = fail_details.split(ConfigGlobal.getInterval());
		int successCount = ERRsns.length;
		System.out.println("发放失败的序列号个数：" + successCount);
		for (int i = 0; i < ERRsns.length; i++) {
			System.out.println(ERRsns[i]);
		}
		return true;
	}

	// 发放成功的序列号处理
	public Boolean exOKSN(String success_details) {
		int InitialPayment = ConfigGlobal.getRules().getInitialPayment();
		int Fee = ConfigGlobal.getRules().getFee();
		String[] OKSNS = success_details.split(ConfigGlobal.getInterval());
		int successCount = OKSNS.length;// 本次发放成功多少个序列号数据
		System.out.println("发放失败的序列号个数：" + successCount);

		for (String SN : OKSNS) {
			String[] strA = SN.split("\\^");
			SerialNumber snA = serviceSerialNumber.getByLayerAndId(InitialPayment, strA[0]);
			User userA = serviceUser.getForId(snA.getUserId());
			userA.setSnsOK(userA.getSnsOK() + snA.getId() + ConfigGlobal.getInterval());
			int okMoney = snA.getMoney() - Fee;
			userA.setTotal(userA.getTotal() + okMoney);
			String[] oldSnsArr = userA.getSns().split(ConfigGlobal.getInterval());

			String str = null;
			for (int i = 0; i < oldSnsArr.length; i++) {
				if (!snA.getId().equals(oldSnsArr[i])) {
					str += oldSnsArr[i];
				}
			}
			if (str != "" || str != null) {
				userA.setSns(str);
			} else {
				userA.setSns("");
			}
			userA.setSnsNo(userA.getSnsNo() - 1);
			if (ConfigGlobal.getRules().getIsSend() > 0) {
				SendSms.smsTest(userA.getuMob(), snA.getMoney()); // 短信通知收益人
			}
			System.out.println(userA.getuMob());
			serviceUser.update(userA);
			snA.setMoney(0);
			snA.setStatus(1);
			serviceSerialNumber.update(snA);
			return true;

			// 放成功的记录暂留
		}
		return false;
	}

	// 兑换红包
	@RequestMapping("/exchange")
	public String exAll(HttpServletRequest request, HttpServletResponse response) {
		int InitialPayment = ConfigGlobal.getRules().getInitialPayment();
		// 本层是否发放，不启用的代表已经发放
		int IPC = serviceSerialNumber.getByLayerIsFail(InitialPayment);
		// 为0则没有发放，小于本层总人数，代表没有发放完
		if (IPC == ConfigGlobal.getInstance().getPeopleCoutForLayer(InitialPayment)) {
			// 本层已经发放完
			ConfigGlobal.getRules().setInitialPayment(InitialPayment + 1);
			UtilRest.restJson("Payment is completed for layer" + InitialPayment, response);
			return null;
		}
		int Batch = ConfigGlobal.getRules().getBatch();
		int ExchangeStarting = ConfigGlobal.getRules().getExchangeStarting();
		List<SerialNumber> isExchangeSN = null;
		try {
			isExchangeSN = serviceSerialNumber.getByLayerIsExchange(InitialPayment, ExchangeStarting, Batch, ConfigGlobal.getRules().getPhasesUseCount());
			if (isExchangeSN.size() > 0 && isExchangeSN != null) {
				// 可以兑换的所有序列号
				AlipaySubmit.setIsExchangeSN(isExchangeSN);
				AlipaySubmit.setUsers(serviceUser.getUsersForSNS(isExchangeSN));
				request.setAttribute("isExchangeSN", isExchangeSN);
				request.setAttribute("Users", serviceUser.getUsersForSNS(isExchangeSN));
				return ViewPages.PAY;
				// return AlipaySubmit.buildRequestGO(request);
			}
		} catch (Exception e) {

		}
		UtilRest.restJson("", response);
		return null;
	}

}
