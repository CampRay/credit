package com.jtc.credit.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.geeksfinance.credit.pub.msg.MessageFormat;
import com.geeksfinance.credit.pub.msg.RequestMessage;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.jtc.credit.commons.SystemConfig;
import com.jtc.credit.commons.WsXmlToBean;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TapiPrice;
import com.jtc.credit.dto.TqueryLog;
import com.jtc.credit.model.BankCardModel;
import com.jtc.credit.model.EleBankCardModel;
import com.jtc.credit.model.IdCardModel;
import com.jtc.credit.model.RiskModel;
import com.jtc.credit.model.SaicModel;
import com.jtc.credit.model.WsBankCardDataModel;
import com.jtc.credit.model.WsIdCardDataModel;
import com.jtc.credit.model.WsResponseModel;
import com.jtc.credit.model.WsRiskDataModel;
import com.jtc.credit.model.WsSaicDataModel;
import com.jtc.credit.rsa.RsaCodingUtil;
import com.jtc.credit.service.ApiPriceService;
import com.jtc.credit.service.CreditWebService;
import com.jtc.credit.service.QueryLogService;
import com.jtc.credit.util.HttpClientUtil;
import com.jtc.credit.util.SecurityUtil;

/**
 * @ClassName: AuthController
 * @Description: TODO
 * @author Phills Li
 * 
 */
@Controller
@RequestMapping("auth")
public class AuthController extends BaseController {
	
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private CreditWebService creditWebService;
	@Autowired
	private QueryLogService queryLogService;
	@Autowired
	private ApiPriceService apiPriceService;
	
	
	/** 
	 * <p>导出数据</p> 
	 * @param response 
	 * @param html
	 * @throws 
	 */ 
	@RequestMapping(value="export",method=RequestMethod.POST)	
	public void export(HttpServletRequest request,HttpServletResponse response,String html){						
		Document document = new Document(PageSize.A4);
	    try {
	      response.setContentType("application/pdf");
	      PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
	      document.open();
	      InputStream htmlStream = new ByteArrayInputStream(html.getBytes("utf-8"));
	      InputStream cssStream = request.getSession().getServletContext().getResourceAsStream("/static/css/bootstrap.min.css");
	      XMLWorkerHelper.getInstance().parseXHtml(writer, document, htmlStream , cssStream,Charset.forName("UTF-8"));
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    document.close();
	}
	
	
	@RequestMapping(value="menu")
	public ModelAndView header(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		mav.addObject("priceList", apiPriceService.loadAll());
		mav.setViewName("auth/menu");
						
		return mav;
	}

	/** 
	 * <p>打开身份证验证页面</p>
	 * @Title: login 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="idcard",method=RequestMethod.GET)
	public ModelAndView idCardAuth() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("idCardModel",new IdCardModel());
		mav.setViewName("auth/idcard");
		return mav;
	}
	
	/** 
	 * <p>身份证验证</p>
	 * @Title: login 
	 * @param request
	 * @param user
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="idcard",method=RequestMethod.POST)	
	public ModelAndView idCardAuth(HttpServletRequest request,IdCardModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		if(model==null||model.getIdCard().isEmpty()||model.getRealName().isEmpty()){
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			TapiPrice price=apiPriceService.getApiPriceById(1);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}
			String trans_id=""+System.currentTimeMillis();
			String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());			
			//组装参数			
			Map<Object, Object> ArrayData = new HashMap<Object, Object>();
			ArrayData.put("member_id", systemConfig.getMemberId());
			ArrayData.put("terminal_id", systemConfig.getTerminalId());
			ArrayData.put("trans_id", trans_id);
			ArrayData.put("trade_date", trade_date);
			ArrayData.put("id_card", model.getIdCard());
			ArrayData.put("id_holder", model.getRealName());
			ArrayData.put("industry_type", "A1");
			ArrayData.put("is_photo", model.getIsPhoto());
						
			String jsonStr = JSON.toJSONString(ArrayData);			
			
			/** base64编码**/
			String base64str;
			try {
				base64str = SecurityUtil.Base64Encode(jsonStr);
			} catch (UnsupportedEncodingException e) {
				mav.addObject(ERROR_MSG_KEY,"加密请求参数失败："+e.getMessage());
				return mav;
			}			
			
			/** rsa加密**/
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String pfxpath = realPath +File.separator+ "key\\" + systemConfig.getPfxName();
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				mav.addObject(ERROR_MSG_KEY,"私钥文件不存在");
				return mav;
			}
			String pfxpwd =systemConfig.getPfxPwd();	
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);
			
