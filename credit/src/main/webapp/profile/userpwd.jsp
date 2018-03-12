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
		   $("#div_validDate").hide();
		   $("#div_validNo").hide();
		   $("#sel_cardtype").change( function() {
			   if($("#sel_cardtype").val()=="102"){
				   $("#div_validDate").show();
				   $("#div_validNo").show();
			   }
			   else{
				   $("#div_validDate").val("");
				   $("#div_validNo").val("");
				   $("#div_validDate").hide();
				   $("#div_validNo").hide();
			   }
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
                        <div class="right-title"><h3>修改登录密码</h3></div>
                        <div class="search-content">
                            <form:form action="userpwd" commandName="pwdModel" method="post">                               	                      
                                <div class="form-group  form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">当前密码：<span class="text-danger">*</span></div>
                                    <div class="col-lgd-9 col-md-8"><form:input type="text" class="w-100 p-1" path="oldpassword"/></div>
                                </div>                                                                                              
                                <div class="form-group  form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">新的密码：<span class="text-danger">*</span></div>
                                    <div class="col-lgd-9 col-md-8"><form:input type="password" class="w-100 p-1" path="newpassword"/></div>
                                </div>
                                <div class="form-group form-inline">
                                    <div class="col-lg-3 col-md-4 text-md-right">确认密码：<span class="text-danger">*</span></div>
                                    <div class="col-lg-9 col-md-8"><input type="password" class="w-100 p-1" id="repwd" /></div>
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
