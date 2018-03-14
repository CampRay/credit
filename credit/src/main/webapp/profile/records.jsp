<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>征信查询记录</title>
    <link href="<%=request.getContextPath()%>/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/style.css">        
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/static/js/jquery-1.10.1.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
    
    <script type="text/javascript">
	    $(window).resize(function(){
			 $("#content_row").css("min-height",$(window).height()-110);
		 });	    
	    jQuery(document).ready(function() {    
	       $("#content_row").css("min-height",$(window).height()-110);	
	       datePicker();
	       
	       
	       $("#first-page").on("click", function(){
	    		$("#page").val("1");
	    		$("#record").submit();
	    	});
	    	
	    	$("#last-page").on("click", function(){
	    		$("#page").val("${record.total}");
	    		$("#record").submit();
	    	});
	    	
	    	$("#prev-page").on("click", function(){
	    		var no=parseInt("${record.page}");
	    		if(no>1){
		    		$("#page").val(no-1);
		    		$("#record").submit();
	    		}
	    	});
	    	
	    	$("#next-page").on("click", function(){
	    		var no=parseInt("${record.page}");
	    		var total=parseInt("${record.totalPage}");
	    		if(no<total){
		    		$("#page").val(no+1);
		    		$("#record").submit();
	    		}
	    	});
	       
		});
	    var datePicker = function(){
	       	$('.date-picker').datepicker({	          
	           autoclose: true
	        });
	    };
    </script>
    
</head>
<body>    
    <%@include file="../auth/header.jsp"%>
    <div class="content">
        <div class="container" style="background-color:white;">
            <div class="row" id="content_row">
                <div class="col-lg-4 col-md-4 col-sm-12">
                    <%@include file="menu.jsp"%>
                </div>
                <div class="col-lg-8 col-md-8 col-sm-12 right-column">
                    <div class="right-content">
                        <div class="right-title"><h3>征信查询历史记录</h3></div>
                        <div class="search-content">
                            <form:form action="records" commandName="record" method="post">  
                            	<form:input type="hidden" path="page"/>                            	                      
                                <div class="form-group form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">开始时间：</div>
                                    <div class="col-lg-9 col-md-8">
                                        <div data-date-format="yyyy/mm/dd" class="input-group date date-picker">
											<form:input type="text" path="startDate" readonly="true"/> <span class="input-group-btn">
												<button type="button" class="btn default">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>                                    
                                    </div>
                                </div>                                                                
                                <div class="form-group  form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">结束时间：</div>
                                    <div class="col-lgd-9 col-md-8">
                                        <div data-date-format="yyyy/mm/dd" class="input-group date date-picker">
											<form:input type="text" path="endDate" readonly="true"/> <span class="input-group-btn">
												<button type="button" class="btn default">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
                                    </div>
                                </div>                                                                  
                                <div class="form-group form-inline bg-secondary p-1">
                                    <div class="col-lg-3 col-md-4 text-md-right"></div>
                                    <div class="col-lgd-9 col-md-8">
                                        <button type="submit" class="btn btn-info">&nbsp;&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp; &nbsp;</button>
                                        <button type="reset" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;取消&nbsp;&nbsp;&nbsp;&nbsp;</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                        <c:if test="${errorMsg!=null&&!errorMsg.isEmpty()}">	
                        <div class="text-center text-danger">${errorMsg}</div>
                        </c:if>  
                        <div class="result-content">
                        	<c:if test="${logList.size()==0}">
                        		<div class="text-center w-100">没有查询记录</div>
                        	</c:if>                            
                            <c:if test="${logList.size()>0}">
                            <div class="w-100">
                            	<table class="table table-bordered">
                            		<thead>
                            			<tr>
                            				<td width="20%">查询日期</td>
                            				<td width="20%">查询接口</td>
                            				<td width="20%">查询金额</td>
                            				<td width="20%">账户余额</td>
                            				<td width="20%">查询说明</td>                            				
                            			</tr>
                            		</thead>
                            		<tbody>
                            			<c:forEach items="${logList}" var="item">
                            			<tr>
                            				<td width="20%">${item.createdTimeStr}</td>
                            				<td width="20%">                            				
                            				<c:if test="${item.apiId==1}">身份证认证(新颜)</c:if>
                            				<c:if test="${item.apiId==2}">诚信信息核查(新颜)</c:if>
                            				<c:if test="${item.apiId==3}">银行卡信息二元素认证(新颜)</c:if>
                            				<c:if test="${item.apiId==4}">银行卡信息三元素认证(新颜)</c:if>
                            				<c:if test="${item.apiId==5}">银行卡信息四元素认证(新颜)</c:if>
                            				<c:if test="${item.apiId==6}">身份证认证(元素)</c:if>
                            				<c:if test="${item.apiId==7}">风险控制核查(元素)</c:if>
                            				<c:if test="${item.apiId==8}">银行卡信息三元素认证(元素)</c:if>
                            				<c:if test="${item.apiId==9}">银行卡信息四元素认证(元素)</c:if>
                            				<c:if test="${item.apiId==10}">工商信息查询(元素)</c:if>
                            				</td>
                            				<td width="20%">HK$ ${item.amount}</td>
                            				<td width="20%">HK$ ${item.balance}</td>
                            				<td width="20%">${item.descr}</td> 
                            			</tr>
                            			</c:forEach>
                            		</tbody>
                            	</table>                            	
                            </div>
                            <div class="row mb-2">
                            	<div class="col-md-4">共有${record.total}条记录, 第${record.page}/${record.totalPage}页</div>
                            	<c:if test="${record.page==1}">
                            		<c:set var="firstStatus" value="disabled"></c:set>
                            	</c:if>
                            	<c:if test="${record.page.equals(record.totalPage)}">
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
					</div>
                </div>
            </div>

        </div>
    </div>
	
</body>
</html>
