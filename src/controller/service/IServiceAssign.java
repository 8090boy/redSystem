package controller.service;

import java.util.List;

import controller.entity.Assign;

public interface IServiceAssign {

	public void add(Assign entity);

	public List<Assign> getAll();

	public boolean del(String id);

	public Assign getById(String id);

	public boolean update(Assign entity);

}
