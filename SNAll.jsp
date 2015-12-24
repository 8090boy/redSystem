<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../dojo10/dojo/dojo.js" data-dojo-config="isDebug:true, parseOnLoad:false"></script>
<title>Insert title here</title>

<style type="text/css">
* {
	margin: 0px;
	padding: 0px;
	border: 0px;
	font-size: 14px;
	font-family: arial;
}

input {
	border: 1px solid #dddddd;
}

table {
	margin: 0px auto;
	cursor: pointer;
}

table tr td {
	border-bottom: 1px solid #ddddd;
	padding: 5px 10px;
	display: inline-block;
}

table tr:HOVER {
	background: #efefaa;
}

#manager {
	width: 100%;
	height: 24px;
	line-height: 24px;
	margin: 0px auto;
	text-align: right;
}

#manager a {
	width: 80px;
	height: 24px;
	line-height: 24px;
	margin: 0px auto;
	text-align: center;
	display: inline-block;
}

#manager a:HOVER {
	background: yellow;
}

</style>


</head>
<body>
	<div id="mask"></div>
	<div id="manager">
	<a class="index" href="../perfect.html">管理中心</a>----------<a class="index" href="../user/GetAll.go">所有会员</a>----- <a href="../addUser.html">添加会员</a>
---------<a href="AddTestSN.go">AddTestSN</a> ------- <a href="delAll.go">Del All</a> ------- <a href="SNAll.go">Get All</a>
	</div>
	<div id="phone"></div>
	<div id="testTime"></div>

	<table>
		<tbody>
			<tr>
				<td>系统ID</td>
				<td>别名</td>
				<td>父级系统ID</td>
				<td>层号</td>
				<td>本层排序</td>
				<td>归属会员</td>
				<td>状态</td>
				<td>红包个数</td>
				<td>总金额</td>
				<td>所需的总积分</td>
				<td>产生时间</td>
				<td>编辑</td>
			</tr>
			<c:if test="${!empty serialNumbers }">
				<c:forEach items="${serialNumbers }" var="sn">
					<tr id="${sn.id }">
						<td>${sn.id }</td>
						<td>${sn.nikeName }</td>
						<td>${sn.parentId }</td>
						<td>${sn.layer }</td>
						<td>${sn.layerOrder }</td>
						<td>${sn.userId }</td>
						<td>${sn.status }</td>
						<td>${sn.numberRed }</td>
						<td>${sn.money }</td>
						<td>${sn.totalPoints }</td>
						<td>${sn.created}</td>
						<td>
							<%-- <a href="deleteSN.go?id=${sn.id}">删除</a> --%>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<script type="text/javascript">
		require([ "dojo/dom", "dojo/dom-construct", "dojo/query", "dojo/on",
				"dojo/_base/xhr" ],
				function(dom, domConstruct, query, on, xhr) {

					var phone = query("#phone")[0];
					var phoneNo = domConstruct.create("input", {
						id : "phoneNo",
						type : "text",
						name : "phoneNo"
					}, phone);
					var subA = domConstruct.create("a", {
						id : "submit",
						innerHTML : "短信测试",
						href : "javascript:void(0)"
					}, phone);

					on(subA, "click", function() {
						var urlA = "../sms/test.go?phoneNo=" + phoneNo.value;
						xhr.get({
							url : urlA,
							load : function(result) {
								alert("短信发送OK " + result);
							}
						});

						subA.style.display = "none";
						phoneNo.disabled = "true";

					});
					/////////

					var testTime = query("#testTime")[0];
					var testTimeInput = domConstruct.create("input", {
						id : "geshu",
						type : "text",
						name : "geshu"
					}, testTime);
					var ttA = domConstruct.create("a", {
						id : "submit2",
						innerHTML : "测试序列号产生时间",
						href : "javascript:void(0)"
					}, testTime);

					
					//
					on(ttA, "click", function() {

						var mask = dojo.byId("mask");
						mask.style.display = "block";

						var urlA = "get200.go?geshu=" + testTimeInput.value;
						xhr.get({
							url : urlA,
							load : function(result) {

								alert(result);
								mask.style.display = "none";
							}
						});

					});

				});
	</script>
</body>
</html>