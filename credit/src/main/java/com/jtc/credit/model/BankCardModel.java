package com.jtc.credit.model;

public class BankCardModel {
	//银行卡类型： 101:借记卡；102:信用卡
	private String cardType;
	//银行卡号
	private String accNo;
	//银行预留手机号
	private String mobile;
	//证件类型： 0:身份证；1:军官证；2:护照
	private String idType;
	//信用卡有效期，如：0117（月+年）
	private String validDate;	
	//信用卡背面3位数字检验码，如：123
	private String validNo;
	/**
	 * 验证类型:
	 * 12：两要素（银行卡号 + 姓名）
	 * 123：三要素（银行卡号 + 姓名 + 身份证号）
	 * 1234：四要素（银行卡号 + 姓名 + 身份证号 + 银行卡预留手机号）
	 **/
	private String verifyElement;		
	
	private String idCard;
	private String realName;
	private String isPhoto;
		
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIsPhoto() {
		return isPhoto;
	}
	public void setIsPhoto(String isPhoto) {
		this.isPhoto = isPhoto;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getValidNo() {
		return validNo;
	}
	public void setValidNo(String validNo) {
		this.validNo = validNo;
	}
	public String getVerifyElement() {
		return verifyElement;
	}
	public void setVerifyElement(String verifyElement) {
		this.verifyElement = verifyElement;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	

}
