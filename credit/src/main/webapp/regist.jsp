<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>征信系统注册页面</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/style.css">    
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/static/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">	   
	    jQuery(document).ready(function() {
	       $("#uname_error").hide();
	       $("#pwd_error").hide();
	       $("#repwd_error").hide();
	       $("#email_error").hide();
		   $("form").submit( function () {
			   var uname=$.trim($("#uname").val());
			   if(uname==""){
				   $("#uname_error").text("请输入注册账号")
				   $("#uname_error").show();
				   $("#uname").focus();
				   return false;
			   }			   
			   else if(!(/^\S{5,20}$/.test(uname))){
				   $("#uname_error").text("注册账号必须是大于等于5并且小于等于20长度的非空白字符！")
				   $("#uname_error").show();
				   $("#uname").focus();
				   return false;
			   }
			   else{				   
				   $("#uname_error").hide();				   
			   }
			   var pwd=$.trim($("#pwd").val());
			   if(pwd==""){
				   $("#pwd_error").text("请输入登录密码")
				   $("#pwd_error").show();	
				   $("#pwd").focus();
				   return false;
			   }
			   else if(!(/^\w{6,20}$/.test(pwd))){
				   $("#pwd_error").text("登录密码必须是大于等于6并且小于等于20长度的字母、数字或下划线组成的字符串！")
				   $("#pwd_error").show();	
				   $("#pwd").focus();
				   return false;
			   }
			   else{				   
				   $("#pwd_error").hide();				   
			   }
			   var repwd=$.trim($("#repwd").val());
			   if(repwd==""){
				   $("#repwd_error").text("请输入确认密码")
				   $("#repwd_error").show();	
				   $("#repwd").focus();
				   return false;
			   }
			   else if(repwd!=pwd){
				   $("#repwd_error").text("确认密码必须和登录密码相同！")
				   $("#repwd_error").show();
				   $("#repwd").focus();
				   return false;
			   }
			   else{				   
				   $("#repwd_error").hide();				   
			   }
			   
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
    <div class="login-bg">
        <div class="container">
            <span class="text-white font-weight-bold">征信系统</span>
        </div>
    </div>
    <div class="login-content">
        <div class="container bg-white">
            <div class="row">
                <div class="col-md-6 mt-5 mb-5 reg-left">
                    <div class="pl-4 pr-4">
                        <h4 class="" style="color: #3f5853">企业及个人征信服务</h4>
                        <h5 class="text-info">政府公开信息、精准识别、快速响应、多模式接入</h5>
                        <div>我们的征信系统基于政府公开数据，开展多维度信息聚合认证服务，认证实名（身份证、银行卡、工商企业等）信息的一致性、实时性、有效性，
                            为您提供定制化的实名综合数据认证、核查。</div>
                        <br/>
                        <img src="static/images/creditreport.jpg" width="100%" class="pb-2"/>
                        <a href="login" class="btn btn-info rounded-0 text-">&nbsp;&nbsp;返回登录&nbsp;&nbsp;</a>
                    </div>
                </div>
                <div class="col-md-6 mt-lg-5 mb-lg-5 mt-md-3 mb-md-3 mt-sm-4">
                    <div class="mr-lg-4 ml-lg-2 ">
                        <div class="login-title"><h3>用戶注册 <span style="color:#41beda ">Sign up</span></h3></div>
                        <div class="search-content">
                            <form:form action="regist" commandName="registModel" method="post">
                                <div class="form-group form-inline">
                                    <div class="col-lg-4 col-md-4 text-md-right">注册账号:</div>
                                    <div class="col-lg-8 col-md-8">
                                        <form:input type="text" path="uname" style="height: 30px;width:90%;" id="uname" /><span class="text-danger">*</span>
                                        <div class="text-danger" id="uname_error"></div>
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <div class="col-lg-4 col-md-4 text-md-right">登录密码:</div>
                                    <div class="col-lg-8 col-md-8">
                                        <form:input type="password" path="pwd" class="" style="height: 30px;width:90%;" id="pwd" /><span class="text-danger">*</span>
                                        <div class="text-danger" id="pwd_error"></div>
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <div class="col-lg-4 col-md-4 text-md-right">确认密码:</div>
                                    <div class="col-lg-8 col-md-8">
                                        <form:input type="password" path="repwd" class="" style="height: 30px;width:90%;" id="repwd" /><span class="text-danger">*</span>
                                        <div class="text-danger" id="repwd_error"></div>
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <div class="col-lg-4 col-md-4 text-md-right">电子邮箱:</div>
                                    <div class="col-lg-8 col-md-8">
                                        <form:input type="text" path="email" class="" style="height: 30px;width:90%;" id="email" /><span class="text-danger">*</span>
                                        <div class="text-danger" id="email_error"></div>
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <div class="col-lg-4 col-md-4 text-md-right">公司名称:</div>
                                    <div class="col-lg-8 col-md-8">
                                        <form:input type="text" path="company" class="w-100" style="height: 30px;" id="company" />
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <div class="col-lg-4 col-md-4 text-md-right">联系电话:</div>
                                    <div class="col-lg-8 col-md-8">
                                        <form:input type="text" path="mobile" class="w-100" style="height: 30px;" id="mobile" />
                                    </div>
                                </div>                                

                                <div class="form-group">
                                    <div class="ml-3 mr-3">
                                        <button type="submit" class="btn btn-block btn-info rounded-0">注&nbsp;&nbsp;册</button>
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
    <div class="fixed-bottom p-2" style="background: #41beda">
        <div class="container">
            <div class="text-center text-white">
                Copyright © CampRay
            </div>
        </div>
    </div>
</body>
</html>