var myDC = {

	/**
	 * 创建元素
	 * 
	 * @param tagName
	 * @param params
	 *            {id:"xxxx",name:"userName"}
	 * @param parentNode
	 * @param postion
	 *            first before
	 * @returns {___ele0}
	 */
	create : function _create(tagName, params, parentNode, postion) {
		var ele = document.createElement(tagName);
		if (params != null) {
			ele.id = params.id;
			if (params.className != null) {
				ele.className = params.className;
			}
		}
		if (parentNode != null) {
			parentNode.appendChild(ele);
		}
		return ele;
	},

	byId : function _byId(id) {
		return document.getElementById(id);
	},

	/**
	 * 查询Node
	 * 
	 * @param tagName
	 *            标签名
	 * @param node
	 *            限定ID区域
	 * @returns
	 */
	query : function _query(tagName, node) {
		var tagName = tagName;
		if (node != null) {
			return node.getElementsByTagName(tagName);
		}
		return document.getElementsByTagName(tagName);
	},
	queryByName : function(name) {
		var area = document.getElementById("con").getElementsByTagName("input");
		for ( var i = 0; i < area.length; i++) {
			if (area[i].name == name) {
				return area[i];
			}
		}
		return null;

	}

};
function val(name, reg) {
	var xxxx = myDC.queryByName(name);
	if (xxxx.value != null && xxxx.value != "") {
		if (reg.exec(xxxx.value)) {
			return true;
		}
		return false;
	} else {
		console.error("val Value error for myUtil 68 line !");
	}
}