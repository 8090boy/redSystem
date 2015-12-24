package controller.service;

import java.util.List;

import controller.dao.IDAORecommend;
import controller.entity.Recommend;

public class ServiceRecommend implements IServiceRecommend {

	private IDAORecommend daoRecommend;

	/* (non-Javadoc)
	 * @see controller.service.IServiceRecommend#setDaoRecommend(controller.dao.IDAORecommend)
	 */
	@Override
	public void setDaoRecommend(IDAORecommend daoRecommend) {
		this.daoRecommend = daoRecommend;
	}

	/* (non-Javadoc)
	 * @see controller.service.IServiceRecommend#add(controller.entity.Recommend)
	 */
	@Override
	public Recommend add(Recommend r) {
		return daoRecommend.add(r);
	}

	/* (non-Javadoc)
	 * @see controller.service.IServiceRecommend#getAll()
	 */
	@Override
	public List<Recommend> getAll() {
		return daoRecommend.getAll();
	}

	/* (non-Javadoc)
	 * @see controller.service.IServiceRecommend#getForCity(java.lang.String)
	 */
	@Override
	public List<Recommend> getForCity(String cityName) {
		return daoRecommend.getForCity(cityName);
	}

	/* (non-Javadoc)
	 * @see controller.service.IServiceRecommend#getForVesting(java.lang.String)
	 */
	@Override
	public List<Recommend> getForVesting(String VestingName) {
		return daoRecommend.getForVesting(VestingName);
	}

	/* (non-Javadoc)
	 * @see controller.service.IServiceRecommend#getForType(int)
	 */
	@Override
	public List<Recommend> getForType(int Type) {
		return daoRecommend.getForType(Type);
	}

	/* (non-Javadoc)
	 * @see controller.service.IServiceRecommend#delForMob(java.lang.String)
	 */
	@Override
	public boolean delForMob(String mob) {
		return daoRecommend.delForMob(mob);
	}

	/* (non-Javadoc)
	 * @see controller.service.IServiceRecommend#update(controller.entity.Recommend)
	 */
	@Override
	public boolean update(Recommend r) {
		return daoRecommend.update(r);
	}

	public List<Recommend> getForMob(String rMob) {
		return daoRecommend.getForMob(rMob);
	}
	

}
