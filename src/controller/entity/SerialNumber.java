package controller.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "go_serialnumber")
public class SerialNumber {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String id;
	private String parentId;
	private int layer;
	private int layerOrder;
	private String userId;
	private int status = 0;// 0正常会员，1已发放，2违规，3管理者
	private int numberRed; //
	private int money;
	private String totalPoints;//获得此号耗费多少个积分
	private String created;
	private String phases;
	private Date sort = new Date(System.currentTimeMillis());
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getLayerOrder() {
		return layerOrder;
	}
	public void setLayerOrder(int layerOrder) {
		this.layerOrder = layerOrder;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getNumberRed() {
		return numberRed;
	}
	public void setNumberRed(int numberRed) {
		this.numberRed = numberRed;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getPhases() {
		return phases;
	}
	public void setPhases(String phases) {
		this.phases = phases;
	}
	public Date getSort() {
		return sort;
	}
	public void setSort(Date sort) {
		this.sort = sort;
	}
 
	
	
}
