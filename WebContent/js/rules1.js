var RULES = new Object();

RULES["all"] = function() {
	var event = {	 
			_data : function () {
				$.ajax({
					type : "POST",
					async : false,
					url :  "/rules/all.go",
					success : function(msg) {
						if(msg == 0) {
							return;
						}
						event._showAll (msg);
						event._eventBinding();	// 事件绑定
						event._onlyNumber();
					}
				});
			},
			
			_onlyNumber : function(){
				var inputs = $("input[type='number']");
				inputs.keydown(function(evn){
					 
					if(evn.keyCode == 13){
						evn.keyCode = 9;
					}
				});	
				inputs.keypress(function(evn){
				  
					if (evn.keyCode < 48 || evn.keyCode > 57){
						return false;
					}
				});
				
				
				
			},
 			 
			_del : function(){
		         	var iid =	$(this).attr("lang");			
				$.ajax({
					type : "GET",
					url :  "/rules/del.go?id=" + iid,
					success : function(msg) {
						if(msg > 0) {
							// 页面处理
							$("#li"+iid).remove();
							return;
						}
						alert("删除失败！");
					}
				});
			},
			
			
			_showIndex : function (){
				$.ajax({
					type : "GET",
					url :  "/rules/default.go",
					success : function(msg) {
						if(msg > 0) {
							$("#li"+ msg).css({"background":"#ffd"});
							return;
						}
					}
				});
			},
			
			
			_showAll :	function  (){
				if($("#allRules")[0]){
					$("#allRules").remove();
				}
				var rules = arguments[0];
				var lang = RULES.Language;
				var lis = "";
				for ( var i = 0; i < rules.length; i++) {
					lis = lis +  RULES.Conmon.getRulesLi( rules[i] );
				}
				var ul = "<div id=\"allRules\"><ul>" + lis +"</ul></div>";
				$("body").append(ul);
				event._showIndex();	// 当前阶段
				
			},
			
			_subm : function(){ 
				var str_data = $( arguments[0] + " input").map(function() {
					return ($(this).attr("name") + '=' + $(this).val());
				}).get().join("&");
				$.ajax({
					   type: "POST",
					   async : false,
					   url:  "/rules/au.go",
					   data: str_data,
					   success: function(msg){
						  event._data();
							event._showIndex();	// 当前阶段
					   }
					});
				 
			},
	 
			_setIndex : function (iid){
				$.ajax({
					type : "GET",
					url :  "/rules/set.go?id=" + iid,
					success : function(msg) {
						if(msg > 0) {
							$("#li"+iid).siblings().css({"background":"white"});
							$("#li"+iid).css({"background":"#ffd"});
							return;
						}
						alert("删除失败！");
					}
				});
			},
		 
			_eventBinding :	function (){
				$(".updateA").click(function (){
					event._subm( "#li"+ this.lang);
				});
				$(".copyA").click(function (){
					$("#isID"+ this.lang).remove();
					event._subm( "#li"+ this.lang);
				});
				$("li h5 a").click(function (){
					event._setIndex( this.lang);				 
				});
				$("#allRules  .delA").click(event._del);
			}
	};
	
 
 
 


	// 展示所有
	function _init() {
	event._data(); 
	}

	return {
		init : _init
	};
 
};

RULES.Conmon = {
		PROJECTNAME : "rs",
		getRulesLi : function(){
			var rules = arguments[0];
			var lang = RULES.Conmon.Language;
		  
			var	li = "<li id=\"li"+ rules.id +"\"><h5 title=\"本阶段ID："+ rules.id +"\">" +lang.Program  + "[<span >"+ rules.id +"</span>]" +  rules.noOne +"<a lang=\""+  rules.id +"\" title=\""+ lang.setDefault +"\"  href=\"javascript:;\" >s</a></h5>" +
					"<b title=\"phasesNextId\">"  +lang.phasesNextId +"<input type=\"number\" name=\"phasesNextId\" value=\""+ rules.phasesNextId + "\" /></b>" +
					"<b title=\"id\" ><input  id=\"isID" + rules.id + "\"  type=\"hidden\" name=\"id\" value=\""+ rules.id + "\"  /></b>" +
					"<b title=\"minIntegration\" >"  +lang.minIntegration +"<input type=\"number\"   name=\"minIntegration\" value=\""+ rules.minIntegration + "\" /></b>" +
					"<b title=\"noOne\" >"  +lang.noOne +"<input type=\"text\"   name=\"noOne\" value=\""+ rules.noOne + "\" maxlength=\"10\" /></b>" +
						"<b title=\"isSend\">"  + lang.isSend +	"<input type=\"number\" name=\"isSend\" value=\"" + rules.isSend + "\"  maxlength=\"1\"  /></b>" +
						"<b title=\"batch\">"  	+ lang.batch + "<input type=\"number\" name=\"batch\" value=\""	+ rules.batch + "\"   maxlength=\"3\"  /></b>" +
						"<b title=\"timeInterval\">"  + lang.timeInterval +	"<input type=\"number\" name=\"timeInterval\" value=\""+ rules.timeInterval + "\" /></b>" +
						"<b title=\"multiple\">"  	+ lang.multiple + "<input type=\"number\" name=\"multiple\" value=\""+ rules.multiple + "\" maxlength=\"1\"  /></b>" +		
						"<b title=\"revenueLayer\">"  + lang.revenueLayer + "<input type=\"number\" name=\"revenueLayer\" value=\""	+ rules.revenueLayer + "\"   maxlength=\"1\"  /></b>" +
						"<b title=\"amount\">"   	+ lang.amount + "<input type=\"text\" name=\"amount\" value=\"" + rules.amount + "\" /></b>" +
						"<b title=\"fee\">"  	+ lang.fee + "<input type=\"number\" name=\"fee\" value=\""+ rules.fee + "\" /></b>" +
						"<b title=\"phasesLayer\">"  	+ lang.phasesLayer + "<input type=\"number\" name=\"phasesLayer\" value=\""+ rules.phasesLayer + "\" /></b>" +
						"<b title=\"exchangeStarting\">"  + lang.exchangeStarting +"<input type=\"number\" name=\"exchangeStarting\" value=\""	+ rules.exchangeStarting + "\" /></b>" +	
			        	"<b><a lang=\""+ rules.id+"\" class=\"delA\" href=\"javascript:;\">-</a><a lang=\""+ rules.id+"\" class=\"updateA\" href=\"javascript:;\">O</a><a lang=\""+ rules.id+"\" class=\"copyA\" href=\"javascript:;\">C</a></b></li>"  ;
			 
			return li;
		},
		Language :   {
			Program : "阶段：",
			setDefault : "从此方案开始",
			addTitle : "添加系统配置方案",
			addSub : "添加",
			minIntegration : "最低起兑积分",
			layerOrder : "本层排序",
			isSend : "短信通知",
			batch : "每批付款数",
			timeInterval : "分配序号间隔",
			revenueLayer : "收益层",
			initialPayment : "发放起始层",
			lastSerialNumberId : "上次注册序号",
			lastWinnerId : "上次序号所属ID",
			amount : "每层收益金额",
			lastLayer : "上次序号所在层",
			fee : "发放扣费",
			multiple : "下代倍数",
			exchangeStarting : "最低兑换金额",
			noOne : "阶段名",
			phasesLayer : "本阶段限定层",
			phasesNextId : "下阶段ID"
		}
		
};
 
