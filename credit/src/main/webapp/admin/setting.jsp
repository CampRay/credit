<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>系统参数配置</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="<%=request.getContextPath()%>/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="<%=request.getContextPath()%>/assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/global/plugins/select2/select2.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css" />
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="<%=request.getContextPath()%>/assets/global/css/components.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/global/css/plugins.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css" />
<link id="style_color" href="<%=request.getContextPath()%>/assets/admin/layout/css/themes/blue.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="<%=request.getContextPath()%>/static/images/favicon.ico" />
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
	<c:import url="/common/header" />
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@include file="../common/left.jsp"%>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">				
				
				<!-- BEGIN PAGE CONTENT-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box grey-cascade">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i>
									系统参数配置表
								</div>
								<div class="actions">									
								    <button id="enable" class="btn blue">锁定/修改</button>							    								    							    														
								</div>
							</div>
							<div class="portlet-body">
								<table id="system_setting" class="table table-bordered table-striped">
									<tbody>
										<tr>
											<td style="width: 15%">系统电子邮箱账号</td>
											<td style="width: 50%"><a href="#" id="email_username" data-type="text" data-pk="1" data-original-title="输入发送服务账号"> ${system_setting['Email_Username'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted"> ${system_setting['Email_Username'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">系统电子邮箱密码</td>
											<td style="width: 50%"><a href="#" id="email_password" data-type="password" data-pk="1" data-original-title="输入发送服务密码"> [hidden] </a></td>
											<td style="width: 35%"><span class="text-muted"> ${system_setting['Email_Password'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">系统电子邮箱发送服务Host</td>
											<td style="width: 50%"><a href="#" id="email_host" data-type="text" data-pk="1" data-original-title="输入发送服务Host"> ${system_setting['Email_Host'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted"> ${system_setting['Email_Host'][1]} </span></td>
										</tr>
										<tr>
											<td style="width: 15%">系统电子邮箱发送服务是否使用SSL</td>
											<td style="width: 50%"><a href="#" id="email_ssl" data-type="select" data-pk="1" data-original-title="是否使用SSL"> ${system_setting['Email_IsSSL'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted"> ${system_setting['Email_IsSSL'][1]} </span></td>
										</tr>	
										<tr>
											<td style="width: 15%">系统电子邮箱发送服务端口</td>
											<td style="width: 50%"><a href="#" id="email_port" data-type="text" data-pk="1" data-original-title="发送服务端口"> ${system_setting['Email_Port'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted"> ${system_setting['Email_Port'][1]} </span></td>
										</tr>		
										<tr>
											<td style="width: 15%">当前网站URL</td>
											<td style="width: 50%"><a href="#" id="web_url" data-type="text" data-pk="1" data-original-title="输入当前网站网址"> ${system_setting['Website_URL'][0]} </a></td>
											<td style="width: 35%"><span class="text-muted"> ${system_setting['Website_URL'][1]} </span></td>
										</tr>									
									</tbody>
								</table>
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT -->
			</div>
		</div>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
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
	<script src="<%=request.getContextPath()%>/assets/global/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/global/plugins/select2/select2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/global/plugins/moment.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/global/plugins/jquery.mockjax.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-editable/inputs-ext/wysihtml5/wysihtml5.js"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<%=request.getContextPath()%>/assets/global/plugins/json/json2.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/static/js/settingTableData.js"></script>
	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout	
			//Demo.init(); // init demo features
			SettingTable.init("<%=request.getContextPath()%>/");
		});
	</script>
</body>
<!-- END BODY -->

</html>