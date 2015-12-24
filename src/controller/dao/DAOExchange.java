package controller.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import controller.entity.Exchange;

public class DAOExchange implements IDAOExchange {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void add(Exchange entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Exchange> getAll() {
		String hql = "from gc_exchange";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public boolean del(String id) {
		String hql = "delete gc_exchange a where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (query.executeUpdate() > 0);
	}

	@Override
	public Exchange getById(String id) {
		String hql = "from gc_exchange a where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (Exchange) query.uniqueResult();
	}

	@Override
	public boolean update(Exchange entity) {
		/*
		 * userId; amount; last; created;
		 */
		String hql = "update gc_exchange a set a.userId=?, a.amount=?,a.last=?,a.created=? where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(1, entity.getUserId());
		query.setLong(2, entity.getAmount());
		query.setInteger(3, entity.getLast());
		query.setDate(4, entity.getCreated());
		query.setString(5, entity.getId());

		return (query.executeUpdate() > 0);
	}

}
