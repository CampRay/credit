<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>银行卡认证</title>
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
	       var hasResult=${result.data!=null};
	       if(hasResult) $("#exportBtn").show();
	       $("#exportBtn").on("click", function(event) {	    			    		
			    var htmlContent = $(".result-content").html();
			    var htmlText="<div class='row' style='font-family:SimHei'>"+htmlContent+"</div>";				    			    
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
                        <div class="right-title"><h3>银行卡信息三元素认证(元素)</h3></div>
                        <div class="search-content">
                            <form:form action="elebankcard3" commandName="bankCardModel" method="post">                            	
                                <input type="hidden" name="service" value="card_tio"/>
                                <div class="form-group  form-inline">
                                    <label class="col-lg-3 col-md-4 control-label">身份证号码：<span class="text-danger">*</span></label>
                                    <div class="col-lgd-9 col-md-8">
                                        <form:input type="text" class="w-100 p-1" path="idNo"/>
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <label class="col-lg-3 col-md-4 control-label">真实姓名：<span class="text-danger">*</span></label>
                                    <div class="col-lg-9 col-md-8">
                                        <form:input type="text" class="w-50 p-1" path="name" />
                                    </div>
                                </div>
                                <div class="form-group form-inline">
                                    <label class="col-lg-3 col-md-4 control-label">银行卡号：<span class="text-danger">*</span></label>
                                    <div class="col-lgd-9 col-md-8">
                                        <form:input type="text" class="w-100 p-1" path="acctNo" />
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
                        </div>
                        </c:if>
                    </div>

                </div>
            </div>

        </div>
    </div>	   
</body>
</html>
