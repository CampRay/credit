<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!-- BEGIN HEADER -->
<div class="page-header navbar navbar-fixed-top">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="page-header-inner">		
		<!-- BEGIN LOGO -->
		<div class="page-logo">
			<a href="<%=request.getContextPath()%>/admin/home">
			<img src="<c:url value="/"/>assets/admin/layout/img/logo.png" alt="logo" class="logo-default"/>
			</a>
			<div class="menu-toggler sidebar-toggler hide"></div>
		</div>
		<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
			</a>          
			<!-- END RESPONSIVE MENU TOGGLER -->            
			<!-- BEGIN TOP NAVIGATION MENU -->              
        <div class="top-menu">
			<ul class="nav navbar-nav pull-right">															
				<li class="dropdown dropdown-quick-sidebar-toggler"><a href="<c:url value="/"/>" class="dropdown-toggle">返回前台</a></li>
				<li class="dropdown dropdown-quick-sidebar-toggler"><a href="<c:url value="/"/>logout" class="dropdown-toggle"><i class="icon-logout"></i></a></li>				
			</ul>
			<!-- END TOP NAVIGATION MENU --> 
		</div>
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->
<div class="clearfix">
</div>