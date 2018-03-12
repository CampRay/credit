<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>用户列表</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="<%=request.getContextPath()%>/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="<%=request.getContextPath()%>/assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="<%=request.getContextPath()%>/assets/global/css/components.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link id="style_color" href="<%=request.getContextPath()%>/assets/admin/layout/css/themes/blue.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="<%=request.getContextPath()%>/static/images/favicon.ico"/>
</head>
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
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				 <div class="page-bar">
					<%--<ul class="page-breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="<%=request.getContextPath()%>/home">Home</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="<%=request.getContextPath()%>/manager"><s:message code="system.management.user.admin"/></a>
						</li>
					</ul>--%>					
				</div> 
				<!-- END PAGE TITLE & BREADCRUMB-->
				
								
				
				<!-- BEGIN SEARCH FORM -->
				<div class="portlet-body">
					<form id="searchForm" name="searchForm" action="manager/managelist" class="form-horizontal" method="post">
					<div class="row">
						<div class="col-md-6">					
							<div class="form-group">
								<label class="col-md-3 control-label">用户帐号</label>
								<div class="col-md-9">
									<input name="adminId" type="text" class="form-control">							
								</div>
							</div>
						</div>
						<div class="col-md-6">	
							<div class="form-group">
								<label class="col-md-3 control-label">用户电邮</label>
								<div class="col-md-9">
									<input name="email" type="text" class="form-control">							
								</div>
							</div>
						</div>
					</div>
					<div class="row">								
						<div class="col-md-6">	
							<div class="form-group">
								<label class="col-md-3 control-label">记录状态</label>
								<div class="col-md-9">
									<div class="radio-list">
										<label class="radio-inline">
										<input type="radio" name="status" value="" checked/>所有 </label>
										<label class="radio-inline">
										<input type="radio" name="status" value="true"/>正常</label>
										<label class="radio-inline">
										<input type="radio" name="status" value="false"/>禁用</label>
									</div>									
								</div>
							</div>
						</div>
						<div class="col-md-6">	
							<div class="form-group">
								<div class="col-md-offset-3 col-md-9">
								<button type="submit" class="btn blue">搜索 <i class="fa fa-search"></i></button>
								<button type="reset" class="btn grey-cascade">重置 <i class="fa fa-reply"></i></button>
								</div>
							</div>
						</div>
					</div>
					
					</form>
				</div>
				<!-- END SEARCH FORM -->
				
				
				
				<!-- BEGIN PAGE CONTENT-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet  box grey-cascade">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i>注册用户记录列表
								</div>
								<div class="actions">									
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#add_users"><i class="fa fa-plus"></i>添加</a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#edit_users" id="openEditRightModal"><i class="fa fa-pencil"></i>编辑</a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#activate_users" id="openActiveadminsModal"><i class="fa fa-key"></i>可用</a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#deactivate_users" id="openDeactiveadminsModal"><i class="fa fa-lock"></i>禁用</a>								    								    							    														
								</div>
							</div>							
							<div class="portlet-body">																
								<table class="table table-striped table-hover table-bordered" id="adminusers_table">
									<thead>
										<tr>
											<th class="table-checkbox">
												<input type="checkbox" class="group-checkable" data-set="#adminusers_table .checkboxes"/>
											</th>
											<th>用户账号</th>
											<th>用户电邮</th>											
											<th>注册时间</th>
											<th>账户余额</th>
											<th>账户状态</th>
											<th></th>											
										</tr>
									</thead>
																						
								</table>
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT -->
				
				<!-- BEGIN ADD MODAL FORM-->
				<div class="modal" id="add_users" tabindex="-1" data-width="760">
					<div class="modal-header">
						<button id="closeAddModal" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">添加用户</h4>
					</div>
					<div id="addFormMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM	-->					
						<form id="addUsersForm" action="" method="post" name="addUsersForm" class="form-horizontal form-bordered">
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									添加用户出现错误，请检查
								</div>								
								<div class="form-group">
									<label class="control-label col-md-3">登录账号<span class="required"> * </span></label>
									<div class="col-md-9">										
										<input name="adminId" class="form-control"/>										
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">登录密码<span class="required"> * </span></label>
									<div class="col-md-9">										
										<input name="password" type="password" class="form-control"/>										
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">电子邮箱<span class="required">* </span></label>
									<div class="col-md-9">																				
										<input name="email" class="form-control"/>
									</div>
								</div>									
								
							</div>
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div class="col-md-offset-6 col-md-6">
										<button type="submit" class="btn green" id="addFormSubmit">确定</button>
										<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
									</div>
								</div>
							</div>
						</form>
						<!-- END FORM-->
					</div>					
				</div>				
				<!-- END ADD MODAL FORM-->
				
							
				<div class="modal" id="edit_users" tabindex="-1" data-width="760">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">编辑用户</h4>
					</div>
					<div id="editFormMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM-->						
						<form id="editUsersForm" action="editUsers" method="post" name="editUsersForm" class="form-horizontal form-bordered">
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									编辑用户出现错误，请检查
								</div>								
								<div class="form-group">
									<label class="control-label col-md-3">登录账号</label>
									<div class="col-md-9">										
										<input name="adminId" class="form-control" readonly="true"/>										
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">登录密码</label>
									<div class="col-md-9">																				
										<input name="password" type="password"  class="form-control"/>
										<span class="help-block">输入一个新的密码或者留空不修改密码</span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">电子邮箱</label>
									<div class="col-md-9">																				
										<input name="email" class="form-control"/>
									</div>
								</div>																																								
																 											
							</div>
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div class="col-md-offset-6 col-md-6">
										<button type="submit" class="btn green" id="editFormSubmit">确定</button>
										<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
									</div>
								</div>
							</div>
						</form>
						<!-- END FORM-->
					</div>					
				</div>				
				<!-- END EDIT MODAL FORM-->				
								
				<!-- BEGIN Activate MODAL FORM-->
				<div class="modal" id="activate_users" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p>您确定要启用所有已经选中的用户？</p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
						<button id="activateBtn" type="button" data-dismiss="modal" class="btn blue">确定</button>
					</div>					
				</div>	
				<!-- END Activate MODAL FORM-->
				
				<!-- BEGIN DEActivate MODAL FORM-->
				<div class="modal" id="deactivate_users" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p>您确定要禁用所有已经选中的用户？</p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
						<button id="deactivateBtn" type="button" data-dismiss="modal" class="btn blue">确定</button>
					</div>					
				</div>				
				<!-- END DELETE MODAL FORM-->
				
				<!-- BEGIN ADD Money FORM-->
				<div class="modal" id="add_money" tabindex="-1" data-width="500">
					<div class="modal-header">
						<button id="closeAddMoneyModal" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">账户充值</h4>
					</div>
					<div id="addMoneyMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM	-->					
						<form id="addMoneyForm" action="" method="post" name="addMoneyForm" class="form-horizontal form-bordered">
							
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									充值出现错误，请检查
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">用户账号</label>
									<div class="col-md-9">
										<input id="addMoneyId" name="adminId" class="form-control" value="" readonly="readonly"/>
									</div>
								</div>								
								<div class="form-group">
									<label class="control-label col-md-3">充值金額<span class="required"> * </span></label>
									<div class="col-md-9">										
										<input id="addMoneyAmount" name="balance" class="form-control"/>										
									</div>
								</div>													
								
							</div>
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div class="col-md-offset-6 col-md-6">
										<button type="submit" class="btn green" id="addMoneySubmit">确定</button>
										<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
									</div>
								</div>
							</div>
						</form>
						<!-- END FORM-->
					</div>					
				</div>				
				<!-- END ADD Money FORM-->
			</div>		
		</div>
	</div>	
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
	<div id="dialogDiv" class="container">
		
	</div>
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/respond.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/excanvas.min.js"></script> 
	<![endif]-->
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="<%=request.getContextPath()%>/assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/datatables/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<%=request.getContextPath()%>/assets/global/plugins/json/json2.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>	
	<script src="<%=request.getContextPath()%>/static/js/managersTableData.js"></script>
	<script>
	jQuery(document).ready(function() { 
		
	   Metronic.init(); // init metronic core components
	   Layout.init(); // init current layout	
	   
	   ManagersTable.init("<%=request.getContextPath()%>/");	   
    });
	</script>	
</body>
<!-- END BODY -->

</html>