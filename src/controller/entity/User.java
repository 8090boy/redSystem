package controller.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "go_user")
public class User {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String id;
	private String userName = null;// 支付宝姓名 //真是姓名
	private String passWord = null;
	private int utype = 0;// 普通0，1为股东，2违规，3管理
	private String uCity = null;// 省、市
	private String uAddress = null;
	private String uMail = null;
	private String uMob = null;
	private String uBankType = null;
	private String uBank = null;
	private String uIdCard = null; //
	private String uAccount = null;// 支付宝帐号手机或邮箱
	@Lob
	@Column(nullable = true)
	// 最大容纳1800个序列号
	private String sns = ""; //序列号集合
	private int snsNo = 0; //序列号数量
	private int integral = 0; //积分
	private String snsOK = "";
	private int total = 0; //
	private String created;
	private String uSQA;//保密问题
	private Date sort = new Date(System.currentTimeMillis());

	// //////////////////

	public String getId() {
		return id;
	}

	public Date getSort() {
		return sort;
	}

	
	public String getuSQA() {
		return uSQA;
	}

	public void setuSQA(String uSQA) {
		this.uSQA = uSQA;
	}

	public void setSort(Date sort) {
		this.sort = sort;
	}

	public String getuCity() {
		return uCity;
	}

	public void setuCity(String uCity) {
		this.uCity = uCity;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getuBankType() {
		return uBankType;
	}

	public void setuBankType(String uBankType) {
		this.uBankType = uBankType;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getSnsOK() {
		return snsOK;
	}

	public void setSnsOK(String snsOK) {
		this.snsOK = snsOK;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getUtype() {
		return utype;
	}

	public void setUtype(int utype) {
		this.utype = utype;
	}

	public String getuAddress() {
		return uAddress;
	}

	public void setuAddress(String uAddress) {
		this.uAddress = uAddress;
	}

	public String getuMail() {
		return uMail;
	}

	public void setuMail(String uMail) {
		this.uMail = uMail;
	}

	public String getuMob() {
		return uMob;
	}

	public void setuMob(String uMob) {
		this.uMob = uMob;
	}

	public String getuBank() {
		return uBank;
	}

	public void setuBank(String uBank) {
		this.uBank = uBank;
	}

	public String getuAccount() {
		return uAccount;
	}

	public void setuAccount(String uAccount) {
		this.uAccount = uAccount;
	}

	public String getuIdCard() {
		return uIdCard;
	}

	public void setuIdCard(String uIdCard) {
		this.uIdCard = uIdCard;
	}

	public String getSns() {
		return sns;
	}

	public void setSns(String sns) {
		this.sns = sns;
	}

	public int getSnsNo() {
		return snsNo;
	}

	public void setSnsNo(int snsNo) {
		this.snsNo = snsNo;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
