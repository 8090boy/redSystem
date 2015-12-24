require([ "dojo/dom-construct", "dojo/query", "dojo/ready" ], function(
		domConstruct, query, ready) {

	var user = {};

	var initSubmit = function() {
		var submit = query("#add")[0];
		submit.onclick = function() {
			user.addUser("./user/addUser.go");
		};
	};

	ready(function() {
		initSubmit();
	});

	user.addUser = function() {
		var form = document.forms[0];
		try {
			document.getElementById("userId").value = "";
		} catch (e) {

		}
		form.action = arguments[0];
		form.method = "POST";
		form.submit();
	};

	user.updateUser = function updateUser() {
		var form = document.forms[0];
		form.action = arguments[0];
		form.method = "POST";
		form.submit();
	};

	user.deleteUser = function() {
		sendRequest("Del.go?id=" + arguments[0], "GET", null, processResponse);
		var tr = document.getElementById(arguments[0]);
		tr.parentNode.removeChild(tr);
	};

	user.exchange = function() {
		sendRequest("../sn/exchange.go?moblie=" + arguments[0], "GET", "",
				exchangeProcess);
	};

	function exchangeProcess() {
		if (xhr.readyState == 4) {
			if (xhr.status == 200) {
				var text = xhr.responseText;
				console.info(text);
			}
		}
	}
	;

	function processResponse() {
		if (xhr.readyState == 4) {
			if (xhr.status == 200) {
				var text = xhr.responseText;
				console.info(text);
			}
		}
	}
	;

	function sendRequest(url, getOrPost, data, funcName) {
		xhr = createXhr();
		xhr.open(getOrPost, url, true);
		xhr.onreadystatechange = funcName;
		if (getOrPost == "GET") {
			xhr.send();
			return;
		}
		xhr.send(data);
	}

	function createXhr() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera,
			// Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}

	return user;
});
