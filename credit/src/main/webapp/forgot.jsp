<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>征信系统忘记密码页面</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/style.css">    
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/static/js/jquery-1.10.1.min.js" type="text/javascript"></script>
    <script type="text/javascript">	   
	    jQuery(document).ready(function() {    	
	       $("#email_error").hide();
		   $("form").submit( function () {
			   var val=$.trim($("#email").val());
			   if(val==""){
				   $("#email_error").text("请输入密码重置的电子邮箱")
				   $("#email_error").show();
				   $("#email").focus();
				   return false;
			   }
			   else if(!(/^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(val))){
				   $("#email_error").text("请输入有效的密码重置的电子邮箱")
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
    <div class="login-bg">
        <div class="container">
            <span class="text-white font-weight-bold">征信系统</span>
        </div>
    </div>
    <div class="login-content">
        <div class="container bg-white">
            <div class="row">
                <div class="col-md-6 mt-lg-5 mb-lg-5 mt-md-3 mb-md-3 mt-sm-4 login-left">
                    <div class="mr-lg-5 ml-lg-2 mr-md-2 ml-md-1">
                        <div class="login-title"><h3>忘记密码 <span style="color:#41beda ">User login</span></h3></div>
                        <div class="search-content">
                            <form:form action="resetPwd" commandName="user" method="post">
                                <div class="form-group">
                                    <div class="pl-lg-5 pr-lg-5">重置密码电子邮箱：</div>
                                    <div class="w-100 pl-lg-5 pr-lg-5">
                                        <input type="text" name="email" class="w-100" style="height: 30px;" id="email" />
                                        <span class="text-danger" id="email_error"></span>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <div class="pl-lg-5 pr-lg-5">
                                        <button type="submit" class="btn btn-block btn-info rounded-0">发送重置密码电邮</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                        <c:if test="${errorMsg!=null&&!errorMsg.isEmpty()}">	
                        <div class="text-center text-danger">${errorMsg}</div>
                        </c:if>
                    </div>
                </div>
                <div class="col-md-6 pt-lg-5 pb-lg-5 pt-md-3 pb-md-3 pb-3 bookmark">
                    <div class="pl-lg-2 pl-md-1 pl-sm-4 pr-sm-4">
                        <h4 class="" style="color: #3f5853">企业及个人征信服务</h4>
                        <h5 class="text-info">政府公开信息、精准识别、快速响应、多模式接入</h5>
                        <div>我们的征信系统基于政府公开数据，开展多维度信息聚合认证服务，认证实名（身份证、银行卡、工商企业等）信息的一致性、实时性、有效性，为您提供定制化的实名综合数据认证、核查。</div>
                        <div style="border-bottom: dashed 1px #3f5853;height: 20px;margin-bottom: 15px;"></div>
                        
                        <div class="form-group form-inline">
                            <div class="col-6">
                            	<div class="mt-4 text-info">还没有征信账号吗？</div>
                                <a href="regist" class="btn btn-info rounded-0">&nbsp;&nbsp;&nbsp;&nbsp;立即注册&nbsp;&nbsp;&nbsp; &nbsp;</a>
                            </div>
                            <div class="col-6">
                            	<div class="mt-4 text-info">已有征信账号吗？</div>
                                <a href="login" class="btn btn-info rounded-0">&nbsp;&nbsp;&nbsp;&nbsp;立即登录&nbsp;&nbsp;&nbsp; &nbsp;</a>
                            </div>
                        </div>                        
                    </div>
                </div>
            </div>

        </div>
    </div>
    <div class="fixed-bottom p-2" style="background: #41beda">
        <div class="container">
            <div class="text-center text-white">
                Copyright © CampRay
            </div>
        </div>
    </div>
</body>
</html>