<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String a1="",a2="",a3="",a4="",a5="",a6="",a7="",a8="",a9="",a10=""; 
	String uri=request.getRequestURI().replace(".jsp", "");
   	if(uri.endsWith("/auth/idcard")){
   		a1="active";	
   	}else if(uri.endsWith("/auth/person")){
   		a2="active";
   	}
   	else if(uri.endsWith("/auth/bankcard2")){
   		a3="active";
   	}
   	else if(uri.endsWith("/auth/bankcard3")){
   		a4="active";
   	}
   	else if(uri.endsWith("/auth/bankcard4")){
   		a5="active";
   	}
   	else if(uri.endsWith("/auth/eleidcard")){
   		a6="active";
   	}
   	else if(uri.endsWith("/auth/elerisklist")){
   		a7="active";
   	}
   	else if(uri.endsWith("/auth/elebankcard3")){
   		a8="active";
   	}
   	else if(uri.endsWith("/auth/elebankcard4")){
   		a9="active";
   	}
   	else if(uri.endsWith("/auth/elesaiclist")){
   		a10="active";
   	}
%>
					<div class="left-title">
                        <div class="title-text">征信 Credit</div>
                    </div>
                    <hr/>
                    <div class="menu">  
                   	<c:forEach items="${priceList}" var="item"> 
                   		<c:if test="${item.id==1}">
                   		<div class="menu-item <%=a1%>" onclick="location.href='<%=request.getContextPath()%>/auth/idcard'"><span class="menu-icon-id">身份证认证(新颜) ${item.price}元/次</span></div>
                   		</c:if>                 	
                   		<c:if test="${item.id==2}">
                   		<div class="menu-item <%=a2%>" onclick="location.href='<%=request.getContextPath()%>/auth/person'"><span class="menu-icon-heart">诚信信息核查(新颜) ${item.price}元/次</span></div>
                   		</c:if>
                   		<c:if test="${item.id==3}">
                   		<div class="menu-item <%=a3%>" onclick="location.href='<%=request.getContextPath()%>/auth/bankcard2'"><span class="menu-icon-credit">银行卡信息二元素认证(新颜) ${item.price}元/次</span></div>
                   		</c:if>
                   		<c:if test="${item.id==4}">
                   		<div class="menu-item <%=a4%>" onclick="location.href='<%=request.getContextPath()%>/auth/bankcard3'"><span class="menu-icon-credit">银行卡信息三元素认证(新颜) ${item.price}元/次</span></div>
                   		</c:if>
                   		<c:if test="${item.id==5}">
                   		<div class="menu-item <%=a5%>" onclick="location.href='<%=request.getContextPath()%>/auth/bankcard4'"><span class="menu-icon-credit">银行卡信息四元素认证(新颜) ${item.price}元/次</span></div>
                   		</c:if>
                   		<c:if test="${item.id==6}">
                   		<div class="menu-item <%=a6%>" onclick="location.href='<%=request.getContextPath()%>/auth/eleidcard'"><span class="menu-icon-id">身份证认证(元素) ${item.price}元/次</span></div>
                   		</c:if>
                   		<c:if test="${item.id==7}">
                   		<div class="menu-item <%=a7%>" onclick="location.href='<%=request.getContextPath()%>/auth/elerisklist'"><span class="menu-icon-heart">风险控制核查(元素) ${item.price}元/次</span></div>
                   		</c:if>
                   		<c:if test="${item.id==8}">
                   		<div class="menu-item <%=a8%>" onclick="location.href='<%=request.getContextPath()%>/auth/elebankcard3'"><span class="menu-icon-credit">银行卡信息三元素认证(元素) ${item.price}元/次</span></div>
                   		</c:if>
                   		<c:if test="${item.id==9}">
                   		<div class="menu-item <%=a9%>" onclick="location.href='<%=request.getContextPath()%>/auth/elebankcard4'"><span class="menu-icon-credit">银行卡信息四元素认证(元素) ${item.price}元/次</span></div>
                   		</c:if>
                   		<c:if test="${item.id==10}">
                   		<div class="menu-item <%=a10%>" onclick="location.href='<%=request.getContextPath()%>/auth/elesaiclist'"><span class="menu-icon-law">工商信息查询(元素) ${item.price}元/次</span></div>
                   		</c:if>
                   	</c:forEach>                        
                    </div>
                    <div style="margin: 50px 0;">
                        <img src="<%=request.getContextPath()%>/static/images/img_credit.jpg" width="100%"/>
                    </div>