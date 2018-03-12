package com.jtc.credit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 风控查询结果数据模型
 * @author Phills
 *
 */
public class WsRiskDataModel {

	private String pageNo;
	private String range;
	private String totalCount;
	private String totalPageNum;
	private List<RiskItem> allItemList = new ArrayList<RiskItem>();
	private String searchSeconds;
	
	private String cpwsCount;
	private String cpwsPageNum;

	private String ktggCount;
	private String ktggPageNum;

	private String zxggCount;
	private String zxggPageNum;

	private String shixinCount;
	private String shixinPageNum;

	private String fyggCount;
	private String fyggPageNum;

	private String wdhmdCount;
	private String wdhmdPageNum;

	private String ajlcCount;
	private String ajlcPageNum;

	private String bgtCount;
	private String bgtPageNum;

	private String satpartyCount;
	private String satpartyPageNum;

	private String fdapartyCount;
	private String fdapartyPageNum;

	private String epbpartyCount;
	private String epbpartyPageNum;

	private String qtspartyCount;
	private String qtspartyPageNum;

	private String mocpartyCount;
	private String mocpartyPageNum;

	private String pbcpartyCount;
	private String pbcpartyPageNum;

	private String xzhmdCount;
	private String xzhmdPageNum;

	private String newsCount;
	private String newsPageNum;

	private String zcdjCount;
	private String zcdjPageNum;

	private String zccfCount;
	private String zccfPageNum;

	private String jyycCount;
	private String jyycPageNum;

	private String jobCount;
	private String jobPageNum;

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getSearchSeconds() {
		return searchSeconds;
	}

