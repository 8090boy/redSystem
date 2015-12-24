package controller.util.alipay;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import controller.entity.SerialNumber;
import controller.entity.User;
import controller.util.ConfigGlobal;
import controller.util.MD5;
import controller.util.UtilDate;
import controller.util.ViewPages;
import controller.util.alipay.AlipayConfig;
import controller.util.alipay.httpClient.HttpProtocolHandler;
import controller.util.alipay.httpClient.HttpRequest;
import controller.util.alipay.httpClient.HttpResponse;
import controller.util.alipay.httpClient.HttpResultType;

public class AlipaySubmit {

	/**
	 * 支付宝提供给商户的服务接入网关URL(新)
	 */
	private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

	private static  List<SerialNumber> isExchangeSN = null;
	private static  List<User> Users = null;

	public static User userForId(String id) {
		for (int i = 0; i < Users.size(); i++) {
			if (id.equals(Users.get(i).getId())) {
				return Users.get(i);
			}
		}
		return null;
	}

 
	@SuppressWarnings("unchecked")
	public static String buildRequestGO( HttpServletRequest request   ) {
		if (isExchangeSN == null ||  Users == null ) {
			isExchangeSN = (List<SerialNumber>) request.getAttribute("isExchangeSN"); 
			Users = (List<User>) request.getAttribute("Users"); 	 
		}

		String email = AlipayConfig.ACCOUNT;
		String account_name = AlipayConfig.ACCOUNT_NAME;
		String pay_date = UtilDate.getDate();
		String batch_no = UtilDate.getOrderNum() + UtilDate.getThree();
		// 付款总金额
		int batch_fee = 0;
		// 付款详细数据
		String detail_data = "";
		// 必填，格式：流水号1^收款方帐号1^真实姓名^付款金额1^备注说明1|流水号2^收款方帐号2^真实姓名^付款金额2^备注说明2....
		
		for (int i = 0; i < isExchangeSN.size(); i++) {
			SerialNumber snAbc = isExchangeSN.get(i);
			User userAbc = userForId(snAbc.getUserId());
			if (userAbc != null) {
				int money = snAbc.getMoney() - ConfigGlobal.getRules().getFee();// 扣手续费
				batch_fee = batch_fee + money;
				detail_data = detail_data + snAbc.getId() + "^" + userAbc.getuAccount() + "^" + userAbc.getUserName() + "^" + money + "^www.gomall.la";
				if (i < (isExchangeSN.size() - 1)) {
					detail_data = detail_data + "|";
				}
			}

		}

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "batch_trans_notify");
		sParaTemp.put("partner", AlipayConfig.PARTNER);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", ViewPages.notify_url );
		sParaTemp.put("email", email);
		sParaTemp.put("account_name", account_name);
		sParaTemp.put("pay_date", pay_date);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("batch_fee", batch_fee + "");
		sParaTemp.put("batch_num", isExchangeSN.size() + "");// 付款笔数
		sParaTemp.put("detail_data", detail_data);

		// 建立请求
		 String sHtmlText = null;
		try {
			sHtmlText = buildRequestForm(sParaTemp);
		//	sHtmlText = buildRequest( sParaTemp);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 输出到客户端，客户端立即执行
		return sHtmlText;
	}
	
	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果， 
	 * 
	 * @param strParaFileName
	 *            文件类型的参数名
	 * @param strFilePath
	 *            文件路径
	 * @param sParaTemp
	 *            请求参数数组
	 * @return 支付宝处理结果
	 * @throws Exception
	 */
	public static String buildRequest( Map<String, String> sParaTemp ) throws Exception {
		System.out.println("开始付款");
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp);
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(AlipayConfig.input_charset);
		request.setParameters(generatNameValuePair(sPara));
		request.setUrl(ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset);		
		HttpResponse response = httpProtocolHandler.execute(request);
		if (response == null) {
			return null;
		}
		String strResult = response.getStringResult();
		System.out.println("完成付款 strResult===" + strResult );
		return strResult;
	}

	/**
	 * 建立请求，以表单HTML形式构造（默认）
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param strMethod
	 *            提交方式。两个值可选：post、get
	 * @param strButtonName
	 *            确认按钮显示文字
	 * @return 提交表单HTML文本
	 */
	public static String buildRequestForm(Map<String, String> sParaTemp) {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp);
		List<String> keys = new ArrayList<String>(sPara.keySet());
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"post\">");
		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) sPara.get(name);
			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}
		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"OK\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
		return sbHtml.toString();
	}



	/**
	 * 生成签名结果
	 * 
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestMysign(Map<String, String> sPara) {
		String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		if (AlipayConfig.sign_type.equals("MD5")) {
			mysign = MD5.sign(prestr, AlipayConfig.KEY, AlipayConfig.input_charset);
		}
		return mysign;
	}

	/**
	 * MAP类型数组转换成NameValuePair类型
	 * 
	 * @param properties
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}

		return nameValuePair;
	}

	/**
	 * 生成要请求给支付宝的参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 */
	private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		// 生成签名结果
		String mysign = buildRequestMysign(sPara);
		// 签名结果与签名方式加入请求提交参数组中
		sPara.put("sign", mysign);
		sPara.put("sign_type", AlipayConfig.sign_type);
		return sPara;
	}

	/**
	 * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
	 * 
	 * @return 时间戳字符串
	 * @throws IOException
	 * @throws DocumentException
	 * @throws MalformedURLException
	 */
	public static String query_timestamp() throws MalformedURLException, DocumentException, IOException {

		// 构造访问query_timestamp接口的URL串
		String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + AlipayConfig.PARTNER + "&_input_charset" + AlipayConfig.input_charset;
		StringBuffer result = new StringBuffer();

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new URL(strUrl).openStream());
		@SuppressWarnings("unchecked")
		List<Node> nodeList = doc.selectNodes("//alipay/*");

		for (Node node : nodeList) {
			// 截取部分不需要解析的信息
			if (node.getName().equals("is_success") && node.getText().equals("T")) {
				// 判断是否有成功标示
				@SuppressWarnings("unchecked")
				List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
				for (Node node1 : nodeList1) {
					result.append(node1.getText());
				}
			}
		}

		return result.toString();
	}


	public static void setIsExchangeSN(List<SerialNumber> isExchangeSN2) {
		isExchangeSN = isExchangeSN2;
	}

	public static void setUsers(List<User> usersForSNS) {
		Users = usersForSNS;
	}
 

}
