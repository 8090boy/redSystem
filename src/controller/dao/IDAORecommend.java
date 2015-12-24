package controller.dao;

import java.util.List;

import controller.entity.Recommend;

public interface IDAORecommend {

	public Recommend add(Recommend r);

	public List<Recommend> getAll();

	public List<Recommend> getForCity(String cityName);

	public List<Recommend> getForVesting(String VestingName);

	public List<Recommend> getForType(int Type);

	public boolean delForMob(String mob);

	public boolean update(Recommend r);

	public List<Recommend> getForMob(String rMob);

}
