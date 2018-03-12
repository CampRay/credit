<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>风险控制核查</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/style.css">
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/js/jquery-1.10.1.min.js"></script> 
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
	    	
	    	$("#first-page").on("click", function(){
	    		$("#pageNo").val("1");
	    		$("#riskModel").submit();
	    	});
	    	
	    	$("#last-page").on("click", function(){
	    		$("#pageNo").val("${result.data.totalPageNum}");
	    		$("#riskModel").submit();
	    	});
	    	
	    	$("#prev-page").on("click", function(){
	    		var no=parseInt("${result.data.pageNo}");
	    		if(no>1){
		    		$("#pageNo").val(no-1);
		    		$("#riskModel").submit();
	    		}
	    	});
	    	
	    	$("#next-page").on("click", function(){
	    		var no=parseInt("${result.data.pageNo}");
	    		var total=parseInt("${result.data.totalPageNum}");
	    		if(no<total){
		    		$("#pageNo").val(no+1);
		    		$("#riskModel").submit();
	    		}
	    	});
	    	
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
                        <div class="right-title"><h3>风险控制核查 （元素）</h3></div>
                        <div class="search-content">
                            <form:form action="elerisklist" commandName="riskModel" method="post">
                            	<form:input type="hidden" path="pageNo" value="1"/>
                                <div class="form-group  form-inline">
                                    <label class="col-lg-3 col-md-4 control-label">企业名称：<span class="text-danger">*</span></label>
                                    <div class="col-lgd-9 col-md-8">
                                        <form:input type="text" class="w-100 p-1" path="entName"/>
                                    </div>
                                </div>                                
                                <div class="form-group form-inline">
                                    <label class="col-lg-3 col-md-4 control-label">数据类型：</label>
                                    <div class="col-lgd-9 col-md-8">
                                        <form:select path="dataType" class="w-50">                                            
                                            <form:option value="">所有类型</form:option>
                                            <form:option value="satparty">税务</form:option>
                                            <form:option value="fdaparty">食品药品监督</form:option>
                                            <form:option value="epbparty">环保</form:option>
                                            <form:option value="qtsparty">质监</form:option>
                                            <form:option value="xzhmd">受惩黑名单</form:option>
                                            <form:option value="mocparty">车管所</form:option>
                                            <form:option value="pbcparty">人行银监</form:option>
                                            <form:option value="news">新闻媒体</form:option>
                                            <form:option value="cpws">裁判文书</form:option>
                                            <form:option value="ktgg">开庭公告</form:option>
                                            <form:option value="zxgg">执行公告</form:option>
                                            <form:option value="shixin">失信公告</form:option>
                                            <form:option value="fygg">法院公告</form:option>
                                            <form:option value="ajlc">案件流程</form:option>
                                            <form:option value="bgt">瀑光台</form:option>
                                            <form:option value="wdhmd">网贷黑名单</form:option>
                                            <form:option value="zcdj">资产冻结</form:option>
                                            <form:option value="zccf">资产查封</form:option>
                                            <form:option value="jyyc">经营异常</form:option>
                                            <form:option value="job">招聘数据</form:option>
                                        </form:select>
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
                        <div class="result-content">
                        	<c:if test="${result.data.getAllItemList().size()==0}">
                        		<div class="text-center w-100">没有查询到此企业的异常信息</div>
                        	</c:if>                            
                            <c:if test="${result.data.getAllItemList().size()>0}">
                            <div class="w-100">
                            	<table class="table table-bordered">
                            		<thead>
                            			<tr>
                            				<td width="15%">数据类型</td>
                            				<td width="20%">数据时间</td>
                            				<td width="30%">标题</td>
                            				<td width="35%">内容</td>                            				
                            			</tr>
                            		</thead>
                            		<tbody>
                            			<c:forEach items="${result.data.getAllItemList()}" var="item">
                            			<tr>
                            				<td width="10%">${item.dataTypeStr}</td>
                            				<td width="10%">${item.sortTimeString}</td>
                            				<td width="30%">${item.title}</td>
                            				<td width="50%">${item.body}</td> 
                            			</tr>
                            			</c:forEach>
                            		</tbody>
                            	</table>                            	
                            </div>
                            <div class="row" style="padding-bottom:8px;">
                            	<div class="col-md-4">共有${result.data.totalCount}条记录, 第${result.data.pageNo}/${result.data.totalPageNum}页</div>
                            	<c:if test="${result.data.pageNo=='1'}">
                            		<c:set var="firstStatus" value="disabled"></c:set>
                            	</c:if>
                            	<c:if test="${result.data.pageNo.equals(result.data.totalPageNum)}">
                            		<c:set var="lastStatus" value="disabled"></c:set>
                            	</c:if>
                                <div class="col-md-8">
                                	<ul class="pagination pagination-sm float-right">
									    <li class="page-item ${firstStatus}"><a id="first-page" class="page-link" href="#">首页</a></li>
									    <li class="page-item ${firstStatus}"><a id="prev-page" class="page-link" href="#">上一页</a></li>									    
									    <li class="page-item ${lastStatus}"><a id="next-page" class="page-link" href="#">下一页</a></li>
									    <li class="page-item ${lastStatus}"><a id="last-page" class="page-link" href="#">末页</a></li>
									  </ul>
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
