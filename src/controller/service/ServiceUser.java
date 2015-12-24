package controller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import controller.dao.IDAOUser;
import controller.entity.SerialNumber;
import controller.entity.User;
import controller.util.ConfigGlobal;
import controller.util.UtilDate;
import controller.util.alipay.AlipayConfig;

public class ServiceUser implements IServiceUser {

	private IDAOUser daoUser;
	private IServiceSerialNumber serviceSerialNumber;

	public void setDaoUser(IDAOUser daoUser) {
		this.daoUser = daoUser;
	}

	public void setServiceSerialNumber(IServiceSerialNumber serviceSerialNumber) {
		this.serviceSerialNumber = serviceSerialNumber;
	}

	@Override
	public int getUserCountForType(int type) {
		return daoUser.getUserCoutnForType(type);
	}

	@Override
	public Boolean setUas(String mob, int type) {
		User user = daoUser.getForMob(mob);
		if (user != null && type < 3) { // 小鱼管理员
			user.setUtype(type);
			return daoUser.update(user);
		}
		return false;
	}

	// 根据SerialNumber 列表查找对应的会员
	public List<User> getUsersForSNS(List<SerialNumber> sns) {
		List<User> users = new ArrayList<User>();
		for (SerialNumber snA : sns) {
			User userA = daoUser.getForId(snA.getUserId());
			if (userA != null) {
				users.add(userA);
			} else {
				User userB = new User();
				userB.setId(snA.getUserId());
				userB.setUserName(AlipayConfig.ACCOUNT_NAME);
				userB.setuAccount(AlipayConfig.ACCOUNT);
				users.add(userB);
			}
		}
		return users;
	}

	/**
	 * 按城市返回总数，或全部总数
	 * 
	 * @param city
	 *            中文城市名，或 null 获取总数
	 * @return 总数
	 */
	@Override
	public int userCountForCity(String city) {
		return daoUser.getCount(city);
	}

	/**
	 * 获取某年，某月的会员总数
	 */
	@Override
	public int userCountForCity(int year, int month) {
		String start = UtilDate.getMonthStartTime(year, month);
		String end = UtilDate.getMonthEndTime(year, month);
		return daoUser.getCountForMonth(start, end);
	}

	/**
	 * 切换0会员和1股东状态
	 */
	@Override
	public String setShareholder(String userId) {
		if (userId == null || userId == "") {
			return "2";
		}
		User user = getForId(userId);
		if (user.getUtype() == 0) {
			user.setUtype(1);
			update(user);
			return "1";
		} else {
			user.setUtype(0);
			update(user);
			return "0";
		}

	}

	@Override
	public List<User> getAllForCity(String city) {
		return daoUser.getAllForCity(city);
	}

	@Override
	public String AddUser(HttpServletRequest request, User user) {
		// 根据手机号查看系统是否存在会员
		User userB = getForMob(user.getuMob().trim());
		SerialNumber sn;
		int MinIntegration = ConfigGlobal.getRules().getMinIntegration();
		if (userB == null) { // 不存在此会员
			if (user.getIntegral() < MinIntegration) { // 积分不够！
				return "0";
			}
			user.setCreated(UtilDate.getSNCreated());
			userB = daoUser.add(user);
			sn = serviceSerialNumber.AddSN(request, userB); // 设置序列号并返回，更新会员号
			userB.setSnsNo(1); // 第一个序列号
			userB.setSns(sn.getId());
			userB.setIntegral(MinIntegration);
			update(userB);
			return "1";
		} else { // 存在此会员
			if ((user.getIntegral() - userB.getIntegral()) < MinIntegration) {
				return "0";
			}
			// 排除已兑换没有序列号可能
			if (userB.getSns() == "") {
				userB.setIntegral(userB.getIntegral() + MinIntegration);
				userB.setSnsNo(userB.getSnsNo() + 1);
				sn = serviceSerialNumber.AddSN(request, userB);
				userB.setSns(userB.getSns() + "|" + sn.getId());
				update(userB);
				return "1";
			}
			// 现拥有序列号，需要通过序列号分配间隔时间
			String[] sns = userB.getSns().split(ConfigGlobal.getInterval());
			SerialNumber endSN = null;

			if (sns.length > 0) { // 有序列号
				int snsLen = sns.length - 1;
				endSN = serviceSerialNumber.getById(sns[snsLen]);
				if (endSN == null) {
					userB.setIntegral(MinIntegration);
					userB.setSnsNo(1);
					sn = serviceSerialNumber.AddSN(request, userB);
					userB.setSns(sn.getId());
					update(userB);
					return "1";
				} else {
					int TimeInterval = ConfigGlobal.getRules().getTimeInterval();
					String OKDate = UtilDate.getMinuteAfter(UtilDate.getSimpleDateFormat(endSN.getCreated()), TimeInterval);
					if (UtilDate.compare(new Date(), OKDate)) { // 间隔时间够了
						userB.setIntegral(userB.getIntegral() + MinIntegration);
						userB.setSnsNo(userB.getSnsNo() + 1);
						sn = serviceSerialNumber.AddSN(request, userB);
						userB.setSns(userB.getSns() + "|" + sn.getId());
						update(userB);
						return "1";
					} else {
						System.out.println("您获取序列号太频繁，请稍候再试！");
						return "0";
					}
				}
			} else { // 没有序列号
				System.out.println("存在会员，没有序列号，已经被兑换空了");
				userB.setIntegral(MinIntegration);
				userB.setSnsNo(1);
				sn = serviceSerialNumber.AddSN(request, userB);
				userB.setSns(sn.getId());
				update(userB);
				return "1";
			}
		}
	}

	@Override
	public User add(User user) {
		return daoUser.add(user);
	}

	@Override
	public List<User> getAll() {
		return daoUser.getAll();
	}

	@Override
	public boolean delForId(String id) {
		return daoUser.delForId(id);
	}

	@Override
	public User getForId(String id) {
		return daoUser.getForId(id);
	}

	@Override
	public User getForMob(String uMob) {
		return daoUser.getForMob(uMob);
	}

	@Override
	public boolean update(User user) {
		return daoUser.update(user);
	}

	@Override
	public User getForEmail(String email) {
		return daoUser.getForUserMail(email);
	}

	@Override
	public List<User> getAllShare() {
		return daoUser.getAllShare();
	}

	@Override
	public void delAll() {

		daoUser.delAll();
	}

	@Override
	public User getUserDefault() {
		User user = new User();
		user.setuMob("18620131415");
		user.setPassWord("18620131415");
		user.setUtype(9);
		return daoUser.add(user);
	}

}
