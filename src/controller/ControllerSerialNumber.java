package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.entity.SerialNumber;
import controller.service.IServiceSerialNumber;
import controller.service.IServiceUser;
import controller.util.ConfigGlobal;
import controller.util.UtilRest;

@Controller
@RequestMapping("/sn")
public class ControllerSerialNumber {

	@Autowired(required = false)
	private IServiceSerialNumber serviceSerialNumber;

	@Autowired(required = false)
	private IServiceUser serviceUser;

	// 兑换所有可换收益，查询用
	@RequestMapping("/isExAll")
	public void exchangeAll(HttpServletRequest request, HttpServletResponse response) {
		// 获取发放起始层
		int InitialPayment = ConfigGlobal.getRules().getInitialPayment();
		// 本层是否发放，不启用的代表已经发放
		int IPC = serviceSerialNumber.getByLayerIsFail(InitialPayment);
		// 为0则没有发放，小于本层总人数，代表没有发放完
		if (IPC == ConfigGlobal.getInstance().getPeopleCoutForLayer(InitialPayment)) {
			// 本层已经发放完
			ConfigGlobal.getRules().setInitialPayment(InitialPayment + 1);
			UtilRest.restJson(0, response);
			return;
		}

		// 最大每批发放个数
		int Batch = ConfigGlobal.getRules().getBatch();
		int ExchangeStarting = ConfigGlobal.getRules().getExchangeStarting();
		List<SerialNumber> isExchangeSN = null;
		try {
			isExchangeSN = serviceSerialNumber.getByLayerIsExchange(InitialPayment, ExchangeStarting, Batch, ConfigGlobal.getRules().getPhasesUseCount());
			if (isExchangeSN != null && isExchangeSN.size() > 0) {
				// 可以兑换的所有序列号
				JSONArray jsonArray = JSONArray.fromObject(isExchangeSN);
				UtilRest.restJson(jsonArray.toString(), response);
				return;
			}
		} catch (Exception e) {

		}
		UtilRest.restJson(0, response);
	}

	// 所有序列号
	@RequestMapping("/all")
	public void GetSNAll(HttpServletRequest request, HttpServletResponse response) {
		List<SerialNumber> sns = serviceSerialNumber.getAll();
		if (sns == null) {
			UtilRest.restJson("0", response);
			return;
		}
		UtilRest.restJson(sns, response);
	}

}
