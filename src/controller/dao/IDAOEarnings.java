package controller.dao;

import java.util.List;

import controller.entity.Earnings; 

public interface IDAOEarnings {

	public void add(Earnings entity);

	public List<Earnings> getAll();

	public boolean del(String id);

	public Earnings getById(String id);

	public boolean update(Earnings entity);

}
