<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="navbar fixed-top bg-dark">
    <div class="container">    	
   		<div class="col-6"><h3 class="text-white font-weight-bold"><a href="<c:url value="/"/>">征信系统</a></h3></div>
       	<div class="col-6">       		
       		<a href="<%=request.getContextPath()%>/logout" class="text-white float-right pl-3 pr-3">退出账户</a>
       		<a href="<%=request.getContextPath()%>/userinfo" class="text-white float-right pl-3 pr-3">账户信息</a>
       	</div>        
    </div>
</div>