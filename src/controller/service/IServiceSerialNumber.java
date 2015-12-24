package controller.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import controller.dao.IDAOSerialNumber;
import controller.dao.IDAOUser;
import controller.entity.SerialNumber;
import controller.entity.User;

public interface IServiceSerialNumber {

	public abstract void setDaoSerialNumber(IDAOSerialNumber daoSerialNumber);

	public abstract void setDaoUser(IDAOUser daoUser);

	public abstract void setRevenue(HttpServletRequest request, SerialNumber SNId);

	/**
	 * 给自己父级分配红包
	 * 
	 * @param SNId
	 *            自己的ID
	 * @param classNo
	 *            几层对应的红包金额
	 * @return 返回父级SerialNumber ID；null则不存在父级
	 */
	public abstract String RedEnvelopesToTheHigher(HttpServletRequest request, SerialNumber SNId, int classNo);

	/**
	 * 收益红包时短信提示
	 * 
	 * @param sn
	 *            谁收益红包
	 * @param jine
	 *            收益多少钱
	 */
	public abstract void EarningsSMS(HttpServletRequest request, SerialNumber sn, int jine);

	public abstract String getParentId( );

	public abstract SerialNumber add(SerialNumber entity);

	public abstract boolean delAll();

	public abstract List<SerialNumber> getAll();

	public abstract boolean del(String id);

	public abstract SerialNumber getById(String id);

	public abstract boolean update(SerialNumber entity);

	public abstract boolean updateNumberRedAndMoney(SerialNumber entity);

	public abstract List<SerialNumber> getSonByParentId(int layer, String ParentId);

	public abstract SerialNumber getByLayerAndId(int layer, String id);

	//
	public abstract List<SerialNumber> getByLayerIsExchange(int InitialPayment, int ExchangeStarting, int Batch,String phases);

	public abstract int getByLayerIsFail(int initialPayment);

	/**
	 * 获取某年某月产生的序列号总数
	 * 
	 * @param year
	 *            2015
	 * @param month
	 *            12
	 * @return
	 */
	public abstract int getCurrentCountFor(int year, int month);

	public abstract int getCurrentMonth();

	SerialNumber AddSN(HttpServletRequest request, User user);

	SerialNumber getByLayerAndOrder(int layer, int order, String phases);



}