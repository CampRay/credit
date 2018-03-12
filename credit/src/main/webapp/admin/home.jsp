<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>后台管理首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="<%=request.getContextPath()%>/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="<%=request.getContextPath()%>/assets/global/css/components.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/admin/layout/css/themes/blue.css" rel="stylesheet" type="text/css" id="style_color"/>

<!-- END THEME STYLES -->
<link rel="shortcut icon" href="<%=request.getContextPath()%>/media/image/favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<c:import url="/common/header"/>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	<%@include file="../common/left.jsp"%>
	<!-- END SIDEBAR -->
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">									
			<div class="row">			  
				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
					<div class="dashboard-stat blue-madison">
						<div class="visual">
							<i class="fa fa-users"></i>
						</div>
						<div class="details">
							<div class="number">${totalUser}</div>																			
							<div class="desc">注册用户总数</div>														
						</div>
						<a class="more" href="<%=request.getContextPath()%>/manager/">更多<i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
					<div class="dashboard-stat red-intense">
						<div class="visual">
							<i class="fa fa-money"></i>
						</div>
						<div class="details">
							<div class="number">${totalAmount}</div>
							<div class="desc">已充值总金额</div>							
						</div>
						<a class="more" href="<%=request.getContextPath()%>/admin/balanceReport">更多 <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
					<div class="dashboard-stat green-haze">
						<div class="visual">
							<i class="fa fa-bar-chart-o"></i>
						</div>
						<div class="details">
							<div class="number">${totalQueryAmount}</div>
							<div class="desc">征信查询总收入</div>	
						</div>
						<a class="more" href="<%=request.getContextPath()%>/admin/queryReport">更多<i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
			</div>
			<!-- END DASHBOARD STATS -->
			<div class="clearfix">
			</div>
			
			<div class="row">
				 <div class="col-md-12">
					<!-- BEGIN PORTLET-->
					<div class="portlet box yellow">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-gift"></i>当前月份数据统计
							</div>
						</div>						
						<div class="portlet-body">																
							<table id="user_role_info" class="table table-bordered table-striped">
								<tbody>
									<tr>
										<td style="width: 50%">当月注册用户数</td>
										<td style="width: 50%">${monthUser}</td>
									</tr>									
									<tr>
										<td style="width: 50%">当月充值用户数</td>
										<td style="width: 50%">${monthAmountUser}</td>
									</tr>
									<tr>
										<td style="width: 50%">当月充值总金额</td>
										<td style="width: 50%">${monthAmount}</td>
									</tr>	
									<tr>
										<td style="width: 50%">当月征信查询总金额</td>
										<td style="width: 50%">${monthQueryAmount}</td>
									</tr>
									<tr>
										<td style="width: 50%">当月征信查询总次数</td>
										<td style="width: 50%">${monthQuery}</td>
									</tr>									
								</tbody>
							</table>
						</div>						
					</div>
					<!-- END PORTLET-->
				</div>
				
			</div>					    		
			
		</div>
	</div>
	<!-- END CONTENT -->	
</div>
<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	 
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script src="<%=request.getContextPath()%>//assets/global/plugins/respond.min.js"></script>
	<script src="<%=request.getContextPath()%>//assets/global/plugins/excanvas.min.js"></script> 
	<![endif]-->		 
    <script src="<%=request.getContextPath()%>/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>		
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>						

	<!-- BEGIN PAGE LEVEL SCRIPTS -->	
	<script src="<%=request.getContextPath()%>/assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>		
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		jQuery(document).ready(function() {  
		   Metronic.init();
		   Layout.init(); // init layout
		});
	</script>

	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>