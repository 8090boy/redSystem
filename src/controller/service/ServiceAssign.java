package controller.service;

import java.util.List;

import controller.dao.IDAOAssign;
import controller.entity.Assign;

public class ServiceAssign implements IServiceAssign {

	private IDAOAssign daoAssign;

	public void setDaoAssign(IDAOAssign daoAssign) {
		this.daoAssign = daoAssign;
	}

	@Override
	public void add(Assign entity) {
		this.daoAssign.add(entity);
	}

	@Override
	public List<Assign> getAll() {
		return this.daoAssign.getAll();
	}

	@Override
	public boolean del(String id) {
		return this.daoAssign.del(id);
	}

	@Override
	public Assign getById(String id) {
		return this.daoAssign.getById(id);
	}

	@Override
	public boolean update(Assign entity) {
		return this.daoAssign.update(entity);
	}

}
