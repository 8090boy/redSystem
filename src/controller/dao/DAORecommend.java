package controller.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import controller.entity.Recommend;

public class DAORecommend implements IDAORecommend {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Recommend add(Recommend r) {
		sessionFactory.getCurrentSession().save(r);
		return r;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommend> getAll() {
		String hql = "from Recommend";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Recommend> recommends = null;
		try {
			recommends = query.list();
			return recommends;
		} catch (Exception e) {

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommend> getForCity(String cityName) {
		String hql = "from Recommend R where R.rCity=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, cityName);
		List<Recommend> recommends = null;
		try {
			recommends = query.list();
			return recommends;
		} catch (Exception e) {

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommend> getForVesting(String VestingName) {
		String hql = "from Recommend R where R.rVesting=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, VestingName);
		List<Recommend> recommends = null;
		try {
			recommends = query.list();
			return recommends;
		} catch (Exception e) {

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommend> getForType(int Type) {
		String hql = "from Recommend R where R.rVesting=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, Type);
		List<Recommend> recommends = null;
		try {
			recommends = query.list();
			return recommends;
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public boolean delForMob(String rMob) {
		String hql = "delete Recommend u where u.rMob=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, rMob);

		return (query.executeUpdate() > 0);
	}

	@Override
	public boolean update(Recommend r) {
		sessionFactory.getCurrentSession().update(r);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommend> getForMob(String rMob) {
		String hql = "from Recommend R where R.rMob=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, rMob);
		List<Recommend> recommends = null;
		try {
			recommends = query.list();
			if (recommends.size() > 1) {
				return recommends;
			}
		} catch (Exception e) {

		}
		return null;
	}

}
