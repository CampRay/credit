<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String a1="",a2="",a3="",a4="",a5="",a6="",a7="",a8="",a9=""; 
	String uri=request.getRequestURI().replace(".jsp", "");
   	if(uri.endsWith("/admin/home")){
   		a1="active";	
   	}else if(uri.endsWith("/admin/manager")||uri.endsWith("/admin/setting")){
   		a2="active open";
   		if(uri.endsWith("/admin/manager")){
   			a3="active";
   		}
   		if(uri.endsWith("/admin/setting")){   			
   			a4="active";
   		}
   	}
   	else if(uri.endsWith("/admin/apiprice")){
   		a5="active";
   	}   
   	else if(uri.endsWith("/admin/breport")||uri.endsWith("/admin/qreport")){
   		a6="active open";
   		if(uri.endsWith("/admin/breport")){
   			a7="active";
   		}
   		if(uri.endsWith("/admin/qreport")){   			
   			a8="active";
   		}
   	}
   	else if(uri.endsWith("/admin/check")){
   		a9="active";
   	}  
   	
%>

    <!-- BEGIN SIDEBAR -->
	<div class="page-sidebar-wrapper">
		<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
		<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
		<div class="page-sidebar navbar-collapse collapse">
		<!-- BEGIN SIDEBAR MENU -->        
		<ul class="page-sidebar-menu" data-auto-scroll="true" data-slide-speed="200">
			<!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
			<li class="sidebar-toggler-wrapper">
				<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				<div class="sidebar-toggler">
				</div>
				<!-- END SIDEBAR TOGGLER BUTTON -->
			</li>
			<!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
			<li class="sidebar-search-wrapper">&nbsp;</li>  
			<li class="start <%=a1%>">															
				<a href="<%=request.getContextPath()%>/admin/home">
				<i class="icon-home"></i> 
				<span class="title">管理首页</span>
				</a>												
			</li>            
        	<li class="<%=a2%>">															
				<a href="javascript;">
				<i class="icon-wrench"></i><span class="title">系统管理</span><span class="arrow"></span>																					
				</a>				
				<ul class="sub-menu">						
					<li class="<%=a3%>">
						<a href="<%=request.getContextPath()%>/manager">注册用户管理</a>
					</li>						
					<li class="<%=a4%>">
						<a href="<%=request.getContextPath()%>/settings">系统配置</a>
					</li>																												
				</ul>					
			</li>
			<li class="<%=a9%>">															
				<a href="<%=request.getContextPath()%>/check">
				<i class="fa fa-tasks"></i> 
				<span class="title">充值申请列表</span>
				</a>												
			</li>
			<li class="<%=a5%>">															
				<a href="<%=request.getContextPath()%>/admin/apiPrice">
				<i class="icon-energy"></i> 
				<span class="title">征信查询订价</span>
				</a>												
			</li>
			<li class="<%=a6%>">															
				<a href="javascript;">
				<i class="icon-docs"></i> 
				<span class="title">报表统计</span>
				</a>				
				<ul class="sub-menu">
					<li class="<%=a7%>">
						<a href="<%=request.getContextPath()%>/admin/balanceReport">用户充值统计报表</a>
					</li>
					<li class="<%=a8%>">
						<a href="<%=request.getContextPath()%>/admin/queryReport">征信查询统计报表</a>
					</li>																											
				</ul>					
			</li>
																
		</ul>
		<!-- END SIDEBAR MENU -->
	   </div>
    </div>    
   <!-- END SIDEBAR -->