package com.jtc.credit.commons;


import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.jtc.credit.model.Alter;
import com.jtc.credit.model.Basic;
import com.jtc.credit.model.CaseInfo;
import com.jtc.credit.model.Entinv;
import com.jtc.credit.model.Filiation;
import com.jtc.credit.model.FinalShareHolder;
import com.jtc.credit.model.Frinv;
import com.jtc.credit.model.Frposition;
import com.jtc.credit.model.Liquidation;
import com.jtc.credit.model.MorDetail;
import com.jtc.credit.model.MorGuaInfo;
import com.jtc.credit.model.Person;
import com.jtc.credit.model.PunishBreak;
import com.jtc.credit.model.Punished;
import com.jtc.credit.model.RiskItem;
import com.jtc.credit.model.Ryposfr;
import com.jtc.credit.model.Ryposper;
import com.jtc.credit.model.Rypossha;
import com.jtc.credit.model.ShareHolder;
import com.jtc.credit.model.SharesFrost;
import com.jtc.credit.model.SharesImpawn;
import com.jtc.credit.model.WsBankCardDataModel;
import com.jtc.credit.model.WsIdCardDataModel;
import com.jtc.credit.model.WsResponseModel;
import com.jtc.credit.model.WsRiskDataModel;
import com.jtc.credit.model.WsSaicDataModel;

