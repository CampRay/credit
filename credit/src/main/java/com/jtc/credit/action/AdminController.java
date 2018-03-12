/**   
 * @Title: UserController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.jtc.credit.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TapiPrice;
import com.jtc.credit.dto.TbalanceLog;
import com.jtc.credit.dto.TqueryLog;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;
import com.jtc.credit.service.AdminUserService;
import com.jtc.credit.service.ApiPriceService;
import com.jtc.credit.service.BalanceLogService;
import com.jtc.credit.service.QueryLogService;
@Controller
@RequestMapping(value="admin")
public class AdminController extends BaseController {
		
    @Autowired
	private AdminUserService adminUserService;
    @Autowired
	private BalanceLogService balanceLogService; 
    @Autowired
	private QueryLogService queryLogService; 
    @Autowired
	private ApiPriceService apiPriceService;
    
    
    /**
     * 打开管理首页
     * @param request
     * @return
     */
    @RequestMapping(value="home",method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("admin/home");
		
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		mav.addObject("totalUser", adminUserService.getUserCount());					
		mav.addObject("monthUser", adminUserService.getUserCount(calendar.getTimeInMillis()));		
		mav.addObject("totalAmount", balanceLogService.getTotalAmount());
		mav.addObject("monthAmount", balanceLogService.getTotalAmount(calendar.getTimeInMillis()));	
		mav.addObject("monthAmountUser", balanceLogService.getTotalUser(calendar.getTimeInMillis()));	
		mav.addObject("totalQueryAmount", queryLogService.getTotalAmount());
		mav.addObject("monthQueryAmount", queryLogService.getTotalAmount(calendar.getTimeInMillis()));
		mav.addObject("totalQuery", queryLogService.getTotalUser(null));
		mav.addObject("monthQuery", queryLogService.getTotalUser(calendar.getTimeInMillis()));
				
		return mav;
	}
    
    @RequestMapping(value="apiPrice",method=RequestMethod.GET)
	public ModelAndView apiPrice(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		TadminUser tUser=getSessionUser(request);
		if(tUser!=null){			
			mav.setViewName("admin/apiprice");
		}
		else{
			tUser=new TadminUser();		
			mav.addObject("user", tUser);
			mav.setViewName("login");
		}
		return mav;
	}
    
    
    @RequestMapping(value="priceList",method=RequestMethod.GET)
	@ResponseBody
	public String priceList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=apiPriceService.loadAll(dtp);		
		pagingData.setSEcho(dtp.sEcho);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}		
		String listJson= JSON.toJSONString(pagingData);
		return listJson;
			
	}
    
    /**
	 * <p>Description: 修改价格的ajax请求</p>
	 * @Title: editPrice 
	 * @param jsonStr
	 * @param request
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="editPrice",method=RequestMethod.POST)
	@ResponseBody
	public String editPrice(HttpServletRequest request,TapiPrice apiPrice){		
		JSONObject respJson = new JSONObject();
		try{
			TapiPrice obj=apiPriceService.getApiPriceById(apiPrice.getId());
			if(obj==null){
				respJson.put("status", false);
				respJson.put("info", "没有找到要修改的接口数据");
			}
			else{
				obj.setPrice(apiPrice.getPrice());
				obj.setApiName(apiPrice.getApiName());
				obj.setDescr(apiPrice.getDescr());
				obj.setGroupName(apiPrice.getGroupName());
				apiPriceService.updateApiPrice(obj);
				respJson.put("status", true);
			}
		}
		catch(Exception be){
			respJson.put("status", false);
			respJson.put("info", "修改价格失败，错误原因："+be.getMessage());
		}		
		return JSON.toJSONString(respJson);
	}
	
	
	@RequestMapping(value="balanceReport",method=RequestMethod.GET)
	public ModelAndView balanceReport(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		TadminUser tUser=getSessionUser(request);
		if(tUser!=null){			
			mav.setViewName("admin/breport");
		}
		else{
			tUser=new TadminUser();		
			mav.addObject("user", tUser);
			mav.setViewName("login");
		}
		return mav;
	}
	
	@RequestMapping(value="balanceList",method=RequestMethod.GET)
	@ResponseBody
	public String balanceList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=balanceLogService.loadAll(dtp);		
		pagingData.setSEcho(dtp.sEcho);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		
		String listJson= JSON.toJSONString(pagingData);
		return listJson;
			
	}
	
	@RequestMapping(value="/balanceExcel",method=RequestMethod.POST)	
	public String balanceExcel(HttpServletRequest request,HttpServletResponse response){		
		TadminUser tUser=getSessionUser(request);					
		if(tUser!=null){
			String userId=request.getParameter("userId");
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");	
			String type=request.getParameter("type");	
			List<TbalanceLog> list=balanceLogService.loadLog(userId, startTime, endTime,type);						
			//创建HSSFWorkbook对象  
			HSSFWorkbook wkb = new HSSFWorkbook();  
			//创建HSSFSheet对象  
			HSSFSheet sheet = wkb.createSheet("sheet");  
			//创建HSSFRow对象  
			HSSFRow hrow = sheet.createRow(0);  
			hrow.createCell(0).setCellValue("编号");
			hrow.createCell(1).setCellValue("充值用户");
			hrow.createCell(2).setCellValue("充值金额");
			hrow.createCell(3).setCellValue("充值后余额");
			hrow.createCell(4).setCellValue("充值时间");
			hrow.createCell(5).setCellValue("说明");
			hrow.createCell(6).setCellValue("记录类型");	
			  			
			int i=1;
			for (TbalanceLog log : list) {				
				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(log.getId());
				row.createCell(1).setCellValue(log.getUserId());
				row.createCell(2).setCellValue(log.getAmount());
				row.createCell(3).setCellValue(log.getBalance());
				row.createCell(4).setCellValue(log.getCreatedTimeStr());
				row.createCell(5).setCellValue(log.getDescr());
				if(new Boolean(log.isType())){
					row.createCell(6).setCellValue("申请充值");
				}
				else{
					row.createCell(6).setCellValue("实际充值");
				}
				i++;
			}
			OutputStream outputStream;
			try {
				outputStream = new BufferedOutputStream(response.getOutputStream());
				wkb.write(outputStream);				
				response.setHeader("Content-Disposition", "attachment; filename=\"BalanceReport.xls\"");
				//response.addHeader("Content-Length", ""+userData.getStream().length);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");								
				outputStream.flush();  	
				outputStream.close();
			} catch (IOException e) {
				
			}			
		}
		return null;
	}
	
	@RequestMapping(value="queryReport",method=RequestMethod.GET)
	public ModelAndView queryReport(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		TadminUser tUser=getSessionUser(request);
		if(tUser!=null){			
			mav.setViewName("admin/qreport");			
		}
		else{
			tUser=new TadminUser();		
			mav.addObject("user", tUser);
			mav.setViewName("login");
		}
		return mav;
	}
	
	@RequestMapping(value="queryList",method=RequestMethod.GET)
	@ResponseBody
	public String queryList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=queryLogService.loadAll(dtp);		
		pagingData.setSEcho(dtp.sEcho);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		
		String listJson= JSON.toJSONString(pagingData);
		return listJson;
			
	}
	
	@RequestMapping(value="queryExcel",method=RequestMethod.POST)	
	public String queryExcel(HttpServletRequest request,HttpServletResponse response){		
		TadminUser tUser=getSessionUser(request);					
		if(tUser!=null){
			String userId=request.getParameter("userId");
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String apiId=request.getParameter("apiId");						
			List<TqueryLog> list=queryLogService.loadLog(userId, startTime, endTime, apiId);						
			//创建HSSFWorkbook对象  
			HSSFWorkbook wkb = new HSSFWorkbook();  
			//创建HSSFSheet对象  
			HSSFSheet sheet = wkb.createSheet("sheet");  
			//创建HSSFRow对象  
			HSSFRow hrow = sheet.createRow(0);  
			hrow.createCell(0).setCellValue("编号");
			hrow.createCell(1).setCellValue("查询用户");
			hrow.createCell(2).setCellValue("查询接口名称");
			hrow.createCell(3).setCellValue("扣减金额");
			hrow.createCell(4).setCellValue("查询时间");
			hrow.createCell(5).setCellValue("说明");
			  			
			int i=1;
			for (TqueryLog log : list) {				
				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(log.getId());
				row.createCell(1).setCellValue(log.getUserId());
				int tem = log.getApiId();
				String str = "";
				if(tem==1){
					str = "身份证认证(新颜)";
				}else if(tem==2){
					str = "诚信信息核查(新颜)";
				}
				else if(tem==3){
					str = "银行卡信息认证(新颜)";
				}
				else if(tem==4){
					str = "身份证认证(元素)";
				}
				else if(tem==5){
					str = "风险控制核查(元素)";
				}
				else if(tem==6){
					str = "银行卡信息认证(元素)";
				}
				else if(tem==7){
					str = "工商信息查询(元素)";
				}								
				
				row.createCell(2).setCellValue(str);
				row.createCell(3).setCellValue(log.getAmount());
				row.createCell(4).setCellValue(log.getCreatedTimeStr());
				row.createCell(5).setCellValue(log.getDescr());		
				
				i++;
			}
			OutputStream outputStream;
			try {
				outputStream = new BufferedOutputStream(response.getOutputStream());
				wkb.write(outputStream);				
				response.setHeader("Content-Disposition", "attachment; filename=\"QueryReport.xls\"");
				//response.addHeader("Content-Length", ""+userData.getStream().length);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");								
				outputStream.flush();  	
				outputStream.close();
			} catch (IOException e) {
				
			}			
		}
		return null;
	}
    
}
