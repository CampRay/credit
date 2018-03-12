package com.jtc.credit.dto;


public class TadminInfo implements java.io.Serializable {

	private static final long serialVersionUID = -6411039121482151510L;
	
	private String adminId;
	private String realName;
	private String company;
	
	private String address;		
	private String mobile;
	private String position;	
	

	public TadminInfo() {
	}

	public TadminInfo(String adminId) {
		this.adminId = adminId;
	}
	

	public String getAdminId() {
		return this.adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	
}
