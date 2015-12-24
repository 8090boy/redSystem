package controller.service;

import java.util.List;

import controller.dao.IDAOExchange;
import controller.entity.Exchange;

public class ServiceExchange implements IDAOExchange {

	private IDAOExchange daoExchange;

	public void setDaoExchange(IDAOExchange daoExchange) {
		this.daoExchange = daoExchange;
	}

	@Override
	public void add(Exchange entity) {
		this.daoExchange.add(entity);

	}

	@Override
	public List<Exchange> getAll() {

		return this.daoExchange.getAll();
	}

	@Override
	public boolean del(String id) {

		return this.daoExchange.del(id);
	}

	@Override
	public Exchange getById(String id) {

		return this.daoExchange.getById(id);
	}

	@Override
	public boolean update(Exchange entity) {

		return this.daoExchange.update(entity);
	}

}
