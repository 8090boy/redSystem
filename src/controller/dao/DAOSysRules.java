package controller.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import controller.entity.SysRules;

public class DAOSysRules implements IDAOSysRules {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public SysRules add(SysRules entity) {
		sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysRules> getAll() {
		String hql = "from SysRules";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public boolean del(String id) {
		String hql = "delete SysRules a where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (query.executeUpdate() > 0);
	}

	@Override
	public SysRules getById(String id) {
		String hql = "from SysRules a where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (SysRules) query.uniqueResult();
	}

	@Override
	public SysRules getIsOK() {
		
		String hql = "from SysRules a where a.isOK > 0";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		SysRules sr = (SysRules) query.uniqueResult();
		if (sr == null) {
			if (getAll().size() > 0) {
				SysRules srA = getAll().get(0);
				srA.setIsOK(1);
				update(srA);
				sr = srA;
			}else{
				return null;
			}
		}
		return sr;
	}

	@Override
	public boolean update(SysRules entity) {
		sessionFactory.getCurrentSession().update(entity);
		return true;
	}

	/**
	 * 设置ID为默认值
	 */
	@Override
	public void setIsOK(int id) {
		String hql = "update SysRules a set a.isOK = 0";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		hql = "update SysRules a set a.isOK = 1 where a.id = ?";
		query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id);
		query.executeUpdate();
	}

	@Override
	public boolean delAll() {
		String hql = "delete SysRules";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (query.executeUpdate() > 0);
		
	}
	
	

}