	public void setSearchSeconds(String searchSeconds) {
		this.searchSeconds = searchSeconds;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(String totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public List<RiskItem> getAllItemList() {
		return allItemList;
	}

	public void setAllItemList(List<RiskItem> allItemList) {
		this.allItemList = allItemList;
	}

	public String getCpwsCount() {
		return cpwsCount;
	}

	public void setCpwsCount(String cpwsCount) {
		this.cpwsCount = cpwsCount;
	}

	public String getCpwsPageNum() {
		return cpwsPageNum;
	}

	public void setCpwsPageNum(String cpwsPageNum) {
		this.cpwsPageNum = cpwsPageNum;
	}

	public String getKtggCount() {
		return ktggCount;
	}

	public void setKtggCount(String ktggCount) {
		this.ktggCount = ktggCount;
	}

	public String getKtggPageNum() {
		return ktggPageNum;
	}

	public void setKtggPageNum(String ktggPageNum) {
		this.ktggPageNum = ktggPageNum;
	}

	public String getZxggCount() {
		return zxggCount;
	}

	public void setZxggCount(String zxggCount) {
		this.zxggCount = zxggCount;
	}

	public String getZxggPageNum() {
		return zxggPageNum;
	}

	public void setZxggPageNum(String zxggPageNum) {
		this.zxggPageNum = zxggPageNum;
	}

	public String getShixinCount() {
		return shixinCount;
	}

	public void setShixinCount(String shixinCount) {
		this.shixinCount = shixinCount;
	}

	public String getShixinPageNum() {
		return shixinPageNum;
	}

	public void setShixinPageNum(String shixinPageNum) {
		this.shixinPageNum = shixinPageNum;
	}

	public String getFyggCount() {
		return fyggCount;
	}

	public void setFyggCount(String fyggCount) {
		this.fyggCount = fyggCount;
	}

	public String getFyggPageNum() {
		return fyggPageNum;
	}

	public void setFyggPageNum(String fyggPageNum) {
		this.fyggPageNum = fyggPageNum;
	}

	public String getWdhmdCount() {
		return wdhmdCount;
	}

	public void setWdhmdCount(String wdhmdCount) {
		this.wdhmdCount = wdhmdCount;
	}

	public String getWdhmdPageNum() {
		return wdhmdPageNum;
	}

	public void setWdhmdPageNum(String wdhmdPageNum) {
		this.wdhmdPageNum = wdhmdPageNum;
	}

	public String getAjlcCount() {
		return ajlcCount;
	}

	public void setAjlcCount(String ajlcCount) {
		this.ajlcCount = ajlcCount;
	}

	public String getAjlcPageNum() {
		return ajlcPageNum;
	}

	public void setAjlcPageNum(String ajlcPageNum) {
		this.ajlcPageNum = ajlcPageNum;
	}

	public String getBgtCount() {
		return bgtCount;
	}

	public void setBgtCount(String bgtCount) {
		this.bgtCount = bgtCount;
	}

	public String getBgtPageNum() {
		return bgtPageNum;
	}

	public void setBgtPageNum(String bgtPageNum) {
		this.bgtPageNum = bgtPageNum;
	}

	public String getSatpartyCount() {
		return satpartyCount;
	}

	public void setSatpartyCount(String satpartyCount) {
		this.satpartyCount = satpartyCount;
	}

	public String getSatpartyPageNum() {
		return satpartyPageNum;
	}

	public void setSatpartyPageNum(String satpartyPageNum) {
		this.satpartyPageNum = satpartyPageNum;
	}

	public String getFdapartyCount() {
		return fdapartyCount;
	}

	public void setFdapartyCount(String fdapartyCount) {
		this.fdapartyCount = fdapartyCount;
	}

	public String getFdapartyPageNum() {
		return fdapartyPageNum;
	}

	public void setFdapartyPageNum(String fdapartyPageNum) {
		this.fdapartyPageNum = fdapartyPageNum;
	}

	public String getEpbpartyCount() {
		return epbpartyCount;
	}

	public void setEpbpartyCount(String epbpartyCount) {
		this.epbpartyCount = epbpartyCount;
	}

	public String getEpbpartyPageNum() {
		return epbpartyPageNum;
	}

	public void setEpbpartyPageNum(String epbpartyPageNum) {
		this.epbpartyPageNum = epbpartyPageNum;
	}

	public String getQtspartyCount() {
		return qtspartyCount;
	}

	public void setQtspartyCount(String qtspartyCount) {
		this.qtspartyCount = qtspartyCount;
	}

	public String getQtspartyPageNum() {
		return qtspartyPageNum;
	}

	public void setQtspartyPageNum(String qtspartyPageNum) {
		this.qtspartyPageNum = qtspartyPageNum;
	}

	public String getMocpartyCount() {
		return mocpartyCount;
	}

	public void setMocpartyCount(String mocpartyCount) {
		this.mocpartyCount = mocpartyCount;
	}

	public String getMocpartyPageNum() {
		return mocpartyPageNum;
	}

	public void setMocpartyPageNum(String mocpartyPageNum) {
		this.mocpartyPageNum = mocpartyPageNum;
	}

	public String getPbcpartyCount() {
		return pbcpartyCount;
	}

	public void setPbcpartyCount(String pbcpartyCount) {
		this.pbcpartyCount = pbcpartyCount;
	}

	public String getPbcpartyPageNum() {
		return pbcpartyPageNum;
	}

	public void setPbcpartyPageNum(String pbcpartyPageNum) {
		this.pbcpartyPageNum = pbcpartyPageNum;
	}

	public String getXzhmdCount() {
		return xzhmdCount;
	}

	public void setXzhmdCount(String xzhmdCount) {
		this.xzhmdCount = xzhmdCount;
	}

	public String getXzhmdPageNum() {
		return xzhmdPageNum;
	}

	public void setXzhmdPageNum(String xzhmdPageNum) {
		this.xzhmdPageNum = xzhmdPageNum;
	}

	public String getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(String newsCount) {
		this.newsCount = newsCount;
	}

	public String getNewsPageNum() {
		return newsPageNum;
	}

	public void setNewsPageNum(String newsPageNum) {
		this.newsPageNum = newsPageNum;
	}

	public String getZcdjCount() {
		return zcdjCount;
	}

	public void setZcdjCount(String zcdjCount) {
		this.zcdjCount = zcdjCount;
	}

	public String getZcdjPageNum() {
		return zcdjPageNum;
	}

	public void setZcdjPageNum(String zcdjPageNum) {
		this.zcdjPageNum = zcdjPageNum;
	}

	public String getZccfCount() {
		return zccfCount;
	}

	public void setZccfCount(String zccfCount) {
		this.zccfCount = zccfCount;
	}

	public String getZccfPageNum() {
		return zccfPageNum;
	}

	public void setZccfPageNum(String zccfPageNum) {
		this.zccfPageNum = zccfPageNum;
	}

	public String getJyycCount() {
		return jyycCount;
	}

	public void setJyycCount(String jyycCount) {
		this.jyycCount = jyycCount;
	}

	public String getJyycPageNum() {
		return jyycPageNum;
	}

	public void setJyycPageNum(String jyycPageNum) {
		this.jyycPageNum = jyycPageNum;
	}

	public String getJobCount() {
		return jobCount;
	}

	public void setJobCount(String jobCount) {
		this.jobCount = jobCount;
	}

	public String getJobPageNum() {
		return jobPageNum;
	}

	public void setJobPageNum(String jobPageNum) {
		this.jobPageNum = jobPageNum;
	}
	
}
