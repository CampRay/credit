package com.jtc.credit.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the admin_user database table.
 * 
 */
public class TadminUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private String adminId;	

	private String email;	

	private String password;	

	private boolean status;
		
	private Long createdTime;
	
	private String createdTimeStr;
	
	private double balance;
		
	private int roleId;
	
	private TadminInfo adminInfo;
		

	public TadminUser() {
	}
	
	public TadminUser(String adminId,String email,String password,int roleId,boolean status) {
		this.adminId=adminId;
		this.email=email;
		this.password=password;
		this.roleId=roleId;
		this.status=status;
	}
			
	
	
	public String getCreatedTimeStr() {
		if(createdTime!=null){
		Date date=new Date(createdTime);
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return sdf.format(date);		
		}else
		return this.createdTimeStr;
	
	}

	public void setCreatedTimeStr(String createdTimeStr) {
		this.createdTimeStr = createdTimeStr;
	}

	

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return the createdTime
	 */
	public Long getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public TadminInfo getAdminInfo() {
		return adminInfo;
	}

	public void setAdminInfo(TadminInfo adminInfo) {
		this.adminInfo = adminInfo;
	}
	
	

}