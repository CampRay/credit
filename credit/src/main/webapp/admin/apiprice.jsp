<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>征信查询订价</title>
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
				<div class="page-bar">					
				</div>				
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box grey-cascade">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i>
									征信查询接口价格表
								</div>
							</div>
							<div class="portlet-body">
								<table id="price_setting" class="table table-bordered table-striped">
									<thead>
										<tr><th>编辑</th><th>接口来源</th><th>查询接口名称</th><th>接口说明</th><th>查询价格</th><th>编辑</th></tr>
									</thead>									
								</table>
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT -->
				
				<!-- BEGIN ADD Money FORM-->
				<div class="modal" id="edit_price" tabindex="-1" data-width="500">
					<div class="modal-header">
						<button id="closeEditPriceModal" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">修改价格</h4>
					</div>
					<div id="editPriceMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM	-->					
						<form id="editPriceForm" action="editPrice" method="post" name="editPriceForm" class="form-horizontal form-bordered">
							<input type="hidden" id="apiId" name="id" class="form-control" value=""/>
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									修改价格出现错误，请检查
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">接口名称</label>
									<div class="col-md-9">
										<input type="text" id="apiName" name="apiName" class="form-control" value=""/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">接口来源</label>
									<div class="col-md-9">
										<input type="text" id="groupName" name="groupName" class="form-control" value=""/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">接口说明</label>
									<div class="col-md-9">
										<input type="text" id="descr" name="descr" class="form-control" value=""/>
									</div>
								</div>								
								<div class="form-group">
									<label class="control-label col-md-3">价格<span class="required"> * </span></label>
									<div class="col-md-9">										
										<input type="text" id="price" name="price" class="form-control"/>										
									</div>
								</div>													
								
							</div>
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div class="col-md-offset-6 col-md-6">
										<button type="submit" class="btn green" id="editPriceSubmit">确定</button>
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
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/respond.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/excanvas.min.js"></script> 
	<![endif]-->
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
	
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	
	<script src="<%=request.getContextPath()%>/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	
	<script src="<%=request.getContextPath()%>/assets/global/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
	
	
	
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<%=request.getContextPath()%>/assets/global/plugins/json/json2.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/static/js/priceTableData.js"></script>
	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout	
			//Demo.init(); // init demo features
			PriceTable.init("<%=request.getContextPath()%>/");
		});
	</script>
</body>
<!-- END BODY -->

</html>