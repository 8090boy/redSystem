package controller.dao;

import java.util.List;

import controller.entity.User;

public interface IDAOUser {
	public User add(User user);

	public List<User> getAll();

	public List<User> getAllShare();

	public List<User> getAllForCity(String city);

	public boolean delForId(String id);

	public User getForId(String id);

	public boolean update(User user);

	User getForMob(String userName);

	User getForUserMail(String uMail);

	/**
	 * 获取会员总数或某个城市对应会员总数
	 * 
	 * @param city
	 *            城市名称中文，传null 返回所有会员总数
	 * @return
	 */
	int getCount(String city);

	/**
	 * 按月份获取会员总数
	 * 
	 * @param start
	 *            月份开始时间 yyyy-MM-dd HH:mm:ss
	 * @param end
	 *            月份结束时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	int getCountForMonth(String start, String end);

	int getUserCoutnForType(int type);

	public void delAll();

}
