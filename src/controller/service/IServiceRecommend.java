package controller.service;

import java.util.List;

import controller.dao.IDAORecommend;
import controller.entity.Recommend;

public interface IServiceRecommend {

	public abstract void setDaoRecommend(IDAORecommend daoRecommend);

	public abstract Recommend add(Recommend r);

	public abstract List<Recommend> getAll();

	public abstract List<Recommend> getForCity(String cityName);

	public abstract List<Recommend> getForVesting(String VestingName);

	public abstract List<Recommend> getForType(int Type);
	
	public abstract List<Recommend> getForMob(String rMob);

	public abstract boolean delForMob(String mob);

	public abstract boolean update(Recommend r);

}