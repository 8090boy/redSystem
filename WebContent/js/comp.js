var C = {};

$(function() {
	C.RA();//系统状态
	C.RU();// 默认本月，某月月注册会员，及相关城市会员总数//没有城市则是所有会员总数

});

C.MYRU = function() {
	$.getJSON("/recom/myGroup.go", function(result) {
		alert(result);
		//var obj = JSON.parse(result);
	});
};

C.MYCITY = function() {
	$.getJSON("/recom/myCity.go", function(result) {
		alert(result);
		//var obj = JSON.parse(result);
	});
};

C.MYADD = function() {
	var mob = $("#recMob").value;
	if (/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i.exec(mob)) {
		$.get("/recom/Add.go?mob=" + mob, function(result) {
			alert(result);
			var obj = JSON.parse(result);
			if (obj[0] == 1) {
				alert("此号已经推荐！");
			}
			if (obj[0] == 2) {
				alert("被推荐人不存在！");
			}
			if (obj[0] == 9) {
				alert("推荐成功！");
			}
		});
	} else {
		alert("请填写手机号");
	}
};

C.US = function() {
	$.get("/user/S.go", function(result) {
		alert(result);
		var obj = JSON.parse(result);

	});
};

C.RA = function() {
	$.get("/comp/RA.go", function(result) {
		var obj = result;
		$("#snCount").html(obj.snCount);
		$("#snCurrentMonth").html(obj.snCurrentMonth);
		$("#uCount").html(obj.uCount);
		$("#myCity").html(obj.myCity);
		$("#endLayer").html(obj.endLayer);
		$("#userCount").html(obj.userCount);
		$("#shareCount").html(obj.shareCount);
		$("#myRecCount").html(obj.myRecCount);
		$("#userWGCount").html(obj.userWGCount);
	});
};

C.Uas = function() {
	var type = arguments[0];
	var mob = $("#mob").value;

	if (/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i.exec(mob)) {
		xhr.get("/user/as.go?mob=" + mob + "&type=" + type, function(result) {
			if (result == "true") {
				C.RA();
			}
		});
	} else {
		alert("请输入正确的手机号！");
	}
};

C.RU = function() {
	$.get("/comp/RU.go?year=2014&month=8", function(result) {
		alert(result);
		if (result != 0) {

		} else {
			alert("没有数据");
		}
	});
};

C.RS = function() {
	$.get("/comp/RS.go?year=2014&month=8", function(result) {
		alert(result);
		if (result != 0) {
			// var obj = JSON.parse(result);

		} else {
			alert("没有数据");
		}
	});
};
