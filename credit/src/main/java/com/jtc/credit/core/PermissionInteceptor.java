package com.jtc.credit.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jtc.credit.commons.SystemConstants;
import com.jtc.credit.dto.TadminUser;

public class PermissionInteceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {		
		//项目根路径
		String rootPath=request.getContextPath();		
		TadminUser loginUser= (TadminUser)request.getSession().getAttribute(SystemConstants.LOGINED);
		//If not login
		if (loginUser == null) {
			response.sendRedirect(rootPath+"/login");
			return false;
		}
		else{			
			return true;
		}		
	}

}
