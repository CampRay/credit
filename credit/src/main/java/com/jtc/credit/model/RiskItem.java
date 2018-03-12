package com.jtc.credit.model;

//风控结果信息类
public class RiskItem {

	private String dataType;
	private String dataTypeStr;
	private String entryId;
	private String title;
	private String body;
	private String sortTime;
	private String sortTimeString;

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}	

	public String getDataTypeStr() {
		if(dataType!=null&&!dataType.isEmpty()){
			if(dataType.equals("satparty")){dataTypeStr="税务";}			
			if(dataType.equals("fdaparty")){dataTypeStr="食品药品监督";}
            if(dataType.equals("epbparty")){dataTypeStr="环保";}
            if(dataType.equals("qtsparty")){dataTypeStr="质监";}
            if(dataType.equals("xzhmd")){dataTypeStr="受惩黑名单";}
            if(dataType.equals("mocparty")){dataTypeStr="车管所";}
            if(dataType.equals("pbcparty")){dataTypeStr="人行银监";}
            if(dataType.equals("news")){dataTypeStr="新闻媒体";}
            if(dataType.equals("cpws")){dataTypeStr="裁判文书";}
            if(dataType.equals("ktgg")){dataTypeStr="开庭公告";}
            if(dataType.equals("zxgg")){dataTypeStr="执行公告";}
            if(dataType.equals("shixin")){dataTypeStr="失信公告";}
            if(dataType.equals("fygg")){dataTypeStr="法院公告";}
            if(dataType.equals("ajlc")){dataTypeStr="案件流程";}
            if(dataType.equals("bgt")){dataTypeStr="瀑光台";}
            if(dataType.equals("wdhmd")){dataTypeStr="网贷黑名单";}
            if(dataType.equals("zcdj")){dataTypeStr="资产冻结";}
            if(dataType.equals("zccf")){dataTypeStr="资产查封";}
            if(dataType.equals("jyyc")){dataTypeStr="经营异常";}
            if(dataType.equals("job")){dataTypeStr="招聘数据";}
		}
		return dataTypeStr;
	}

	public void setDataTypeStr(String dataTypeStr) {
		this.dataTypeStr = dataTypeStr;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSortTime() {
		return sortTime;
	}

	public void setSortTime(String sortTime) {
		this.sortTime = sortTime;
	}

	public String getSortTimeString() {
		return sortTimeString;
	}

	public void setSortTimeString(String sortTimeString) {
		this.sortTimeString = sortTimeString;
	}

}
