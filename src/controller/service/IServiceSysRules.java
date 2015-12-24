package controller.service;

import java.util.List;

import controller.entity.SysRules;

public interface IServiceSysRules {

	public void add(SysRules entity);

	public List<SysRules> getAll();

	public boolean del(String id);

	public SysRules getById(String id);

	public boolean update(SysRules entity);

	public SysRules getIsOK();

	public void setIsOK(int id);

	public void delAll();
	
	public SysRules getSysRulesSingle();

}
