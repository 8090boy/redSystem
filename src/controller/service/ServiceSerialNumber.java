package controller.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import controller.dao.IDAOSerialNumber;
import controller.dao.IDAOSysRules;
import controller.dao.IDAOUser;
import controller.entity.SerialNumber;
import controller.entity.User;
import controller.util.ConfigGlobal;
import controller.util.UtilVerify;
import controller.util.SendSms;
import controller.util.UtilDate;

public class ServiceSerialNumber implements IServiceSerialNumber {

	private IDAOSerialNumber daoSerialNumber;
	private IDAOUser daoUser;
	private IDAOSysRules daoSysRules;
	

 
	public void setDaoSerialNumber(IDAOSerialNumber daoSerialNumber) {
		this.daoSerialNumber = daoSerialNumber;
	}

 
	public void setDaoUser(IDAOUser daoUser) {
		this.daoUser = daoUser;
	}
	
	

	public void setDaoSysRules(IDAOSysRules daoSysRules) {
		this.daoSysRules = daoSysRules;
	}


	/**
	 * 添加序列号
	 */
	@Override
	public SerialNumber AddSN(HttpServletRequest request, User user) {
		SerialNumber sn = new SerialNumber();
		sn.setUserId(user.getId());
		sn.setTotalPoints(ConfigGlobal.getRules().getMinIntegration() + "");
		sn.setCreated(UtilDate.getSNCreated());
		sn.setLayer(ConfigGlobal.getInstance().getLastLayer());
		sn.setLayerOrder(ConfigGlobal.getInstance().getLayerOrder());
		sn.setMoney(0);// 需要lengetshop user设置
		sn.setNumberRed(0);
		sn.setParentId(getParentId());
		sn.setStatus(0); // 0普通会员、1股东
		sn.setPhases(ConfigGlobal.getRules().getPhasesUseCount());
		SerialNumber SnAbc = add(sn);
		ConfigGlobal.getRules().setLastSerialNumberId(SnAbc.getId());
		//设置收益
		setRevenue(request, SnAbc);
		//是否下阶段
		setNextPhases(SnAbc);
		return SnAbc;
	}
	
	/**
	 * 根据序列号判断是否跳转到下阶段
	 * @param SN
	 */
	public void setNextPhases(SerialNumber SN  ){
		//是最大层
		int snLayer = SN.getLayer();
		int phasesLayer = ConfigGlobal.getRules().getPhasesLayer();
		if(snLayer <  phasesLayer ){
			return;
		}
		
		//是最后排序
		int phasesMaxSort = ConfigGlobal.getInstance().getPeopleCoutForLayer( phasesLayer );
		if(SN.getLayerOrder() <  phasesMaxSort  ){
			return;
		}
	  
		//设置系统参数
		int nextPhasesID = ConfigGlobal.getRules().getPhasesNextId();
		int currentPhasesID = ConfigGlobal.getRules().getId();
		if (nextPhasesID != currentPhasesID ) {
			daoSysRules.setIsOK(nextPhasesID);
			ConfigGlobal.setRules( daoSysRules.getIsOK());
			ConfigGlobal.defaultSysRules(); 
		}else{
			ConfigGlobal.defaultSysRules(); 
		}
		System.out.println("成功转到下阶段了");
	}
	
 
	
 
/**
 * 设置收益
 */
	@Override
	public void setRevenue(HttpServletRequest request, SerialNumber SNId) {
		int RevenueLayer = ConfigGlobal.getRules().getRevenueLayer();
		//String parentId = RedEnvelopesToTheHigher(request, SNId, 0);
	//	if (parentId != null) {
		//给父级分配红包
			for (int i = 0; i < RevenueLayer; i++) { // 限制收益层
				RedEnvelopesToTheHigher(request, SNId, i);
			}
			mySelfRevenue(SNId);
	//	}
	}

