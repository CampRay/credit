<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>诚信信息核查</title>
    <link href="<%=request.getContextPath()%>/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/style.css">    
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/static/js/jquery-1.10.1.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/static/js/datepicker.js" type="text/javascript"></script>
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
	       $("#email_error").hide();
	       $("form").submit( function () {
	    	   var email=$.trim($("#email").val());
			   if(email==""){
				   $("#email_error").text("请输入电子邮箱地址")
				   $("#email_error").show();	
				   $("#email").focus();
				   return false;
			   }
			   else if(!(/^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(email))){
				   $("#email_error").text("请输入正确格式的电子邮箱！")
				   $("#email_error").show();
				   $("#email").focus();
				   return false;
			   }
			   else{				   
				   $("#email_error").hide();				   
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
                        <div class="right-title"><h3>修改账户信息</h3></div>
                        <div class="search-content">
                            <form:form action="userinfo" commandName="userModel" method="post"> 
                            	<form:input type="hidden" path="adminId"/>                              	                      
                                <div class="form-group  form-inline">
                                    <div class="col-lg-3 col-md-4  col-sm-3 text-md-right">登录账号：</div>
                                    <div class="col-lgd-9 col-md-8 col-sm-9">${userModel.adminId}</div>
                                </div>                                                                 
                                <div class="form-group  form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">电子邮箱：<span class="text-danger">*</span></div>
                                    <div class="col-lgd-9 col-md-8">
                                        <form:input type="text" class="w-100 p-1" path="email"/>
                                        <span class="text-danger" id="email_error"></span>
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">真实名称：</div>
                                    <div class="col-lg-9 col-md-8">
                                        <form:input type="text" class="w-50 p-1" path="adminInfo.realName" />
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">公司名称：</div>
                                    <div class="col-lg-9 col-md-8">
                                        <form:input type="text" class="w-50 p-1" path="adminInfo.company" />
                                    </div>
                                </div>     
                                <div class="form-group form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">联系电话：</div>
                                    <div class="col-lg-9 col-md-8">
                                        <form:input type="text" class="w-50 p-1" path="adminInfo.mobile" />
                                    </div>
                                </div>                                                            
                                <div class="form-group form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">职位：</div>
                                    <div class="col-lg-9 col-md-8">
                                        <form:input type="text" class="w-100 p-1" path="adminInfo.position" />
                                    </div>
                                </div>                                                        
								<div class="form-group form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">地址：</div>
                                    <div class="col-lg-9 col-md-8">
                                        <form:input type="text" class="w-100 p-1" path="adminInfo.address" />
                                    </div>
                                </div>    
                                <div class="form-group form-inline bg-secondary p-1">
                                    <div class="col-lg-3 col-md-4 text-md-right"></div>
                                    <div class="col-lgd-9 col-md-8">
                                        <button type="submit" class="btn btn-info">&nbsp;&nbsp;&nbsp;&nbsp;修改&nbsp;&nbsp;&nbsp; &nbsp;</button>
                                        <button type="reset" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;取消&nbsp;&nbsp;&nbsp;&nbsp;</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                        <c:if test="${errorMsg!=null&&!errorMsg.isEmpty()}">	
                        <div class="text-center text-danger">${errorMsg}</div>
                        </c:if>                        
					</div>
                </div>
            </div>

        </div>
    </div>
	
</body>
</html>
