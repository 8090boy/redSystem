<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../ss/user.css" rel="stylesheet">
<script type="text/javascript" src="../dojo10/extend/crud.js"></script>
</head>
<body>
	<h4>完善我的信息</h4>
	<div id="editUser">
		<form name="userForm">
			<ul>
				<li style="display: none;"><b>id：</b><b> <input id="userId"  type="text" name="id" value="${user.id }" readonly="readonly" /></b></li>
				<li><b>城市：</b><b> <input type="text" name="uAddress" value="${user.uAddress }" /></b></li>
				<li><b>姓名：</b><b> <input type="text" name="userName" value="${user.userName }" /></b></li>
				<li><b>身份证：</b><b> <input type="text" name="uIdCard" value="${user.uIdCard }" /></b></li>
				<li><b>开户行：</b><b> <input type="text" name="uBank" value="${user.uBank }" /></b></li>
				<li><b>帐号：</b><b> <input type="text" name="uAccount" value="${user.uAccount }" /></b></li>
				<li><b> </b><b> </b></li>
				<li> <a  class="return" href="./GetAll.go">返回所有</a><a href="javascript: void(0)" onclick="updateUser('updateUser.go')">确定修改</a></li>
			</ul>
		</form>
	</div>
</body>
</html>