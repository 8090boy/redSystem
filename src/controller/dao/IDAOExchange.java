package controller.dao;

import java.util.List;

import controller.entity.Exchange;

public interface IDAOExchange {

	public void add(Exchange entity);

	public List<Exchange> getAll();

	public boolean del(String id);

	public Exchange getById(String id);

	public boolean update(Exchange entity);

}
