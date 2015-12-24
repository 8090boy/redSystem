package controller.util.alipay;

public class AlipayConfig {

	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String PARTNER = "2088511296231891";
	// 商户的私钥
	public static String KEY = "l576485dpfdgpal4f7jvqd248jsmstjd";
	// 付款账户
	public static String ACCOUNT = "1918707016@qq.com";
	// 付款账户名
	public static String ACCOUNT_NAME = "南平市启腾商贸有限公司";
	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "C:\\";
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	// 签名方式 不需修改
	public static String sign_type = "MD5";
	

}
