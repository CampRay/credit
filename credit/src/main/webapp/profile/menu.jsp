<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String a1="",a2="",a3="",a4=""; 
	String uri=request.getRequestURI().replace(".jsp", "");
   	if(uri.endsWith("/userinfo")){
   		a1="active";	
   	}else if(uri.endsWith("/userpwd")){
   		a2="active";
   	}
   	else if(uri.endsWith("/balances")){
   		a3="active";
   	}
   	else if(uri.endsWith("/records")){
   		a4="active";
   	}
   	
%>
					<div class="left-title">
                        <div class="title-text">账户信息</div>
                    </div>
                    <hr/>
                    <div class="menu">                    	
                        <div class="menu-item <%=a1%>" onclick="location.href='<%=request.getContextPath()%>/userinfo'"><i class="fa fa-cog pr-3"></i>账户信息</div>
                        <div class="menu-item <%=a2%>" onclick="location.href='<%=request.getContextPath()%>/userpwd'"><i class="fa fa-lock pr-3"></i>修改密码</div>
                        <div class="menu-item <%=a3%>" onclick="location.href='<%=request.getContextPath()%>/balances'"><i class="fa fa-database pr-3"></i>充值管理</div>
                        <div class="menu-item <%=a4%>" onclick="location.href='<%=request.getContextPath()%>/records'"><i class="fa fa-eye pr-3"></i>查询记录</div>                     
                    </div>                    