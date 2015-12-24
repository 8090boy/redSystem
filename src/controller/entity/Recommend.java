package controller.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "go_recommend")
public class Recommend {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String id;
	/**
	 * 被推荐人手机
	 */
	private String rMob = null;
	/**
	 * 推荐人
	 */
	private String rVesting = null;
	/**
	 * 被推荐人所在城市
	 */
	private String rCity = null;
	/**
	 * 推荐时间
	 */
	private Date sort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSort() {
		return sort;
	}

	public void setSort(Date sort) {
		this.sort = sort;
	}

	public String getrMob() {
		return rMob;
	}

	public void setrMob(String rMob) {
		this.rMob = rMob;
	}

	public String getrVesting() {
		return rVesting;
	}

	public void setrVesting(String rVesting) {
		this.rVesting = rVesting;
	}

	public String getrCity() {
		return rCity;
	}

	public void setrCity(String rCity) {
		this.rCity = rCity;
	}

}
