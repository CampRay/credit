package com.jtc.credit.model;

/**
 * 元素银行卡验证请求参数模型
 * @author Phills
 *
 */
public class EleBankCardModel {
	//请求web service接口类型, 三要素认证: card_tio， 四要素认证: card_fio
	private String Service="card_tio";
	//姓名
	private String name;
	//身份证号码
	private String idNo;
	//银行卡号
	private String acctNo;
	//银行预留手机号
	private String mobile;
	public String getService() {
		return Service;
	}
	public void setService(String service) {
		Service = service;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
}
