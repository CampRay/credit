package com.jtc.credit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 工商信息查询结果数据模型
 * @author Phills
 *
 */
public class WsSaicDataModel {
			
	
	//企业法人信息
	private List<Ryposfr> ryposfrList=new ArrayList<Ryposfr>();
	//企业股东信息
	private List<Rypossha> ryposshaList=new ArrayList<Rypossha>();
	//企业主要管理人员信息
	private List<Ryposper> ryposperList=new ArrayList<Ryposper>();
	
	//企业基本信息
	private List<Basic> basicList=new ArrayList<Basic>();
	
	//企业股东及出资信息
	private List<ShareHolder> shareHolderList=new ArrayList<ShareHolder>();
	//企业主要管理人员信息
	private List<Person> personList=new ArrayList<Person>();
	//企业法定代表人对外投资信息
	private List<Frinv> frinvList=new ArrayList<Frinv>();
	//企业法定代表人其他公司任职信息
	private List<Frposition> frpositionList=new ArrayList<Frposition>();
	//企业对外投资信息
	private List<Entinv> entinvList=new ArrayList<Entinv>();
	//最终控股股东信息
	private List<FinalShareHolder> finalShareHolderList=new ArrayList<FinalShareHolder>();
	//企业历史变更信息
	private List<Alter> alterList=new ArrayList<Alter>();
	//企业分支机构信息
	private List<Filiation> filiationList=new ArrayList<Filiation>();
	//股权出质历史信息
	private List<SharesImpawn> sharesImpawnList=new ArrayList<SharesImpawn>();
	//动产抵押信息
	private List<MorDetail> morDetailList=new ArrayList<MorDetail>();
	//动产抵押物信息
	private List<MorGuaInfo> morGuaInfoList=new ArrayList<MorGuaInfo>();
	//失信被执行人信息
	private List<PunishBreak> punishBreakList=new ArrayList<PunishBreak>();
	//被执行人信息
	private List<Punished> punishedList=new ArrayList<Punished>();	
	//股权冻结历史信息
	private List<SharesFrost> sharesFrostList=new ArrayList<SharesFrost>();
	//清算信息
	private List<Liquidation> liquidationList=new ArrayList<Liquidation>();
	//行政处罚历史信息
	private List<CaseInfo> caseInfoList=new ArrayList<CaseInfo>();
	
	
	
	public List<Basic> getBasicList() {
		return basicList;
	}
	public void setBasicList(List<Basic> basicList) {
		this.basicList = basicList;
	}
	public List<ShareHolder> getShareHolderList() {
		return shareHolderList;
	}
	public void setShareHolderList(List<ShareHolder> shareHolderList) {
		this.shareHolderList = shareHolderList;
	}
	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	public List<Frinv> getFrinvList() {
		return frinvList;
	}
	public void setFrinvList(List<Frinv> frinvList) {
		this.frinvList = frinvList;
	}
	public List<Frposition> getFrpositionList() {
		return frpositionList;
	}
	public void setFrpositionList(List<Frposition> frpositionList) {
		this.frpositionList = frpositionList;
	}
	public List<Entinv> getEntinvList() {
		return entinvList;
	}
	public void setEntinvList(List<Entinv> entinvList) {
		this.entinvList = entinvList;
	}
	public List<FinalShareHolder> getFinalShareHolderList() {
		return finalShareHolderList;
	}
	public void setFinalShareHolderList(List<FinalShareHolder> finalShareHolderList) {
		this.finalShareHolderList = finalShareHolderList;
	}
	public List<Alter> getAlterList() {
		return alterList;
	}
	public void setAlterList(List<Alter> alterList) {
		this.alterList = alterList;
	}
	public List<Filiation> getFiliationList() {
		return filiationList;
	}
	public void setFiliationList(List<Filiation> filiationList) {
		this.filiationList = filiationList;
	}
	public List<SharesImpawn> getSharesImpawnList() {
		return sharesImpawnList;
	}
	public void setSharesImpawnList(List<SharesImpawn> sharesImpawnList) {
		this.sharesImpawnList = sharesImpawnList;
	}
	public List<MorDetail> getMorDetailList() {
		return morDetailList;
	}
	public void setMorDetailList(List<MorDetail> morDetailList) {
		this.morDetailList = morDetailList;
	}
	public List<MorGuaInfo> getMorGuaInfoList() {
		return morGuaInfoList;
	}
	public void setMorGuaInfoList(List<MorGuaInfo> morGuaInfoList) {
		this.morGuaInfoList = morGuaInfoList;
	}
	public List<PunishBreak> getPunishBreakList() {
		return punishBreakList;
	}
	public void setPunishBreakList(List<PunishBreak> punishBreakList) {
		this.punishBreakList = punishBreakList;
	}
	public List<Punished> getPunishedList() {
		return punishedList;
	}
	public void setPunishedList(List<Punished> punishedList) {
		this.punishedList = punishedList;
	}
	public List<SharesFrost> getSharesFrostList() {
		return sharesFrostList;
	}
	public void setSharesFrostList(List<SharesFrost> sharesFrostList) {
		this.sharesFrostList = sharesFrostList;
	}
	public List<Liquidation> getLiquidationList() {
		return liquidationList;
	}
	public void setLiquidationList(List<Liquidation> liquidationList) {
		this.liquidationList = liquidationList;
	}
	public List<CaseInfo> getCaseInfoList() {
		return caseInfoList;
	}
	public void setCaseInfoList(List<CaseInfo> caseInfoList) {
		this.caseInfoList = caseInfoList;
	}
	
	
	
	public List<Ryposfr> getRyposfrList() {
		return ryposfrList;
	}
	public void setRyposfrList(List<Ryposfr> ryposfrList) {
		this.ryposfrList = ryposfrList;
	}
	public List<Rypossha> getRyposshaList() {
		return ryposshaList;
	}
	public void setRyposshaList(List<Rypossha> ryposshaList) {
		this.ryposshaList = ryposshaList;
	}
	public List<Ryposper> getRyposperList() {
		return ryposperList;
	}
	public void setRyposperList(List<Ryposper> ryposperList) {
		this.ryposperList = ryposperList;
	}
	

	
	
}
