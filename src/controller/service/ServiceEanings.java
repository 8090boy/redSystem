package controller.service;

import java.util.List;

import controller.dao.IDAOEarnings;
import controller.entity.Earnings;

public class ServiceEanings implements IDAOEarnings {

	private IDAOEarnings daoEarnings;

	public void setDaoEarnings(IDAOEarnings daoEarnings) {
		this.daoEarnings = daoEarnings;
	}

	@Override
	public void add(Earnings entity) {
		this.daoEarnings.add(entity);

	}

	@Override
	public List<Earnings> getAll() {

		return this.daoEarnings.getAll();
	}

	@Override
	public boolean del(String id) {

		return this.daoEarnings.del(id);
	}

	@Override
	public Earnings getById(String id) {

		return this.daoEarnings.getById(id);
	}

	@Override
	public boolean update(Earnings entity) {

		return this.daoEarnings.update(entity);
	}

}
