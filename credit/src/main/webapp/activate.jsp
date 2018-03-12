<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>征信系统账号激活页面</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/style.css">    
    <script src="<%=request.getContextPath()%>/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/static/js/jquery-1.10.1.min.js" type="text/javascript"></script>
    
</head>
<body>
    <div class="login-bg">
        <div class="container">
            <span class="text-white font-weight-bold">征信系统</span>
        </div>
    </div>
    <div class="login-content">
        <div class="container bg-white">
            <div class="row">                
                <div class="col-12 p-5 bookmark">
                    <div class="p-4">
                    <div class="login-title"><h3>账号激活消息</h3></div>
                    <div class="text-center text-danger p-5 font-weight-bold" style="font-family: SimSun,NSimSun,SimHei;font-size: 26px;">${errorMsg}</div>
                    <div class="form-group">
                        <div class="text-center">
                            <a href="login" class="btn btn-info rounded-0 text-">&nbsp;&nbsp;返回登录页面&nbsp;&nbsp;</a>
                        </div>
                    </div>
                </div>
                </div>
            </div>

        </div>
    </div>
    <div class="fixed-bottom p-2" style="background: #41beda">
        <div class="container">
            <div class="text-center text-white">
                Copyright © CampRay
            </div>
        </div>
    </div>
</body>
</html>