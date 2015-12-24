package controller.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SendSms {

	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

	public static Boolean smsTest(String phoneNo, int jine) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
		int mobile_code = (int) ((Math.random() * 9 + 1) * 1000); // 验证码
		System.out.println(mobile_code);

		// String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
		String content = new String("您的验证码是：" + jine + "。请不要把验证码泄露给其他人。");
		System.out.println(phoneNo + "已发送内容：" + content);
		NameValuePair[] data = {// 提交短信
		new NameValuePair("account", "cf_dapengma"), new NameValuePair("password", "qq611041314"), // 密码可以使用明文密码或使用32位MD5加密
				new NameValuePair("mobile", phoneNo), new NameValuePair("content", content), };
		method.setRequestBody(data);

		try {
			client.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);

			if (code.equalsIgnoreCase("2")) { // 短信提交成功
				return true;

			}

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return false;
	}

}