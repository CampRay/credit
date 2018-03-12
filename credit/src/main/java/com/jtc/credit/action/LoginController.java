/**   
* @Title: LoginController.java 
* @Package com.uswop.action 
*
* @Description: TODO
* 
* @date Sep 10, 2014 3:06:32 PM
* @version V1.0   
*/ 
package com.jtc.credit.action;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jtc.credit.commons.EMailTool;
import com.jtc.credit.commons.SecurityTools;
import com.jtc.credit.commons.SystemConstants;
import com.jtc.credit.dto.TadminInfo;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TemaiMessage;
import com.jtc.credit.model.RegistModel;
import com.jtc.credit.service.AdminInfoService;
import com.jtc.credit.service.AdminUserService;

/** 
 * @ClassName: LoginController 
 * @Description: 
 * @author Phills Li
 * @date Sep 10, 2014 3:06:32 PM 
 *  
 */
@Controller
public class LoginController extends BaseController {	
	@Autowired
	private AdminUserService adminUserService;	
	@Autowired
	private AdminInfoService adminInfoService;
	   
	/** 
	 * <p>Open the login page</p>
	 * @Title: login 
	 * @return String
	 * @throws 
	 */     
    @RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mav=new ModelAndView();
		TadminUser tUser=new TadminUser();		
		mav.addObject("user", tUser);
		mav.setViewName("login");
		return mav;
	}
		
	
	/** 
	 * <p>User Login</p>
	 * @Title: login 
	 * @param request
	 * @param user
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,TadminUser user){						
		ModelAndView mav=new ModelAndView();
		mav.addObject("user", user);												
		//查询已经登录失败次数
		Long time = getLoginFailureTimes(request);
		
		if(time != null &&time>5&& System.currentTimeMillis()-time<600000){
			mav.addObject(ERROR_MSG_KEY, "登录失败超过限定次数，10分钟内将不允许再次登录。");
			mav.addObject("user", user);						
		}else{
			TadminUser tUser=adminUserService.getAdminUserByIdOrEmail(user.getAdminId());
			if(tUser==null){	
				if(time>=3){
					mav.addObject(ERROR_MSG_KEY, "登录失败，用户名或密码错误. 请注意您已经登录失败"+time+"次，如果超过3次，10分钟内将不允许再次登录！");
				}
				else{
					mav.addObject(ERROR_MSG_KEY, "登录失败，用户名或密码错误。");
				}
				saveLoginErrorTims(request);																	
			}
			else if(!SecurityTools.SHA1(user.getPassword()).equalsIgnoreCase(tUser.getPassword())){
				if(time>=3){
					mav.addObject(ERROR_MSG_KEY, "登录失败，用户名或密码错误. 请注意您已经登录失败"+time+"次，如果超过3次，10分钟内将不允许再次登录！");
				}
				else{
					mav.addObject(ERROR_MSG_KEY, "登录失败，用户名或密码错误.");
				}			
				saveLoginErrorTims(request);	
			}else if(!tUser.getStatus()){
				mav.addObject(ERROR_MSG_KEY, " 登录失败，此账号还没有被激活。");
				saveLoginErrorTims(request);
			}else{				
				setSessionUser(request, tUser);
				String toUrl=(String)request.getSession().getAttribute(LOGIN_TO_URL);
				request.getSession().removeAttribute(LOGIN_TO_URL);
				request.getSession().removeAttribute(SystemConstants.LOGIN_ERROR);								
				
				if(StringUtils.isEmpty(toUrl)){
					if(tUser.getRoleId()==1){
						toUrl="/admin/home";
					}					
					else{					
						toUrl="/auth/idcard";
					}
				}
				
				mav.setViewName("redirect:"+toUrl);						
			}	
		}
		return mav;
	}
	
	/** 
	 * <p>Open the regist page</p>
	 * @Title: login 
	 * @return String
	 * @throws 
	 */     
    @RequestMapping(value="/regist",method=RequestMethod.GET)
	public ModelAndView regist() {
		ModelAndView mav=new ModelAndView();		
		mav.addObject("registModel", new RegistModel());
		mav.setViewName("regist");
		return mav;
	}
	
	/** 
	 * <p>User regist</p>
	 * @Title: regist 
	 * @param request
	 * @param user
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	public ModelAndView regist(HttpServletRequest request,RegistModel regist){						
		ModelAndView mav=new ModelAndView();
		mav.addObject("registModel", regist);		
		mav.setViewName("regist");
		//查询已经登录失败次数
		Long time = getLoginFailureTimes(request);
		
		if(time != null &&time>5&& System.currentTimeMillis()-time<600000){
			mav.addObject(ERROR_MSG_KEY, "注册超过限定次数，10分钟内将不允许再次注册。");									
		}else{						
			TadminUser tUser=adminUserService.getAdminUserById(regist.getUname());
			if(tUser==null){
				tUser=adminUserService.getTadminUsersByEmail(regist.getEmail());
				if(tUser==null){
					TadminUser user=new TadminUser();
					user.setAdminId(regist.getUname());
					user.setPassword(SecurityTools.SHA1(regist.getPwd()));
					user.setEmail(regist.getEmail());
					user.setBalance(0);
					user.setRoleId(2);
					user.setStatus(false);
					user.setCreatedTime(System.currentTimeMillis());
					adminUserService.createAdminUser(user);
																			
					try{
						String webUrl=SystemConstants.Admin_Setting_Map.get(SystemConstants.WEBSITE_URL);
						TemaiMessage message=new TemaiMessage();
						message.setTo(user.getEmail());	
						StringBuffer sb=new StringBuffer();
						sb.append("<p>您好：</p><p>您已经成功注册了征信网站的会员账号，注册账号为："+user.getAdminId()+"， 请点击以下链接激活您的账号：<br/>");
						sb.append("<a href='"+webUrl+"/activate?code="+SecurityTools.encryptBASE64(user.getAdminId())+"'>"+webUrl+"/activate?code="+SecurityTools.encryptBASE64(user.getAdminId())+"</a></p>");
						sb.append("<p>此致<br/><a href='"+webUrl+"'>征信系统网站</a></p>");
						message.setText(sb.toString());
						message.setSubject("征信系统-激活账号");
						EMailTool.send(message,true);
						
						TadminInfo adminInfo=new TadminInfo();
						adminInfo.setAdminId(user.getAdminId());
						adminInfo.setCompany(regist.getCompany());
						adminInfo.setMobile(regist.getMobile());
						adminInfoService.createAdminInfo(adminInfo);
						
						mav.setViewName("activate");	
						mav.addObject(ERROR_MSG_KEY, "账号注册成功，系统将发送账号激活电邮到您的电子邮箱中，收到邮件后请点击邮件中的激活链接以便激活您的注册账号，激活成功后，您就可以正常使用您的注册账号！");
					}
					catch(Exception e){
						mav.addObject(ERROR_MSG_KEY, "注册失败，异常信息："+e.getMessage());
						adminUserService.deleteAdminUser(user);
					}
					
//					//登录账号并转到首页
//					setSessionUser(request, user);					
//					mav.setViewName("redirect:/auth/idcard");		
										
				}
				else{
					mav.addObject(ERROR_MSG_KEY, "注册失败，您输入的电子邮箱已经被其他账号注册过.");
				}
			}else{	
				mav.addObject(ERROR_MSG_KEY, "注册失败，您输入的注册账号已经被注册.");										
			}	
		}
		return mav;
	}
	
	/** 
	 * <p>激活帐号</p>
	 * @Title: activate 
	 * @return ModelAndView
	 * @throws 
	 */     
    @RequestMapping(value="/activate",method=RequestMethod.GET)
	public ModelAndView activate(String code) {
		ModelAndView mav=new ModelAndView();
		if(code==null||code.isEmpty()){
			mav.addObject(ERROR_MSG_KEY, "激活账号失败，激活码不能为空！");
		}
		else{
			try{
				String id=SecurityTools.decryptBASE64(code);
				TadminUser user=adminUserService.getAdminUserById(id);
				if(user==null){
					mav.addObject(ERROR_MSG_KEY, "激活账号失败，无效的激活码！");
				}
				else{
					adminUserService.activateUsersByIds(new String[]{id});
					mav.addObject(ERROR_MSG_KEY, "激活账号成功，您现在可以正常使用您的账号登录本网站！");
				}
			}
			catch(Exception e){
				mav.addObject(ERROR_MSG_KEY, "激活账号失败，错误原因："+e.getMessage());
			}
		}		
		return mav;
	}
	
	/** 
	 * <p>打开忘记密码页面</p>
	 * @Title: resetPwd 
	 * @return ModelAndView
	 * @throws 
	 */     
    @RequestMapping(value="/resetPwd",method=RequestMethod.GET)
	public ModelAndView resetPwd() {
		ModelAndView mav=new ModelAndView();			
		mav.addObject("user", new TadminUser());
		mav.setViewName("forgot");
		return mav;
	}
	
	@RequestMapping(value="/resetPwd",method=RequestMethod.POST)
	public ModelAndView resetPwd(HttpServletRequest request,String email){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forgot");
		mav.addObject("email",email);
		//查询已经失败次数
		Long time = getLoginFailureTimes(request);
		if(time != null &&time>5&& System.currentTimeMillis()-time<600000){
			mav.addObject(ERROR_MSG_KEY, "重置密码超过限定次数，10分钟内将不允许再次使用。");	
			saveLoginErrorTims(request);
		}else{
			TadminUser adminUser = adminUserService.getTadminUsersByEmail(email);
			if(adminUser == null){				 		 
				 mav.addObject(ERROR_MSG_KEY,"此电子邮箱没有关联任何注册账号，请输入您在注册账号时填写的电子邮箱!");
			}else{					   
				String random = UUID.randomUUID().toString().trim().replace("-","").substring(0,6);
				adminUser.setPassword(SecurityTools.SHA1(random));			
				TemaiMessage message=new TemaiMessage();
				message.setTo(email);			
				message.setText("<p>征信用户您好：</p><p>您的账号("+adminUser.getAdminId()+")密码已经被重新设置，新的登录密码是：</p><p style='color:red;font-size:20px;'>"+random+"</p>");
				message.setSubject("征信系统-重置密码");
				EMailTool.send(message,true);
				adminUserService.updateAdminUserPassword(adminUser);
				mav.addObject(ERROR_MSG_KEY,"密码已經被重置，新的密码已经发送到您的电子邮箱中.");	
				saveLoginErrorTims(request);
			}			
		}				
		return mav;
	}
	
	/** 
	 * <p></p>
	 * @Title: logout 
	 * @param session
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){		
		if((TadminUser)session.getAttribute(SystemConstants.LOGINED)!=null){		
		}
		session.removeAttribute(SystemConstants.LOGINED);			
		return "forward:login";
	}
}
