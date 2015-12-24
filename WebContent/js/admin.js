var RS = {};
$(function() {
	RS.admin().init();
});
RS.admin = function() {

	function initUI() {
		var uMode = '\<div id="uMode">\
		<a href="/comp/Main.go">公司主界面</a>\
		<a href="/share/Main.go">股东主界面</a>\
		<a href="javascript:;">系统初始化</a>\
		<a href="javascript:;">系统参数设置</a>\
		</div>';
		var menuDiv = '\<div id="menu" >\
		<a href="javascript:;"  >所有会员</a>\
		<a href="javascript:;" onclick="subSomething()"  >添加会员</a>\
		<a href="javascript:;"  >所有序列号</a>\
		<a href="javascript:;"  >所有可兑换的序列号</a>\
		<a href="/alipay/exchange.go"  >兑换序列号</a>\
		<a href="javascript:;"  >退出</a>\
		</div>';
		$("body").append(uMode + menuDiv);
		event.bandingEvent();
	}

	var event = {
		bandingEvent : function() {			
			var ma = $("#menu a");
			$(ma[0]).click(event.userAll);
			$(ma[2]).click(event.snAll);
			$(ma[3]).click(event.AllIsExSN);
			$(ma[5]).click(event.exit);
			
			var uMode = $("#uMode a");
			$(uMode[2]).click( event.initSys );
			$(uMode[3]).click( new RULES.all().init );
		},
		//初始化系统
		initSys : function() {
			if (confirm("初始化会清空系统所有数据！\n您确定要初始化吗？")) {
				$.get("/admin/initSys.go", null, function(result) {
					alert("初始化完毕！");
					event.exit();
				});
			}
		},
		
		// 获取所有会员信息
		snAll : function() {
			$.post( "/sn/all.go", function(result) {
				var obj = result;
				console.log( result );
				if (obj.length > 0) {
					event.showSNS(obj);
				} else {
					alert("没有数据");
				}
			});
		},
		// 获取所有会员信息
		userAll : function() {
			$.post( "/user/all.go", function(result) {
				var obj = result;
				if (obj.length > 0) {
					event.showUsers(obj);
				} else {
					alert("没有数据");
				}
			});
		},
		// 所有序列号信息
		showSNS : function() {
			var oldCon = $("#con");
			if (oldCon[0]) {
				oldCon.remove();
			}		
			var divCon = "<div id=\"con\" ><ul id=\"userList\"></ul><div>";
			$("body").append(divCon);
			for ( var i = 0; i < arguments[0].length; i++) {
				var sn = arguments[0][i];
				var userInfo = '\
						"<li id="' + sn.id + '">\
					<b title="归属会员ID：'+ sn.userId + '&#13;属 于 阶 段 ：' + sn.phases + '">'+ sn.id + '</b>\
					<b>' + sn.parentId + '</b>\
					<b>' + sn.layer + '</b>\
					<b>' + sn.layerOrder + '</b>\
					<b>' + sn.status + '</b>\
					<b>' + sn.numberRed + '</b>\
					<b>' + sn.money  + '</b>\
					<b>' + sn.totalPoints + '</b>\
					<b>' + sn.created  + '</b>	</li>';				 
				$("#userList").append(userInfo);
			}
		},
		// 显示会员信息
		showUsers : function() {
			var oldCon = $("#con");
			if (oldCon[0]) {
				oldCon.remove();
			} 
			var divCon = "<div id=\"con\" ><ul id=\"userList\"></ul><div>";
			$("body").append(divCon);
			for ( var i = 0; i < arguments[0].length; i++) {
				var user = arguments[0][i];
				var userInfo = "<li id=\"" + user.id + "\"><b>" + user.id + "<br/>" + user.userName + "—" + user.uMob + "—" + user.snsNo + "—" + user.integral
						+ "—" + user.uMail + "</b>" + "<b>" + user.utype + "</b>" + "<b>" + user.sns + "</b>" + "<b>" + user.created + "</b></li>";
				$("#userList").append(userInfo);
			}
		},
	

		// 所有可兑换的序列号
		AllIsExSN : function() {
			$.post("/sn/isExAll.go", function(result) {
					if (! result ) {
						alert("没有数据"); 	return;
					}
				
					if (result.length > 0) {
						event.showIsExSN(result);
						}
				
				});
		},
		
		// 显示可兑换的序列号
		showIsExSN : function() {
			console.log( arguments[0]  );
			var oldCon = $("#con");
			if (oldCon[0]) { oldCon.remover();	};
			var divCon = "<div id=\"con\" ><ul id=\"userList\"></ul><div>";
			$("body").append(divCon);
			for ( var i = 0; i < arguments[0].length; i++) {
				var SN = arguments[0][i];
				var userInfo = "<li id=\"" + SN.id + "\"><b>id: " + SN.id + "</b>" + "----<b>userId: " + SN.userId + "</b>" + "----<b>money: " + SN.money
						+ "</b></li>";
				$("#userList").append(userInfo);
			}
		},
		
		// 退出系统
		exit : function() {
			$.post( "/exit.go", function(result) {
					if (result) {
						location.href = "/";
					}
				});
		}
	};

	return {
		init : initUI
	};

};