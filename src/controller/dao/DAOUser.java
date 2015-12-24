package controller.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import controller.entity.User;

public class DAOUser implements IDAOUser {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public User add(User user) {
		sessionFactory.getCurrentSession().save(user);
	
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		String hql = "from User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<User> users = null;
		try {
			users = query.list();
			return users;
		} catch (Exception e) {

		}

		return null;
	}

	@Override
	public boolean delForId(String id) {
		String hql = "delete User u where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);

		return (query.executeUpdate() > 0);
	}

	@Override
	public User getForId(String id) {
		String hql = "from User u where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);

		return (User) query.uniqueResult();
	}

	@Override
	public User getForMob(String uMob) {
		String hql = "from User as u where u.uMob = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, uMob.trim());
		@SuppressWarnings("unchecked")
		List<User> users = query.list();
		if (users.size() > 0) {
			return (User) query.list().get(0);
		}
		return null;
	}

	@Override
	public int getUserCoutnForType(int type) {
		String hql = "from User as u where u.utype = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, type);
		return  query.list().size();
	}

	@Override
	public User getForUserMail(String uMail) {
		String hql = "from User u where u.uMail=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, uMail);
		@SuppressWarnings("unchecked")
		List<User> users = query.list();
		if (users.size() > 0) {
			return (User) query.list().get(0);
		}
		return null;
	}

	@Override
	public int getCount(String city) {
		Query query;
		String hql1;
		hql1 = "from User";
		String hql2;
		hql2 = "from User u where u.uCity like ?";
		if (city == null) {
			query = sessionFactory.getCurrentSession().createQuery(hql1);
		} else {
			query = sessionFactory.getCurrentSession().createQuery(hql2);
			query.setString(0, "'%" + city + "%'");
		}
		return query.list().size();
	}

	@Override
	public int getCountForMonth(String start, String end) {
	 
		String sqlStr = "SELECT * FROM go_user AS U WHERE U.sort >= '"+ start +"' AND U.sort <= '"+ end +"'";
		 SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(sqlStr);
		 return sql.list().size();
		 
	}

	@Override
	public boolean update(User user) {
		sessionFactory.getCurrentSession().update(user);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllShare() {
		String hql = "from User as u where u.utype = 1";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllForCity(String city) {
		String hql = "from User as u where u.uCity link ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, "%" + city + "%");
		return query.list();
	}

	@Override
	public void delAll() {
		String hql = "delete User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}
 

}
