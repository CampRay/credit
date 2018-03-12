package com.jtc.credit.action;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtc.credit.commons.EMailTool;
import com.jtc.credit.commons.MyException;
import com.jtc.credit.commons.SecurityTools;
import com.jtc.credit.commons.SystemConstants;
import com.jtc.credit.dto.TadminInfo;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TbalanceLog;
import com.jtc.credit.dto.TemaiMessage;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;
import com.jtc.credit.service.AdminInfoService;
import com.jtc.credit.service.AdminUserService;
import com.jtc.credit.service.BalanceLogService;

@Controller
@RequestMapping(value="manager")
public class ManagerController extends BaseController {			
	@Autowired
	private AdminUserService adminUserService;	
	@Autowired
	private AdminInfoService adminInfoService;
	@Autowired
	private BalanceLogService balanceLogService;
			
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView adminusers(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		TadminUser tUser=getSessionUser(request);
		if(tUser!=null){			
			mav.setViewName("admin/manager");
		}
		else{
			tUser=new TadminUser();		
			mav.addObject("user", tUser);
			mav.setViewName("login");
		}
		return mav;
	}
	
	@RequestMapping(value="managersList",method=RequestMethod.GET)
	@ResponseBody
	public String AdminusersList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=adminUserService.loadAdminUserList(dtp);
		
		pagingData.setSEcho(dtp.sEcho);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		
		String rightsListJson= JSON.toJSONString(pagingData);
		return rightsListJson;
			
	}
		
	
	/**
	 * <p>Description: 处理新增数据的ajax请求</p>
	 * @Title: addRights 
	 * @param jsonStr
	 * @param request
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="addUsers",method=RequestMethod.POST)
	@ResponseBody
	public String addAdmins(HttpServletRequest request,TadminUser adminuser){		
		JSONObject respJson = new JSONObject();
		try{
			TadminUser user=adminUserService.getAdminUserByIdOrEmail(adminuser.getAdminId(),adminuser.getEmail());
			if(user==null){				
				adminuser.setStatus(true);
				String pwd=adminuser.getPassword();
				adminuser.setPassword(SecurityTools.SHA1(pwd));
				adminuser.setRoleId(2);
				adminuser.setCreatedTime(System.currentTimeMillis());					
				adminUserService.createAdminUser(adminuser);
				TadminInfo adminInfo=new TadminInfo();
				adminInfo.setAdminId(adminuser.getAdminId());				
				adminInfoService.createAdminInfo(adminInfo);
				
				String webUrl=SystemConstants.Admin_Setting_Map.get(SystemConstants.WEBSITE_URL);
				TemaiMessage message=new TemaiMessage();
				message.setTo(adminuser.getEmail());	
				StringBuffer sb=new StringBuffer();
				sb.append("<p>您好：</p><p>征信网站的系统管理员已经为您创建了网站登录账号，<br/>登录账号是："+adminuser.getAdminId()+" 或 "+adminuser.getEmail()+"<br/>登录密码是："+pwd+"</p>");
				sb.append("<p>注意：为了保护您的账户安全，请在登录征信网站后尽快修改登录密码！</p>");
				sb.append("<p>此致<br/><a href='"+webUrl+"'>征信系统网站</a></p>");
				message.setText(sb.toString());
				message.setSubject("征信系统-您的账号");
				EMailTool.send(message,true);													
				respJson.put("status", true);
			}			
			else{
				respJson.put("status", false);
				respJson.put("info", getMessage(request,"system.management.user.existing"));
			}						
		}
		catch(MyException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}		
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value="editUsers",method=RequestMethod.POST)
	@ResponseBody
	public String updateAdmin(HttpServletRequest request,TadminUser adminuser){		
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");		
		JSONObject respJson = new JSONObject();
		Date sdate = null;
		TadminUser dbAdminUser=adminUserService.getAdminUserById(adminuser.getAdminId());
		if(dbAdminUser!=null){					
			try{
				try {
					String ss=adminuser.getCreatedTimeStr();
					sdate = simpleDateFormat.parse(ss);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dbAdminUser.setCreatedTime(sdate.getTime());				
				if(adminuser.getPassword()!=null&&!adminuser.getPassword().isEmpty()){
					dbAdminUser.setPassword(SecurityTools.SHA1(adminuser.getPassword()));
				}
								
				adminUserService.updateAdminUser(dbAdminUser);
				respJson.put("status", true);
			}
			catch(MyException be){
				respJson.put("status", false);
				respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
			}	
		}
		else{
			respJson.put("status", false);
			respJson.put("info", "The user do not exist or have been deleted!");
		}
		String str=JSON.toJSONString(respJson);		
		return str;
	}

	@RequestMapping(value="managers/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteAdmins(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
	//	Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			adminUserService.deleteAdminUserByIds(idstrArr);
			respJson.put("status", true);
		}
		catch(MyException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	@RequestMapping(value="activateusers/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String activateUsers(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
	
		JSONObject respJson = new JSONObject();
		try{
			adminUserService.activateUsersByIds(idstrArr);
			respJson.put("status", true);
		}
		catch(MyException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	
	@RequestMapping(value="deactivateusers/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String deactivateUsers(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");				
		JSONObject respJson = new JSONObject();
		try{
			adminUserService.deactivateUsersByIds(idstrArr);
			respJson.put("status", true);
		}
		catch(MyException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	
	/**
	 * <p>Description: 处理充值的ajax请求</p>
	 * @Title: addMoney 
	 * @param jsonStr
	 * @param request
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="addMoney",method=RequestMethod.POST)
	@ResponseBody
	public String addMoney(HttpServletRequest request,TadminUser adminuser){		
		JSONObject respJson = new JSONObject();
		try{
			TadminUser user=adminUserService.getAdminUserById(adminuser.getAdminId());
			if(user==null){				
				respJson.put("status", false);
				respJson.put("info", "您要充值的账户不存在");
			}			
			else{
				TbalanceLog balanceLog=new TbalanceLog();
				balanceLog.setAmount(adminuser.getBalance());
				balanceLog.setCreatedTime(System.currentTimeMillis());
				balanceLog.setUserId(user.getAdminId());
				balanceLog.setBalance(user.getBalance()+adminuser.getBalance());
				balanceLog.setDescr("管理员为账户充值: "+adminuser.getBalance());
				balanceLogService.createBalanceLog(balanceLog, user);								
				respJson.put("status", true);
			}						
		}
		catch(MyException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}		
		return JSON.toJSONString(respJson);
	}
	
}
