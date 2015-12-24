package controller.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "go_sysRules")
public class SysRules {

	/**
	 * 配置id
	 */
	@Id
	@GeneratedValue
	private int id;

	private int isOK = 0;
	/**
	 * 最低起兑积分 800
	 */
	private int minIntegration = 1;
	/**
	 * 最后注册序列号本层排序
	 */
	private int layerOrder = 0;
	/**
	 * 是否发短信
	 */
	private int isSend = 0;
	/**
	 * 每批付款总个数
	 */
	private int batch = 50;
	/**
	 * 分配序列号间隔时间单位分钟
	 */
	private int timeInterval = 0;
	/**
	 * 全局收益层
	 */
	private int revenueLayer = 0;
	/**
	 * 发放起始层
	 */
	private int initialPayment = 0;
	/**
	 * 上次注册序列号
	 */
	private String lastSerialNumberId = "gomallla";
	/**
	 * 上次注册序列号的所属会员ID
	 */
	private String lastWinnerId = "gomallla";
	/**
	 * 每层对应的收益金额 16|25|25|50
	 */
	private String amount = "1|1";
	/**
	 * 上次创建序列号所在层
	 */
	private int lastLayer = 0;
	/**
	 * 发放手续费
	 */
	private int fee = 1;
	/**
	 * 下代倍数
	 */
	private int multiple = 2;
	/**
	 * 兑换金额起点（元）
	 */
	private int exchangeStarting = 1;

	/**
	 * 头名，本阶段的头名
	 */
	private String NoOne = null;
	/**
	 * 本阶段发展多少代，不能低于 revenueLayer
	 */
	private int phasesLayer = 2;
	/**
	 * 下阶段ID，0代表是自己，非0则将对应ID设为默认值
	 */
	private int phasesNextId = 0;
	/**
	 * 本阶段总数，设置参数时自动求值
	 */
	private int phasesCount = 0;
	/**
	 * 本阶段当前总数
	 */
	private int phasesCurrentCount = 0;
	/**
	 * 本阶段使用次数
	 */
	private String phasesUseCount = "";

	/**
	 * 在第几个序列号时，扣除收益
	 */
	private int snContribute = 0;
	/**
	 * 本阶段最后层的序列号收益金额
	 */
	private int lastRevenue = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIsOK() {
		return isOK;
	}

	public void setIsOK(int isOK) {
		this.isOK = isOK;
	}

	public int getMinIntegration() {
		return minIntegration;
	}

	public void setMinIntegration(int minIntegration) {
		this.minIntegration = minIntegration;
	}

	public int getLayerOrder() {
		return layerOrder;
	}

	public void setLayerOrder(int layerOrder) {
		this.layerOrder = layerOrder;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	public int getRevenueLayer() {
		return revenueLayer;
	}

	public void setRevenueLayer(int revenueLayer) {
		this.revenueLayer = revenueLayer;
	}

	public int getInitialPayment() {
		return initialPayment;
	}

	public void setInitialPayment(int initialPayment) {
		this.initialPayment = initialPayment;
	}

	public String getLastSerialNumberId() {
		return lastSerialNumberId;
	}

	public void setLastSerialNumberId(String lastSerialNumberId) {
		this.lastSerialNumberId = lastSerialNumberId;
	}

	public String getLastWinnerId() {
		return lastWinnerId;
	}

	public void setLastWinnerId(String lastWinnerId) {
		this.lastWinnerId = lastWinnerId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public int getLastLayer() {
		return lastLayer;
	}

	public void setLastLayer(int lastLayer) {
		this.lastLayer = lastLayer;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getExchangeStarting() {
		return exchangeStarting;
	}

	public void setExchangeStarting(int exchangeStarting) {
		this.exchangeStarting = exchangeStarting;
	}

	public String getNoOne() {
		return NoOne;
	}

	public void setNoOne(String noOne) {
		NoOne = noOne;
	}

	public int getPhasesLayer() {
		return phasesLayer;
	}

	public void setPhasesLayer(int phasesLayer) {
		this.phasesLayer = phasesLayer;
	}

	public int getPhasesNextId() {
		return phasesNextId;
	}

	public void setPhasesNextId(int phasesNextId) {
		this.phasesNextId = phasesNextId;
	}

	public int getPhasesCount() {
		return phasesCount;
	}

	public void setPhasesCount(int phasesCount) {
		this.phasesCount = phasesCount;
	}

	public int getPhasesCurrentCount() {
		return phasesCurrentCount;
	}

	public void setPhasesCurrentCount(int phasesCurrentCount) {
		this.phasesCurrentCount = phasesCurrentCount;
	}

	public String getPhasesUseCount() {
		return phasesUseCount;
	}

	public void setPhasesUseCount(String phasesUseCount) {
		this.phasesUseCount = phasesUseCount;
	}

	public int getSnContribute() {
		return snContribute;
	}

	public void setSnContribute(int snContribute) {
		this.snContribute = snContribute;
	}

	public int getLastRevenue() {
		return lastRevenue;
	}

	public void setLastRevenue(int lastRevenue) {
		this.lastRevenue = lastRevenue;
	}

}
