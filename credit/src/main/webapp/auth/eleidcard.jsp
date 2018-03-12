<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>身份证认证</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/style.css">
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/js/jquery-1.10.1.min.js"></script> 
     <script type="text/javascript">  
    	 $(window).resize(function(){
    		 $("#content_row").css("min-height",$(window).height()-110);
    	 });
    	 $(window).keydown(function(event){
	    	  switch(event.keyCode) {
	    	      case 116://F5按键
	    	    	  return false;	    	    	 
	    	  }
	    });
	    jQuery(document).ready(function() {    
	    	$("#content_row").css("min-height",$(window).height()-110);	
	    	var hasResult=${result.success};
	    	if(hasResult) $("#exportBtn").show();
	    	$("#exportBtn").on("click", function(event) {	    			    		
			    var htmlContent = $(".result-content").html();
			    var htmlText="<div class='row' style='font-family:SimHei'>"+htmlContent+"</div>";				    
			    htmlText=htmlText.replace("pic\"","pic\" /");
		        var form = $("<form></form>").attr("action", "export").attr("method", "post");
		        form.append($("<input></input>").attr("type", "hidden").attr("name", "html").attr("value", htmlText));
		        form.appendTo('body').submit().remove();
		   });
		});
    </script>   
</head>
<body>
    <%@include file="header.jsp"%>
    <div class="content">
        <div class="container" style="background-color:white;">
            <div class="row" id="content_row">
                <div class="col-lg-4 col-md-4 col-sm-12">
                    <c:import url="/auth/menu"/>
                </div>
                <div class="col-lg-8 col-md-8 col-sm-12 right-column">
                    <div class="right-content">
                        <div class="right-title"><h3>身份证认证 （元素）</h3></div>
                        <div class="search-content">
                            <form:form action="eleidcard" commandName="idCardModel" method="post">
                                <div class="form-group  form-inline">
                                    <label class="col-lg-3 col-md-4 control-label">身份证号码：<span class="text-danger">*</span></label>
                                    <div class="col-lgd-9 col-md-8">
                                        <form:input type="text" class="w-100 p-1" path="idCard"/>
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <label class="col-lg-3 col-md-4 control-label">真实姓名：<span class="text-danger">*</span></label>
                                    <div class="col-lg-9 col-md-8">
                                        <form:input type="text" class="w-50 p-1" path="realName" />
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <label class="col-lg-3 col-md-4 control-label">是否返照片：</label>
                                    <div class="col-lgd-9 col-md-8">
                                        <form:select path="isPhoto" class="w-50">                                                                                        
                                            <form:option value="1">是</form:option>
                                            <form:option value="0">否</form:option>
                                        </form:select>
                                    </div>
                                </div>

                                <div class="form-group form-inline bg-secondary p-1">
                                    <label class="col-lg-3 col-md-4 control-label"></label>
                                    <div class="col-lgd-9 col-md-8">
                                        <button type="submit" class="btn btn-info">&nbsp;&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp; &nbsp;</button>
                                        <button type="reset" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;取消&nbsp;&nbsp;&nbsp;&nbsp;</button>
                                        <button type="button" class="btn" id="exportBtn" style="display:none;">&nbsp;&nbsp;&nbsp;&nbsp;导出结果&nbsp;&nbsp;&nbsp;&nbsp;</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                        <c:if test="${errorMsg!=null&&!errorMsg.isEmpty()}">	
                        <div class="text-center text-danger">系统异常：${errorMsg}</div>
                        </c:if>
                        <c:if test="${result!=null&&result.data==null}">	
                        <div class="text-center text-danger">查询失败：${result.code} - ${result.message}</div>
                        </c:if>                        
						<c:if test="${result.data!=null}">		
                        <div class="result-content">
                            <div class="text-center font-weight-bold" style="padding-bottom:8px;">
                                	查询结果：${result.data.result}                           	
                            </div>
                            <div class="p-2 w-100 bg-light">
                            	<c:if test="${result.data.name!=null&&!result.data.name.isEmpty()}">
                            	<div class="form-group form-inline">
                                    <label class="col-md-4 control-label font-weight-bold">姓名：</label>
                                    <div class="col-md-8">${result.data.name}</div>
                                </div>
                                </c:if>
                                <c:if test="${result.data.birthday!=null&&!result.data.birthday.isEmpty()}">
                                <div class="form-group form-inline">
                                    <label class="col-md-4 control-label font-weight-bold">出生日期：</label>
                                    <div class="col-md-8">${result.data.birthday}</div>
                                </div>
                                </c:if>
                                <c:if test="${result.data.idNumber!=null&&!result.data.idNumber.isEmpty()}">
                                <div class="form-group form-inline">
                                    <label class="col-md-4 control-label font-weight-bold">身份证号码：</label>
                                    <div class="col-md-8">${result.data.idNumber}</div>
                                </div>  
                                </c:if>
                                <c:if test="${result.data.gender!=null&&!result.data.gender.isEmpty()}">
                                <div class="form-group form-inline">
                                    <label class="col-md-4 control-label font-weight-bold">性别：</label>
                                    <div class="col-md-8">${result.data.gender}</div>
                                </div>   
                                </c:if>                          
                                <c:if test="${result.data.photo!=null&&!result.data.photo.isEmpty()}">	
                                <div class="form-group form-inline">
                                    <label class="col-md-4 control-label font-weight-bold">返回照片：</label>
                                    <div class="col-md-8"><img src="data:image/jpeg;base64,${result.data.photo}" style="max-width: 100%;height:auto;" id="pic"></div>
                                </div>
                                </c:if>
                            </div>
                        </div>
                        </c:if>
                    </div>

                </div>
            </div>

        </div>
    </div>	   
</body>
</html>
