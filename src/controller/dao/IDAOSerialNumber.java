package controller.dao;

import java.util.List;

import controller.entity.SerialNumber;

public interface IDAOSerialNumber {

	public SerialNumber add(SerialNumber entity);

	public List<SerialNumber> getAll();

	public boolean del(String id);

	public SerialNumber getById(String id);


	public SerialNumber getByLayerAndId(int layer, String id);

	public List<SerialNumber> getByParentId(int layer, String ParentId);

	public boolean update(SerialNumber entity);

	public boolean delAll();

	boolean updateNumberRedAndMoney(SerialNumber entity);


	public int getByLayerIsFail(int layer);

	int getCount(String start, String end);

	public SerialNumber getByLayerAndOrder(int layer, int order, String phases);

	public List<SerialNumber> getByLayer(int initialPayment, int exchangeStarting, int batch, String phases);

}
