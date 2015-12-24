var myAjax = {
	/**
	 * post 提交，返回字符串
	 * 
	 * @param url
	 * @param data
	 *            参数格式 name=abc&pwd=123
	 */
	post : function _myPost(url, data, fn) {
		xhr = _createXhr();
		xhr.open("POST", url, true);
		xhr.onreadystatechange = fn;
		xhr.send(data);
	},

	/**
	 * Get 提交，返回字符串
	 * 
	 * @param url
	 *            可带参数
	 */
	get : function _myGet(url,fn) {
		xhr = _createXhr();
		xhr.open("GET", url, true);
		xhr.onreadystatechange = fn;
		xhr.send();
	},

	// user.exchange = function() {
	// Ajax("../xxx/xxx.do?user=" + arguments[0], "GET", "", fnName );
	// };
	//			
	// function fnName() {
	// if (xhr.readyState == 4) {
	// if (xhr.status == 200) {
	// var text = xhr.responseText;
	// }
	// }
	// }

	/**
	 * 通用ajax调用
	 * 
	 * @param url
	 * @param getOrPost
	 * @param data
	 * @param funcName
	 */
	ajax : function _myAjax(url, getOrPost, data, funcName) {
		xhr = _createXhr();
		xhr.open(getOrPost, url, true);
		xhr.onreadystatechange = funcName;
		if (getOrPost == "GET") {
			xhr.send();
			return;
		}
		xhr.send(data);
	}

};

function _createXhr() {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera,
		// Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}