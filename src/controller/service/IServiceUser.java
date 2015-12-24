package controller.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import controller.entity.SerialNumber;
import controller.entity.User;

public interface IServiceUser {

	public abstract int getUserCountForType(int type);

	public abstract Boolean setUas(String mob, int type);

	/**
	 * 按城市返回总数，或全部总数
	 * 
	 * @param city
	 *            中文城市名，或 null 获取总数
	 * @return 总数
	 */
	public abstract int userCountForCity(String city);

	/**
	 * 获取某年，某月的会员总数
	 */
	public abstract int userCountForCity(int year, int month);

	/**
	 * 切换0会员和1股东状态
	 */
	public abstract String setShareholder(String userId);

	public abstract User add(User user);

	public abstract List<User> getAll();

	public abstract List<User> getAllShare();

	public abstract boolean delForId(String id);

	public abstract User getForId(String id);

	public abstract User getForMob(String uMob);

	public abstract boolean update(User user);

	public abstract User getForEmail(String email);

	public abstract List<User> getAllForCity(String city);

	public abstract void delAll();

	public abstract String AddUser(HttpServletRequest request, User user);

	public abstract List<User> getUsersForSNS(List<SerialNumber> isExchangeSN);
	
	public abstract User getUserDefault();

}