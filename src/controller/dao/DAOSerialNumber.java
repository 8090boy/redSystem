package controller.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import controller.entity.SerialNumber;

public class DAOSerialNumber implements IDAOSerialNumber {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public SerialNumber add(SerialNumber entity) {
		sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

	 

	@Override
	public int getCount(String start, String end) {
		String sqlStr = "SELECT * FROM go_serialnumber AS S WHERE S.sort >= '"+ start +"' AND S.sort <= '"+ end +"'";
		 SQLQuery sql = sessionFactory.getCurrentSession().createSQLQuery(sqlStr);
		 return sql.list().size();
	}

	@Override
	public boolean delAll() {
		String hql = "delete SerialNumber";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (query.executeUpdate() > 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNumber> getAll() {
		String hql = "from SerialNumber order by created desc";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<SerialNumber> SNS = query.list();
		if (SNS.size() <= 0) {
			return null;
		}
		return SNS;
	}

	@Override
	public boolean del(String id) {
		String hql = "delete SerialNumber a where a.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (query.executeUpdate() > 0);
	}

	@Override
	public SerialNumber getById(String id) {
		String hql = "from SerialNumber S where S.id =?";// from objectName
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (SerialNumber) query.uniqueResult();
	}

	@Override
	public boolean update(SerialNumber entity) {
		sessionFactory.getCurrentSession().update(entity);
		return true;
	}

	@Override
	public boolean updateNumberRedAndMoney(SerialNumber entity) {
		String hql = "update SerialNumber S set S.numberRed=? , S.money=?  where S.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, entity.getNumberRed());
		query.setLong(1, entity.getMoney());
		query.setString(2, entity.getId());
		return (query.executeUpdate() > 0);
	}

	@Override
	public SerialNumber getByLayerAndOrder(int layer, int order , String phases) {
		String hql = "from SerialNumber S where S.layer =? and S.layerOrder =? and S.phases =?";// from objectName
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, layer);
		query.setInteger(1, order);
		query.setString(2, phases);
		return (SerialNumber) query.uniqueResult();
	}

	@Override
	public List<SerialNumber> getByParentId(int layer, String parentId) {
		String hql = "from SerialNumber S where S.layer =? and S.parentId =? order by S.created desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, layer);
		query.setString(1, parentId);
		@SuppressWarnings("unchecked")
		List<SerialNumber> SNS = query.list();
		if (SNS.size() <= 0) {
			return null;
		}
		return SNS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNumber> getByLayer(int layer, int money, int Batch , String phases) {
		try {
			String hql = "from SerialNumber S where S.layer =? and S.money >=?  and S.status = 0  and S.phases =?  order by S.created asc";// from objectName
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setInteger(0, layer);
			query.setInteger(1, money);
			query.setString(2, phases);
			query.setMaxResults(Batch);
			List<SerialNumber> SNS = null;
			SNS = query.list();
			 return SNS;	 
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public SerialNumber getByLayerAndId(int layer, String id) {
		String hql = "from SerialNumber S where S.id =? and S.layer=?";// from objectName
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		query.setInteger(1, layer);
		return (SerialNumber) query.uniqueResult();
	}

	@Override
	public int getByLayerIsFail(int layer) {
		String hql = "from SerialNumber as S where S.layer =? and S.status = 1";// from objectName
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, layer);
		int a = query.list().size();
		return a;
	}

 

}