//			Map<String, String> headers =new HashMap<String, String>();
			String url = systemConfig.getIdCardAuthUrl();
			Map<String, Object> params =new HashMap<String,Object>();
			params.put("member_id", systemConfig.getMemberId());
			params.put("terminal_id", systemConfig.getTerminalId());
			params.put("data_type", "json");
			params.put("data_content", data_content);	        
		    String resultStr = HttpClientUtil.sendPost(url, systemConfig.getCharSet(), params);
		    		    		    
		    TqueryLog queryLog=new TqueryLog();
		    queryLog.setUserId(user.getAdminId());
		    queryLog.setAmount(price.getPrice());
		    queryLog.setBalance(user.getBalance()-price.getPrice());
		    queryLog.setApiId(1);
		    queryLog.setDescr("查询成功");
		    queryLog.setCreatedTime(System.currentTimeMillis());
		    queryLogService.createQueryLog(queryLog,user);
		    
		    if(resultStr.isEmpty()){	
		    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
				return mav;
			}

		    mav.addObject("idCardModel",model);
		    mav.addObject("result",JSON.parseObject(resultStr));
		    mav.setViewName("auth/idcard");
		    return mav;
		}
	}
				
		
	/** 
	 * <p>打开诚信验证页面</p> 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="person",method=RequestMethod.GET)
	public ModelAndView person() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("idCardModel",new IdCardModel());
		mav.setViewName("auth/person");
		return mav;
	}
	
	/** 
	 * <p>诚信验证</p>
	 * @param request
	 * @param model
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="person",method=RequestMethod.POST)
	public ModelAndView person(HttpServletRequest request,IdCardModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		if(model==null||model.getIdCard().isEmpty()||model.getRealName().isEmpty()){
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			TapiPrice price=apiPriceService.getApiPriceById(2);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}
			
			String trans_id=""+System.currentTimeMillis();
			String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());			
			
			/** 组装参数	  **/
			Map<Object, Object> ArrayData = new HashMap<Object, Object>();
			ArrayData.put("member_id", systemConfig.getMemberId());
			ArrayData.put("terminal_id", systemConfig.getTerminalId());
			ArrayData.put("trans_id", trans_id);
			ArrayData.put("trade_date", trade_date);
			ArrayData.put("id_card", model.getIdCard());
			ArrayData.put("name", model.getRealName());
			ArrayData.put("product_type", "0");
			ArrayData.put("industry_type", "A1");
			
						
			String jsonStr = JSON.toJSONString(ArrayData);			
			
			/** base64编码**/
			String base64str;
			try {
				base64str = SecurityUtil.Base64Encode(jsonStr);
			} catch (UnsupportedEncodingException e) {
				mav.addObject(ERROR_MSG_KEY,"加密请求参数失败："+e.getMessage());
				return mav;
			}			
			
			/** rsa加密 **/
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String pfxpath = realPath +File.separator+ "key\\" + systemConfig.getPfxName();
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				mav.addObject(ERROR_MSG_KEY,"私钥文件不存在");
				return mav;
			}
			String pfxpwd =systemConfig.getPfxPwd();
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);
			
			//Map<String, String> headers =new HashMap<String, String>();
			String url = systemConfig.getBizAuthUrl();
			Map<String, Object> params =new HashMap<String,Object>();
			params.put("member_id", systemConfig.getMemberId());
			params.put("terminal_id", systemConfig.getTerminalId());
			params.put("data_type", "json");
			params.put("data_content", data_content);	        
		    String resultStr = HttpClientUtil.sendPost(url, systemConfig.getCharSet(), params);
		    
		    TqueryLog queryLog=new TqueryLog();
		    queryLog.setUserId(user.getAdminId());
		    queryLog.setAmount(price.getPrice());
		    queryLog.setBalance(user.getBalance()-price.getPrice());
		    queryLog.setApiId(2);
		    queryLog.setDescr("查询成功");
		    queryLog.setCreatedTime(System.currentTimeMillis());
		    queryLogService.createQueryLog(queryLog,user);
		    
		    if(resultStr.isEmpty()){	
		    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
				return mav;
			}
				
		    mav.addObject("idCardModel",model);
		    mav.addObject("result",JSON.parseObject(resultStr));
		    mav.setViewName("auth/person");
		    return mav;			
		}		
	}
	
	
	/** 
	 * <p>打开银行卡验证页面</p> 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="bankcard2",method=RequestMethod.GET)
	public ModelAndView bankCard2() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("bankCardModel",new BankCardModel());
		mav.setViewName("auth/bankcard2");
		return mav;
	}
	
	/** 
	 * <p>打开银行卡验证页面</p> 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="bankcard3",method=RequestMethod.GET)
	public ModelAndView bankCard3() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("bankCardModel",new BankCardModel());
		mav.setViewName("auth/bankcard3");
		return mav;
	}
	
	/** 
	 * <p>打开银行卡验证页面</p> 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="bankcard4",method=RequestMethod.GET)
	public ModelAndView bankCard4() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("bankCardModel",new BankCardModel());
		mav.setViewName("auth/bankcard4");
		return mav;
	}
	
	/** 
	 * <p>银行卡二元素验证</p>
	 * @param request
	 * @param model
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="bankcard2",method=RequestMethod.POST)	
	public ModelAndView bankcard2(HttpServletRequest request,BankCardModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		if(model==null||model.getAccNo().isEmpty()||model.getRealName().isEmpty()){
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			TapiPrice price=apiPriceService.getApiPriceById(3);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}
			
			String trans_id=""+System.currentTimeMillis();
			String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());			
			
			/** 组装参数	  **/
			String validDate="";
			if(model.getValidDate()!=null&&!model.getValidDate().isEmpty()){
				validDate=model.getValidDate().substring(5, 7)+model.getValidDate().substring(2, 4);
			}
			
			Map<Object, Object> ArrayData = new HashMap<Object, Object>();
			ArrayData.put("member_id", systemConfig.getMemberId());
			ArrayData.put("terminal_id", systemConfig.getTerminalId());
			ArrayData.put("acc_no", model.getAccNo());
			ArrayData.put("id_card", model.getIdCard());
			ArrayData.put("id_holder", model.getRealName());
			ArrayData.put("mobile", model.getMobile());
			ArrayData.put("card_type", model.getCardType());
			ArrayData.put("valid_date",validDate );			
			ArrayData.put("valid_no", model.getValidNo());
			ArrayData.put("verify_element", model.getVerifyElement());
			ArrayData.put("trans_id", trans_id);
			ArrayData.put("trade_date", trade_date);					
			ArrayData.put("industry_type", "A1");
			ArrayData.put("product_type", "0");
			
						
			String jsonStr = JSON.toJSONString(ArrayData);			
			
			/** base64 编码 **/
			String base64str;
			try {
				base64str = SecurityUtil.Base64Encode(jsonStr);
			} catch (UnsupportedEncodingException e) {
				mav.addObject(ERROR_MSG_KEY,"加密请求参数失败："+e.getMessage());
				return mav;
			}			
			
			/** rsa加密 **/
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String pfxpath = realPath +File.separator+ "key\\" + systemConfig.getPfxName();// 鍟嗘埛绉侀挜
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				mav.addObject(ERROR_MSG_KEY,"私钥文件不存在");
				return mav;
			}
			String pfxpwd =systemConfig.getPfxPwd();// 绉侀挜瀵嗙爜			
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);//鍔犲瘑鏁版嵁
			
			//Map<String, String> headers =new HashMap<String, String>();
			String url = systemConfig.getBankCardAuthUrl();
			Map<String, Object> params =new HashMap<String,Object>();
			params.put("member_id", systemConfig.getMemberId());
			params.put("terminal_id", systemConfig.getTerminalId());
			params.put("data_type", "json");
			params.put("data_content", data_content);	        
		    String resultStr = HttpClientUtil.sendPost(url, systemConfig.getCharSet(), params);
		    
		    TqueryLog queryLog=new TqueryLog();
		    queryLog.setUserId(user.getAdminId());
		    queryLog.setAmount(price.getPrice());
		    queryLog.setBalance(user.getBalance()-price.getPrice());
		    queryLog.setApiId(3);
		    queryLog.setDescr("查询成功");
		    queryLog.setCreatedTime(System.currentTimeMillis());
		    queryLogService.createQueryLog(queryLog,user);
		    
		    if(resultStr.isEmpty()){	
		    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
				return mav;
			}
				
		    mav.addObject("bankCardModel",model);
		    mav.addObject("result",JSON.parseObject(resultStr));
		    mav.setViewName("auth/bankcard");
		    return mav;			
		}		
	}
	
	/** 
	 * <p>银行卡三元素验证</p>
	 * @param request
	 * @param model
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="bankcard3",method=RequestMethod.POST)	
	public ModelAndView bankcard3(HttpServletRequest request,BankCardModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		if(model==null||model.getAccNo().isEmpty()||model.getIdCard().isEmpty()||model.getRealName().isEmpty()){
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			TapiPrice price=apiPriceService.getApiPriceById(4);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}
			
			String trans_id=""+System.currentTimeMillis();
			String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());			
			
			/** 组装参数	  **/
			String validDate="";
			if(model.getValidDate()!=null&&!model.getValidDate().isEmpty()){
				validDate=model.getValidDate().substring(5, 7)+model.getValidDate().substring(2, 4);
			}
			
			Map<Object, Object> ArrayData = new HashMap<Object, Object>();
			ArrayData.put("member_id", systemConfig.getMemberId());
			ArrayData.put("terminal_id", systemConfig.getTerminalId());
			ArrayData.put("acc_no", model.getAccNo());
			ArrayData.put("id_card", model.getIdCard());
			ArrayData.put("id_holder", model.getRealName());
			ArrayData.put("mobile", model.getMobile());
			ArrayData.put("card_type", model.getCardType());
			ArrayData.put("valid_date",validDate );			
			ArrayData.put("valid_no", model.getValidNo());
			ArrayData.put("verify_element", model.getVerifyElement());
			ArrayData.put("trans_id", trans_id);
			ArrayData.put("trade_date", trade_date);					
			ArrayData.put("industry_type", "A1");
			ArrayData.put("product_type", "0");
			
						
			String jsonStr = JSON.toJSONString(ArrayData);			
			
			/** base64 编码 **/
			String base64str;
			try {
				base64str = SecurityUtil.Base64Encode(jsonStr);
			} catch (UnsupportedEncodingException e) {
				mav.addObject(ERROR_MSG_KEY,"加密请求参数失败："+e.getMessage());
				return mav;
			}			
			
			/** rsa加密 **/
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String pfxpath = realPath +File.separator+ "key\\" + systemConfig.getPfxName();// 鍟嗘埛绉侀挜
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				mav.addObject(ERROR_MSG_KEY,"私钥文件不存在");
				return mav;
			}
			String pfxpwd =systemConfig.getPfxPwd();// 绉侀挜瀵嗙爜			
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);//鍔犲瘑鏁版嵁
			
			//Map<String, String> headers =new HashMap<String, String>();
			String url = systemConfig.getBankCardAuthUrl();
			Map<String, Object> params =new HashMap<String,Object>();
			params.put("member_id", systemConfig.getMemberId());
			params.put("terminal_id", systemConfig.getTerminalId());
			params.put("data_type", "json");
			params.put("data_content", data_content);	        
		    String resultStr = HttpClientUtil.sendPost(url, systemConfig.getCharSet(), params);
		    
		    TqueryLog queryLog=new TqueryLog();
		    queryLog.setUserId(user.getAdminId());
		    queryLog.setAmount(price.getPrice());
		    queryLog.setBalance(user.getBalance()-price.getPrice());
		    queryLog.setApiId(4);
		    queryLog.setDescr("查询成功");
		    queryLog.setCreatedTime(System.currentTimeMillis());
		    queryLogService.createQueryLog(queryLog,user);
		    
		    if(resultStr.isEmpty()){	
		    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
				return mav;
			}
				
		    mav.addObject("bankCardModel",model);
		    mav.addObject("result",JSON.parseObject(resultStr));
		    mav.setViewName("auth/bankcard");
		    return mav;			
		}		
	}
	
	/** 
	 * <p>银行卡四元素验证</p>
	 * @param request
	 * @param model
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="bankcard4",method=RequestMethod.POST)	
	public ModelAndView bankcard4(HttpServletRequest request,BankCardModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		if(model==null||model.getAccNo().isEmpty()||model.getIdCard().isEmpty()||model.getRealName().isEmpty()||model.getMobile().isEmpty()){
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			TapiPrice price=apiPriceService.getApiPriceById(5);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}
			
			String trans_id=""+System.currentTimeMillis();
			String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());			
			
			/** 组装参数	  **/
			String validDate="";
			if(model.getValidDate()!=null&&!model.getValidDate().isEmpty()){
				validDate=model.getValidDate().substring(5, 7)+model.getValidDate().substring(2, 4);
			}
			
			Map<Object, Object> ArrayData = new HashMap<Object, Object>();
			ArrayData.put("member_id", systemConfig.getMemberId());
			ArrayData.put("terminal_id", systemConfig.getTerminalId());
			ArrayData.put("acc_no", model.getAccNo());
			ArrayData.put("id_card", model.getIdCard());
			ArrayData.put("id_holder", model.getRealName());
			ArrayData.put("mobile", model.getMobile());
			ArrayData.put("card_type", model.getCardType());
			ArrayData.put("valid_date",validDate );			
			ArrayData.put("valid_no", model.getValidNo());
			ArrayData.put("verify_element", model.getVerifyElement());
			ArrayData.put("trans_id", trans_id);
			ArrayData.put("trade_date", trade_date);					
			ArrayData.put("industry_type", "A1");
			ArrayData.put("product_type", "0");
			
						
			String jsonStr = JSON.toJSONString(ArrayData);			
			
			/** base64 编码 **/
			String base64str;
			try {
				base64str = SecurityUtil.Base64Encode(jsonStr);
			} catch (UnsupportedEncodingException e) {
				mav.addObject(ERROR_MSG_KEY,"加密请求参数失败："+e.getMessage());
				return mav;
			}			
			
			/** rsa加密 **/
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String pfxpath = realPath +File.separator+ "key\\" + systemConfig.getPfxName();// 鍟嗘埛绉侀挜
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				mav.addObject(ERROR_MSG_KEY,"私钥文件不存在");
				return mav;
			}
			String pfxpwd =systemConfig.getPfxPwd();// 绉侀挜瀵嗙爜			
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);//鍔犲瘑鏁版嵁
			
			//Map<String, String> headers =new HashMap<String, String>();
			String url = systemConfig.getBankCardAuthUrl();
			Map<String, Object> params =new HashMap<String,Object>();
			params.put("member_id", systemConfig.getMemberId());
			params.put("terminal_id", systemConfig.getTerminalId());
			params.put("data_type", "json");
			params.put("data_content", data_content);	        
		    String resultStr = HttpClientUtil.sendPost(url, systemConfig.getCharSet(), params);
		    
		    TqueryLog queryLog=new TqueryLog();
		    queryLog.setUserId(user.getAdminId());
		    queryLog.setAmount(price.getPrice());
		    queryLog.setBalance(user.getBalance()-price.getPrice());
		    queryLog.setApiId(5);
		    queryLog.setDescr("查询成功");
		    queryLog.setCreatedTime(System.currentTimeMillis());
		    queryLogService.createQueryLog(queryLog,user);
		    
		    if(resultStr.isEmpty()){	
		    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
				return mav;
			}
				
		    mav.addObject("bankCardModel",model);
		    mav.addObject("result",JSON.parseObject(resultStr));
		    mav.setViewName("auth/bankcard");
		    return mav;			
		}		
	}
	
	
	/** 
	 * <p>打开元素身份证认证</p>
	 * @Title: login 
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="eleidcard",method=RequestMethod.GET)
	public ModelAndView eleIdCardAuth() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("idCardModel",new IdCardModel());
		mav.setViewName("auth/eleidcard");
		return mav;
	}
	
	/** 
	 * <p>元素身份证认证</p>
	 * @Title: login 
	 * @param request
	 * @param user
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="eleidcard",method=RequestMethod.POST)	
	public ModelAndView eleIdCardAuth(HttpServletRequest request,IdCardModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		mav.addObject("idCardModel",model);	
		mav.setViewName("auth/eleidcard");
		if(model==null||model.getIdCard().isEmpty()||model.getRealName().isEmpty()){
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			TapiPrice price=apiPriceService.getApiPriceById(6);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}	
			
		    try{
				MessageFormat format = new MessageFormat();
				RequestMessage requestMsg = new RequestMessage();
				Map<String, String> data = new HashMap<String, String>();
				requestMsg.setUsername(systemConfig.getEleUsername());
				requestMsg.setPassword(systemConfig.getElePassword());
				//填写接口服务名称
				if(model.getIsPhoto().equals("0")){
					requestMsg.setService("nciic_simple");//身份证信息查询
				}
				else{
					requestMsg.setService("nciic_photo");//身份证返照查询
				}
				//填写参数
				data.put("name", model.getRealName());
				data.put("id_no", model.getIdCard()); 
				requestMsg.setData(format.formatMapData(data));
				String resultStr = creditWebService.invokeService(format.formatRequest(requestMsg));				
				
				TqueryLog queryLog=new TqueryLog();
			    queryLog.setUserId(user.getAdminId());
			    queryLog.setAmount(price.getPrice());
			    queryLog.setBalance(user.getBalance()-price.getPrice());
			    queryLog.setApiId(6);
			    queryLog.setDescr("查询成功");
			    queryLog.setCreatedTime(System.currentTimeMillis());
			    queryLogService.createQueryLog(queryLog,user);
				
			    if(resultStr.isEmpty()){	
			    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
					return mav;
				}

			    WsResponseModel<WsIdCardDataModel> responseModel=WsXmlToBean.parseTextToIdCardBean(resultStr);			    			    			    		    
			    mav.addObject("result",responseModel);
			    		   
		    }
		    catch(Exception exe){
		    	mav.addObject(ERROR_MSG_KEY,"征信查询请求失败："+exe.getMessage());
		    }		    
		    return mav;
		}
	}
	
	/** 
	 * <p>打开元素银行卡三元素认证</p>
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="elebankcard3",method=RequestMethod.GET)
	public ModelAndView eleBankCardAuth3() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("bankCardModel",new EleBankCardModel());
		mav.setViewName("auth/elebankcard3");
		return mav;
	}
	
	/** 
	 * <p>打开元素银行卡四元素认证</p>
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="elebankcard4",method=RequestMethod.GET)
	public ModelAndView eleBankCardAuth4() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("bankCardModel",new EleBankCardModel());
		mav.setViewName("auth/elebankcard4");
		return mav;
	}
	
	/** 
	 * <p>元素银行卡认证</p>
	 * @param request
	 * @param EleBankCardModel
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="elebankcard3",method=RequestMethod.POST)	
	public ModelAndView eleBankCardAuth3(HttpServletRequest request,EleBankCardModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		mav.addObject("bankCardModel",model);
		mav.setViewName("auth/elebankcard");
		if(model==null||model.getAcctNo().isEmpty()||model.getName().isEmpty()||model.getIdNo().isEmpty()){			
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			TapiPrice price=apiPriceService.getApiPriceById(8);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}	
			
		    try{
				MessageFormat format = new MessageFormat();
				RequestMessage requestMsg = new RequestMessage();
				Map<String, String> data = new HashMap<String, String>();
				requestMsg.setUsername(systemConfig.getEleUsername());
				requestMsg.setPassword(systemConfig.getElePassword());
				requestMsg.setService(model.getService());
				//填写参数
				data.put("name", model.getName());
				data.put("id_no", model.getIdNo());
				data.put("acct_no", model.getAcctNo()); 
				if(model.getService().equals("card_fio")){
					if(model.getMobile().isEmpty()){
						mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
						return mav;
					}
					data.put("mobile", model.getMobile());
				}
								
				requestMsg.setData(format.formatMapData(data));
				String resultStr = creditWebService.invokeService(format.formatRequest(requestMsg));				
				
				TqueryLog queryLog=new TqueryLog();
			    queryLog.setUserId(user.getAdminId());
			    queryLog.setAmount(price.getPrice());
			    queryLog.setBalance(user.getBalance()-price.getPrice());
			    queryLog.setApiId(8);
			    queryLog.setDescr("查询成功");
			    queryLog.setCreatedTime(System.currentTimeMillis());
			    queryLogService.createQueryLog(queryLog,user);
				
			    if(resultStr.isEmpty()){	
			    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
					return mav;
				}

			    WsResponseModel<WsBankCardDataModel> responseModel=WsXmlToBean.parseTextToBankBean(resultStr);
			    			    
			    			    
			    mav.addObject("result",responseModel);
			    		   
		    }
		    catch(Exception exe){
		    	mav.addObject(ERROR_MSG_KEY,"征信查询请求失败："+exe.getMessage());
		    }		    
		    return mav;
		}
	}
	
	/** 
	 * <p>元素银行卡认证</p>
	 * @param request
	 * @param EleBankCardModel
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="elebankcard4",method=RequestMethod.POST)	
	public ModelAndView eleBankCardAuth4(HttpServletRequest request,EleBankCardModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		mav.addObject("bankCardModel",model);
		mav.setViewName("auth/elebankcard");
		if(model==null||model.getAcctNo().isEmpty()||model.getName().isEmpty()||model.getIdNo().isEmpty()||model.getMobile().isEmpty()){			
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			TapiPrice price=apiPriceService.getApiPriceById(9);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}	
			
		    try{
				MessageFormat format = new MessageFormat();
				RequestMessage requestMsg = new RequestMessage();
				Map<String, String> data = new HashMap<String, String>();
				requestMsg.setUsername(systemConfig.getEleUsername());
				requestMsg.setPassword(systemConfig.getElePassword());
				requestMsg.setService(model.getService());
				//填写参数
				data.put("name", model.getName());
				data.put("id_no", model.getIdNo());
				data.put("acct_no", model.getAcctNo()); 
				if(model.getService().equals("card_fio")){
					if(model.getMobile().isEmpty()){
						mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
						return mav;
					}
					data.put("mobile", model.getMobile());
				}
								
				requestMsg.setData(format.formatMapData(data));
				String resultStr = creditWebService.invokeService(format.formatRequest(requestMsg));				
				
				TqueryLog queryLog=new TqueryLog();
			    queryLog.setUserId(user.getAdminId());
			    queryLog.setAmount(price.getPrice());
			    queryLog.setBalance(user.getBalance()-price.getPrice());
			    queryLog.setApiId(9);
			    queryLog.setDescr("查询成功");
			    queryLog.setCreatedTime(System.currentTimeMillis());
			    queryLogService.createQueryLog(queryLog,user);
				
			    if(resultStr.isEmpty()){	
			    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
					return mav;
				}

			    WsResponseModel<WsBankCardDataModel> responseModel=WsXmlToBean.parseTextToBankBean(resultStr);
			    			    
			    			    
			    mav.addObject("result",responseModel);
			    		   
		    }
		    catch(Exception exe){
		    	mav.addObject(ERROR_MSG_KEY,"征信查询请求失败："+exe.getMessage());
		    }		    
		    return mav;
		}
	}
	
	/** 
	 * <p>打开元素风控查询页面</p>
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="elerisklist",method=RequestMethod.GET)
	public ModelAndView eleRiskList() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("riskModel",new RiskModel());
		mav.setViewName("auth/elerisklist");
		return mav;
	}
	
	/** 
	 * <p>元素风控查询</p>
	 * @param request
	 * @param RiskModel
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="elerisklist",method=RequestMethod.POST)	
	public ModelAndView eleRiskList(HttpServletRequest request,RiskModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		mav.addObject("riskModel",model);
		mav.setViewName("auth/elerisklist");
		if(model==null||model.getEntName().isEmpty()){
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			
			TapiPrice price=apiPriceService.getApiPriceById(7);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}
						
		    try{
				MessageFormat format = new MessageFormat();
				RequestMessage requestMsg = new RequestMessage();
				Map<String, String> data = new HashMap<String, String>();
				requestMsg.setUsername(systemConfig.getEleUsername());
				requestMsg.setPassword(systemConfig.getElePassword());
				requestMsg.setService("risk_list_vip");//身份证信息查询				
				//填写参数
				data.put("ent_name", model.getEntName());
				data.put("datatype", model.getDataType()); 
				data.put("pageno", model.getPageNo()); 
				
				requestMsg.setData(format.formatMapData(data));
				String resultStr = creditWebService.invokeService(format.formatRequest(requestMsg));				
				
				TqueryLog queryLog=new TqueryLog();
			    queryLog.setUserId(user.getAdminId());
			    queryLog.setAmount(price.getPrice());
			    queryLog.setBalance(user.getBalance()-price.getPrice());
			    queryLog.setApiId(7);
			    queryLog.setDescr("查询成功");
			    queryLog.setCreatedTime(System.currentTimeMillis());
			    queryLogService.createQueryLog(queryLog,user);
				
			    if(resultStr.isEmpty()){	
			    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
					return mav;
				}

			    WsResponseModel<WsRiskDataModel> responseModel=WsXmlToBean.parseTextToRiskBean(resultStr);			    
			    			    
			    mav.addObject("result",responseModel);
			    		   
		    }
		    catch(Exception exe){
		    	mav.addObject(ERROR_MSG_KEY,"征信查询请求失败："+exe.getMessage());
		    }		    
		    return mav;
		}
	}
	
	/** 
	 * <p>打开工商信息查询页面</p>
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="elesaiclist",method=RequestMethod.GET)
	public ModelAndView eleSaicList() {
		ModelAndView mav=new ModelAndView();
		mav.addObject("saicModel",new SaicModel());
		mav.setViewName("auth/elesaiclist");
		return mav;
	}
	
	/** 
	 * <p>工商信息查询</p>
	 * @param request
	 * @param SaicModel
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="elesaiclist",method=RequestMethod.POST)	
	public ModelAndView eleSaicList(HttpServletRequest request,SaicModel model){
		ModelAndView mav=new ModelAndView();
		TadminUser user=getSessionUser(request);
		mav.addObject("saicModel",model);	
		mav.setViewName("auth/elesaiclist");
		if(model==null||model.getKey().isEmpty()){
			mav.addObject(ERROR_MSG_KEY,"查询参数不能为空");
			return mav;
		}
		else{
			TapiPrice price=apiPriceService.getApiPriceById(10);
			if(user.getBalance()<price.getPrice()){
				mav.addObject(ERROR_MSG_KEY,"账户余额不足，请充值!");
				return mav;
			}
						
		    try{
				MessageFormat format = new MessageFormat();
				RequestMessage requestMsg = new RequestMessage();
				Map<String, String> data = new HashMap<String, String>();
				requestMsg.setUsername(systemConfig.getEleUsername());
				requestMsg.setPassword(systemConfig.getElePassword());
				requestMsg.setService("saic_single");
				//填写参数
				data.put("keytype", model.getKeyType());
				data.put("key", model.getKey()); 												
				
				requestMsg.setData(format.formatMapData(data));
				String resultStr = creditWebService.invokeService(format.formatRequest(requestMsg));				
				
				TqueryLog queryLog=new TqueryLog();
			    queryLog.setUserId(user.getAdminId());
			    queryLog.setAmount(price.getPrice());
			    queryLog.setBalance(user.getBalance()-price.getPrice());
			    queryLog.setApiId(10);
			    queryLog.setDescr("查询成功");
			    queryLog.setCreatedTime(System.currentTimeMillis());
			    queryLogService.createQueryLog(queryLog,user);
				
			    if(resultStr.isEmpty()){	
			    	mav.addObject(ERROR_MSG_KEY,"返回数据为空");
					return mav;
				}

			    WsResponseModel<WsSaicDataModel> responseModel=WsXmlToBean.parseTextToSaicBean(resultStr);
//			    WsSaicDataModel wsSaicDataModel=new WsSaicDataModel();
//			    wsSaicDataModel.setABUITEM("aaa");
//			    wsSaicDataModel.setANCHEDATE("bbb");
//			    wsSaicDataModel.setANCHEYEAR("2017");
//			    wsSaicDataModel.setCANDATE("20170505");
//			    wsSaicDataModel.setCBUITEM("电子");
//			    wsSaicDataModel.setCHANGEDATE("2015年6月2日");
//			    wsSaicDataModel.setCREDITCODE("20050205454054504");
//			    wsSaicDataModel.setDOM("asdf asdf asdf asdf asd fsa dfsd fasd fas dsf  sdaf");
//			    wsSaicDataModel.setDOMDISTRICT("市");
//			    wsSaicDataModel.setEMPNUM("1000");
//			    wsSaicDataModel.setENTNAME("凯瑞时代信息技术有限公司");
//			    wsSaicDataModel.setENTNAMEENG("CampRay");
//			    wsSaicDataModel.setENTSTATUS("正常");
//			    wsSaicDataModel.setENTTYPE("私营");
//			    wsSaicDataModel.setESDATE("20140416");
//			    wsSaicDataModel.setFRNAME("李德广");
//			    wsSaicDataModel.setINDUSTRYCOALL("201464646");			    			    
//			    responseModel.setData(wsSaicDataModel);
			    			    
			    mav.addObject("result",responseModel);
			    		   
		    }
		    catch(Exception exe){
		    	mav.addObject(ERROR_MSG_KEY,"征信查询请求失败："+exe.getMessage());
		    }		    
		    return mav;
		}
	}

}
