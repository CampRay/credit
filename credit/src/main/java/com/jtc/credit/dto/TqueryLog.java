package com.jtc.credit.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TqueryLog implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;	

	private String userId;	

	private String descr;	

	private boolean status;
		
	private Long createdTime;
	
	private String createdTimeStr;
	
	private int apiId;	
	
	private double amount;
	
	private double balance;
	
	
	private int roleId;
		

	public TqueryLog() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public Long getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}


	public String getCreatedTimeStr() {
		if(createdTime!=null){
			Date date=new Date(createdTime);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format(date);		
		}else
		return this.createdTimeStr;
	}


	public void setCreatedTimeStr(String createdTimeStr) {
		this.createdTimeStr = createdTimeStr;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
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


	public int getApiId() {
		return apiId;
	}


	public void setApiId(int apiId) {
		this.apiId = apiId;
	}
	
	

}