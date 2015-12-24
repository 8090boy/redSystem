package controller.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import controller.entity.Assign;

public class DAOAssign implements IDAOAssign {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void add(Assign entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Assign> getAll() {
		String hql = "from gc_assign";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		return query.list();
	}

	@Override
	public boolean del(String id) {
		String hql = "delete gc_assign a where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (query.executeUpdate() > 0);
	}

	@Override
	public Assign getById(String id) {
		String hql = "from gc_assign a where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);

		return (Assign) query.uniqueResult();
	}

	@Override
	public boolean update(Assign entity) {
		String hql = "update gc_assign a set a.userId=?,a.snId=?,a.consume=?,a.created=? where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, entity.getUserId());
		query.setString(1, entity.getSnId());
		query.setInteger(2, entity.getConsume());
		query.setDate(3, entity.getCreated());
		query.setString(4, entity.getId());

		return (query.executeUpdate() > 0);
	}

}
