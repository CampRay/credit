package com.jtc.credit.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtc.credit.commons.MyException;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.Tsetting;
import com.jtc.credit.service.SystemSettingService;


@Controller
@RequestMapping(value="/settings")
public class SettingController extends BaseController {
	
	@Autowired
	private SystemSettingService systemSettingService;
		
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView settings(HttpServletRequest request){		
		Map<String, List<String>> system_setting =new LinkedHashMap<String, List<String>>();
		ModelAndView mav=new ModelAndView();
		try{			
			List<Tsetting> settingList=systemSettingService.getAllSystemSetting();	
			for (Tsetting tsetting : settingList) {
				List<String> list = new ArrayList<String>();
				list.add(new String(tsetting.getValue(),"UTF-8"));
				list.add(tsetting.getDescr());
				system_setting.put(tsetting.getName(), list);
			}
			
		}catch(MyException m){
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TadminUser tUser=getSessionUser(request);
		if(tUser!=null){
			mav.addObject("system_setting",system_setting);
		    mav.setViewName("admin/setting");
		}
		else{
			tUser=new TadminUser();		
			mav.addObject("user", tUser);
			mav.setViewName("login");
		}
		return mav;
	}
	   
	
	@RequestMapping(value="editsetting",method=RequestMethod.POST)
	@ResponseBody
	public String editSettings(HttpServletRequest request,String name,String value){
		JSONObject resp = new JSONObject();
		
		try{
			Tsetting tsetting = systemSettingService.getSystemSettingByName(name);
			if(tsetting != null){	
				tsetting.setValue(value.getBytes("UTF-8"));
	            systemSettingService.updateSystemsetting(tsetting);
	            systemSettingService.cachedSystemSettingData();
			}			
		}catch(MyException m){
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.put("status", true);
		return JSON.toJSONString(resp);
	}
			 
}
