package com.jtc.credit.model;

/**
 * 企业管理人员信息
 * @author Phills
 *
 */
public class Person {

	private String NATDATE;
	private String POSITION;
	private String PERSONAMOUNT;
	private String SEX;
	private String PERNAME;
	public String getNATDATE() {
		return NATDATE;
	}
	public void setNATDATE(String nATDATE) {
		NATDATE = nATDATE;
	}
	public String getPOSITION() {
		return POSITION;
	}
	public void setPOSITION(String pOSITION) {
		POSITION = pOSITION;
	}
	public String getPERSONAMOUNT() {
		return PERSONAMOUNT;
	}
	public void setPERSONAMOUNT(String pERSONAMOUNT) {
		PERSONAMOUNT = pERSONAMOUNT;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}
	public String getPERNAME() {
		return PERNAME;
	}
	public void setPERNAME(String pERNAME) {
		PERNAME = pERNAME;
	}
	
	
}