public class WsXmlToBean {
	/**
	 * 解析身份证认证xml结果到数据Bean
	 * @param xml
	 * @return
	 */
	public static WsResponseModel<WsIdCardDataModel> parseTextToIdCardBean(String xml){
		WsResponseModel<WsIdCardDataModel> responseModel=new WsResponseModel<WsIdCardDataModel>();		
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            Element metaElt=rootElt.element("meta");
            if(metaElt!=null){
	            responseModel.setService(metaElt.elementTextTrim("service"));
	            responseModel.setCode(metaElt.elementTextTrim("code"));
	            responseModel.setMessage(metaElt.elementTextTrim("message"));
            }
            
            Element dataElt=rootElt.element("data");
            if(dataElt!=null){
            	WsIdCardDataModel dataModel = new WsIdCardDataModel();
	            dataModel.setResult(dataElt.elementTextTrim("result"));
	            dataModel.setName(dataElt.elementTextTrim("name"));
	            dataModel.setBirthday(dataElt.elementTextTrim("birthday"));
	            dataModel.setIdNumber(dataElt.elementTextTrim("idnumber"));
	            dataModel.setGender(dataElt.elementTextTrim("gender"));
	            dataModel.setResultCode(dataElt.elementTextTrim("result_code"));
	            dataModel.setPhoto(dataElt.elementTextTrim("photo"));
	            responseModel.setData(dataModel);
            }
            
        } catch (DocumentException e) {
        	responseModel.setCode("502");
        	responseModel.setMessage("解析xml查询结果数据失败，错误原因："+e.getMessage());
        } catch (Exception e) {
        	responseModel.setCode("502");
        	responseModel.setMessage("解析xml查询结果数据失败，错误原因："+e.getMessage());
        }
        return responseModel;
	}
	
	/**
	 * 解析银行卡认证xml结果到数据Bean
	 * @param xml
	 * @return
	 */
	public static WsResponseModel<WsBankCardDataModel> parseTextToBankBean(String xml){
		WsResponseModel<WsBankCardDataModel> responseModel=new WsResponseModel<WsBankCardDataModel>();		
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            Element metaElt=rootElt.element("meta");
            if(metaElt!=null){
	            responseModel.setService(metaElt.elementTextTrim("service"));
	            responseModel.setCode(metaElt.elementTextTrim("code"));
	            responseModel.setMessage(metaElt.elementTextTrim("message"));
            }
            
            Element dataElt=rootElt.element("data");
            if(dataElt!=null){
            	WsBankCardDataModel dataModel = new WsBankCardDataModel();
	            dataModel.setResult(dataElt.elementTextTrim("result"));
	            dataModel.setResultCode(dataElt.elementTextTrim("result_code"));
	            responseModel.setData(dataModel);
            }
            
        } catch (DocumentException e) {
        	responseModel.setCode("502");
        	responseModel.setMessage("解析xml查询结果数据失败，错误原因："+e.getMessage());
        } catch (Exception e) {
        	responseModel.setCode("502");
        	responseModel.setMessage("解析xml查询结果数据失败，错误原因："+e.getMessage());
        }
        return responseModel;
	}
	
	/**
	 * 解析风控核查xml结果到数据Bean
	 * @param xml
	 * @return
	 */
	public static WsResponseModel<WsRiskDataModel> parseTextToRiskBean(String xml){
		WsResponseModel<WsRiskDataModel> responseModel=new WsResponseModel<WsRiskDataModel>();		
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            Element metaElt=rootElt.element("meta");
            if(metaElt!=null){
	            responseModel.setService(metaElt.elementTextTrim("service"));
	            responseModel.setCode(metaElt.elementTextTrim("code"));
	            responseModel.setMessage(metaElt.elementTextTrim("message"));
            }
            
            Element dataElt=rootElt.element("data");
            if(dataElt!=null){
            	WsRiskDataModel dataModel = new WsRiskDataModel();
	            dataModel.setPageNo(dataElt.elementTextTrim("pageNo"));
	            dataModel.setTotalCount(dataElt.elementTextTrim("totalCount"));
	            dataModel.setTotalPageNum(dataElt.elementTextTrim("totalPageNum"));
	            dataModel.setRange(dataElt.elementTextTrim("range"));
	            dataModel.setSearchSeconds(dataElt.elementTextTrim("searchSeconds"));
	            
	            dataModel.setCpwsCount(dataElt.elementTextTrim("cpwsCount"));
	            dataModel.setCpwsPageNum(dataElt.elementTextTrim("cpwsPageNum"));	            
	            dataModel.setKtggCount(dataElt.elementTextTrim("ktggCount"));
	            dataModel.setKtggPageNum(dataElt.elementTextTrim("ktggPageNum"));	            
	            dataModel.setZxggCount(dataElt.elementTextTrim("zxggCount"));
	            dataModel.setZxggPageNum(dataElt.elementTextTrim("zxggPageNum"));	            
	            dataModel.setShixinCount(dataElt.elementTextTrim("shixinCount"));
	            dataModel.setShixinPageNum(dataElt.elementTextTrim("shixinPageNum"));	            
	            dataModel.setFyggCount(dataElt.elementTextTrim("fyggCount"));
	            dataModel.setFyggPageNum(dataElt.elementTextTrim("fyggPageNum"));	            
	            dataModel.setWdhmdCount(dataElt.elementTextTrim("wdhmdCount"));
	            dataModel.setWdhmdPageNum(dataElt.elementTextTrim("wdhmdPageNum"));	            
	            dataModel.setAjlcCount(dataElt.elementTextTrim("ajlcCount"));
	            dataModel.setAjlcPageNum(dataElt.elementTextTrim("ajlcPageNum"));	            
	            dataModel.setBgtCount(dataElt.elementTextTrim("bgtCount"));
	            dataModel.setBgtPageNum(dataElt.elementTextTrim("bgtPageNum"));	            
	            dataModel.setSatpartyCount(dataElt.elementTextTrim("satpartyCount"));
	            dataModel.setSatpartyPageNum(dataElt.elementTextTrim("satpartyPageNum"));	            
	            dataModel.setFdapartyCount(dataElt.elementTextTrim("fdapartyCount"));
	            dataModel.setFdapartyPageNum(dataElt.elementTextTrim("fdapartyPageNum"));	            
	            dataModel.setEpbpartyCount(dataElt.elementTextTrim("epbpartyCount"));
	            dataModel.setEpbpartyPageNum(dataElt.elementTextTrim("epbpartyPageNum"));	            
	            dataModel.setQtspartyCount(dataElt.elementTextTrim("qtspartyCount"));
	            dataModel.setQtspartyPageNum(dataElt.elementTextTrim("qtspartyPageNum"));	            
	            dataModel.setMocpartyCount(dataElt.elementTextTrim("mocpartyCount"));
	            dataModel.setMocpartyPageNum(dataElt.elementTextTrim("mocpartyPageNum"));	            
	            dataModel.setPbcpartyCount(dataElt.elementTextTrim("pbcpartyCount"));
	            dataModel.setPbcpartyPageNum(dataElt.elementTextTrim("pbcpartyPageNum"));	            
	            dataModel.setXzhmdCount(dataElt.elementTextTrim("xzhmdCount"));
	            dataModel.setXzhmdPageNum(dataElt.elementTextTrim("xzhmdPageNum"));	            
	            dataModel.setNewsCount(dataElt.elementTextTrim("newsCount"));
	            dataModel.setNewsPageNum(dataElt.elementTextTrim("newsPageNum"));	            
	            dataModel.setZcdjCount(dataElt.elementTextTrim("zcdjCount"));
	            dataModel.setZcdjPageNum(dataElt.elementTextTrim("zcdjPageNum"));	            
	            dataModel.setZccfCount(dataElt.elementTextTrim("zccfCount"));
	            dataModel.setZccfPageNum(dataElt.elementTextTrim("zccfPageNum"));	            
	            dataModel.setJyycCount(dataElt.elementTextTrim("jyycCount"));
	            dataModel.setJyycPageNum(dataElt.elementTextTrim("jyycPageNum"));
	            dataModel.setJobCount(dataElt.elementTextTrim("jobCount"));
	            dataModel.setJobPageNum(dataElt.elementTextTrim("jobPageNum"));
	            
	            List<Node> nodeList=dataElt.selectNodes("./*/item");
	            for (Node node : nodeList) {
	            	Element itemEle=(Element)node;
	            	RiskItem item=new RiskItem();
	            	item.setDataType(itemEle.elementTextTrim("dataType"));
	            	item.setTitle(itemEle.elementTextTrim("title"));
	            	item.setBody(itemEle.elementTextTrim("body"));
	            	item.setSortTime(itemEle.elementTextTrim("sortTime"));
	            	item.setSortTimeString(itemEle.elementTextTrim("sortTimeString"));
	            	item.setEntryId(itemEle.elementTextTrim("entryId"));	            	
	            	dataModel.getAllItemList().add(item);
				}
	            responseModel.setData(dataModel);
            }
            
        } catch (DocumentException e) {
        	responseModel.setCode("502");
        	responseModel.setMessage("解析xml查询结果数据失败，错误原因："+e.getMessage());
        } catch (Exception e) {
        	responseModel.setCode("502");
        	responseModel.setMessage("解析xml查询结果数据失败，错误原因："+e.getMessage());
        }
        return responseModel;
	}
	
	/**
	 * 解析工商信息xml结果到数据Bean
	 * @param xml
	 * @return
	 */
	public static WsResponseModel<WsSaicDataModel> parseTextToSaicBean(String xml){
		WsResponseModel<WsSaicDataModel> responseModel=new WsResponseModel<WsSaicDataModel>();		
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            Element metaElt=rootElt.element("meta");
            if(metaElt!=null){
	            responseModel.setService(metaElt.elementTextTrim("service"));
	            responseModel.setCode(metaElt.elementTextTrim("code"));
	            responseModel.setMessage(metaElt.elementTextTrim("message"));
            }
            
            Element dataElt=rootElt.element("data");
            if(dataElt!=null){
            	WsSaicDataModel dataModel = new WsSaicDataModel();
            	//企业基本信息
            	Element basicElt=dataElt.element("BASIC");
            	if(basicElt!=null){
	            	List<Element> basicItemList=basicElt.elements("ITEM");
	            	for (Iterator<Element> it = basicItemList.iterator(); it.hasNext();) {
	            		Element itemEle=it.next();
	            		Basic basic=new Basic();
		            	basic.setABUITEM(itemEle.elementTextTrim("ABUITEM"));
		            	basic.setANCHEDATE(itemEle.elementTextTrim("ANCHEDATE"));
		            	basic.setANCHEYEAR(itemEle.elementTextTrim("ANCHEYEAR"));
		            	basic.setCANDATE(itemEle.elementTextTrim("CANDATE"));
		            	basic.setCBUITEM(itemEle.elementTextTrim("CBUITEM"));
		            	basic.setCHANGEDATE(itemEle.elementTextTrim("CHANGEDATE"));
		            	basic.setCREDITCODE(itemEle.elementTextTrim("CREDITCODE"));
		            	basic.setDOM(itemEle.elementTextTrim("DOM"));
		            	basic.setDOMDISTRICT(itemEle.elementTextTrim("DOMDISTRICT"));
		            	basic.setEMPNUM(itemEle.elementTextTrim("EMPNUM"));
		            	basic.setENTNAME(itemEle.elementTextTrim("ENTNAME"));
		            	basic.setENTNAMEENG(itemEle.elementTextTrim("ENTNAMEENG"));
		            	basic.setENTSTATUS(itemEle.elementTextTrim("ENTSTATUS"));
		            	basic.setENTTYPE(itemEle.elementTextTrim("ENTTYPE"));
		            	basic.setESDATE(itemEle.elementTextTrim("ESDATE"));
		            	basic.setFRNAME(itemEle.elementTextTrim("FRNAME"));
		            	basic.setINDUSTRYCOALL(itemEle.elementTextTrim("INDUSTRYCOALL"));
		            	basic.setINDUSTRYCOCODE(itemEle.elementTextTrim("INDUSTRYCOCODE"));
		            	basic.setINDUSTRYCONAME(itemEle.elementTextTrim("INDUSTRYCONAME"));
		            	basic.setINDUSTRYPHYALL(itemEle.elementTextTrim("INDUSTRYPHYALL"));
		            	basic.setINDUSTRYPHYCODE(itemEle.elementTextTrim("INDUSTRYPHYCODE"));
		            	basic.setINDUSTRYPHYNAME(itemEle.elementTextTrim("INDUSTRYPHYNAME"));
		            	basic.setOPFROM(itemEle.elementTextTrim("OPFROM"));
		            	basic.setOPLOC(itemEle.elementTextTrim("OPLOC"));
		            	basic.setOPSCOANDFORM(itemEle.elementTextTrim("OPSCOANDFORM"));
		            	basic.setOPSCOPE(itemEle.elementTextTrim("OPSCOPE"));
		            	basic.setOPTO(itemEle.elementTextTrim("OPTO"));
		            	basic.setORIREGNO(itemEle.elementTextTrim("ORIREGNO"));
		            	basic.setRECCAP(itemEle.elementTextTrim("RECCAP"));
		            	basic.setREGCAP(itemEle.elementTextTrim("REGCAP"));
		            	basic.setREGCAPCUR(itemEle.elementTextTrim("REGCAPCUR"));
		            	basic.setREGNO(itemEle.elementTextTrim("REGNO"));
		            	basic.setREGORG(itemEle.elementTextTrim("REGORG"));
		            	basic.setREGORGCODE(itemEle.elementTextTrim("REGORGCODE"));
		            	basic.setREGORGPROVINCE(itemEle.elementTextTrim("REGORGPROVINCE"));
		            	basic.setREVDATE(itemEle.elementTextTrim("REVDATE"));
		            	basic.setS_EXT_NODENUM(itemEle.elementTextTrim("S_EXT_NODENUM"));
		            	basic.setTEL(itemEle.elementTextTrim("TEL"));
		            	basic.setZSOPSCOPE(itemEle.elementTextTrim("ZSOPSCOPE"));
		            	dataModel.getBasicList().add(basic);
	            	}
            	}
            	
            	//企业股东及出资信息
            	Element shareHolderRoot=dataElt.element("SHAREHOLDER");
            	if(shareHolderRoot!=null){
	            	List<Element> shareHolderList=shareHolderRoot.elements("ITEM");
	            	for (Iterator<Element> it = shareHolderList.iterator(); it.hasNext();) {
	            		Element shareHolderEle=it.next();
	            		ShareHolder shareHolder=new ShareHolder();
	            		shareHolder.setCONDATE(shareHolderEle.elementTextTrim("CONDATE"));
	            		shareHolder.setCONFORM(shareHolderEle.elementTextTrim("CONFORM"));
	            		shareHolder.setCOUNTRY(shareHolderEle.elementTextTrim("COUNTRY"));
	            		shareHolder.setFUNDEDRATIO(shareHolderEle.elementTextTrim("FUNDEDRATIO"));
	            		shareHolder.setINVAMOUNT(shareHolderEle.elementTextTrim("INVAMOUNT"));
	            		shareHolder.setINVSUMFUNDEDRATIO(shareHolderEle.elementTextTrim("INVSUMFUNDEDRATIO"));
	            		shareHolder.setREGCAPCUR(shareHolderEle.elementTextTrim("REGCAPCUR"));
	            		shareHolder.setSHANAME(shareHolderEle.elementTextTrim("SHANAME"));
	            		shareHolder.setSUBCONAM(shareHolderEle.elementTextTrim("SUBCONAM"));
	            		shareHolder.setSUMCONAM(shareHolderEle.elementTextTrim("SUMCONAM"));            		
	            		dataModel.getShareHolderList().add(shareHolder);
	            	}
            	}
            	
            	//企业股东及出资信息
            	Element personRoot=dataElt.element("PERSON");
            	if(personRoot!=null){
	            	List<Element> personList=personRoot.elements("ITEM");
	            	for (Iterator<Element> it = personList.iterator(); it.hasNext();) {
	            		Element personEle=it.next();
	            		Person person=new Person();
	            		person.setNATDATE(personEle.elementTextTrim("NATDATE"));
	            		person.setPERNAME(personEle.elementTextTrim("PERNAME"));
	            		person.setPERSONAMOUNT(personEle.elementTextTrim("PERSONAMOUNT"));
	            		person.setPOSITION(personEle.elementTextTrim("POSITION"));
	            		person.setSEX(personEle.elementTextTrim("SEX"));            		
	            		dataModel.getPersonList().add(person);
	            	}
            	}
            	
            	//企业法定代表人对外投资信息
            	Element frinvRoot=dataElt.element("FRINV");
            	if(frinvRoot!=null){
	            	List<Element> frinvList=frinvRoot.elements("ITEM");
	            	for (Iterator<Element> it = frinvList.iterator(); it.hasNext();) {
	            		Element frinvEle=it.next();
	            		Frinv frinv=new Frinv();
	            		frinv.setCANDATE(frinvEle.elementTextTrim("CANDATE"));
	            		frinv.setCONFORM(frinvEle.elementTextTrim("CONFORM"));       	
	            		frinv.setCURRENCY(frinvEle.elementTextTrim("CURRENCY"));
	            		frinv.setENTNAME(frinvEle.elementTextTrim("ENTNAME"));
	            		frinv.setENTSTATUS(frinvEle.elementTextTrim("ENTSTATUS"));
	            		frinv.setENTTYPE(frinvEle.elementTextTrim("ENTTYPE"));
	            		frinv.setESDATE(frinvEle.elementTextTrim("ESDATE"));
	            		frinv.setFUNDEDRATIO(frinvEle.elementTextTrim("FUNDEDRATIO"));
	            		frinv.setNAME(frinvEle.elementTextTrim("NAME"));
	            		frinv.setPINVAMOUNT(frinvEle.elementTextTrim("PINVAMOUNT"));
	            		frinv.setREGCAP(frinvEle.elementTextTrim("REGCAP"));
	            		frinv.setREGCAPCUR(frinvEle.elementTextTrim("REGCAPCUR"));
	            		frinv.setREGNO(frinvEle.elementTextTrim("REGNO"));
	            		frinv.setREGORG(frinvEle.elementTextTrim("REGORG"));
	            		frinv.setREGORGCODE(frinvEle.elementTextTrim("REGORGCODE"));
	            		frinv.setREVDATE(frinvEle.elementTextTrim("REVDATE"));
	            		frinv.setSUBCONAM(frinvEle.elementTextTrim("SUBCONAM"));            		
	            		dataModel.getFrinvList().add(frinv);
	            	}
            	}
            	
            	//企业法定代表人其他公司任职信息
            	Element frpositionRoot=dataElt.element("FRPOSITION");
            	if(frpositionRoot!=null){
	            	List<Element> frpositionList=frpositionRoot.elements("ITEM");
	            	for (Iterator<Element> it = frpositionList.iterator(); it.hasNext();) {
	            		Element frpositionEle=it.next();
	            		Frposition frposition=new Frposition();
	            		frposition.setCANDATE(frpositionEle.elementTextTrim("CANDATE"));
	            		frposition.setENTNAME(frpositionEle.elementTextTrim("ENTNAME"));
	            		frposition.setENTSTATUS(frpositionEle.elementTextTrim("ENTSTATUS"));
	            		frposition.setENTTYPE(frpositionEle.elementTextTrim("ENTTYPE"));
	            		frposition.setESDATE(frpositionEle.elementTextTrim("ESDATE"));
	            		frposition.setLEREPSIGN(frpositionEle.elementTextTrim("LEREPSIGN"));
	            		frposition.setNAME(frpositionEle.elementTextTrim("NAME"));
	            		frposition.setPOSITION(frpositionEle.elementTextTrim("POSITION"));
	            		frposition.setPPVAMOUNT(frpositionEle.elementTextTrim("PPVAMOUNT"));
	            		frposition.setREGCAP(frpositionEle.elementTextTrim("REGCAP"));
	            		frposition.setREGCAPCUR(frpositionEle.elementTextTrim("REGCAPCUR"));
	            		frposition.setREGNO(frpositionEle.elementTextTrim("REGNO"));
	            		frposition.setREGORG(frpositionEle.elementTextTrim("REGORG"));
	            		frposition.setREGORGCODE(frpositionEle.elementTextTrim("REGORGCODE"));
	            		frposition.setREVDATE(frpositionEle.elementTextTrim("REVDATE"));            		
	            		dataModel.getFrpositionList().add(frposition);
	            	}
            	}
            	
            	//企业对外投资信息
            	Element entinvRoot=dataElt.element("ENTINV");
            	if(entinvRoot!=null){
	            	List<Element> entinvList=entinvRoot.elements("ITEM");
	            	for (Iterator<Element> it = entinvList.iterator(); it.hasNext();) {
	            		Element entinvEle=it.next();
	            		Entinv entinv=new Entinv();
	            		entinv.setBINVVAMOUNT(entinvEle.elementTextTrim("BINVVAMOUNT"));
	            		entinv.setCANDATE(entinvEle.elementTextTrim("CANDATE"));
	            		entinv.setCONFORM(entinvEle.elementTextTrim("CONFORM"));
	            		entinv.setCONGROCUR(entinvEle.elementTextTrim("CONGROCUR"));
	            		entinv.setENTNAME(entinvEle.elementTextTrim("ENTNAME"));
	            		entinv.setENTSTATUS(entinvEle.elementTextTrim("ENTSTATUS"));
	            		entinv.setENTTYPE(entinvEle.elementTextTrim("ENTTYPE"));
	            		entinv.setESDATE(entinvEle.elementTextTrim("ESDATE"));
	            		entinv.setFUNDEDRATIO(entinvEle.elementTextTrim("FUNDEDRATIO"));
	            		entinv.setNAME(entinvEle.elementTextTrim("NAME"));
	            		entinv.setREGCAP(entinvEle.elementTextTrim("REGCAP"));
	            		entinv.setREGCAPCUR(entinvEle.elementTextTrim("REGCAPCUR"));
	            		entinv.setREGNO(entinvEle.elementTextTrim("REGNO"));
	            		entinv.setREGORG(entinvEle.elementTextTrim("REGORG"));
	            		entinv.setREGORGCODE(entinvEle.elementTextTrim("REGORGCODE"));
	            		entinv.setREVDATE(entinvEle.elementTextTrim("REVDATE"));
	            		entinv.setSUBCONAM(entinvEle.elementTextTrim("SUBCONAM"));            		            		            		            		            	            		
	            		dataModel.getEntinvList().add(entinv);
	            	}
            	}
            	
            	//最终控股股东信息
            	Element finalShareHolderRoot=dataElt.element("FINALSHAREHOLDER");
            	if(finalShareHolderRoot!=null){
	            	List<Element> finalShareHolderList=finalShareHolderRoot.elements("ITEM");
	            	for (Iterator<Element> it = finalShareHolderList.iterator(); it.hasNext();) {
	            		Element finalShareHolderEle=it.next();
	            		FinalShareHolder finalShareHolder=new FinalShareHolder();
	            		finalShareHolder.setCAPITALCHAIN(finalShareHolderEle.elementTextTrim("CAPITALCHAIN"));
	            		finalShareHolder.setCAPITALCHAINEX(finalShareHolderEle.elementTextTrim("CAPITALCHAINEX"));
	            		finalShareHolder.setFINALACCONAM(finalShareHolderEle.elementTextTrim("FINALACCONAM"));
	            		finalShareHolder.setFINALCONAM(finalShareHolderEle.elementTextTrim("FINALCONAM"));
	            		finalShareHolder.setFINALCONDATE(finalShareHolderEle.elementTextTrim("FINALCONDATE"));
	            		finalShareHolder.setFINALCONFORM(finalShareHolderEle.elementTextTrim("FINALCONFORM"));
	            		finalShareHolder.setFINALCOUNTRY(finalShareHolderEle.elementTextTrim("FINALCOUNTRY"));
	            		finalShareHolder.setFINALCURRENCY(finalShareHolderEle.elementTextTrim("FINALCURRENCY"));
	            		finalShareHolder.setFINALENTNAME(finalShareHolderEle.elementTextTrim("FINALENTNAME"));
	            		finalShareHolder.setFINALINVTYPE(finalShareHolderEle.elementTextTrim("FINALINVTYPE"));
	            		finalShareHolder.setFINALRATIO(finalShareHolderEle.elementTextTrim("FINALRATIO"));
	            		finalShareHolder.setFINALSUBCONAM(finalShareHolderEle.elementTextTrim("FINALSUBCONAM"));            		            		
	            		dataModel.getFinalShareHolderList().add(finalShareHolder);
	            	}
            	}
            	
            	//企业历史变更信息
            	Element alterRoot=dataElt.element("ALTER");
            	if(alterRoot!=null){
	            	List<Element> alterList=alterRoot.elements("ITEM");
	            	for (Iterator<Element> it = alterList.iterator(); it.hasNext();) {
	            		Element alterEle=it.next();
	            		Alter alter=new Alter();
	            		alter.setALTAF(alterEle.elementTextTrim("ALTAF"));
	            		alter.setALTBE(alterEle.elementTextTrim("ALTBE"));
	            		alter.setALTDATE(alterEle.elementTextTrim("ALTDATE"));
	            		alter.setALTITEM(alterEle.elementTextTrim("ALTITEM"));            		            		
	            		dataModel.getAlterList().add(alter);
	            	}
            	}
            	
            	//企业分支机构信息
            	Element filiationRoot=dataElt.element("FILIATION");
            	if(filiationRoot!=null){
	            	List<Element> filiationList=filiationRoot.elements("ITEM");
	            	for (Iterator<Element> it = filiationList.iterator(); it.hasNext();) {
	            		Element filiationEle=it.next();
	            		Filiation filiation=new Filiation();
	            		filiation.setBRADDR(filiationEle.elementTextTrim("BRADDR"));
	            		filiation.setBRNAME(filiationEle.elementTextTrim("BRNAME"));   
	            		filiation.setBRPRINCIPAL(filiationEle.elementTextTrim("BRPRINCIPAL"));
	            		filiation.setBRREGNO(filiationEle.elementTextTrim("BRREGNO"));
	            		filiation.setCBUITEM(filiationEle.elementTextTrim("CBUITEM"));
	            		dataModel.getFiliationList().add(filiation);
	            	}
            	}
            	
            	//股权出质历史信息
            	Element sharesImpawnRoot=dataElt.element("SHARESIMPAWN");
            	if(sharesImpawnRoot!=null){
	            	List<Element> sharesImpawnList=sharesImpawnRoot.elements("ITEM");
	            	for (Iterator<Element> it = sharesImpawnList.iterator(); it.hasNext();) {
	            		Element sharesImpawnEle=it.next();
	            		SharesImpawn sharesImpawn=new SharesImpawn();
	            		sharesImpawn.setIMPAM(sharesImpawnEle.elementTextTrim("IMPAM"));
	            		sharesImpawn.setIMPEXAEEP(sharesImpawnEle.elementTextTrim("IMPEXAEEP"));
	            		sharesImpawn.setIMPONRECDATE(sharesImpawnEle.elementTextTrim("IMPONRECDATE"));
	            		sharesImpawn.setIMPORG(sharesImpawnEle.elementTextTrim("IMPORG"));            		
	            		sharesImpawn.setIMPORGTYPE(sharesImpawnEle.elementTextTrim("IMPORGTYPE"));
	            		sharesImpawn.setIMPSANDATE(sharesImpawnEle.elementTextTrim("IMPSANDATE"));
	            		sharesImpawn.setIMPTO(sharesImpawnEle.elementTextTrim("IMPTO"));
	            		dataModel.getSharesImpawnList().add(sharesImpawn);
	            	}
            	}
            	
            	//动产抵押信息
            	Element morDetailRoot=dataElt.element("MORDETAIL");
            	if(morDetailRoot!=null){
	            	List<Element> morDetailList=morDetailRoot.elements("ITEM");
	            	for (Iterator<Element> it = morDetailList.iterator(); it.hasNext();) {
	            		Element morDetailEle=it.next();
	            		MorDetail morDetail=new MorDetail();
	            		morDetail.setAPPREGREA(morDetailEle.elementTextTrim("APPREGREA"));
	            		morDetail.setCANDATE(morDetailEle.elementTextTrim("CANDATE"));
	            		morDetail.setMORE(morDetailEle.elementTextTrim("MORE"));
	            		morDetail.setMORREG_ID(morDetailEle.elementTextTrim("MORREG_ID"));
	            		morDetail.setMORREGCNO(morDetailEle.elementTextTrim("MORREGCNO"));
	            		morDetail.setMORTGAGOR(morDetailEle.elementTextTrim("MORTGAGOR"));
	            		morDetail.setMORTYPE(morDetailEle.elementTextTrim("MORTYPE"));
	            		morDetail.setPEFPERFORM(morDetailEle.elementTextTrim("PEFPERFORM"));
	            		morDetail.setPEFPERTO(morDetailEle.elementTextTrim("PEFPERTO"));
	            		morDetail.setPRICLASECAM(morDetailEle.elementTextTrim("PRICLASECAM"));
	            		morDetail.setPRICLASECKIND(morDetailEle.elementTextTrim("PRICLASECKIND"));
	            		morDetail.setREGIDATE(morDetailEle.elementTextTrim("REGIDATE"));
	            		morDetail.setREGORG(morDetailEle.elementTextTrim("REGORG"));
	            		morDetail.setREGORGCODE(morDetailEle.elementTextTrim("REGORGCODE"));            		            		
	            		dataModel.getMorDetailList().add(morDetail);
	            	}
            	}
            	
            	//动产抵押物信息
            	Element morGuaInfoRoot=dataElt.element("MORGUAINFO");
            	if(morGuaInfoRoot!=null){
	            	List<Element> morGuaInfoList=morGuaInfoRoot.elements("ITEM");
	            	for (Iterator<Element> it = morGuaInfoList.iterator(); it.hasNext();) {
	            		Element morGuaInfoEle=it.next();
	            		MorGuaInfo morGuaInfo=new MorGuaInfo();
	            		morGuaInfo.setGUANAME(morGuaInfoEle.elementTextTrim("GUANAME"));
	            		morGuaInfo.setMORREG_ID(morGuaInfoEle.elementTextTrim("MORREG_ID"));
	            		morGuaInfo.setQUAN(morGuaInfoEle.elementTextTrim("QUAN"));
	            		morGuaInfo.setVALUE(morGuaInfoEle.elementTextTrim("VALUE"));            		
	            		dataModel.getMorGuaInfoList().add(morGuaInfo);
	            	}
            	}
            	
            	//失信被执行人信息
            	Element punishBreakRoot=dataElt.element("PUNISHBREAK");
            	if(punishBreakRoot!=null){
	            	List<Element> punishBreakList=punishBreakRoot.elements("ITEM");
	            	for (Iterator<Element> it = punishBreakList.iterator(); it.hasNext();) {
	            		Element punishBreakEle=it.next();
	            		PunishBreak punishBreak=new PunishBreak();
	            		punishBreak.setAGECLEAN(punishBreakEle.elementTextTrim("AGECLEAN"));
	            		punishBreak.setAREANAMECLEAN(punishBreakEle.elementTextTrim("AREANAMECLEAN"));
	            		punishBreak.setBUSINESSENTITY(punishBreakEle.elementTextTrim("BUSINESSENTITY"));
	            		punishBreak.setCARDNUM(punishBreakEle.elementTextTrim("CARDNUM"));
	            		punishBreak.setCASECODE(punishBreakEle.elementTextTrim("CASECODE"));
	            		punishBreak.setCOURTNAME(punishBreakEle.elementTextTrim("COURTNAME"));
	            		punishBreak.setDISRUPTTYPENAME(punishBreakEle.elementTextTrim("DISRUPTTYPENAME"));
	            		punishBreak.setDUTY(punishBreakEle.elementTextTrim("DUTY"));
	            		punishBreak.setEXITDATE(punishBreakEle.elementTextTrim("EXITDATE"));
	            		punishBreak.setFOCUSNUMBER(punishBreakEle.elementTextTrim("FOCUSNUMBER"));
	            		punishBreak.setGISTID(punishBreakEle.elementTextTrim("GISTID"));
	            		punishBreak.setGISTUNIT(punishBreakEle.elementTextTrim("GISTUNIT"));
	            		punishBreak.setINAMECLEAN(punishBreakEle.elementTextTrim("INAMECLEAN"));
	            		punishBreak.setPERFORMANCE(punishBreakEle.elementTextTrim("PERFORMANCE"));
	            		punishBreak.setPERFORMEDPART(punishBreakEle.elementTextTrim("PERFORMEDPART"));
	            		punishBreak.setPUBLISHDATECLEAN(punishBreakEle.elementTextTrim("PUBLISHDATECLEAN"));
	            		punishBreak.setREGDATECLEAN(punishBreakEle.elementTextTrim("REGDATECLEAN"));
	            		punishBreak.setSEXYCLEAN(punishBreakEle.elementTextTrim("SEXYCLEAN"));
	            		punishBreak.setTYPE(punishBreakEle.elementTextTrim("TYPE"));
	            		punishBreak.setUNPERFORMPART(punishBreakEle.elementTextTrim("UNPERFORMPART"));
	            		punishBreak.setYSFZD(punishBreakEle.elementTextTrim("YSFZD"));            		            		            		            	
	            		dataModel.getPunishBreakList().add(punishBreak);
	            	}
            	}
            	
            	//被执行人信息
            	Element punishedRoot=dataElt.element("PUNISHED");
            	if(punishedRoot!=null){
	            	List<Element> punishedList=punishedRoot.elements("ITEM");
	            	for (Iterator<Element> it = punishedList.iterator(); it.hasNext();) {
	            		Element punishedEle=it.next();
	            		Punished punished=new Punished();
	            		punished.setAGECLEAN(punishedEle.elementTextTrim("AGECLEAN"));
	            		punished.setAREANAMECLEAN(punishedEle.elementTextTrim("AREANAMECLEAN"));
	            		punished.setCARDNUMCLEAN(punishedEle.elementTextTrim("CARDNUMCLEAN"));
	            		punished.setCASECODE(punishedEle.elementTextTrim("CASECODE"));
	            		punished.setCASESTATE(punishedEle.elementTextTrim("CASESTATE"));
	            		punished.setCOURTNAME(punishedEle.elementTextTrim("COURTNAME"));
	            		punished.setEXECMONEY(punishedEle.elementTextTrim("EXECMONEY"));
	            		punished.setFOCUSNUMBER(punishedEle.elementTextTrim("FOCUSNUMBER"));
	            		punished.setINAMECLEAN(punishedEle.elementTextTrim("INAMECLEAN"));
	            		punished.setREGDATECLEAN(punishedEle.elementTextTrim("REGDATECLEAN"));
	            		punished.setSEXYCLEAN(punishedEle.elementTextTrim("SEXYCLEAN"));
	            		punished.setYSFZD(punishedEle.elementTextTrim("YSFZD"));            		
	            		dataModel.getPunishedList().add(punished);
	            	}
            	}
            	
            	//股权冻结历史信息
            	Element sharesFrostRoot=dataElt.element("SHARESFROST");
            	if(sharesFrostRoot!=null){
	            	List<Element> sharesFrostList=sharesFrostRoot.elements("ITEM");
	            	for (Iterator<Element> it = sharesFrostList.iterator(); it.hasNext();) {
	            		Element sharesFrostEle=it.next();
	            		SharesFrost sharesFrost=new SharesFrost();
	            		sharesFrost.setFROAM(sharesFrostEle.elementTextTrim("FROAM"));
	            		sharesFrost.setFROAUTH(sharesFrostEle.elementTextTrim("FROAUTH"));
	            		sharesFrost.setFRODOCNO(sharesFrostEle.elementTextTrim("FRODOCNO"));
	            		sharesFrost.setFROFROM(sharesFrostEle.elementTextTrim("FROFROM"));
	            		sharesFrost.setFROTO(sharesFrostEle.elementTextTrim("FROTO"));
	            		sharesFrost.setTHAWAUTH(sharesFrostEle.elementTextTrim("THAWAUTH"));
	            		sharesFrost.setTHAWCOMMENT(sharesFrostEle.elementTextTrim("THAWCOMMENT"));
	            		sharesFrost.setTHAWDATE(sharesFrostEle.elementTextTrim("THAWDATE"));
	            		sharesFrost.setTHAWDOCNO(sharesFrostEle.elementTextTrim("THAWDOCNO"));            		
	            		dataModel.getSharesFrostList().add(sharesFrost);
	            	}
            	}
            	
            	//清算信息
            	Element liquidationRoot=dataElt.element("LIQUIDATION");
            	if(liquidationRoot!=null){
	            	List<Element> liquidationList=liquidationRoot.elements("ITEM");
	            	for (Iterator<Element> it = liquidationList.iterator(); it.hasNext();) {
	            		Element liquidationEle=it.next();
	            		Liquidation liquidation=new Liquidation();
	            		liquidation.setCLAIMTRANEE(liquidationEle.elementTextTrim("CLAIMTRANEE"));
	            		liquidation.setDEBTTRANEE(liquidationEle.elementTextTrim("DEBTTRANEE"));
	            		liquidation.setLIGENDDATE(liquidationEle.elementTextTrim("LIGENDDATE"));
	            		liquidation.setLIGENTITY(liquidationEle.elementTextTrim("LIGENTITY"));
	            		liquidation.setLIGPRINCIPAL(liquidationEle.elementTextTrim("LIGPRINCIPAL"));
	            		liquidation.setLIGST(liquidationEle.elementTextTrim("LIGST"));
	            		liquidation.setLIQMEN(liquidationEle.elementTextTrim("LIQMEN"));
	            		liquidation.setTEL(liquidationEle.elementTextTrim("TEL"));            		            		
	            		dataModel.getLiquidationList().add(liquidation);
	            	}
            	}
            	
            	//清算信息
            	Element caseInfoRoot=dataElt.element("CASEINFO");
            	if(caseInfoRoot!=null){
	            	List<Element> caseInfoList=caseInfoRoot.elements("ITEM");
	            	for (Iterator<Element> it = caseInfoList.iterator(); it.hasNext();) {
	            		Element caseInfoEle=it.next();
	            		CaseInfo caseInfo=new CaseInfo();
	            		caseInfo.setCASEREASON(caseInfoEle.elementTextTrim("CASEREASON"));
	            		caseInfo.setCASERESULT(caseInfoEle.elementTextTrim("CASERESULT"));
	            		caseInfo.setCASETIME(caseInfoEle.elementTextTrim("CASETIME"));
	            		caseInfo.setCASETYPE(caseInfoEle.elementTextTrim("CASETYPE"));
	            		caseInfo.setCASEVAL(caseInfoEle.elementTextTrim("CASEVAL"));
	            		caseInfo.setCERNO(caseInfoEle.elementTextTrim("CERNO"));
	            		caseInfo.setEXESORT(caseInfoEle.elementTextTrim("EXESORT"));
	            		caseInfo.setILLEGFACT(caseInfoEle.elementTextTrim("ILLEGFACT"));
	            		caseInfo.setNAME(caseInfoEle.elementTextTrim("NAME"));
	            		caseInfo.setPENAM(caseInfoEle.elementTextTrim("PENAM"));
	            		caseInfo.setPENAUTH(caseInfoEle.elementTextTrim("PENAUTH"));
	            		caseInfo.setPENBASIS(caseInfoEle.elementTextTrim("PENBASIS"));
	            		caseInfo.setPENDECISSDATE(caseInfoEle.elementTextTrim("PENDECISSDATE"));
	            		caseInfo.setPENDECNO(caseInfoEle.elementTextTrim("PENDECNO"));
	            		caseInfo.setPENEXEST(caseInfoEle.elementTextTrim("PENEXEST"));
	            		caseInfo.setPENRESULT(caseInfoEle.elementTextTrim("PENRESULT"));
	            		caseInfo.setPENTYPE(caseInfoEle.elementTextTrim("PENTYPE"));            		
	            		dataModel.getCaseInfoList().add(caseInfo);
	            	}
            	}
            	
            	
            	//按法人查询的企业法人基本信息
            	Element ryposfrRoot=dataElt.element("RYPOSFR");
            	if(ryposfrRoot!=null){
	            	List<Element> ryposfrList=ryposfrRoot.elements("ITEM");
	            	for (Iterator<Element> it = ryposfrList.iterator(); it.hasNext();) {
	            		Element ryposfrElt=it.next(); 
	            		Ryposfr ryposfr=new Ryposfr();
	            		ryposfr.setCREDITCODE(ryposfrElt.elementTextTrim("CREDITCODE"));
	            		ryposfr.setENTNAME(ryposfrElt.elementTextTrim("ENTNAME"));
	            		ryposfr.setENTSTATUS(ryposfrElt.elementTextTrim("ENTSTATUS"));
	            		ryposfr.setENTTYPE(ryposfrElt.elementTextTrim("ENTTYPE"));
	            		ryposfr.setREGCAP(ryposfrElt.elementTextTrim("REGCAP"));
	            		ryposfr.setREGCAPCUR(ryposfrElt.elementTextTrim("REGCAPCUR"));
	            		ryposfr.setREGNO(ryposfrElt.elementTextTrim("REGNO"));
	            		ryposfr.setRYNAME(ryposfrElt.elementTextTrim("RYNAME"));            		
	            		dataModel.getRyposfrList().add(ryposfr);
	            	}
            	}
            	
            	
            	//按法人查询的企业股东信息
            	Element ryposshaRoot=dataElt.element("RYPOSSHA");
            	if(ryposshaRoot!=null){
	            	List<Element> ryposshaList=ryposshaRoot.elements("ITEM");
	            	for (Iterator<Element> it = ryposshaList.iterator(); it.hasNext();) {
	            		Element ryposshaElt=it.next(); 
	            		Rypossha rypossha=new Rypossha();
	            		rypossha.setCREDITCODE(ryposshaElt.elementTextTrim("CREDITCODE"));
	            		rypossha.setENTNAME(ryposshaElt.elementTextTrim("ENTNAME"));
	            		rypossha.setENTSTATUS(ryposshaElt.elementTextTrim("ENTSTATUS"));
	            		rypossha.setENTTYPE(ryposshaElt.elementTextTrim("ENTTYPE"));
	            		rypossha.setREGCAP(ryposshaElt.elementTextTrim("REGCAP"));
	            		rypossha.setREGCAPCUR(ryposshaElt.elementTextTrim("REGCAPCUR"));
	            		rypossha.setREGNO(ryposshaElt.elementTextTrim("REGNO"));
	            		rypossha.setRYNAME(ryposshaElt.elementTextTrim("RYNAME"));   
	            		rypossha.setSUBCONAM(ryposshaElt.elementTextTrim("SUBCONAM"));
	            		rypossha.setCURRENCY(ryposshaElt.elementTextTrim("CURRENCY"));
	            		rypossha.setCONFORM(ryposshaElt.elementTextTrim("CONFORM"));
	            		rypossha.setFUNDEDRATIO(ryposshaElt.elementTextTrim("FUNDEDRATIO"));            		
	            		dataModel.getRyposshaList().add(rypossha);
	            	}
            	}
            	
            	//按法人查询的企业主要管理人员基本信息
            	Element ryposperRoot=dataElt.element("RYPOSPER");
            	if(ryposperRoot!=null){
	            	List<Element> ryposperList=ryposperRoot.elements("ITEM");
	            	for (Iterator<Element> it = ryposperList.iterator(); it.hasNext();) {
	            		Element ryposperElt=it.next();            		            	
	            		Ryposper ryposper=new Ryposper();
	            		ryposper.setCREDITCODE(ryposperElt.elementTextTrim("CREDITCODE"));
	            		ryposper.setENTNAME(ryposperElt.elementTextTrim("ENTNAME"));
	            		ryposper.setENTSTATUS(ryposperElt.elementTextTrim("ENTSTATUS"));
	            		ryposper.setENTTYPE(ryposperElt.elementTextTrim("ENTTYPE"));
	            		ryposper.setREGCAP(ryposperElt.elementTextTrim("REGCAP"));
	            		ryposper.setREGCAPCUR(ryposperElt.elementTextTrim("REGCAPCUR"));
	            		ryposper.setREGNO(ryposperElt.elementTextTrim("REGNO"));
	            		ryposper.setRYNAME(ryposperElt.elementTextTrim("RYNAME"));  
	            		ryposper.setPOSITION(ryposperElt.elementTextTrim("POSITION"));            		
	            		dataModel.getRyposperList().add(ryposper);
	            	}
            	}
            	
            	//按法人查询的清算信息
            	Element personCaseInfoRoot=dataElt.element("PERSONCASEINFO");
            	if(personCaseInfoRoot!=null){
	            	List<Element> personCaseInfoList=personCaseInfoRoot.elements("ITEM");
	            	for (Iterator<Element> it = personCaseInfoList.iterator(); it.hasNext();) {
	            		Element caseInfoEle=it.next();
	            		CaseInfo caseInfo=new CaseInfo();
	            		caseInfo.setCASEREASON(caseInfoEle.elementTextTrim("CASEREASON"));
	            		caseInfo.setCASERESULT(caseInfoEle.elementTextTrim("CASERESULT"));
	            		caseInfo.setCASETIME(caseInfoEle.elementTextTrim("CASETIME"));
	            		caseInfo.setCASETYPE(caseInfoEle.elementTextTrim("CASETYPE"));
	            		caseInfo.setCASEVAL(caseInfoEle.elementTextTrim("CASEVAL"));
	            		caseInfo.setCERNO(caseInfoEle.elementTextTrim("CERNO"));
	            		caseInfo.setEXESORT(caseInfoEle.elementTextTrim("EXESORT"));
	            		caseInfo.setILLEGFACT(caseInfoEle.elementTextTrim("ILLEGFACT"));
	            		caseInfo.setNAME(caseInfoEle.elementTextTrim("NAME"));
	            		caseInfo.setPENAM(caseInfoEle.elementTextTrim("PENAM"));
	            		caseInfo.setPENAUTH(caseInfoEle.elementTextTrim("PENAUTH"));
	            		caseInfo.setPENBASIS(caseInfoEle.elementTextTrim("PENBASIS"));
	            		caseInfo.setPENDECISSDATE(caseInfoEle.elementTextTrim("PENDECISSDATE"));
	            		caseInfo.setPENDECNO(caseInfoEle.elementTextTrim("PENDECNO"));
	            		caseInfo.setPENEXEST(caseInfoEle.elementTextTrim("PENEXEST"));
	            		caseInfo.setPENRESULT(caseInfoEle.elementTextTrim("PENRESULT"));
	            		caseInfo.setPENTYPE(caseInfoEle.elementTextTrim("PENTYPE"));            		
	            		dataModel.getCaseInfoList().add(caseInfo);
	            	}
            	}
            	            	            	
	            responseModel.setData(dataModel);
            }
            
        } catch (DocumentException e) {
        	responseModel.setCode("502");
        	responseModel.setMessage("解析xml查询结果数据失败，错误原因："+e.getMessage());
        } catch (Exception e) {
        	responseModel.setCode("502");
        	responseModel.setMessage("解析xml查询结果数据失败，错误原因："+e.getMessage());
        }
        return responseModel;
	}

}
