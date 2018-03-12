package com.jtc.credit.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jtc.credit.commons.SecurityTools;
import com.jtc.credit.dto.TadminInfo;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TbalanceLog;
import com.jtc.credit.dto.TqueryLog;
import com.jtc.credit.model.ChangePasswordModel;
import com.jtc.credit.model.RecordsModel;
import com.jtc.credit.service.AdminInfoService;
import com.jtc.credit.service.AdminUserService;
import com.jtc.credit.service.BalanceLogService;
import com.jtc.credit.service.QueryLogService;

/**
 * @ClassName: IndexController
 * @Description: TODO
 * @author Phills Li
 * 
 */
@Controller
public class IndexController extends BaseController {

	@Autowired
	private AdminInfoService adminInfoService;
	
    @Autowired
	private AdminUserService adminUserService;
    
    @Autowired
	private BalanceLogService balanceLogService;
    
    @Autowired
	private QueryLogService queryLogService;
	
	/** 
	 * <p>Open the login page</p>
	 * @Title: login 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index() {	
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/auth/idcard");
		return mav;
	}
	
	/** 
	 * <p>打开用户账号信息页面</p>
	 * @Title: userinfo 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="userinfo",method=RequestMethod.GET)
	public ModelAndView userinfo(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		TadminInfo adminInfo=adminInfoService.getAdminInfoById(user.getAdminId());
		user.setAdminInfo(adminInfo);
		mav.addObject("userModel",user);
		mav.setViewName("profile/userinfo");
		return mav;
	}
	
	/** 
	 * <p>提交修改用户账号信息</p>
	 * @Title: userinfo 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="userinfo",method=RequestMethod.POST)
	public ModelAndView userinfo(HttpServletRequest request,TadminUser user) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("profile/userinfo");
		mav.addObject("userModel",user);
		TadminUser sessionUser=getSessionUser(request);
		if(!sessionUser.getEmail().equals(user.getEmail())){			
			if(adminUserService.getTadminUsersByEmail(user.getEmail())!=null){
				mav.addObject(ERROR_MSG_KEY, "您的电子邮箱已经被其他账号使用，请替换一个新的电子邮箱地址。");	
				return mav;
			}
			else{
				sessionUser.setEmail(user.getEmail());
				adminUserService.updateAdminUser(sessionUser);
				setSessionUser(request,sessionUser);
			}
		}		
		
		TadminInfo adminInfo=adminInfoService.getAdminInfoById(sessionUser.getAdminId());
		TadminInfo info=user.getAdminInfo();
		info.setAdminId(sessionUser.getAdminId());
		if(adminInfo==null){				
			adminInfoService.createAdminInfo(info);				
		}
		else{
			adminInfo.setAddress(info.getAddress());
			adminInfo.setCompany(info.getCompany());
			adminInfo.setMobile(info.getMobile());
			adminInfo.setPosition(info.getPosition());
			adminInfo.setRealName(info.getRealName());
			adminInfoService.updateAdminInfo(adminInfo);
		}	
		mav.addObject(ERROR_MSG_KEY, "账户信息修改成功！");	
		return mav;
	}
	
	/** 
	 * <p>打开修改密码页面</p>
	 * @Title: userpwd 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="userpwd",method=RequestMethod.GET)
	public ModelAndView userpwd(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();		
		mav.addObject("pwdModel",new ChangePasswordModel());
		mav.setViewName("profile/userpwd");
		return mav;
	}
	
	/** 
	 * <p>提交修改密码</p>
	 * @Title: userinfo 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="userpwd",method=RequestMethod.POST)
	public ModelAndView userpwd(HttpServletRequest request,ChangePasswordModel cpwd) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("profile/userpwd");
		TadminUser user=getSessionUser(request);
		mav.addObject("pwdModel",cpwd);
		user.setPassword(SecurityTools.SHA1(cpwd.getNewpassword()));		
		adminUserService.updateAdminUser(user);
		setSessionUser(request,user);
		mav.addObject(ERROR_MSG_KEY, "密码修改成功。");	
		return mav;
	}
		
	
	/** 
	 * <p>打开充值记录页面</p>
	 * @Title: balances 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="balances",method=RequestMethod.GET)
	public ModelAndView balances(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("profile/balances");
		TadminUser user=getSessionUser(request);
		List<TbalanceLog> list=balanceLogService.loadAllLogByUser(user.getAdminId());
		mav.addObject("logList",list);				
		return mav;
	}
	
	/**
	 * <p>Description: 处理充值的请求</p>
	 * @Title: addMoney 
	 * @param jsonStr
	 * @param request
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="addMoney",method=RequestMethod.POST)	
	public ModelAndView addMoney(HttpServletRequest request,TbalanceLog balanceLog,@RequestParam(value = "file", required = false) MultipartFile file){		
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/balances");
		try{
			TadminUser user=getSessionUser(request);
			if(user==null){				
				mav.addObject(ERROR_MSG_KEY, "请先登录要充值的账户");					
			}			
			else{				
				long timemillis=System.currentTimeMillis();
				balanceLog.setCreatedTime(timemillis);
				balanceLog.setUserId(user.getAdminId());
				balanceLog.setBalance(user.getBalance());				
				balanceLog.setType(true);
				balanceLogService.createBalanceLog(balanceLog);	
				if(!file.isEmpty()){
					try{
						String realPath = request.getSession().getServletContext().getRealPath("/");
						String suffix=".jpg";
						File saveFile=new File(realPath,File.separator+"upload"+File.separator+balanceLog.getId()+timemillis+suffix);												
				        OutputStream out = new FileOutputStream(saveFile);						        
						out.write(file.getBytes());				                
				        out.close();
					}
					catch (FileNotFoundException e) {
						mav.addObject(ERROR_MSG_KEY, "保存充值证明文件失败");
					}
					catch (IOException e) {
						mav.addObject(ERROR_MSG_KEY, "保存充值证明文件失败");
					}
				}
			}						
		}
		catch(Exception be){
			mav.addObject(ERROR_MSG_KEY, "账户充值申请失败");
		}
		
		
		return mav;
	}
	
	/** 
	 * <p>打开查询记录页面</p>
	 * @Title: records 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="records",method=RequestMethod.GET)
	public ModelAndView records(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();				
		mav.setViewName("profile/records");
		TadminUser user=getSessionUser(request);	
		List<TqueryLog> list=queryLogService.loadAllLogByUser(user.getAdminId(),1,null,null);
		mav.addObject("logList",list);	
		RecordsModel record=new RecordsModel();
		int total=queryLogService.getCountByUser(user.getAdminId(),null,null);
		record.setTotal(total);
		record.setTotalPage((int)Math.ceil((double)total/(double)record.getPageSize()));
		
		mav.addObject("record",record);		
		return mav;
	}
	
	/** 
	 * <p>打开查询记录页面</p>
	 * @Title: records 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="records",method=RequestMethod.POST)
	public ModelAndView records(HttpServletRequest request,RecordsModel record) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("profile/records");
		TadminUser user=getSessionUser(request);	
		
		Long startTime=null;
		Long endTime=null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			if(!record.getStartDate().isEmpty()){
				Date sdate=sdf.parse(record.getStartDate());
				startTime=sdate.getTime();
			}
			if(!record.getEndDate().isEmpty()){
				Date edate=sdf.parse(record.getEndDate());
				endTime=edate.getTime();
			}
		} catch (ParseException e) {			
		}
		List<TqueryLog> list=queryLogService.loadAllLogByUser(user.getAdminId(),record.getPage(),startTime,endTime);
		mav.addObject("logList",list);	
		int total=queryLogService.getCountByUser(user.getAdminId(),startTime,endTime);		
		record.setTotal(total);
		record.setTotalPage((int)Math.ceil((double)total/(double)record.getPageSize()));
		
		mav.addObject("record",record);	
		return mav;
	}
	
}
