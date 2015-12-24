 
/**
 * 城市选择
 */
function select() {
	var loca2;
	with (document.creator.province) {
		loca2 = options[selectedIndex].value;
	}
	for (i = 0; i < WHERE.length; i++) {
		if (WHERE[i].loca == loca2) {
			loca3 = (WHERE[i].locacity).split("|");
			for (j = 0; j < loca3.length; j++) {
				with (document.creator.city) {
					length = loca3.length;
					options[j].text = loca3[j];
					options[j].value = loca3[j];
					var loca4 = options[selectedIndex].value;
				}
				document.creator.uCity.value = loca2 + "省" + loca4 + "市";
			}
			break;
		}
	}

}

var WHERE = new Array(35);
WHERE[0] = new comefrom("请选择省份", "请选择城市");
WHERE[1] = new comefrom("北京", "|北京");
WHERE[2] = new comefrom("上海", "|上海");
WHERE[3] = new comefrom("天津", "|天津");
WHERE[4] = new comefrom("重庆", "|重庆");
WHERE[5] = new comefrom("河北", "|石家庄|邯郸|邢台|保定|张家口|承德|廊坊|唐山|秦皇岛|沧州|衡水");
WHERE[6] = new comefrom("山西", "|太原|大同|阳泉|长治|晋城|朔州|吕梁|忻州|晋中|临汾|运城");
WHERE[7] = new comefrom("内蒙古",
		"|呼和浩特|包头|乌海|赤峰|通辽|鄂尔多斯|呼伦贝尔|阿拉善盟|兴安盟|乌兰察布|锡林郭勒盟|巴彦淖尔");
WHERE[8] = new comefrom("辽宁", "|沈阳|大连|鞍山|抚顺|本溪|丹东|锦州|营口|阜新|辽阳|盘锦|铁岭|朝阳|葫芦岛");
WHERE[9] = new comefrom("吉林", "|长春|吉林|四平|辽源|通化|白山|松原|白城|延边");
WHERE[10] = new comefrom("黑龙江",
		"|哈尔滨|齐齐哈尔|牡丹江|佳木斯|大庆|绥化|鹤岗|鸡西|黑河|双鸭山|伊春|七台河|大兴安岭");
WHERE[11] = new comefrom("江苏", "|南京|镇江|苏州|南通|扬州|盐城|徐州|连云港|常州|无锡|宿迁|泰州|淮安");
WHERE[12] = new comefrom("浙江", "|杭州|宁波|温州|嘉兴|湖州|绍兴|金华|衢州|舟山|台州|丽水");
WHERE[13] = new comefrom("安徽",
		"|合肥|芜湖|蚌埠|马鞍山|淮北|铜陵|安庆|黄山|滁州|宿州|池州|淮南|巢湖|阜阳|六安|宣城|亳州");
WHERE[14] = new comefrom("福建", "|福州|厦门|莆田|三明|泉州|漳州|南平|龙岩|宁德");
WHERE[15] = new comefrom("江西", "|南昌市|景德镇|九江|鹰潭|萍乡|新余|赣州|吉安|宜春|抚州|上饶");
WHERE[16] = new comefrom("山东",
		"|济南|青岛|淄博|枣庄|东营|烟台|潍坊|济宁|泰安|威海|日照|莱芜|临沂|德州|聊城|滨州|菏泽");
WHERE[17] = new comefrom("河南",
		"|郑州|开封|洛阳|平顶山|安阳|鹤壁|新乡|焦作|濮阳|许昌|漯河|三门峡|南阳|商丘|信阳|周口|驻马店");
WHERE[18] = new comefrom("湖北", "|武汉|宜昌|荆州|襄樊|黄石|荆门|黄冈|十堰|恩施|随州|咸宁|孝感|鄂州");
WHERE[19] = new comefrom("湖南", "|长沙|常德|株洲|湘潭|衡阳|岳阳|邵阳|益阳|娄底|怀化|郴州|永州|湘西|张家界");
WHERE[20] = new comefrom("广东",
		"|广州|深圳|珠海|汕头|东莞|中山|佛山|韶关|江门|湛江|茂名|肇庆|惠州|梅州|汕尾|河源|阳江|清远|潮州|揭阳|云浮");
WHERE[21] = new comefrom("广西", "|南宁|柳州|桂林|梧州|北海|防城港|钦州|贵港|玉林|贺州|百色|河池|来宾|崇左");
WHERE[22] = new comefrom("海南", "|海口|三亚|三沙");
WHERE[23] = new comefrom("四川",
		"|成都|绵阳|德阳|自贡|攀枝花|广元|内江|乐山|南充|宜宾|广安|达州|遂宁|巴中|资阳|阿坝|雅安|眉山|甘孜|凉山|泸州");
WHERE[24] = new comefrom("贵州", "|贵阳|六盘水|遵义|安顺|铜仁|黔西南|毕节|黔东南|黔南");
WHERE[25] = new comefrom("云南",
		"|昆明|大理|曲靖|玉溪|昭通|楚雄|红河|文山|思茅|西双版纳|保山|德宏|丽江|怒江|迪庆|临沧");
WHERE[26] = new comefrom("西藏", "|拉萨|日喀则|山南|林芝|昌都|阿里|那曲");
WHERE[27] = new comefrom("陕西", "|西安|宝鸡|咸阳|铜川|渭南|延安|榆林|汉中|安康|商洛");
WHERE[28] = new comefrom("甘肃", "|兰州|嘉峪关|金昌|白银|天水|酒泉|张掖|武威|定西|陇南|平凉|庆阳|临夏|甘南");
WHERE[29] = new comefrom("宁夏", "|银川|石嘴山|吴忠|固原|中卫");
WHERE[30] = new comefrom("青海", "|西宁|海东|海南|海北|黄南|玉树|果洛|海西");
WHERE[31] = new comefrom("新疆",
		"|乌鲁木齐|克拉玛依|伊犁|巴音郭勒|昌吉|克孜勒苏柯尔克孜|博尔塔拉|吐鲁番|哈密|喀什|和田|阿克苏|塔城|阿勒泰");
WHERE[32] = new comefrom("香港", "|香港");
WHERE[33] = new comefrom("澳门", "|澳门");
WHERE[34] = new comefrom("台湾", "|台湾");

function comefrom(loca, locacity) {
	this.loca = loca;
	this.locacity = locacity;
}

// initCity()
function initCity() {
	with (document.creator.province) {
		length = WHERE.length;
		for (k = 0; k < WHERE.length; k++) {
			options[k].text = WHERE[k].loca;
			options[k].value = WHERE[k].loca;
		}
		options[selectedIndex].text = WHERE[0].loca;
		options[selectedIndex].value = WHERE[0].loca;
	}
	with (document.creator.city) {
		loca3 = (WHERE[0].locacity).split("|");
		length = loca3.length;
		for (l = 0; l < length; l++) {
			options[l].text = loca3[l];
			options[l].value = loca3[l];
		}
		options[selectedIndex].text = loca3[0];
		options[selectedIndex].value = loca3[0];
	}

}
