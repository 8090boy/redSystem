//document.onreadystatechange = subSomething;// 当页面加载状态改变的时候执行这个方法.
function subSomething() {
		//myDOM = new myDOM();
		_initPage(); // 初始化页面
		initCity(); // 初始化城市
		initBindingEvent(); // 绑定添加事件
}

function onTab() {

	var uls = myDC.query("ul", myDC.byId("con"));
	var spans = myDC.query("span", myDC.byId("con"));
	if (this.id == "p") {
		if (!validateForm())
		//	return;
		spans[0].className = "sMenu";
		spans[1].className = "active";
		uls[0].style.display = "none";
		uls[1].style.display = "block";
		this.parentNode.style.display = "none";
		myDC.byId("pp").parentNode.style.display = "block";
	} else {
		spans[0].className = "active";
		spans[1].className = "sMenu";
		uls[0].style.display = "block";
		uls[1].style.display = "none";
		this.parentNode.style.display = "none";
		myDC.byId("p").parentNode.style.display = "block";

	}
}

function initBindingEvent() {

	myDC.byId("mobVal").onclick = valSms; //短信验证码
	myDC.byId("valImg").onclick = valImg; //图片验证
	myDC.byId("p").onclick = onTab; //下一页
	myDC.byId("pp").onclick = onTab; //上一页
	myDC.byId("add").onclick = sendInfo; //添加
}

function valImg() {
	this.src = "http://www.baidu.com/img/bd_logo.png";
	//获取服务器随机验证码
}

function valSms() {
	if (!val("uMob", /^1[3|4|7|5|8]\d{9}$/)) {
		alert("uMob error !");
		return false;
	}
	var mob = myDC.queryByName("uMob").value;
	myAjax.get("/common/mob.go?mob=" + mob, getVal);


	function getVal() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				alert( this.responseText );
			//	return this.responseText;
			}
		}
	}

}

function validateForm() {

	if (!val("uMob", /^1[3|4|7|5|8]\d{9}$/)) {
		alert("手机格式错误！");
		return false;
	}
	if (!val("passWord", /[a-zA-Z0-9]{6,16}/)) {
		alert("密码格式错误！");
		return false;
	}
	if (!val("uSQA", /^[\u4e00-\u9fa5]{2,8}$/i)) {
		alert("自定义词语错误！");
		return false;
	}
	if (!val("uCheckCodeImg", /[0-9]{4,6}/)) {
		alert("校验码格式错误！");
		return false;
	}
	return true;
}

function sendInfo() {
	var form = document.forms[0];
	form.action = "/user/addUser.go";
	form.method = "POST";
	form.submit();
}

/**
 * 加载页面
 */
function _initPage() {
	var mask = myDC.create("div", {
		id : "mask"
	}, document.body, "before");
	mask.style.display = "block";
	var editWrap = myDC.create("div", {
		id : "wrap"
	}, document.body, "first");

	editWrap.innerHTML = '<h4>积分兑换</h4>'
			+ '<div id="con">'
			+ '	<form name="creator">'
			+ '	<span id="yz" class="active" >1、用户验证</span><span class="sMenu"   id="ws">2、完善信息</span>'
			+ '		<input type="hidden" name="uCity" readonly="readonly" />'
			+ '		<ul >'
			+ '			<li id="uMob"><b> 手机号码：</b><b><input type="text" name="uMob" value="13620102021"     /></b><a id="mobVal" href="javascript:;">获取手机验证码</a><b><input type="text" name="uMobCheckCode"  /></b><b></b></li>'
			+ '			<li id="passWord"><b> 登录密码：</b><b><input type="text" name="passWord" value="123456" /></b><b>必填！</b></li>'
			+ '			<li id="passWord"><b> 验证问题：</b><b><input type="text" name="uSQA" value="自定义词语或语句！"      /></b><b>必选！</b></li>'
			+ '			<li id="uCheckCodeImg"><b>验证码：</b><b><input type="text" name="uCheckCodeImg"  value="输入后面的文字"     /></b><b><img id="valImg" src="#" /></b></li>'
			+ '		</ul>'
			+ '		<h5><a id="p" href="#">下一步</a></h5>'
			+ '		<ul   id="ul2">'
			+ '			<li><b>省份：</b><b><select name="province" onchange="select();"></select></b><b>城市：</b><b><select name="city" onchange="select();"></select></b></li>'
			+ '			<li id="userName"><b> 收款人支付宝姓名：</b><b><input type="text" name="userName" value="" /></b><b>必填！</b></li>'
			+ '			<li id="uAccount"><b>收款人支付宝帐号：</b><b><input type="text" name="uAccount" value="" /></b><b>必填！</b></li>'
			+ '			<li id="integral" style="display: none;"><b> 总积分：</b><b><input type="text" name="integral" value="7300" /></b><b>必填！</b></li>'
			+ '		</ul>'
			+ '			<h5 style="display:none" ><a id="pp" href="#">上一步</a><b>&nbsp;&nbsp;&nbsp;&nbsp;</b><a id="add" href="javascript:;">确&nbsp;&nbsp;认</a></h5>'
			+ '	</form>' + '</div>';

	var closeWrap = myDC.create("a", {
		id : "closeWrap"
	}, editWrap);
	closeWrap.innerHTML = "X";
	closeWrap.onclick = function() {
		mask.style.display = "none";
		_closeEditWrap(this);
	};

}

function _closeEditWrap() {
	arguments[0].parentElement.parentElement
			.removeChild(arguments[0].parentElement);
}