	/**
	 * 如果是本阶段最后一层的序列号，就添加固定金额红包
	 * 
	 * @param SNId
	 */
	private void mySelfRevenue(SerialNumber SNId) {
		// 是否为本阶段最后一层
		int PhasesLayer = ConfigGlobal.getRules().getPhasesLayer();
		int currentLayer = SNId.getLayer();
		if (currentLayer == PhasesLayer) {
			SNId.setNumberRed(SNId.getNumberRed() + 1);
			SNId.setMoney(  ConfigGlobal.getRules().getSnContribute()  );
			updateNumberRedAndMoney(SNId);
		}
	}	
 

	/**
	 * 给自己父级分配红包，并发送短信通知
	 * 
	 * @param SNId
	 *            自己的ID
	 * @param classNo
	 *            几层对应的红包金额
	 * @return 返回父级SerialNumber ID；null则不存在父级
	 */
	@Override
	public String RedEnvelopesToTheHigher(HttpServletRequest request, SerialNumber SNId, int classNo) {
		SerialNumber snA = SNId;
		SerialNumber snParent = getById(snA.getParentId());
		if (snParent != null) { // 父亲存在
			if (snParent.getStatus() == 0) { // 父级序列号可用
				
				snParent.setNumberRed(snParent.getNumberRed() + 1);
				int jine = snParent.getMoney() + getAmount( classNo);
				snParent.setMoney(jine);
				updateNumberRedAndMoney(snParent);
				//短信通知
				EarningsSMS(request, snParent, jine);
				return snParent.getId();
				
			} else {
				return snParent.getId();
			}
		} 
		return null;
	}

	/**
	 * 收益红包时短信提示
	 * 
	 * @param sn
	 *            谁收益红包
	 * @param jine
	 *            收益多少钱
	 */
	@Override
	public void EarningsSMS(HttpServletRequest request, SerialNumber sn, int jine) {
		String userId = sn.getUserId();
		User user = daoUser.getForId(userId);
		if (user != null) {
			if (UtilVerify.isMobileNO(user.getuMob())) {
				if (jine > 0) { // 金额为零时，不发短信
					if (ConfigGlobal.getRules().getIsSend() > 0) {
						SendSms.smsTest(user.getuMob(), jine); // 短信通知收益人
					}
				}
			}
		} else {
			System.out.println(" 序列号对应会员不存在，或信息不复合要求，短信发送失败！");
		}
	}

	/**
	 * 获取层对应的金额
	 * @param request
	 * @param index
	 * @return
	 */
	private int getAmount( int index) {
		int RevenueLayer = ConfigGlobal.getRules().getRevenueLayer();
		if (index < RevenueLayer) {
			String Amount = ConfigGlobal.getRules().getAmount();
			String[] ary = Amount.split(ConfigGlobal.getInterval());
			String abc = ary[index];
			return Integer.parseInt(abc);
		}
		return 0;
	}


 

/**
 * 根据最后序列号找父级
 */
	@Override
	public String getParentId( ) {
		String NoOne = ConfigGlobal.getRules().getNoOne() + "===" + ConfigGlobal.getRules().getPhasesUseCount();
		String LastSerialNumberId = ConfigGlobal.getRules().getLastSerialNumberId();
		if (LastSerialNumberId.getBytes().length < 20) { // 不是系统产生的ParentId
			return NoOne; // 设置头名，本阶段的头名
		}
		SerialNumber lastSn = getById(LastSerialNumberId);
		
		if (lastSn == null) { // 上次序列号不存在
			ConfigGlobal.defaultSysRules(); // 预置系统配置数据
			return NoOne; // 设置头名，本阶段的头名
		} 
		
		
		//上次序列号存在
			String NoOneA = ConfigGlobal.getRules().getNoOne();
			if (lastSn.getParentId() == NoOneA || lastSn.getLayer() < 1) {
				return lastSn.getId();
			}  
			//此号不是顶层，可能第1层
			SerialNumber lastSnPar = null;
			lastSnPar = getByLayerAndId( lastSn.getLayer() - 1 ,  lastSn.getParentId()  );
			if(lastSnPar == null  ){
			//  System.out.println("没有父级");
				//返回自己
				return lastSn.getId();
			}
			//有父级
			//是否边界
		if(	isBianjie( lastSn )){ //是边界就要找父级大哥
			SerialNumber lastSnBorthers = getByLayerAndOrder( lastSn.getLayer()  , 1 , ConfigGlobal.getRules().getPhasesUseCount() );
			return lastSnBorthers.getId();
		}
		List<SerialNumber> ls = null;
		ls = getSonByParentId(lastSn.getLayer(), lastSnPar.getId() );
		
		//父级的儿子没有满就返回父级
			if ( ls == null ||  ls.size() < ConfigGlobal.getRules().getMultiple() ) {
			//	System.out.println("父级儿子未满倍数");
				return lastSn.getParentId();
			}
 	 
			//父级儿子满了找父级兄弟
			SerialNumber parentBrothers = getByLayerAndOrder( lastSnPar.getLayer() , lastSnPar.getLayerOrder() + 1, ConfigGlobal.getRules().getPhasesUseCount() );
			//父级没有兄弟
			if(parentBrothers == null  ){
				SerialNumber bigBrother = getByLayerAndOrder( lastSnPar.getLayer(), 1, ConfigGlobal.getRules().getPhasesUseCount() );// 上次序列号的大哥
				return bigBrother.getId();
			}else{
				return parentBrothers.getId();
			}
		  
	}
	
