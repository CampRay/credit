<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>账户充值记录</title>
    <link href="<%=request.getContextPath()%>/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/style.css">    
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/static/js/jquery-1.10.1.min.js" type="text/javascript"></script>    
    <script type="text/javascript">
	    $(window).resize(function(){
			 $("#content_row").css("min-height",$(window).height()-110);
		 });
	    
	    jQuery(document).ready(function() {    
	       $("#content_row").css("min-height",$(window).height()-110);		
	       $("form").submit( function () {	    	   
	    	   var amount=$.trim($("#amount").val());
			   if(amount==""){
				   $("#amount_error").text("请输入您已经充值的金额！")
				   $("#amount_error").show();	
				   $("#amount").focus();
				   return false;
			   }
			   else if(!(/^(\-|\+)?\d+(\.\d+)?$/.test(amount))){
				   $("#amount_error").text("充值金额必须为数字！")
				   $("#amount_error").show();
				   $("#amount").focus();
				   return false;
			   }
			   else if(amount<=0){
				   $("#amount_error").text("充值金额必须是大于零的数字！")
				   $("#amount_error").show();
				   $("#amount").focus();
				   return false;
			   }
			   else{				   
				   $("#amount_error").hide();				   
			   }
			   
			   var descr=$("#descr").val();
			   if(descr==""){
				   $("#descr_error").text("充值说明必须填写，请简单描述成功充值付款的证明信息，例如银行转账成功的相关凭证信息，时间，银行帐号等。")
				   $("#descr_error").show();	
				   $("#descr").focus();
				   return false;
			   }
			   else if(descr.length>1000){
				   $("#descr_error").text("充值说明的内容不能超过500个字符！")
				   $("#descr_error").show();	
				   $("#descr").focus();
				   return false;
			   }
			   else{
				   $("#descr_error").hide();		
			   }
			   
			   var fpath=$("#ufile").val();
			   if(fpath!=""){				  
				   var arr=fpath.split(".");
				   var suffix=arr[arr.length-1].toLowerCase();
				   if(suffix.toLowerCase()!="png"&&suffix!="jpg"&&suffix!="jpeg"&&suffix!="gif"){
					   $("#file_error").text("充值证明图片只能是后缀名为png、jpg、jpeg或gif格式的图片！")
					   $("#file_error").show();	
					   $("#ufile").focus();
					   return false;
				   }
				   $("#file_error").hide();				   
			   }
			   return true;
		   });	   		
		});
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
                        <div class="right-title"><h3>账户充值申请</h3></div>   
                        <div class="search-content">
                            <form id="addForm" action="addMoney" method="post" enctype="multipart/form-data">                             	                              	                    	                     
                                <div class="form-group form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">充值金额(HK$)：<span class="text-danger">*</span></div>
                                    <div class="col-lg-9 col-md-8">
                                        <input id="amount" type="text" class="w-100 p-1" name="amount"/>  
                                        <span class="text-danger" id="amount_error"></span>                                
                                    </div>
                                </div>  
                                <div class="form-group form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">充值说明：<span class="text-danger">*</span></div>
                                    <div class="col-lg-9 col-md-8">
                                        <textarea id="descr" class="w-100 p-1" name="descr" rows="3"></textarea>  
                                        <span class="text-danger" id="descr_error"></span>                                
                                    </div>
                                </div>                                                                
                                <div class="form-group  form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">转账凭证图片：</div>
                                    <div class="col-lgd-9 col-md-8">
                                        <input id="ufile" type="file" name="file" class="w-100"/>                                        
                                        <span class="text-danger" id="file_error"></span>
                                    </div>
                                </div>                                                                  
                                <div class="form-group form-inline bg-secondary p-1">
                                    <div class="col-lg-3 col-md-4 text-md-right"></div>
                                    <div class="col-lgd-9 col-md-8">
                                        <button type="submit" class="btn btn-info">&nbsp;&nbsp;&nbsp;&nbsp;提交&nbsp;&nbsp;&nbsp; &nbsp;</button>
                                        <button type="reset" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;取消&nbsp;&nbsp;&nbsp;&nbsp;</button>
                                    </div>
                                </div>
                            </form>
                        </div>                     
                        <c:if test="${errorMsg!=null&&!errorMsg.isEmpty()}">	
                        <div class="text-center text-danger">${errorMsg}</div>
                        </c:if>  
                        <div class="result-content p-3">
                        	<c:if test="${logList.size()==0}">
                        		<div class="text-center w-100">没有充值记录，请联系客服人员进行充值</div>
                        	</c:if>                            
                            <c:if test="${logList.size()>0}">
                            <div class="w-100">
                            	<table class="table table-bordered">
                            		<thead>
                            			<tr>
                            				<td width="20%">充值日期</td>
                            				<td width="20%">充值金额</td>
                            				<td width="20%">账户余额</td>                            				
                            				<td width="25%">充值说明</td>
                            				<td width="15%">记录类型</td>                                				                        				
                            			</tr>
                            		</thead>
                            		<tbody>
                            			<c:forEach items="${logList}" var="item">
                            			<tr>
                            				<td>${item.createdTimeStr}</td>
                            				<td>HK$ ${item.amount}</td>
                            				<td>HK$ ${item.balance}</td>
                            				<td>${item.descr}</td> 
                            				<td><c:if test="${item.type}">申请充值</c:if><c:if test="!${item.type}">实际充值</c:if></td> 
                            			</tr>
                            			</c:forEach>
                            		</tbody>
                            	</table>                            	
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
