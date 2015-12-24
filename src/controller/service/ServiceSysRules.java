package controller.service;

import java.util.List;

import controller.dao.IDAOSysRules;
import controller.entity.SysRules;
import controller.util.UtilDate;

public class ServiceSysRules implements IServiceSysRules {

	private IDAOSysRules daoSysRules;

	public void setDaoSysRules(IDAOSysRules daoSysRules) {
		this.daoSysRules = daoSysRules;
	}
	
	@Override
	public SysRules getIsOK(){
		return daoSysRules.getIsOK();
	}

	@Override
	public void add(SysRules entity) {
		daoSysRules.add(entity);
	}

	@Override
	public List<SysRules> getAll() {
		return daoSysRules.getAll();
	}

	@Override
	public boolean del(String id) {
		return daoSysRules.del(id);
	}

	@Override
	public SysRules getById(String id) {
		return daoSysRules.getById(id);
	}

	@Override
	public boolean update(SysRules entity) {
		return daoSysRules.update(entity);
	}

	@Override
	public void setIsOK(int id) {
		daoSysRules.setIsOK(id);
		
	}

	@Override
	public void delAll() {
		daoSysRules.delAll();
		
	}

	@Override
	public SysRules getSysRulesSingle() {
		SysRules rs = new SysRules();
 		rs.setPhasesNextId(0);
 		rs.setPhasesLayer(2);
 		rs.setMinIntegration(1);
 		rs.setNoOne("gomall");
 		rs.setIsSend(0);
 		rs.setBatch(50);
 		rs.setTimeInterval(0);
 		rs.setMultiple(3);
 		rs.setRevenueLayer(2);
 		rs.setAmount("1|1");
 		rs.setFee(0);
 		rs.setExchangeStarting(1);
 		rs.setIsOK(1);
 		rs.setPhasesUseCount(UtilDate.getOrderNum());
 		return daoSysRules.add(rs);
 		
	}

}
