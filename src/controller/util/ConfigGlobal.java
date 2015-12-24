package controller.util;

import controller.entity.SerialNumber;
import controller.entity.SysRules;
import controller.service.IServiceSerialNumber;
import controller.service.IServiceSysRules;
import controller.service.IServiceUser;

public class ConfigGlobal {

	private static ConfigGlobal uniqueInstance = null;
	private static SysRules rules;
	private static String MANAGERMOB = "18620131415";

	private IServiceSysRules serviceSysRules;
	private IServiceSerialNumber serviceSerialNumber;
	private IServiceUser serviceUser;

	public void setServiceSysRules(IServiceSysRules serviceSysRules) {
		this.serviceSysRules = serviceSysRules;
	}

	public void setServiceSerialNumber(IServiceSerialNumber serviceSerialNumber) {
		this.serviceSerialNumber = serviceSerialNumber;
	}

	public void setServiceUser(IServiceUser serviceUser) {
		this.serviceUser = serviceUser;
	}

	private ConfigGlobal() {

	}

	public static ConfigGlobal getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ConfigGlobal();
		}
		return uniqueInstance;
	}

	/**
	 * 获取内存的rules对象
	 * 
	 * @return
	 */
	public static SysRules getRules() {
		return rules;
	}

	public static void setRules(SysRules SR) {
		rules = SR;
	}

	/**
	 * 是否下阶段
	 */
	public static Boolean isNextPhases() {
		int phasesCount = rules.getPhasesCount();
		int phasesCurrentCount = rules.getPhasesCurrentCount();
		if (phasesCurrentCount <= phasesCount) {
			return false;
		}
		System.out.println("下阶段");
		return true;
	}

	/**
	 * 系统启动时设置全局变量到应用范围
	 * 
	 * @param request
	 * @return
	 */
	public void getRulesForDB() {

		if (rules == null) {
			// 清空系统配置添加默认系统配置
			serviceSysRules.delAll();
			serviceSysRules.getSysRulesSingle();
			rules = serviceSysRules.getIsOK();
		}
		SerialNumber lastSn = serviceSerialNumber.getById(rules.getLastSerialNumberId());
		if (lastSn == null) { // 上次序列号不存在
			ConfigGlobal.defaultSysRules(); // 预置系统配置数据
		}

		if (serviceUser.getForMob(MANAGERMOB) == null) {
			serviceSerialNumber.delAll();
			serviceUser.delAll();
			serviceUser.getUserDefault();
			ConfigGlobal.defaultSysRules(); // 预置系统配置数据
		}
	}

	public int getLastLayer() {
		int lastLayer = rules.getLastLayer();// 获得当前层
		// 当前层总数 - 已排列顺序号，满了就加+1
		// 是否添加层号
		// 取得当前层剩余人数
		if (getPeopleCoutForLayer(lastLayer) == rules.getLayerOrder()) {
			lastLayer = lastLayer + 1;
			rules.setLastLayer(lastLayer);
			rules.setLayerOrder(0);// 当前层的排序默认是0，实际计数为1开始
		}
		return lastLayer;
	}

	/**
	 * 获取当前已满层排序
	 * 
	 * @return
	 */
	public int getLayerOrder() {
		int layerOrder = rules.getLayerOrder() + 1;// 当前层已经满的状态
		rules.setLayerOrder(layerOrder);
		return layerOrder;
	}

	public int setTheTotalNumberOfRevenue() {
		int sycs = 0;
		int RevenueLayer = rules.getRevenueLayer();// 收益层数
		for (int i = 1; i <= RevenueLayer; i++) {
			sycs += getPeopleCoutForLayer(i);
		}
		return sycs;
	}

	/**
	 * 获取本层总序列号数量
	 * 
	 * @param signle
	 * @return
	 */
	public int getPeopleCoutForLayer(int layer) {
		long l = Math.round(Math.pow(rules.getMultiple(), layer));
		return (int) l;
	}

	/**
	 * 预置部分系统配置数据
	 */
	public static void defaultSysRules() {
		rules.setLastLayer(0);
		rules.setLayerOrder(0);
		rules.setLastSerialNumberId(rules.getNoOne());
		rules.setPhasesUseCount(UtilDate.getOrderNum());
		rules.setPhasesCount(0);
		rules.setPhasesCurrentCount(0);
	}

	/**
	 * 获得分隔符
	 * 
	 * @return
	 */
	public static String getInterval() {
		return "\\|";
	}

	public void setLastSerialNumberId(String Id) {
		rules.setLastSerialNumberId(Id);
	}

}
