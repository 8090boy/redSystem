package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.util.SendSms;

@Controller
@RequestMapping("/sms")
public class ControllerSMS {

	String result = null;

	@RequestMapping("/test")
	public String valiedateCode(String phoneNo, HttpServletRequest request, HttpServletResponse response) {
		new SendSms();
		if (SendSms.smsTest(phoneNo, 10  )) {
			result = "OK";
			PrintWriter out = null;
			response.setContentType("application/json");
			try {
				out = response.getWriter();
				out.write(result.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.close();

		} else {
			System.out.println("error");
			return "error";
		}

		return "success";
	}
}
