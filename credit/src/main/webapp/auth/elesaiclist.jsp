<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>工商数据核查</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/style.css">    
    <script src="<%=request.getContextPath()%>/static/js/jquery-1.10.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.bundle.min.js"></script>  
     <script type="text/javascript">  
    	 $(window).resize(function(){
    		 $("#content_row").css("min-height",$(window).height()-110);
    	 });
    	 $(window).keydown(function(event){
	    	  switch(event.keyCode) {
	    	      case 116://F5按键
	    	    	  return false;	    	    	 
	    	  }
	    });
	    jQuery(document).ready(function() {    
	    	$("#content_row").css("min-height",$(window).height()-110);	
	    	var hasResult=${result.data!=null};
	        if(hasResult) $("#exportBtn").show();
	        $("#exportBtn").on("click", function(event) {	    			    		
			    var htmlContent = $(".result-content").html();
			    var htmlText="<div class='row' style='font-family:SimHei'>"+htmlContent+"</div>";				    			    
		        var form = $("<form></form>").attr("action", "export").attr("method", "post");
		        form.append($("<input></input>").attr("type", "hidden").attr("name", "html").attr("value", htmlText));
		        form.appendTo('body').submit().remove();
		    });
		});
    </script>   
</head>
<body>
    <%@include file="header.jsp"%>
    <div class="content">
        <div class="container" style="background-color:white;">
            <div class="row" id="content_row">
                <div class="col-lg-4 col-md-4 col-sm-12">
                    <c:import url="/auth/menu"/>
                </div>
                <div class="col-lg-8 col-md-8 col-sm-12 right-column">
                    <div class="right-content">
                        <div class="right-title"><h3>工商信息查询 （元素）</h3></div>
                        <div class="search-content">
                            <form:form action="elesaiclist" commandName="saicModel" method="post">                            	
                                <div class="form-group form-inline" id="div_keytype">
                                    <label class="col-lg-3 col-md-4 control-label">查询参数：</label>
                                    <div class="col-lgd-9 col-md-8">
                                        <form:select path="keyType" class="w-50">                                            
                                            <form:option value="2">企业名称</form:option>
                                            <form:option value="3">企业注册号或统一社会信用代码</form:option>
                                            <form:option value="4">按法人身份证</form:option>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group  form-inline">
                                    <label class="col-lg-3 col-md-4 control-label">参数值：<span class="text-danger">*</span></label>
                                    <div class="col-lgd-9 col-md-8">
                                        <form:input type="text" class="w-100 p-1" path="key"/>
                                    </div>
                                </div>                                
                                
                                <div class="form-group form-inline bg-secondary p-1">
                                    <label class="col-lg-3 col-md-4 control-label"></label>
                                    <div class="col-lgd-9 col-md-8">
                                        <button type="submit" class="btn btn-info">&nbsp;&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp; &nbsp;</button>
                                        <button type="reset" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;取消&nbsp;&nbsp;&nbsp;&nbsp;</button>
                                        <button type="button" class="btn" id="exportBtn" style="display:none;">&nbsp;&nbsp;&nbsp;&nbsp;导出结果&nbsp;&nbsp;&nbsp;&nbsp;</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                        <c:if test="${errorMsg!=null&&!errorMsg.isEmpty()}">	
                        <div class="text-center text-danger">系统异常：${errorMsg}</div>
                        </c:if>
                        <c:if test="${result!=null&&result.data==null}">	
                        <div class="text-center text-danger">查询失败：${result.code} - ${result.message}</div>
                        </c:if>                        
						<c:if test="${result.data!=null}">		
                        <div id="accordion" class="result-content">
                        	<c:if test="${!saicModel.keyType.equals('4')}">
                        	<div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse1">
							         	企业照面信息
							        </a>
                        		</div>
                            	<div id="collapse1" class="collapse">
                            		<c:if test="${result.data.getBasicList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getBasicList()}" var="item">
                            		<table class="table table-bordered">
	                            		<c:if test="${item.ENTNAME!=null&&!item.ENTNAME.isEmpty()}">
	                                    <tr>
		                                    <td>企业名称：</td>
		                                    <td width="60%">${item.ENTNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ENTNAMEENG!=null&&!item.ENTNAMEENG.isEmpty()}">
	                                	<tr>
		                                    <td>企业英文名称：</td>
		                                    <td width="60%">${item.ENTNAMEENG}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ESDATE!=null&&!item.ESDATE.isEmpty()}">
	                                	<tr>
		                                    <td>成立日期：</td>
		                                    <td width="60%">${item.ESDATE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.CHANGEDATE!=null&&!item.CHANGEDATE.isEmpty()}">
	                                	<tr>
		                                    <td>核准日期：</td>
		                                    <td width="60%">${item.CHANGEDATE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGCAP!=null&&!item.REGCAP.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本(万元)：</td>
		                                    <td width="60%">${item.REGCAP}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGCAPCUR!=null&&!item.REGCAPCUR.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本币种：</td>
		                                    <td width="60%">${item.REGCAPCUR}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.RECCAP!=null&&!item.RECCAP.isEmpty()}">
	                                	<tr>
		                                    <td>实收资本(万元)：</td>
		                                    <td width="60%">${item.RECCAP}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.FRNAME!=null&&!item.FRNAME.isEmpty()}">
	                                	<tr>
		                                    <td>法定代表人/负责人/执行事务合伙人：</td>
		                                    <td width="60%">${item.FRNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.TEL!=null&&!item.TEL.isEmpty()}">
	                                	<tr>
		                                    <td>联系人电话：</td>
		                                    <td width="60%">${item.TEL}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.EMPNUM!=null&&!item.EMPNUM.isEmpty()}">
	                                	<tr>
		                                    <td>员工人数：</td>
		                                    <td width="60%">${item.EMPNUM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGORGPROVINCE!=null&&!item.REGORGPROVINCE.isEmpty()}">
	                                	<tr>
		                                    <td>所在省份：</td>
		                                    <td width="60%">${item.REGORGPROVINCE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGORGCODE!=null&&!item.REGORGCODE.isEmpty()}">
	                                	<tr>
		                                    <td>注册地址行政区编号：</td>
		                                    <td width="60%">${item.REGORGCODE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.OPLOC!=null&&!item.OPLOC.isEmpty()}">
	                                	<tr>
		                                    <td>经营场所：</td>
		                                    <td width="60%">${item.OPLOC}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ZSOPSCOPE!=null&&!item.ZSOPSCOPE.isEmpty()}">
	                                	<tr>
		                                    <td>经营业务范围：</td>
		                                    <td width="60%">${item.ZSOPSCOPE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.OPSCOPE!=null&&!item.OPSCOPE.isEmpty()}">
	                                	<tr>
		                                    <td>经营(业务)范围：</td>
		                                    <td width="60%">${item.OPSCOPE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.OPSCOANDFORM!=null&&!item.OPSCOANDFORM.isEmpty()}">
	                                	<tr>
		                                    <td>经营(业务)范围及方式：</td>
		                                    <td width="60%">${item.OPSCOANDFORM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ABUITEM!=null&&!item.ABUITEM.isEmpty()}">
	                                	<tr>
		                                    <td>许可经营项目：</td>
		                                    <td width="60%">${item.ABUITEM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.CBUITEM!=null&&!item.CBUITEM.isEmpty()}">
	                                	<tr>
		                                    <td>一般经营项目：</td>
		                                    <td width="60%">${item.CBUITEM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INDUSTRYPHYNAME!=null&&!item.INDUSTRYPHYNAME.isEmpty()}">
	                                	<tr>
		                                    <td>行业门类名称：</td>
		                                    <td width="60%">${item.INDUSTRYPHYNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INDUSTRYPHYCODE!=null&&!item.INDUSTRYPHYCODE.isEmpty()}">
	                                	<tr>
		                                    <td>行业门类代码：</td>
		                                    <td width="60%">${item.INDUSTRYPHYCODE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INDUSTRYPHYALL!=null&&!item.INDUSTRYPHYALL.isEmpty()}">
	                                	<tr>
		                                    <td>行业门类代码及名称：</td>
		                                    <td width="60%">${item.INDUSTRYPHYALL}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INDUSTRYCONAME!=null&&!item.INDUSTRYCONAME.isEmpty()}">
	                                	<tr>
		                                    <td>国民经济行业名称：</td>
		                                    <td width="60%">${item.INDUSTRYCONAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INDUSTRYCOCODE!=null&&!item.INDUSTRYCOCODE.isEmpty()}">
	                                	<tr>
		                                    <td>国民经济行业代码：</td>
		                                    <td width="60%">${item.INDUSTRYCOCODE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INDUSTRYCOALL!=null&&!item.INDUSTRYCOALL.isEmpty()}">
	                                	<tr>
		                                    <td>国民经济行业代码及名称：</td>
		                                    <td width="60%">${item.INDUSTRYCOALL}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ENTTYPE!=null&&!item.ENTTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)类型：</td>
		                                    <td width="60%">${item.ENTTYPE}</td>
	                                	</tr>  
	                                	</c:if>
	                                	<c:if test="${item.ENTSTATUS!=null&&!item.ENTSTATUS.isEmpty()}">                        	
	                                	<tr>
		                                    <td>经营状态：</td>
		                                    <td width="60%">${item.ENTSTATUS}</td>
	                                	</tr>
	                                	</c:if>                                	
	                                	<tr>
		                                    <td>经营期限：</td>
		                                    <td width="60%">${item.OPFROM} - ${item.OPTO}</td>
	                                	</tr>                                	
	                                	<c:if test="${item.REGNO!=null&&!item.REGNO.isEmpty()}">
	                                	<tr>
		                                    <td>注册号：</td>
		                                    <td width="60%">${item.REGNO}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ORIREGNO!=null&&!item.ORIREGNO.isEmpty()}">
	                                	<tr>
		                                    <td>原注册号：</td>
		                                    <td width="60%">${item.ORIREGNO}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.DOM!=null&&!item.DOM.isEmpty()}">
	                                	<tr>
		                                    <td>住所：</td>
		                                    <td width="60%">${item.DOM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.DOMDISTRICT!=null&&!item.DOMDISTRICT.isEmpty()}">
	                                	<tr>
		                                    <td>住所所在行政区划：</td>
		                                    <td width="60%">${item.DOMDISTRICT}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ANCHEDATE!=null&&!item.ANCHEDATE.isEmpty()}">
	                                	<tr>
		                                    <td>最后年检日期：</td>
		                                    <td width="60%">${item.ANCHEDATE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ANCHEYEAR!=null&&!item.ANCHEYEAR.isEmpty()}">
	                                	<tr>
		                                    <td>最后年检年度：</td>
		                                    <td width="60%">${item.ANCHEYEAR}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGORG!=null&&!item.REGORG.isEmpty()}">
	                                	<tr>
		                                    <td>登记机关：</td>
		                                    <td width="60%">${item.REGORG}</td>
	                                	</tr> 
	                                	</c:if>
	                                	<c:if test="${item.REVDATE!=null&&!item.REVDATE.isEmpty()}">                             	
	                                	<tr>
		                                    <td>吊销日期：</td>
		                                    <td width="60%">${item.REVDATE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.CANDATE!=null&&!item.CANDATE.isEmpty()}">
	                                	<tr>
		                                    <td>注销日期：</td>
		                                    <td width="60%">${item.CANDATE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.CREDITCODE!=null&&!item.CREDITCODE.isEmpty()}">
	                                	<tr>
		                                    <td>统一社会信用代码：</td>
		                                    <td width="60%">${item.CREDITCODE}</td>
	                                	</tr>
	                                	</c:if>
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse2">
							         	企业股东及出资信息
							        </a>
                        		</div>
                            	<div id="collapse2" class="collapse">
                            		<c:if test="${result.data.getShareHolderList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getShareHolderList()}" var="item">
                            		<table class="table table-bordered">
	                                    <tr>
		                                    <td>股东名称：</td>
		                                    <td width="60%">${item.SHANAME}</td>
	                                	</tr>
	                                	<c:if test="${item.COUNTRY!=null&&!item.COUNTRY.isEmpty()}">
	                                	<tr>
		                                    <td>国别：</td>
		                                    <td width="60%">${item.COUNTRY}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INVAMOUNT!=null&&!item.INVAMOUNT.isEmpty()}">
	                                	<tr>
		                                    <td>股东总数量：</td>
		                                    <td width="60%">${item.INVAMOUNT}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.CONDATE!=null&&!item.CONDATE.isEmpty()}">
	                                	<tr>
		                                    <td>出资日期：</td>
		                                    <td width="60%">${item.CONDATE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.CONFORM!=null&&!item.CONFORM.isEmpty()}">
	                                	<tr>
		                                    <td>出资方式：</td>
		                                    <td width="60%">${item.CONFORM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.SUMCONAM!=null&&!item.SUMCONAM.isEmpty()}">
	                                	<tr>
		                                    <td>股东出资总和(万元)：</td>
		                                    <td width="60%">${item.SUMCONAM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INVSUMFUNDEDRATIO!=null&&!item.INVSUMFUNDEDRATIO.isEmpty()}">
	                                	<tr>
		                                    <td>股东出资比例总和：</td>
		                                    <td width="60%">${item.INVSUMFUNDEDRATIO}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.FUNDEDRATIO!=null&&!item.FUNDEDRATIO.isEmpty()}">
	                                	<tr>
		                                    <td>出资比例：</td>
		                                    <td width="60%">${item.FUNDEDRATIO}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.SUBCONAM!=null&&!item.SUBCONAM.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资额(万元)：</td>
		                                    <td width="60%">${item.SUBCONAM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGCAPCUR!=null&&!item.REGCAPCUR.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资币种：</td>
		                                    <td width="60%">${item.REGCAPCUR}</td>
	                                	</tr>
	                                	</c:if>
	                                		                                		                                	                    	
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse3">
							         	企业主要管理人员信息
							        </a>
                        		</div>
                            	<div id="collapse3" class="collapse">
                            		<c:if test="${result.data.getPersonList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getPersonList()}" var="item">
                            		<table class="table table-bordered">
	                                    <tr>
		                                    <td>人员姓名：</td>
		                                    <td width="60%">${item.PERNAME}</td>
	                                	</tr>
	                                	<c:if test="${item.SEX!=null&&!item.SEX.isEmpty()}">
	                                	<tr>
		                                    <td>性别：</td>
		                                    <td width="60%">${item.SEX}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.NATDATE!=null&&!item.NATDATE.isEmpty()}">
	                                	<tr>
		                                    <td>出生年份：</td>
		                                    <td width="60%">${item.NATDATE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.POSITION!=null&&!item.POSITION.isEmpty()}">
	                                	<tr>
		                                    <td>职务：</td>
		                                    <td width="60%">${item.POSITION}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.PERSONAMOUNT!=null&&!item.PERSONAMOUNT.isEmpty()}">
	                                	<tr>
		                                    <td>人员总数量：</td>
		                                    <td width="60%">${item.PERSONAMOUNT}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                		                                		                                	                    	
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse4">
							         	企业法定代表人对外投资信息
							        </a>
                        		</div>
                            	<div id="collapse4" class="collapse">
                            		<c:if test="${result.data.getFrinvList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getFrinvList()}" var="item">
                            		<table class="table table-bordered">
                            			<c:if test="${item.ENTNAME!=null&&!item.ENTNAME.isEmpty()}">
	                                    <tr>
		                                    <td>企业(机构)名称：</td>
		                                    <td width="60%">${item.ENTNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.NAME!=null&&!item.NAME.isEmpty()}">
	                                	<tr>
		                                    <td>法定代表人姓名：</td>
		                                    <td width="60%">${item.NAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ENTTYPE!=null&&!item.ENTTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)类型：</td>
		                                    <td width="60%">${item.ENTTYPE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGCAP!=null&&!item.REGCAP.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本(万元)：</td>
		                                    <td width="60%">${item.REGCAP}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.REGCAPCUR!=null&&!item.REGCAPCUR.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本币种：</td>
		                                    <td width="60%">${item.REGCAPCUR}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.REGORGCODE!=null&&!item.REGORGCODE.isEmpty()}">
	                                	<tr>
		                                    <td>注册地址行政区编号：</td>
		                                    <td width="60%">${item.REGORGCODE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.REGNO!=null&&!item.REGNO.isEmpty()}">
	                                	<tr>
		                                    <td>注册号：</td>
		                                    <td width="60%">${item.REGNO}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.ENTSTATUS!=null&&!item.ENTSTATUS.isEmpty()}">
	                                	<tr>
		                                    <td>企业状态：</td>
		                                    <td width="60%">${item.ENTSTATUS}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.ESDATE!=null&&!item.ESDATE.isEmpty()}">
	                                	<tr>
		                                    <td>开业日期：</td>
		                                    <td width="60%">${item.ESDATE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.REGORG!=null&&!item.REGORG.isEmpty()}">
	                                	<tr>
		                                    <td>登记机关：</td>
		                                    <td width="60%">${item.REGORG}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CANDATE!=null&&!item.CANDATE.isEmpty()}">
	                                	<tr>
		                                    <td>注销日期：</td>
		                                    <td width="60%">${item.CANDATE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.REVDATE!=null&&!item.REVDATE.isEmpty()}">
	                                	<tr>
		                                    <td>吊销日期：</td>
		                                    <td width="60%">${item.REVDATE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CONFORM!=null&&!item.CONFORM.isEmpty()}">
	                                	<tr>
		                                    <td>出资方式：</td>
		                                    <td width="60%">${item.CONFORM}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.FUNDEDRATIO!=null&&!item.FUNDEDRATIO.isEmpty()}">
	                                	<tr>
		                                    <td>出资比例：</td>
		                                    <td width="60%">${item.FUNDEDRATIO}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.SUBCONAM!=null&&!item.SUBCONAM.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资额(万元)：</td>
		                                    <td width="60%">${item.SUBCONAM}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CURRENCY!=null&&!item.CURRENCY.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资币种：</td>
		                                    <td width="60%">${item.CURRENCY}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.PINVAMOUNT!=null&&!item.PINVAMOUNT.isEmpty()}">
	                                	<tr>
		                                    <td>企业总数量：</td>
		                                    <td width="60%">${item.PINVAMOUNT}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	 	                                		                                	                    
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse5">
							         	企业法定代表人其他公司任职信息
							        </a>
                        		</div>
                            	<div id="collapse5" class="collapse">
                            		<c:if test="${result.data.getFrpositionList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getFrpositionList()}" var="item">
                            		<table class="table table-bordered">
                            			<c:if test="${item.ENTNAME!=null&&!item.ENTNAME.isEmpty()}">
	                                    <tr>
		                                    <td>企业(机构)名称：</td>
		                                    <td width="60%">${item.ENTNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.NAME!=null&&!item.NAME.isEmpty()}">
	                                	<tr>
		                                    <td>法定代表人姓名：</td>
		                                    <td width="60%">${item.NAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ENTTYPE!=null&&!item.ENTTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)类型：</td>
		                                    <td width="60%">${item.ENTTYPE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGCAP!=null&&!item.REGCAP.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本(万元)：</td>
		                                    <td width="60%">${item.REGCAP}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.REGCAPCUR!=null&&!item.REGCAPCUR.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本币种：</td>
		                                    <td width="60%">${item.REGCAPCUR}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.REGORGCODE!=null&&!item.REGORGCODE.isEmpty()}">
	                                	<tr>
		                                    <td>注册地址行政区编号：</td>
		                                    <td width="60%">${item.REGORGCODE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.REGNO!=null&&!item.REGNO.isEmpty()}">
	                                	<tr>
		                                    <td>注册号：</td>
		                                    <td width="60%">${item.REGNO}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.ENTSTATUS!=null&&!item.ENTSTATUS.isEmpty()}">
	                                	<tr>
		                                    <td>企业状态：</td>
		                                    <td width="60%">${item.ENTSTATUS}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.ESDATE!=null&&!item.ESDATE.isEmpty()}">
	                                	<tr>
		                                    <td>开业日期：</td>
		                                    <td width="60%">${item.ESDATE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.REGORG!=null&&!item.REGORG.isEmpty()}">
	                                	<tr>
		                                    <td>登记机关：</td>
		                                    <td width="60%">${item.REGORG}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CANDATE!=null&&!item.CANDATE.isEmpty()}">
	                                	<tr>
		                                    <td>注销日期：</td>
		                                    <td width="60%">${item.CANDATE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.REVDATE!=null&&!item.REVDATE.isEmpty()}">
	                                	<tr>
		                                    <td>吊销日期：</td>
		                                    <td width="60%">${item.REVDATE}</td>
	                                	</tr>
	                                	</c:if>	    	                                	    
	                                	<c:if test="${item.POSITION!=null&&!item.POSITION.isEmpty()}">
	                                	<tr>
		                                    <td>职务：</td>
		                                    <td width="60%">${item.POSITION}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.PINVAMOUNT!=null&&!item.PINVAMOUNT.isEmpty()}">
	                                	<tr>
		                                    <td>企业总数量：</td>
		                                    <td width="60%">${item.PINVAMOUNT}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	 	                                		                                	                    
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse6">
							         	企业对外投资信息
							        </a>
                        		</div>
                            	<div id="collapse6" class="collapse">
                            		<c:if test="${result.data.getEntinvList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getEntinvList()}" var="item">
                            		<table class="table table-bordered">
                            			<c:if test="${item.ENTNAME!=null&&!item.ENTNAME.isEmpty()}">
	                                    <tr>
		                                    <td>企业(机构)名称：</td>
		                                    <td width="60%">${item.ENTNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.NAME!=null&&!item.NAME.isEmpty()}">
	                                	<tr>
		                                    <td>法定代表人姓名：</td>
		                                    <td width="60%">${item.NAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ENTTYPE!=null&&!item.ENTTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)类型：</td>
		                                    <td width="60%">${item.ENTTYPE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGCAP!=null&&!item.REGCAP.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本(万元)：</td>
		                                    <td width="60%">${item.REGCAP}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.REGCAPCUR!=null&&!item.REGCAPCUR.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本币种：</td>
		                                    <td width="60%">${item.REGCAPCUR}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.REGORGCODE!=null&&!item.REGORGCODE.isEmpty()}">
	                                	<tr>
		                                    <td>注册地址行政区编号：</td>
		                                    <td width="60%">${item.REGORGCODE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.REGNO!=null&&!item.REGNO.isEmpty()}">
	                                	<tr>
		                                    <td>注册号：</td>
		                                    <td width="60%">${item.REGNO}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.ENTSTATUS!=null&&!item.ENTSTATUS.isEmpty()}">
	                                	<tr>
		                                    <td>企业状态：</td>
		                                    <td width="60%">${item.ENTSTATUS}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.ESDATE!=null&&!item.ESDATE.isEmpty()}">
	                                	<tr>
		                                    <td>开业日期：</td>
		                                    <td width="60%">${item.ESDATE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.REGORG!=null&&!item.REGORG.isEmpty()}">
	                                	<tr>
		                                    <td>登记机关：</td>
		                                    <td width="60%">${item.REGORG}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CANDATE!=null&&!item.CANDATE.isEmpty()}">
	                                	<tr>
		                                    <td>注销日期：</td>
		                                    <td width="60%">${item.CANDATE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.REVDATE!=null&&!item.REVDATE.isEmpty()}">
	                                	<tr>
		                                    <td>吊销日期：</td>
		                                    <td width="60%">${item.REVDATE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CONFORM!=null&&!item.CONFORM.isEmpty()}">
	                                	<tr>
		                                    <td>出资方式：</td>
		                                    <td width="60%">${item.CONFORM}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.FUNDEDRATIO!=null&&!item.FUNDEDRATIO.isEmpty()}">
	                                	<tr>
		                                    <td>出资比例：</td>
		                                    <td width="60%">${item.FUNDEDRATIO}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.SUBCONAM!=null&&!item.SUBCONAM.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资额(万元)：</td>
		                                    <td width="60%">${item.SUBCONAM}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CURRENCY!=null&&!item.CURRENCY.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资币种：</td>
		                                    <td width="60%">${item.CURRENCY}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.PINVAMOUNT!=null&&!item.PINVAMOUNT.isEmpty()}">
	                                	<tr>
		                                    <td>企业总数量：</td>
		                                    <td width="60%">${item.PINVAMOUNT}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	 	                                		                                	                    
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse7">
							         	最终控股股东信息
							        </a>
                        		</div>
                            	<div id="collapse7" class="collapse">
                            		<c:if test="${result.data.getFinalShareHolderList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getFinalShareHolderList()}" var="item">
                            		<table class="table table-bordered">
                            			<c:if test="${item.FINALENTNAME!=null&&!item.FINALENTNAME.isEmpty()}">
	                                    <tr>
		                                    <td>最终控股股东名称：</td>
		                                    <td width="60%">${item.FINALENTNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.FINALCOUNTRY!=null&&!item.FINALCOUNTRY.isEmpty()}">
	                                	<tr>
		                                    <td>国别：</td>
		                                    <td width="60%">${item.FINALCOUNTRY}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.FINALINVTYPE!=null&&!item.FINALINVTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>股东类型：</td>
		                                    <td width="60%">${item.FINALINVTYPE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.FINALCONFORM!=null&&!item.FINALCONFORM.isEmpty()}">
	                                	<tr>
		                                    <td>出资方式：</td>
		                                    <td width="60%">${item.FINALCONFORM}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.FINALCONDATE!=null&&!item.FINALCONDATE.isEmpty()}">
	                                	<tr>
		                                    <td>出资日期：</td>
		                                    <td width="60%">${item.FINALCONDATE}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.FINALCONAM!=null&&!item.FINALCONAM.isEmpty()}">
	                                	<tr>
		                                    <td>出资额：</td>
		                                    <td width="60%">${item.FINALCONAM}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.FINALRATIO!=null&&!item.FINALRATIO.isEmpty()}">
	                                	<tr>
		                                    <td>出资比例：</td>
		                                    <td width="60%">${item.FINALRATIO}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CAPITALCHAIN!=null&&!item.CAPITALCHAIN.isEmpty()}">
	                                	<tr>
		                                    <td>出资链：</td>
		                                    <td width="60%">${item.CAPITALCHAIN}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CAPITALCHAINEX!=null&&!item.CAPITALCHAINEX.isEmpty()}">
	                                	<tr>
		                                    <td>出资链：</td>
		                                    <td width="60%">${item.CAPITALCHAINEX}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.FINALACCONAM!=null&&!item.FINALACCONAM.isEmpty()}">
	                                	<tr>
		                                    <td>实缴出资额(万元)：</td>
		                                    <td width="60%">${item.FINALACCONAM}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.FINALSUBCONAM!=null&&!item.FINALSUBCONAM.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资额(万元)：</td>
		                                    <td width="60%">${item.FINALSUBCONAM}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.FINALCURRENCY!=null&&!item.FINALCURRENCY.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资额币种：</td>
		                                    <td width="60%">${item.FINALCURRENCY}</td>
	                                	</tr>
	                                	</c:if>	    	                                	    	                                	
	                                	 	                                		                                	                    
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse8">
							         	企业历史变更信息
							        </a>
                        		</div>
                            	<div id="collapse8" class="collapse">
                            		<c:if test="${result.data.getAlterList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getAlterList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.ALTITEM!=null&&!item.ALTITEM.isEmpty()}">
	                                	<tr>
		                                    <td>变更事项：</td>
		                                    <td width="60%">${item.ALTITEM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ALTDATE!=null&&!item.ALTDATE.isEmpty()}">
	                                	<tr>
		                                    <td>变更日期：</td>
		                                    <td width="60%">${item.ALTDATE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ALTBE!=null&&!item.ALTBE.isEmpty()}">
	                                	<tr>
		                                    <td>变更前内容：</td>
		                                    <td width="60%">${item.ALTBE}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.ALTAF!=null&&!item.ALTAF.isEmpty()}">
	                                	<tr>
		                                    <td>变更后内容：</td>
		                                    <td width="60%">${item.ALTAF}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                		                                		                                	                    	
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse9">
							         	企业分支机构信息
							        </a>
                        		</div>
                            	<div id="collapse9" class="collapse">
                            		<c:if test="${result.data.getFiliationList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getFiliationList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.BRNAME!=null&&!item.BRNAME.isEmpty()}">
	                                	<tr>
		                                    <td>分支机构名称：</td>
		                                    <td width="60%">${item.BRNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.BRREGNO!=null&&!item.BRREGNO.isEmpty()}">
	                                	<tr>
		                                    <td>分支机构企业注册号：</td>
		                                    <td width="60%">${item.BRREGNO}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.BRPRINCIPAL!=null&&!item.BRPRINCIPAL.isEmpty()}">
	                                	<tr>
		                                    <td>分支机构负责人：</td>
		                                    <td width="60%">${item.BRPRINCIPAL}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.BRADDR!=null&&!item.BRADDR.isEmpty()}">
	                                	<tr>
		                                    <td>分支机构地址：</td>
		                                    <td width="60%">${item.BRADDR}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.CBUITEM!=null&&!item.CBUITEM.isEmpty()}">
	                                	<tr>
		                                    <td>一般经营项目：</td>
		                                    <td width="60%">${item.CBUITEM}</td>
	                                	</tr>
	                                	</c:if>	   	                                		                                	                    	
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse10">
							         	股权出质历史信息
							        </a>
                        		</div>
                            	<div id="collapse10" class="collapse">
                            		<c:if test="${result.data.getSharesImpawnList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getSharesImpawnList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.IMPORG!=null&&!item.IMPORG.isEmpty()}">
	                                	<tr>
		                                    <td>质权人姓名：</td>
		                                    <td width="60%">${item.IMPORG}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.IMPORGTYPE!=null&&!item.IMPORGTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>质权人类别：</td>
		                                    <td width="60%">${item.IMPORGTYPE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.IMPAM!=null&&!item.IMPAM.isEmpty()}">
	                                	<tr>
		                                    <td>出质金额(万元)：</td>
		                                    <td width="60%">${item.IMPAM}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.IMPEXAEEP!=null&&!item.IMPEXAEEP.isEmpty()}">
	                                	<tr>
		                                    <td>出质审批部门：</td>
		                                    <td width="60%">${item.IMPEXAEEP}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.IMPONRECDATE!=null&&!item.IMPONRECDATE.isEmpty()}">
	                                	<tr>
		                                    <td>出质备案日期：</td>
		                                    <td width="60%">${item.IMPONRECDATE}</td>
	                                	</tr>
	                                	</c:if>	 
	                                	<c:if test="${item.IMPSANDATE!=null&&!item.IMPSANDATE.isEmpty()}">
	                                	<tr>
		                                    <td>出质批准日期：</td>
		                                    <td width="60%">${item.IMPSANDATE}</td>
	                                	</tr>
	                                	</c:if>	   	
	                                	<c:if test="${item.IMPTO!=null&&!item.IMPTO.isEmpty()}">
	                                	<tr>
		                                    <td>出质截至日期：</td>
		                                    <td width="60%">${item.IMPTO}</td>
	                                	</tr>
	                                	</c:if>	   	  	                                		                                	                    	
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse11">
							         	动产抵押信息
							        </a>
                        		</div>
                            	<div id="collapse11" class="collapse">
                            		<c:if test="${result.data.getMorDetailList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getMorDetailList()}" var="item">
                            		<table class="table table-bordered">
                            			<c:if test="${item.MORREG_ID!=null&&!item.MORREG_ID.isEmpty()}">
	                                    <tr>
		                                    <td>抵押ID：</td>
		                                    <td width="60%">${item.MORREG_ID}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.MORTGAGOR!=null&&!item.MORTGAGOR.isEmpty()}">
	                                	<tr>
		                                    <td>抵押人：</td>
		                                    <td width="60%">${item.MORTGAGOR}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.MORE!=null&&!item.MORE.isEmpty()}">
	                                	<tr>
		                                    <td>抵押权人：</td>
		                                    <td width="60%">${item.MORE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.MORREGCNO!=null&&!item.MORREGCNO.isEmpty()}">
	                                	<tr>
		                                    <td>登记证号：</td>
		                                    <td width="60%">${item.MORREGCNO}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.REGIDATE!=null&&!item.REGIDATE.isEmpty()}">
	                                	<tr>
		                                    <td>登记日期：</td>
		                                    <td width="60%">${item.REGIDATE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.REGORG!=null&&!item.REGORG.isEmpty()}">
	                                	<tr>
		                                    <td>登记机关：</td>
		                                    <td width="60%">${item.REGORG}</td>
	                                	</tr>
	                                	</c:if>                                	
	                                	<c:if test="${item.PRICLASECAM!=null&&!item.PRICLASECAM.isEmpty()}">
	                                	<tr>
		                                    <td>被担保主债权数额(万元)：</td>
		                                    <td width="60%">${item.PRICLASECAM}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.PRICLASECKIND!=null&&!item.PRICLASECKIND.isEmpty()}">
	                                	<tr>
		                                    <td>被担保主债权种类：</td>
		                                    <td width="60%">${item.PRICLASECKIND}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.APPREGREA!=null&&!item.APPREGREA.isEmpty()}">
	                                	<tr>
		                                    <td>申请抵押原因：</td>
		                                    <td width="60%">${item.APPREGREA}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.PEFPERFORM!=null&&!item.PEFPERFORM.isEmpty()}">
	                                	<tr>
		                                    <td>履约起始日期：</td>
		                                    <td width="60%">${item.PEFPERFORM}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.PEFPERTO!=null&&!item.PEFPERTO.isEmpty()}">
	                                	<tr>
		                                    <td>履约截止日期：</td>
		                                    <td width="60%">${item.PEFPERTO}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.MORTYPE!=null&&!item.MORTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>状态标识：</td>
		                                    <td width="60%">${item.MORTYPE}</td>
	                                	</tr>
	                                	</c:if>	    
	                                	<c:if test="${item.CANDATE!=null&&!item.CANDATE.isEmpty()}">
	                                	<tr>
		                                    <td>注销日期：</td>
		                                    <td width="60%">${item.CANDATE}</td>
	                                	</tr>
	                                	</c:if>	    	                                	    	                                	
	                                	<c:if test="${item.REGORGCODE!=null&&!item.REGORGCODE.isEmpty()}">
	                                	<tr>
		                                    <td>注册地址行政区编号：</td>
		                                    <td width="60%">${item.REGORGCODE}</td>
	                                	</tr>
	                                	</c:if>	 	                                		                                	                    
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse12">
							         	动产抵押物信息
							        </a>
                        		</div>
                            	<div id="collapse12" class="collapse">
                            		<c:if test="${result.data.getMorGuaInfoList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getMorGuaInfoList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.MORREG_ID!=null&&!item.MORREG_ID.isEmpty()}">
	                                	<tr>
		                                    <td>抵押ID：</td>
		                                    <td width="60%">${item.MORREG_ID}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.GUANAME!=null&&!item.GUANAME.isEmpty()}">
	                                	<tr>
		                                    <td>抵押物名称：</td>
		                                    <td width="60%">${item.GUANAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.QUAN!=null&&!item.QUAN.isEmpty()}">
	                                	<tr>
		                                    <td>数量：</td>
		                                    <td width="60%">${item.QUAN}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.VALUE!=null&&!item.VALUE.isEmpty()}">
	                                	<tr>
		                                    <td>价值(万元)：</td>
		                                    <td width="60%">${item.VALUE}</td>
	                                	</tr>
	                                	</c:if>	                                		                                	 	  	                                		                                	                    
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse13">
							         	失信被执行人信息
							        </a>
                        		</div>
                            	<div id="collapse13" class="collapse">
                            		<c:if test="${result.data.getPunishBreakList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getPunishBreakList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.CASECODE!=null&&!item.CASECODE.isEmpty()}">
	                                	<tr>
		                                    <td>案号：</td>
		                                    <td width="60%">${item.CASECODE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGDATECLEAN!=null&&!item.REGDATECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>立案时间：</td>
		                                    <td width="60%">${item.REGDATECLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.PUBLISHDATECLEAN!=null&&!item.PUBLISHDATECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>发布时间：</td>
		                                    <td width="60%">${item.PUBLISHDATECLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.AREANAMECLEAN!=null&&!item.AREANAMECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>省份：</td>
		                                    <td width="60%">${item.AREANAMECLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.TYPE!=null&&!item.TYPE.isEmpty()}">
	                                	<tr>
		                                    <td>失信人类型：</td>
		                                    <td width="60%">${item.TYPE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INAMECLEAN!=null&&!item.INAMECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>被执行人姓名/名称：</td>
		                                    <td width="60%">${item.INAMECLEAN}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.BUSINESSENTITY!=null&&!item.BUSINESSENTITY.isEmpty()}">
	                                	<tr>
		                                    <td>法定代表人/负责人姓名：</td>
		                                    <td width="60%">${item.BUSINESSENTITY}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.SEXYCLEAN!=null&&!item.SEXYCLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>性别：</td>
		                                    <td width="60%">${item.SEXYCLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.AGECLEAN!=null&&!item.AGECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>年龄：</td>
		                                    <td width="60%">${item.AGECLEAN}</td>
	                                	</tr>
	                                	</c:if>	  
	                                	<c:if test="${item.CARDNUM!=null&&!item.CARDNUM.isEmpty()}">
	                                	<tr>
		                                    <td>身份证号码/工商注册号：</td>
		                                    <td width="60%">${item.CARDNUM}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.YSFZD!=null&&!item.YSFZD.isEmpty()}">
	                                	<tr>
		                                    <td>身份证原始发证地：</td>
		                                    <td width="60%">${item.YSFZD}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.DUTY!=null&&!item.DUTY.isEmpty()}">
	                                	<tr>
		                                    <td>生效法律文书确定的义务：</td>
		                                    <td width="60%">${item.DUTY}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.COURTNAME!=null&&!item.COURTNAME.isEmpty()}">
	                                	<tr>
		                                    <td>执行法院：</td>
		                                    <td width="60%">${item.COURTNAME}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.GISTID!=null&&!item.GISTID.isEmpty()}">
	                                	<tr>
		                                    <td>执行依据文号：</td>
		                                    <td width="60%">${item.GISTID}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.GISTUNIT!=null&&!item.GISTUNIT.isEmpty()}">
	                                	<tr>
		                                    <td>做出执行依据单位：</td>
		                                    <td width="60%">${item.GISTUNIT}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.DISRUPTTYPENAME!=null&&!item.DISRUPTTYPENAME.isEmpty()}">
	                                	<tr>
		                                    <td>失信被执行人行为具体情形：</td>
		                                    <td width="60%">${item.DISRUPTTYPENAME}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.PERFORMANCE!=null&&!item.PERFORMANCE.isEmpty()}">
	                                	<tr>
		                                    <td>被执行人的履行情况：</td>
		                                    <td width="60%">${item.PERFORMANCE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.UNPERFORMPART!=null&&!item.UNPERFORMPART.isEmpty()}">
	                                	<tr>
		                                    <td>未履行(元)：</td>
		                                    <td width="60%">${item.UNPERFORMPART}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.EXITDATE!=null&&!item.EXITDATE.isEmpty()}">
	                                	<tr>
		                                    <td>退出日期：</td>
		                                    <td width="60%">${item.EXITDATE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.FOCUSNUMBER!=null&&!item.FOCUSNUMBER.isEmpty()}">
	                                	<tr>
		                                    <td>关注次数：</td>
		                                    <td width="60%">${item.FOCUSNUMBER}</td>
	                                	</tr>
	                                	</c:if>		                                	                              		                                	 	  	                                		                                	                   
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse14">
							         	被执行人信息
							        </a>
                        		</div>
                            	<div id="collapse14" class="collapse">
                            		<c:if test="${result.data.getPunishedList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getPunishedList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.CASECODE!=null&&!item.CASECODE.isEmpty()}">
	                                	<tr>
		                                    <td>案号：</td>
		                                    <td width="60%">${item.CASECODE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGDATECLEAN!=null&&!item.REGDATECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>立案时间：</td>
		                                    <td width="60%">${item.REGDATECLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.CASESTATE!=null&&!item.CASESTATE.isEmpty()}">
	                                	<tr>
		                                    <td>案件状态：</td>
		                                    <td width="60%">${item.CASESTATE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.AREANAMECLEAN!=null&&!item.AREANAMECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>省份：</td>
		                                    <td width="60%">${item.AREANAMECLEAN}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.INAMECLEAN!=null&&!item.INAMECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>被执行人姓名/名称：</td>
		                                    <td width="60%">${item.INAMECLEAN}</td>
	                                	</tr>
	                                	</c:if>	                                	                               
	                                	<c:if test="${item.SEXYCLEAN!=null&&!item.SEXYCLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>性别：</td>
		                                    <td width="60%">${item.SEXYCLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.AGECLEAN!=null&&!item.AGECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>年龄：</td>
		                                    <td width="60%">${item.AGECLEAN}</td>
	                                	</tr>
	                                	</c:if>	  
	                                	<c:if test="${item.CARDNUMCLEAN!=null&&!item.CARDNUMCLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>身份证号码/企业注册号：</td>
		                                    <td width="60%">${item.CARDNUMCLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.YSFZD!=null&&!item.YSFZD.isEmpty()}">
	                                	<tr>
		                                    <td>身份证原始发证地：</td>
		                                    <td width="60%">${item.YSFZD}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.EXECMONEY!=null&&!item.EXECMONEY.isEmpty()}">
	                                	<tr>
		                                    <td>执行标的（元）：</td>
		                                    <td width="60%">${item.EXECMONEY}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.COURTNAME!=null&&!item.COURTNAME.isEmpty()}">
	                                	<tr>
		                                    <td>执行法院：</td>
		                                    <td width="60%">${item.COURTNAME}</td>
	                                	</tr>
	                                	</c:if>		                                		                                	
	                                	<c:if test="${item.FOCUSNUMBER!=null&&!item.FOCUSNUMBER.isEmpty()}">
	                                	<tr>
		                                    <td>关注次数：</td>
		                                    <td width="60%">${item.FOCUSNUMBER}</td>
	                                	</tr>
	                                	</c:if>		                                	                              		                                	 	  	                                		                                	                   
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse15">
							         	股权冻结历史信息
							        </a>
                        		</div>
                            	<div id="collapse15" class="collapse">
                            		<c:if test="${result.data.getSharesFrostList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getSharesFrostList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.FRODOCNO!=null&&!item.FRODOCNO.isEmpty()}">
	                                	<tr>
		                                    <td>冻结文号：</td>
		                                    <td width="60%">${item.FRODOCNO}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.FROAM!=null&&!item.FROAM.isEmpty()}">
	                                	<tr>
		                                    <td>冻结金额(万元)：</td>
		                                    <td width="60%">${item.FROAM}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.FROAUTH!=null&&!item.FROAUTH.isEmpty()}">
	                                	<tr>
		                                    <td>冻结机关：</td>
		                                    <td width="60%">${item.FROAUTH}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.FROFROM!=null&&!item.FROFROM.isEmpty()}">
	                                	<tr>
		                                    <td>冻结起始日期：</td>
		                                    <td width="60%">${item.FROFROM}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.FROTO!=null&&!item.FROTO.isEmpty()}">
	                                	<tr>
		                                    <td>冻结截至日期：</td>
		                                    <td width="60%">${item.FROTO}</td>
	                                	</tr>
	                                	</c:if>	                                	                               
	                                	<c:if test="${item.THAWDOCNO!=null&&!item.THAWDOCNO.isEmpty()}">
	                                	<tr>
		                                    <td>解冻文号：</td>
		                                    <td width="60%">${item.THAWDOCNO}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.THAWCOMMENT!=null&&!item.THAWCOMMENT.isEmpty()}">
	                                	<tr>
		                                    <td>解冻说明：</td>
		                                    <td width="60%">${item.THAWCOMMENT}</td>
	                                	</tr>
	                                	</c:if>	  
	                                	<c:if test="${item.THAWAUTH!=null&&!item.THAWAUTH.isEmpty()}">
	                                	<tr>
		                                    <td>解冻机关：</td>
		                                    <td width="60%">${item.THAWAUTH}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.THAWDATE!=null&&!item.THAWDATE.isEmpty()}">
	                                	<tr>
		                                    <td>解冻日期：</td>
		                                    <td width="60%">${item.THAWDATE}</td>
	                                	</tr>
	                                	</c:if>		                                		                                	                              		                                	 	  	                                		                                	                  
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse16">
							         	破产清算信息
							        </a>
                        		</div>
                            	<div id="collapse16" class="collapse">
                            		<c:if test="${result.data.getLiquidationList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getLiquidationList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.LIGPRINCIPAL!=null&&!item.LIGPRINCIPAL.isEmpty()}">
	                                	<tr>
		                                    <td>清算负责人：</td>
		                                    <td width="60%">${item.LIGPRINCIPAL}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.LIGENTITY!=null&&!item.LIGENTITY.isEmpty()}">
	                                	<tr>
		                                    <td>清算责任人：</td>
		                                    <td width="60%">${item.LIGENTITY}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.LIQMEN!=null&&!item.LIQMEN.isEmpty()}">
	                                	<tr>
		                                    <td>清算组成员：</td>
		                                    <td width="60%">${item.LIQMEN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.TEL!=null&&!item.TEL.isEmpty()}">
	                                	<tr>
		                                    <td>联系电话：</td>
		                                    <td width="60%">${item.TEL}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.CLAIMTRANEE!=null&&!item.CLAIMTRANEE.isEmpty()}">
	                                	<tr>
		                                    <td>债权承接人：</td>
		                                    <td width="60%">${item.CLAIMTRANEE}</td>
	                                	</tr>
	                                	</c:if>	                                	                               
	                                	<c:if test="${item.DEBTTRANEE!=null&&!item.DEBTTRANEE.isEmpty()}">
	                                	<tr>
		                                    <td>债务承接人：</td>
		                                    <td width="60%">${item.DEBTTRANEE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.LIGENDDATE!=null&&!item.LIGENDDATE.isEmpty()}">
	                                	<tr>
		                                    <td>清算完结日期：</td>
		                                    <td width="60%">${item.LIGENDDATE}</td>
	                                	</tr>
	                                	</c:if>	  
	                                	<c:if test="${item.LIGST!=null&&!item.LIGST.isEmpty()}">
	                                	<tr>
		                                    <td>清算完结情况：</td>
		                                    <td width="60%">${item.LIGST}</td>
	                                	</tr>
	                                	</c:if>		                                		                                		                                	                              		                                	 	  	                                		                                	                  
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse17">
							         	行政处罚历史信息
							        </a>
                        		</div>
                            	<div id="collapse17" class="collapse">
                            		<c:if test="${result.data.getCaseInfoList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getCaseInfoList()}" var="item">
                            		<table class="table table-bordered">	
                            		<c:if test="${item.CASETYPE!=null&&!item.CASETYPE.isEmpty()}">
	                                	<tr>
		                                    <td>案件类型：</td>
		                                    <td width="60%">${item.CASETYPE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.CASETIME!=null&&!item.CASETIME.isEmpty()}">
	                                	<tr>
		                                    <td>案发时间：</td>
		                                    <td width="60%">${item.CASETIME}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.CASEREASON!=null&&!item.CASEREASON.isEmpty()}">
	                                	<tr>
		                                    <td>案由：</td>
		                                    <td width="60%">${item.CASEREASON}</td>
	                                	</tr>
	                                	</c:if>	                                	                               
	                                	<c:if test="${item.ILLEGFACT!=null&&!item.ILLEGFACT.isEmpty()}">
	                                	<tr>
		                                    <td>主要违法事实：</td>
		                                    <td width="60%">${item.ILLEGFACT}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.CASEVAL!=null&&!item.CASEVAL.isEmpty()}">
	                                	<tr>
		                                    <td>案值：</td>
		                                    <td width="60%">${item.CASEVAL}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.CASERESULT!=null&&!item.CASERESULT.isEmpty()}">
	                                	<tr>
		                                    <td>案件结果：</td>
		                                    <td width="60%">${item.CASERESULT}</td>
	                                	</tr>
	                                	</c:if>	                                    
	                                	<c:if test="${item.PENAUTH!=null&&!item.PENAUTH.isEmpty()}">
	                                	<tr>
		                                    <td>处罚机关：</td>
		                                    <td width="60%">${item.PENAUTH}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.PENTYPE!=null&&!item.PENTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>处罚种类：</td>
		                                    <td width="60%">${item.PENTYPE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.PENDECNO!=null&&!item.PENDECNO.isEmpty()}">
	                                	<tr>
		                                    <td>处罚决定文书：</td>
		                                    <td width="60%">${item.PENDECNO}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.PENBASIS!=null&&!item.PENBASIS.isEmpty()}">
	                                	<tr>
		                                    <td>处罚依据：</td>
		                                    <td width="60%">${item.PENBASIS}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.PENAM!=null&&!item.PENAM.isEmpty()}">
	                                	<tr>
		                                    <td>处罚金额(万元)：</td>
		                                    <td width="60%">${item.PENAM}</td>
	                                	</tr>
	                                	</c:if>	                                	                               
	                                	<c:if test="${item.PENDECISSDATE!=null&&!item.PENDECISSDATE.isEmpty()}">
	                                	<tr>
		                                    <td>处罚决定书签发日期：</td>
		                                    <td width="60%">${item.PENDECISSDATE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.PENRESULT!=null&&!item.PENRESULT.isEmpty()}">
	                                	<tr>
		                                    <td>处罚结果：</td>
		                                    <td width="60%">${item.PENRESULT}</td>
	                                	</tr>
	                                	</c:if>	  
	                                	<c:if test="${item.PENEXEST!=null&&!item.PENEXEST.isEmpty()}">
	                                	<tr>
		                                    <td>处罚执行情况：</td>
		                                    <td width="60%">${item.PENEXEST}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.EXESORT!=null&&!item.EXESORT.isEmpty()}">
	                                	<tr>
		                                    <td>执行类别：</td>
		                                    <td width="60%">${item.EXESORT}</td>
	                                	</tr>
	                                	</c:if>	                                		                                		                                	                              		                                	 	  	                                		                                	                  
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                        	</c:if>
                        	
                        	
                        	<!-- 按人员查询结果 -->
                        	<c:if test="${saicModel.keyType.equals('4')}">
                        	<div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse20">
							         	企业法人信息
							        </a>
                        		</div>
                            	<div id="collapse20" class="collapse">
                            		<c:if test="${result.data.getRyposfrList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		
                            		<c:forEach items="${result.data.getRyposfrList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.RYNAME!=null&&!item.RYNAME.isEmpty()}">
	                                	<tr>
		                                    <td>查询人姓名：</td>
		                                    <td width="60%">${item.RYNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ENTNAME!=null&&!item.ENTNAME.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)名称：</td>
		                                    <td width="60%">${item.ENTNAME}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.REGNO!=null&&!item.REGNO.isEmpty()}">
	                                	<tr>
		                                    <td>注册号：</td>
		                                    <td width="60%">${item.REGNO}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.ENTTYPE!=null&&!item.ENTTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)类型：</td>
		                                    <td width="60%">${item.ENTTYPE}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.REGCAP!=null&&!item.REGCAP.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本(万元)：</td>
		                                    <td width="60%">${item.REGCAP}</td>
	                                	</tr>
	                                	</c:if>	                                	                               
	                                	<c:if test="${item.REGCAPCUR!=null&&!item.REGCAPCUR.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本币种：</td>
		                                    <td width="60%">${item.REGCAPCUR}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.ENTSTATUS!=null&&!item.ENTSTATUS.isEmpty()}">
	                                	<tr>
		                                    <td>企业状态：</td>
		                                    <td width="60%">${item.ENTSTATUS}</td>
	                                	</tr>
	                                	</c:if>	  
	                                	<c:if test="${item.CREDITCODE!=null&&!item.CREDITCODE.isEmpty()}">
	                                	<tr>
		                                    <td>统一社会信用代码：</td>
		                                    <td width="60%">${item.CREDITCODE}</td>
	                                	</tr>
	                                	</c:if>		                                		                                		                                	                              		                                	 	  	                                		                                	                  
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse21">
							         	企业股东信息
							        </a>
                        		</div>
                            	<div id="collapse21" class="collapse">
                            		<c:if test="${result.data.getRyposshaList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getRyposshaList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.RYNAME!=null&&!item.RYNAME.isEmpty()}">
	                                	<tr>
		                                    <td>查询人姓名：</td>
		                                    <td width="60%">${item.RYNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ENTNAME!=null&&!item.ENTNAME.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)名称：</td>
		                                    <td width="60%">${item.ENTNAME}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.REGNO!=null&&!item.REGNO.isEmpty()}">
	                                	<tr>
		                                    <td>注册号：</td>
		                                    <td width="60%">${item.REGNO}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.ENTTYPE!=null&&!item.ENTTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)类型：</td>
		                                    <td width="60%">${item.ENTTYPE}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.REGCAP!=null&&!item.REGCAP.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本(万元)：</td>
		                                    <td width="60%">${item.REGCAP}</td>
	                                	</tr>
	                                	</c:if>	                                	                               
	                                	<c:if test="${item.REGCAPCUR!=null&&!item.REGCAPCUR.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本币种：</td>
		                                    <td width="60%">${item.REGCAPCUR}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.ENTSTATUS!=null&&!item.ENTSTATUS.isEmpty()}">
	                                	<tr>
		                                    <td>企业状态：</td>
		                                    <td width="60%">${item.ENTSTATUS}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.SUBCONAM!=null&&!item.SUBCONAM.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资额(万元)：</td>
		                                    <td width="60%">${item.SUBCONAM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.CURRENCY!=null&&!item.CURRENCY.isEmpty()}">
	                                	<tr>
		                                    <td>认缴出资币种：</td>
		                                    <td width="60%">${item.CURRENCY}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.CONFORM!=null&&!item.CONFORM.isEmpty()}">
	                                	<tr>
		                                    <td>出资方式：</td>
		                                    <td width="60%">${item.CONFORM}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.FUNDEDRATIO!=null&&!item.FUNDEDRATIO.isEmpty()}">
	                                	<tr>
		                                    <td>出资比例：</td>
		                                    <td width="60%">${item.FUNDEDRATIO}</td>
	                                	</tr>
	                                	</c:if>	  
	                                	<c:if test="${item.CREDITCODE!=null&&!item.CREDITCODE.isEmpty()}">
	                                	<tr>
		                                    <td>统一社会信用代码：</td>
		                                    <td width="60%">${item.CREDITCODE}</td>
	                                	</tr>
	                                	</c:if>		                                		                                	                              		                                	 	  	                                		                                	                  
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse22">
							         	企业主要管理人员信息
							        </a>
                        		</div>
                            	<div id="collapse22" class="collapse">
                            		<c:if test="${result.data.getRyposperList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getRyposperList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.RYNAME!=null&&!item.RYNAME.isEmpty()}">
	                                	<tr>
		                                    <td>查询人姓名：</td>
		                                    <td width="60%">${item.RYNAME}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.ENTNAME!=null&&!item.ENTNAME.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)名称：</td>
		                                    <td width="60%">${item.ENTNAME}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.REGNO!=null&&!item.REGNO.isEmpty()}">
	                                	<tr>
		                                    <td>注册号：</td>
		                                    <td width="60%">${item.REGNO}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.ENTTYPE!=null&&!item.ENTTYPE.isEmpty()}">
	                                	<tr>
		                                    <td>企业(机构)类型：</td>
		                                    <td width="60%">${item.ENTTYPE}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.REGCAP!=null&&!item.REGCAP.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本(万元)：</td>
		                                    <td width="60%">${item.REGCAP}</td>
	                                	</tr>
	                                	</c:if>	                                	                               
	                                	<c:if test="${item.REGCAPCUR!=null&&!item.REGCAPCUR.isEmpty()}">
	                                	<tr>
		                                    <td>注册资本币种：</td>
		                                    <td width="60%">${item.REGCAPCUR}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.ENTSTATUS!=null&&!item.ENTSTATUS.isEmpty()}">
	                                	<tr>
		                                    <td>企业状态：</td>
		                                    <td width="60%">${item.ENTSTATUS}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.POSITION!=null&&!item.POSITION.isEmpty()}">
	                                	<tr>
		                                    <td>职务：</td>
		                                    <td width="60%">${item.POSITION}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.CREDITCODE!=null&&!item.CREDITCODE.isEmpty()}">
	                                	<tr>
		                                    <td>统一社会信用代码：</td>
		                                    <td width="60%">${item.CREDITCODE}</td>
	                                	</tr>
	                                	</c:if>		                                		                                	                              		                                	 	  	                                		                                	                  
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse23">
							         	失信被执行人信息
							        </a>
                        		</div>
                            	<div id="collapse23" class="collapse">
                            		<c:if test="${result.data.getPunishBreakList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getPunishBreakList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.CASECODE!=null&&!item.CASECODE.isEmpty()}">
	                                	<tr>
		                                    <td>案号：</td>
		                                    <td width="60%">${item.CASECODE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGDATECLEAN!=null&&!item.REGDATECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>立案时间：</td>
		                                    <td width="60%">${item.REGDATECLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.PUBLISHDATECLEAN!=null&&!item.PUBLISHDATECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>发布时间：</td>
		                                    <td width="60%">${item.PUBLISHDATECLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.AREANAMECLEAN!=null&&!item.AREANAMECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>省份：</td>
		                                    <td width="60%">${item.AREANAMECLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.TYPE!=null&&!item.TYPE.isEmpty()}">
	                                	<tr>
		                                    <td>失信人类型：</td>
		                                    <td width="60%">${item.TYPE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.INAMECLEAN!=null&&!item.INAMECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>被执行人姓名/名称：</td>
		                                    <td width="60%">${item.INAMECLEAN}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.BUSINESSENTITY!=null&&!item.BUSINESSENTITY.isEmpty()}">
	                                	<tr>
		                                    <td>法定代表人/负责人姓名：</td>
		                                    <td width="60%">${item.BUSINESSENTITY}</td>
	                                	</tr>
	                                	</c:if>	                                	
	                                	<c:if test="${item.SEXYCLEAN!=null&&!item.SEXYCLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>性别：</td>
		                                    <td width="60%">${item.SEXYCLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.AGECLEAN!=null&&!item.AGECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>年龄：</td>
		                                    <td width="60%">${item.AGECLEAN}</td>
	                                	</tr>
	                                	</c:if>	  
	                                	<c:if test="${item.CARDNUM!=null&&!item.CARDNUM.isEmpty()}">
	                                	<tr>
		                                    <td>身份证号码/工商注册号：</td>
		                                    <td width="60%">${item.CARDNUM}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.YSFZD!=null&&!item.YSFZD.isEmpty()}">
	                                	<tr>
		                                    <td>身份证原始发证地：</td>
		                                    <td width="60%">${item.YSFZD}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.DUTY!=null&&!item.DUTY.isEmpty()}">
	                                	<tr>
		                                    <td>生效法律文书确定的义务：</td>
		                                    <td width="60%">${item.DUTY}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.COURTNAME!=null&&!item.COURTNAME.isEmpty()}">
	                                	<tr>
		                                    <td>执行法院：</td>
		                                    <td width="60%">${item.COURTNAME}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.GISTID!=null&&!item.GISTID.isEmpty()}">
	                                	<tr>
		                                    <td>执行依据文号：</td>
		                                    <td width="60%">${item.GISTID}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.GISTUNIT!=null&&!item.GISTUNIT.isEmpty()}">
	                                	<tr>
		                                    <td>做出执行依据单位：</td>
		                                    <td width="60%">${item.GISTUNIT}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.DISRUPTTYPENAME!=null&&!item.DISRUPTTYPENAME.isEmpty()}">
	                                	<tr>
		                                    <td>失信被执行人行为具体情形：</td>
		                                    <td width="60%">${item.DISRUPTTYPENAME}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.PERFORMANCE!=null&&!item.PERFORMANCE.isEmpty()}">
	                                	<tr>
		                                    <td>被执行人的履行情况：</td>
		                                    <td width="60%">${item.PERFORMANCE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.UNPERFORMPART!=null&&!item.UNPERFORMPART.isEmpty()}">
	                                	<tr>
		                                    <td>未履行(元)：</td>
		                                    <td width="60%">${item.UNPERFORMPART}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.EXITDATE!=null&&!item.EXITDATE.isEmpty()}">
	                                	<tr>
		                                    <td>退出日期：</td>
		                                    <td width="60%">${item.EXITDATE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.FOCUSNUMBER!=null&&!item.FOCUSNUMBER.isEmpty()}">
	                                	<tr>
		                                    <td>关注次数：</td>
		                                    <td width="60%">${item.FOCUSNUMBER}</td>
	                                	</tr>
	                                	</c:if>		                                	                              		                                	 	  	                                		                                	                   
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>                            
                            <div class="card">
                        		<div class="card-header">
                        			<a class="card-link" data-toggle="collapse" data-parent="#accordion" href="#collapse24">
							         	被执行人信息
							        </a>
                        		</div>
                            	<div id="collapse24" class="collapse">
                            		<c:if test="${result.data.getPunishedList().size()==0}">
                            		<div class="alert alert-secondary text-center">无记录</div>
                            		</c:if>
                            		<c:forEach items="${result.data.getPunishedList()}" var="item">
                            		<table class="table table-bordered">	                                    
	                                	<c:if test="${item.CASECODE!=null&&!item.CASECODE.isEmpty()}">
	                                	<tr>
		                                    <td>案号：</td>
		                                    <td width="60%">${item.CASECODE}</td>
	                                	</tr>
	                                	</c:if>
	                                	<c:if test="${item.REGDATECLEAN!=null&&!item.REGDATECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>立案时间：</td>
		                                    <td width="60%">${item.REGDATECLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.CASESTATE!=null&&!item.CASESTATE.isEmpty()}">
	                                	<tr>
		                                    <td>案件状态：</td>
		                                    <td width="60%">${item.CASESTATE}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.AREANAMECLEAN!=null&&!item.AREANAMECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>省份：</td>
		                                    <td width="60%">${item.AREANAMECLEAN}</td>
	                                	</tr>
	                                	</c:if>		                                	
	                                	<c:if test="${item.INAMECLEAN!=null&&!item.INAMECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>被执行人姓名/名称：</td>
		                                    <td width="60%">${item.INAMECLEAN}</td>
	                                	</tr>
	                                	</c:if>	                                	                               
	                                	<c:if test="${item.SEXYCLEAN!=null&&!item.SEXYCLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>性别：</td>
		                                    <td width="60%">${item.SEXYCLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.AGECLEAN!=null&&!item.AGECLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>年龄：</td>
		                                    <td width="60%">${item.AGECLEAN}</td>
	                                	</tr>
	                                	</c:if>	  
	                                	<c:if test="${item.CARDNUMCLEAN!=null&&!item.CARDNUMCLEAN.isEmpty()}">
	                                	<tr>
		                                    <td>身份证号码/企业注册号：</td>
		                                    <td width="60%">${item.CARDNUMCLEAN}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.YSFZD!=null&&!item.YSFZD.isEmpty()}">
	                                	<tr>
		                                    <td>身份证原始发证地：</td>
		                                    <td width="60%">${item.YSFZD}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.EXECMONEY!=null&&!item.EXECMONEY.isEmpty()}">
	                                	<tr>
		                                    <td>执行标的（元）：</td>
		                                    <td width="60%">${item.EXECMONEY}</td>
	                                	</tr>
	                                	</c:if>	
	                                	<c:if test="${item.COURTNAME!=null&&!item.COURTNAME.isEmpty()}">
	                                	<tr>
		                                    <td>执行法院：</td>
		                                    <td width="60%">${item.COURTNAME}</td>
	                                	</tr>
	                                	</c:if>		                                		                                	
	                                	<c:if test="${item.FOCUSNUMBER!=null&&!item.FOCUSNUMBER.isEmpty()}">
	                                	<tr>
		                                    <td>关注次数：</td>
		                                    <td width="60%">${item.FOCUSNUMBER}</td>
	                                	</tr>
	                                	</c:if>		                                	                              		                                	 	  	                                		                                	                   
                                	</table>
                                	</c:forEach>
                                </div>
                            </div>
                            
                        	</c:if>
                        </div>
                        </c:if>
                    </div>
                </div>
            </div>

        </div>
    </div>	   
</body>
</html>