	/**
	 * 是否为边界序列号
	 * @param sn
	 * @return
	 */
	public Boolean isBianjie(SerialNumber sn){
		int bczs = ConfigGlobal.getInstance().getPeopleCoutForLayer(sn.getLayer());
		if(sn.getLayerOrder() <  bczs ){
		//	System.out.println(sn.getId() );
			return false;
		}
		return true;
	}

	@Override
	public SerialNumber add(SerialNumber entity) {
		return daoSerialNumber.add(entity);
	}

	@Override
	public boolean delAll() {
		return daoSerialNumber.delAll();
	}

	@Override
	public List<SerialNumber> getAll() {
		return daoSerialNumber.getAll();
	}

	@Override
	public boolean del(String id) {

		return daoSerialNumber.del(id);
	}

	@Override
	public SerialNumber getById(String id) {
		return daoSerialNumber.getById(id);
	}

	@Override
	public boolean update(SerialNumber entity) {
		return daoSerialNumber.update(entity);
	}

	/**
	 * 更新红包和金额
	 */
	@Override
	public boolean updateNumberRedAndMoney(SerialNumber entity) {

		return daoSerialNumber.updateNumberRedAndMoney(entity);
	}

	@Override
	public SerialNumber getByLayerAndOrder(int layer, int order, String phases) {
		return daoSerialNumber.getByLayerAndOrder(layer, order, phases);
	}

	/**
	 * 在哪个层找，父级ID为这个
	 */
	@Override
	public List<SerialNumber> getSonByParentId(int layer, String ParentId) {
		return daoSerialNumber.getByParentId(layer, ParentId);
	}

	@Override
	public SerialNumber getByLayerAndId(int layer, String id) {
		return daoSerialNumber.getByLayerAndId(layer, id);
	}

	@Override
	public List<SerialNumber> getByLayerIsExchange(int InitialPayment, int ExchangeStarting, int Batch, String phases ) {		
		List<SerialNumber> snAbc = daoSerialNumber.getByLayer(InitialPayment, ExchangeStarting, Batch, phases );
		return snAbc;
	}

	@Override
	public int getByLayerIsFail(int initialPayment) {
		return daoSerialNumber.getByLayerIsFail(initialPayment);
	}

	/**
	 * 获取某年某月产生的序列号总数
	 * 
	 * @param year
	 *            2015
	 * @param month
	 *            12
	 * @return
	 */
	@Override
	public int getCurrentCountFor(int year, int month) {
		String start = UtilDate.getMonthStartTime(year, month);
		String end = UtilDate.getMonthEndTime(year, month);
		return daoSerialNumber.getCount(start, end);
	}

	@Override
	public int getCurrentMonth() {
		String start = UtilDate.getCurrentMonthStartTime();
		String end = UtilDate.getCurrentMonthEndTime();
		return daoSerialNumber.getCount(start, end);
	}

 

}
