require(
		[ "dojo/dom", "dojo/dom-construct", "dojo/query", "dojo/on",
				"dojo/html", "dojo/_base/xhr", "dojo/ready",
				"dojo/_base/window", "dojo/text!extend/editUser.htm","dojo/request" ],
		function(dom, domConstruct, query, on, html, xhr, ready, win, edit , request) {
			u = {};

			ready(function() {
				pageStaticCon();
			});

			function pageStaticCon() {
				var menu = domConstruct.create("div", {
					id : "menu"
				}, win.body(), "last");
				domConstruct.create("a", {
					href : ".",
					innerHTML : "Home"
				}, menu);
				var allUser = domConstruct.create("a", {
					href : "javascript:void(0)",
					innerHTML : "All user"
				}, menu);
				allUser.onclick = u.initUser;
				domConstruct.create("a", {
					href : "addUser.html",
					innerHTML : "Add user"
				}, menu);
				domConstruct.create("a", {
					href : "sn/SNAll.go",
					innerHTML : "All SN"
				}, menu);
				var isExSN = domConstruct.create("a", {
					innerHTML : "All is ex.SN"
				}, menu);
				isExSN.onclick = u.AllIsExSN;
				var ExSN = domConstruct.create("a", {
					innerHTML : "ExSN"
				}, menu);
				ExSN.href = "alipay/EXALL.go";
				//EX_ALL
				//u.initUser();

			}
		 
			
			u.AllIsExSN = function _AllIsExSN(){
				xhr.get({
					url : "sn/EX_ALL.go",
					load : function(result) {
						if (result != 0 && result != "o" ) {
							var obj = JSON.parse(result);
							if (obj.length > 0) {
								showIsExSN(obj);
							}
						} else {
							alert("没有数据");
							// location.href = "index.html";
						}
					}
				});
				
			};
			
			function showIsExSN() {
				var oldCon = dom.byId("con2");
				if( oldCon != null ){
					oldCon.parentElement.removeChild(oldCon);
				}
				var divCon = domConstruct.create("div", {
					id : "con2"
				}, win.body());
				var aSubmit = domConstruct.create("a", {
					id : "submit",
					innerHTML:"全部兑出",
					href:"index.jsp"
				}, divCon );
				var ul = domConstruct.create("ul", {
					id : "isExSNList"
				}, divCon);
				

				for ( var i = 0; i < arguments[0].length; i++) {
					var SN = arguments[0][i];
					var li = domConstruct.create("li", {
						id : SN.id
					}, ul);
					var userInfo = "<b>id: " + SN.id + "</b>" +
					"----<b>userId: "	+ SN.userId + "</b>"+
					"----<b>money: "	+ SN.money + "</b>";
					li.innerHTML = userInfo;
				}
			}
			
			u.updateUser = function _updateUser(){
				 //	document.creator.id.value = u.info.id;
				u.info.id = document.creator.id.value;
				u.info.uAddress = document.creator.uAddress.value;
				u.info.userName = document.creator.userName.value;
				u.info.uIdCard = document.creator.uIdCard.value;
				u.info.uBank = document.creator.uBank.value;
				u.info.uAccount = document.creator.uAccount.value;
 
				request.post("user/updateUser.go", {
					data : u.info,
					timeout : 2000
				}).then(function(response) {
					var editWrap = document.getElementById("editWrap");
					editWrap.parentElement.removeChild(editWrap);
					var mask = document.getElementById("mask");
					mask.parentElement.removeChild(mask);
					
				});
			
				
			};

			u.edit = function _edit() {
				var mask = domConstruct.create("div", {
					id : "mask"
				}, win.body(), "first");
				mask.style.display = "block";
				var editWrap = domConstruct.create("div", {
					id : "editWrap"
				}, mask, "before");

				editWrap.innerHTML = html._secureForInnerHtml(edit);
				var closeWrap = domConstruct.create("a", {
					id : "closeWrap"
				}, editWrap);
				closeWrap.innerHTML = "X";
				closeWrap.onclick = function() {
					mask.style.display = "none";
					_closeEditWrap(this);
				};
				initCity();
				myInfo(arguments[0]);

			};

			function myInfo() {
				var id = arguments[0];
				xhr.get({
					url : "user/my.go?id=" + id,
					load : function(result) {
						var obj = JSON.parse(result);
						if (obj.length > 0) {
							u.info = obj[0];
							document.creator.id.value = u.info.id;
							document.creator.uAddress.value = u.info.uAddress;
							document.creator.userName.value = u.info.userName;
							document.creator.uIdCard.value = u.info.uIdCard;
							document.creator.uBank.value = u.info.uBank;
							document.creator.uAccount.value = u.info.uAccount;
						} else {
							location.href = "index.html";
						}
					}
				});
			}
			
			
			u.exchange = function _exchange() {
				var urlA = "sn/exchange.go?moblie=" + arguments[0] ;
				xhr.get({
					url : urlA,
					load : function(result) {
						 switch (result) {
						case 1:
							alert("您的信息不完整 !" );
							break;
						case 2:
							alert("未满兑换金额最低起点 !" );
							break;
						case 3:
							alert("兑换中，请稍候..." );
							break;

						default:
							alert("Illegal operation !" );
							break;
						}
						
						 
					}
				});
				 
			};
 

			u.initUser = function _initUser() {
				xhr.get({
					url : "user/all.go",
					load : function(result) {
						var obj = JSON.parse(result);
						if (obj.length > 0) {
							showUsers(obj);
						} else {
							alert("没有数据");
							// location.href = "index.html";
						}
					}
				});
			};

			function showUsers() {
				var oldCon = dom.byId("con");
				if( oldCon != null ){
					oldCon.parentElement.removeChild(oldCon);
				}
				var divCon = domConstruct.create("div", {
					id : "con"
				}, win.body());
				var ul = domConstruct.create("ul", {
					id : "userList"
				}, divCon);

				for ( var i = 0; i < arguments[0].length; i++) {
					var user = arguments[0][i];
					var li = domConstruct.create("li", {
						id : user.id
					}, ul);
					var userInfo = "<b>"	+ user.id	+ "<br/>" + user.userName + "—" + user.uMob	+ "—" + user.snsNo	+ "—" + user.integral	 + "—" + user.uMail	+ "</b>"
							+ "<b><a href='javascript:void(0)'  title='Set shareholders' ondblclick='u.setGD(\""+ user.id	+ "\")' >"+ user.utype	+ "</a></b>"
							+ "<b>" + user.sns	+ "</b>"
							+ "<b>" + user.created + "</b>"
							+ "<b><a href='javascript:void(0)' ondblclick='u.edit(\""+ user.id + "\")'>Edit</a></b>"
							+ "<b><a href='javascript:void(0)' ondblclick='u.deletea(\""+ user.id + "\")'>Delete</a></b>";
					li.innerHTML = userInfo;
				}
			}

			function _closeEditWrap() {
				arguments[0].parentElement.parentElement
						.removeChild(arguments[0].parentElement);
			}
			
 

			u.setGD = function _setGD() {
				var li = dom.byId(  arguments[0] );
				var a = li.querySelector("a");
				xhr.get({
					url : "user/setSh.go?id=" + arguments[0],
					load : function(result) {
						if(result > 1){
							alert( "操作错误!");
							return;
						}else if(result > 0){
							li.style.backgroundColor="#ffaaaa";
						}
						a.innerHTML = result;
					 
					}
				});
			};

			u.deletea = function _deletea() {
				var tr = document.getElementById(arguments[0]);
				xhr.get({
					url : "user/Del.go?id=" + arguments[0],
					load : function(result) {
						var obj = JSON.parse(result);
						if (obj.result == "success") {
							
							tr.parentNode.removeChild(tr);
						}
					}
				});

			};

			// defer
			var where = new Array(28);
			where[0] = new comefrom("请选择省份名", "请选择城市名");
			where[1] = new comefrom("直辖市或特区","|北京|上海|天津|重庆|香港|澳门|台湾");
			where[2] = new comefrom("河北","|石家庄|邯郸|邢台|保定|张家口|承德|廊坊|唐山|秦皇岛|沧州|衡水");
			where[3] = new comefrom("山西", "|太原|大同|阳泉|长治|晋城|朔州|吕梁|忻州|晋中|临汾|运城");
			where[4] = new comefrom("内蒙古","|呼和浩特|包头|乌海|赤峰|通辽|鄂尔多斯|呼伦贝尔|阿拉善盟|兴安盟|乌兰察布|锡林郭勒盟|巴彦淖尔");
			where[5] = new comefrom("辽宁","|沈阳|大连|鞍山|抚顺|本溪|丹东|锦州|营口|阜新|辽阳|盘锦|铁岭|朝阳|葫芦岛");
			where[6] = new comefrom("吉林", "|长春|吉林|四平|辽源|通化|白山|松原|白城|延边");
			where[7] = new comefrom("黑龙江","|哈尔滨|齐齐哈尔|牡丹江|佳木斯|大庆|绥化|鹤岗|鸡西|黑河|双鸭山|伊春|七台河|大兴安岭");
			where[8] = new comefrom("江苏","|南京|镇江|苏州|南通|扬州|盐城|徐州|连云港|常州|无锡|宿迁|泰州|淮安");
			where[9] = new comefrom("浙江", "|杭州|宁波|温州|嘉兴|湖州|绍兴|金华|衢州|舟山|台州|丽水");
			where[10] = new comefrom("安徽","|合肥|芜湖|蚌埠|马鞍山|淮北|铜陵|安庆|黄山|滁州|宿州|池州|淮南|巢湖|阜阳|六安|宣城|亳州");
			where[11] = new comefrom("福建", "|福州|厦门|莆田|三明|泉州|漳州|南平|龙岩|宁德");
			where[12] = new comefrom("江西","|南昌市|景德镇|九江|鹰潭|萍乡|新余|赣州|吉安|宜春|抚州|上饶");
			where[13] = new comefrom("山东","|济南|青岛|淄博|枣庄|东营|烟台|潍坊|济宁|泰安|威海|日照|莱芜|临沂|德州|聊城|滨州|菏泽");
			where[14] = new comefrom("河南","|郑州|开封|洛阳|平顶山|安阳|鹤壁|新乡|焦作|濮阳|许昌|漯河|三门峡|南阳|商丘|信阳|周口|驻马店|济源");
			where[15] = new comefrom("湖北","|武汉|宜昌|荆州|洪湖|襄樊|黄石|荆门|黄冈|十堰|恩施|潜江|天门|仙桃|随州|咸宁|孝感|鄂州");
			where[16] = new comefrom("湖南","|长沙|常德|株洲|湘潭|衡阳|岳阳|邵阳|益阳|娄底|怀化|郴州|永州|湘西|张家界");
			where[17] = new comefrom("广东","|广州|深圳|珠海|汕头|东莞|中山|佛山|韶关|江门|湛江|茂名|肇庆|惠州|梅州|汕尾|河源|阳江|清远|潮州|揭阳|云浮");
			where[18] = new comefrom("广西","|南宁|柳州|桂林|梧州|北海|防城港|钦州|贵港|玉林|南宁地区|柳州地区|贺州|百色|河池");
			where[19] = new comefrom("海南", "|海口|三亚|三沙");
			where[20] = new comefrom("四川","|成都|绵阳|德阳|自贡|攀枝花|广元|内江|乐山|南充|宜宾|广安|达川|雅安|眉山|甘孜|凉山|泸州");
			where[21] = new comefrom("贵州", "|贵阳|六盘水|遵义|安顺|铜仁|黔西南|毕节|黔东南|黔南");
			where[22] = new comefrom("云南","|昆明|大理|曲靖|玉溪|昭通|楚雄|红河|文山|思茅|西双版纳|保山|德宏|丽江|怒江|迪庆|临沧");
			where[23] = new comefrom("西藏", "|拉萨|日喀则|山南|林芝|昌都|阿里|那曲");
			where[24] = new comefrom("陕西", "|西安|宝鸡|咸阳|铜川|渭南|延安|榆林|汉中|安康|商洛");
			where[25] = new comefrom("甘肃","|兰州|嘉峪关|金昌|白银|天水|酒泉|张掖|武威|定西|陇南|平凉|庆阳|临夏|甘南");
			where[26] = new comefrom("宁夏", "|银川|石嘴山|吴忠|固原");
			where[27] = new comefrom("青海", "|西宁|海东|海南|海北|黄南|玉树|果洛|海西");
			where[28] = new comefrom("新疆","|乌鲁木齐|石河子|克拉玛依|伊犁|巴音郭勒|昌吉|克孜勒苏柯尔克孜|博尔塔拉|吐鲁番|哈密|喀什|和田|阿克苏");

			function comefrom(loca, locacity) {
				this.loca = loca;
				this.locacity = locacity;
			}

			// select()
			u.select = function selectA() {
				var loca2;
				with (document.creator.province) {
					loca2 = options[selectedIndex].value;
				}
				for (i = 0; i < where.length; i++) {
					if (where[i].loca == loca2) {
						loca3 = (where[i].locacity).split("|");
						for (j = 0; j < loca3.length; j++) {
							with (document.creator.city) {
								length = loca3.length;
								options[j].text = loca3[j];
								options[j].value = loca3[j];
								var loca4 = options[selectedIndex].value;
							}
							document.creator.uAddress.value = loca2 + "省"
									+ loca4 + "市";
						}
						break;
					}
				}

			};

			// initCity()
			function initCity() {
				with (document.creator.province) {
					length = where.length;
					for (k = 0; k < where.length; k++) {
						options[k].text = where[k].loca;
						options[k].value = where[k].loca;
					}
					options[selectedIndex].text = where[0].loca;
					options[selectedIndex].value = where[0].loca;
				}
				with (document.creator.city) {
					loca3 = (where[0].locacity).split("|");
					length = loca3.length;
					for (l = 0; l < length; l++) {
						options[l].text = loca3[l];
						options[l].value = loca3[l];
					}
					options[selectedIndex].text = loca3[0];
					options[selectedIndex].value = loca3[0];
				}

			}

		});