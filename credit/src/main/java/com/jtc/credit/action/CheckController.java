package com.jtc.credit.action;
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
import com.jtc.credit.commons.MyException;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TbalanceLog;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;
import com.jtc.credit.service.AdminUserService;
import com.jtc.credit.service.BalanceLogService;

@Controller
@RequestMapping(value="check")
public class CheckController extends BaseController {			
	@Autowired
	private AdminUserService adminUserService;		
	@Autowired
	private BalanceLogService balanceLogService;
			
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView check(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		TadminUser tUser=getSessionUser(request);
		if(tUser!=null){			
			mav.setViewName("admin/check");
		}
		else{
			tUser=new TadminUser();		
			mav.addObject("user", tUser);
			mav.setViewName("login");
		}
		return mav;
	}
	
	@RequestMapping(value="checkList",method=RequestMethod.GET)
	@ResponseBody
	public String checkList(HttpServletRequest request,DataTableParamter dtp){	
		JSONObject searchJson = new JSONObject();
		searchJson.put("type", true);
		searchJson.put("status", true);
		dtp.setsSearch(JSON.toJSONString(searchJson));
		PagingData pagingData=balanceLogService.loadAll(dtp);		
		pagingData.setSEcho(dtp.sEcho);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}		
		String listJson= JSON.toJSONString(pagingData);
		return listJson;
			
	}
				
	
	@RequestMapping(value="delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public String delete(@PathVariable int id,HttpServletRequest request){					
		JSONObject respJson = new JSONObject();
		try{
//			balanceLogService.deleteBalanceLogById(id);
			TbalanceLog balanceLog=balanceLogService.getBalanceLogById(id);
			balanceLog.setStatus(false);
			balanceLogService.updateBalanceLog(balanceLog);
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
	public String addMoney(HttpServletRequest request,TbalanceLog balanceLog){		
		JSONObject respJson = new JSONObject();
		try{
			TadminUser user=adminUserService.getAdminUserById(balanceLog.getUserId());
			if(user==null){				
				respJson.put("status", false);
				respJson.put("info", "您要充值的账户不存在");
			}			
			else{					
				balanceLog.setCreatedTime(System.currentTimeMillis());				
				balanceLog.setBalance(user.getBalance()+balanceLog.getAmount());
				balanceLog.setDescr("管理员审核后充值: "+balanceLog.getAmount());
				balanceLog.setType(false);
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
