package com.jtc.credit.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TbalanceLog implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;	

	private String userId;	

	private String descr;		
		
	private Long createdTime;
	
	private String createdTimeStr;
	
	private double amount;
	
	private double balance;	
	
	//充值记录类型：0 管理员充值， 1 用户充值申请
	private boolean type=false;
	//充值记录状态：0 关闭， 1 有效
	private boolean status=true;
		
	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public boolean isType() {
		return type;
	}


	public void setType(boolean type) {
		this.type = type;
	}


	public TbalanceLog() {
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
	

}