<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="controller.util.alipay.AlipaySubmit"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<%
	//输出到客户端，客户端立即执行
	out.println(AlipaySubmit.buildRequestGO());
%>
<body>
</body>
</html>
