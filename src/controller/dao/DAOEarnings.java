package controller.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import controller.entity.Earnings;

public class DAOEarnings implements IDAOEarnings {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void add(Earnings entity) {
		sessionFactory.getCurrentSession().save(entity);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Earnings> getAll() {
		String hql = "from gc_earnings";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public boolean del(String id) {
		String hql = "delete gc_earnings a where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (query.executeUpdate() > 0);
	}

	@Override
	public Earnings getById(String id) {
		String hql = "from gc_earnings a where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (Earnings) query.uniqueResult();
	}

	@Override
	public boolean update(Earnings entity) {
		String hql = "update gc_earnings a set a.snId=?,a.userId=?, a.money=?,a.relativeLayer=?,a.status=?,a.created=? where a.id=?";
		/*
		 * snId; userId; money; relativeLayer; status; created;
		 */
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, entity.getSnId());
		query.setString(1, entity.getUserId());
		query.setLong(2, entity.getMoney());
		query.setInteger(3, entity.getRelativeLayer());
		query.setString(4, entity.getStatus());
		query.setDate(5, entity.getCreated());
		query.setString(6, entity.getId());

		return (query.executeUpdate() > 0);
	}

}
