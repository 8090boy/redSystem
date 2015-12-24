<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../ss/user.css" rel="stylesheet">
<script type="text/javascript" src="../dojo10/dojo/dojo.js" data-dojo-config="isDebug:true, parseOnLoad:false"></script>
<script type="text/javascript" src="../dojo10/extend/crud.js"></script>
</head>
<body>
	<a href="../">首页</a> <!-- <a   href="../sn/SNAll.go">所有序列号</a> <a href="../addUser.html">添加会员</a> -->
	<h4>所有会员</h4>
	<table >
		<tr>
			<td>ID | 会员名 | 密码 | 手机</td>
			<td>序列号总数</td>
			<td>总积分</td>
			<td>邮箱 | 加入时间</td>
			<td>类别</td>
			<td>地址</td>
			<td>序列号</td>

		</tr>
		<c:if test="${!empty user }">
			<c:forEach items="${user }" var="u">
				<tr id="${u.id}">
					<td>${u.id }<br/> ${u.userName }  | ${u.passWord }  | ${u.uMob }</td>
						<td>${u.snsNo }</td>
					<td>${u.integral }</td>
					<td>${u.uMail } <br/>${u.created }</td>
					<td>${u.utype }</td>
					<td>${u.uAddress }<br/>${u.userName }&nbsp;|&nbsp;${u.uAccount }</td>
					<td>${u.sns }</td>
				

				</tr>
			</c:forEach>
		</c:if>

	</table>
</body>
</html>