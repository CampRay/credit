/**   
* @Title: BaseController.java 
* @Package com.uswop.action 
*
* @Description: 积分管理系统
* 
* @date Sep 10, 2014 3:27:05 PM
* @version V1.0   
*/ 
package com.jtc.credit.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContext;

import com.jtc.credit.commons.SystemConstants;
import com.jtc.credit.dto.TadminUser;


/** 
 * <p>Spring控制器的基类</p>
 * <此类实现了一些控制器处理类通用的方法，实际的业务控制器可以根据需要继承此类>
 * @ClassName: BaseController 
 * @author Phills Li 
 *  
 */ 
public class BaseController {
	
	protected final String ERROR_MSG_KEY="errorMsg";
	protected final String LOGIN_TO_URL="LoginToUrl";
	
	/** 
	 * <p>Description:Get the login user from session</p>
	 * @Title: getSessionUser 
	 * @param request
	 * @return User
	 * @throws 
	 */ 
	protected TadminUser getSessionUser(HttpServletRequest request){
		return (TadminUser)request.getSession().getAttribute(SystemConstants.LOGINED);
	}
		
	/** 
	 * <p>Description:Save the login user into session</p>
	 * @Title: setSessionUser 
	 * @param request
	 * @param user
	 * @throws 
	 */ 
	protected void setSessionUser(HttpServletRequest request,TadminUser user){
		request.getSession().setAttribute(SystemConstants.LOGINED, user);		
	}
		
	/** 
	 * <p>获取基于应用程序的url的绝对路径</p>
	 * @Title: getAppBaseUrl 
	 * @param request
	 * @param url
	 * @return String
	 * @throws 
	 */ 
	public final String getAppBaseUrl(HttpServletRequest request,String url){
		Assert.hasLength(url,"url不能为空");
		Assert.isTrue(url.startsWith("/"),"必须以/开头");
		return request.getContextPath()+url;
	}
	
	/**
	 * <p>Description:根据资源code获取资源文件中对应的消息</p>
	 * @Title: getMessage 
	 * @param request
	 * @param code
	 * @param args
	 * @return
	 * @throws
	 */
	public String getMessage(HttpServletRequest request,String code,Object[] args){
//		ApplicationContext ctx =WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());		
//		MessageSource ms=(MessageSource)ctx.getBean("messageSource");
		RequestContext requestContext = new RequestContext(request);
		if(args==null){
			return requestContext.getMessage(code);
		}
		return requestContext.getMessage(code, args);
	}
	
	public String getLocale(HttpServletRequest request){
		//Locale locale=LocaleContextHolder.getLocale(); 
		Locale locale=(Locale)request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if(locale==null){
			return "en_US";
		}
		return locale.toString();
		
	}
	
	/**
	 * <p>Description:根据资源code获取资源文件中对应的消息</p>
	 * @Title: getMessage 
	 * @param request
	 * @param code
	 * @param arg
	 * @return
	 * @throws
	 */
	public String getMessage(HttpServletRequest request,String code,Object arg){				
		return getMessage(request,code, new Object[]{arg});
	}
	
	/**
	 * <p>Description:根据资源code获取资源文件中对应的消息</p>
	 * @Title: getMessage 
	 * @param request
	 * @param code
	 * @return
	 * @throws
	 */
	public String getMessage(HttpServletRequest request,String code){				
		return getMessage(request,code, null);
	}
	
	/**
	 * <p>Description:改变当前语言环境</p>
	 * @Title: changeLocale 
	 * @param request
	 * @param locale
	 * @throws
	 */
	public void changeLocale(HttpServletRequest request,Locale locale){
		RequestContext requestContext = new RequestContext(request);
		requestContext.changeLocale(locale);
	}
	
	/**
	 * <p>Description:改变当前语言环境</p>
	 * @Title: changeLocale 
	 * @param request
	 * @param locale
	 * @throws
	 */
	public void changeLocale(HttpServletRequest request,String localeStr){
		RequestContext requestContext = new RequestContext(request);
		if(localeStr!=null&&!localeStr.isEmpty()){
			String[] strs=localeStr.split("_");			
			if(strs.length==1){
				Locale locale=new Locale(strs[0]);
				requestContext.changeLocale(locale);
			}
			else if(strs.length==2){
				Locale locale=new Locale(strs[0],strs[1]);
				requestContext.changeLocale(locale);
			}	
		}		
	}
	
	/**
	 * <p>Description:获取登录错误次数</p>
	 * @Title:getLoginFailureTimes
	 * @param request
	 * @return LoginFailureModel
	 */
	public Long getLoginFailureTimes(HttpServletRequest request){
		if(request.getSession().getAttribute(SystemConstants.LOGIN_ERROR)==null){
			return null;
		}
		return (Long) request.getSession().getAttribute(SystemConstants.LOGIN_ERROR);
	}
	
	/**
	 * <p>Description:save login error info</p>
	 * @Title:saveLoginErrorTims
	 * @param request
	 * 
	 */
	public void saveLoginErrorTims(HttpServletRequest request){		
		Long errorNum=getLoginFailureTimes(request);
		if(errorNum==null){
			request.getSession().setAttribute(SystemConstants.LOGIN_ERROR, new Long(1));
		}
		else{
			//如果会话中保存的值大于10000则表示是时间记录
			if(errorNum>10000){
				Long time_interval = System.currentTimeMillis()-errorNum;
				//如果登录间隔时间大于10分钟
				if(time_interval>600000){
					request.getSession().setAttribute(SystemConstants.LOGIN_ERROR, new Long(4));
				}
				else{
					request.getSession().setAttribute(SystemConstants.LOGIN_ERROR, new Long(System.currentTimeMillis()));
				}
			}
			else{
				//如果已经登录失败5次，则保存为当前时间戳
				if(errorNum>=5){
					request.getSession().setAttribute(SystemConstants.LOGIN_ERROR, new Long(System.currentTimeMillis()));
				}
				else{
					errorNum++;
					request.getSession().setAttribute(SystemConstants.LOGIN_ERROR, new Long(errorNum));
				}
			}
		}									
	}
}
