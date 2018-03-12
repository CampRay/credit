/**   
* @Title: SystemConstants.java 
* @Package com.uswop.commons 
*
* @Description: 系统全局常量类
* 
* @date Sep 9, 2014 7:14:02 PM
* @version V1.0   
*/ 
package com.jtc.credit.commons;

import java.util.Hashtable;
import java.util.Map;

/** 
 * @ClassName: SystemConstants 
 * @Description: TODO
 * @author Phills Li
 * @date Sep 9, 2014 7:14:02 PM 
 *  
 */
public class SystemConstants {
	public static final String LOGINED="Logined";
	
	public static final String LOGIN_STATUS="Login_Status";
	
	public static final String LOGIN_ERROR="Login_Error";
	
	public static final String LOGIN_ERROR_LOCK="Login_Error_Locked";
	
	public static final String EMAIL_HOST="Email_Host";
	
	public static final String EMAIL_NAME="Email_Username";
	
	public static final String EMAIl_PASSWORD="Email_Password";
	
	public static final String EMAIl_ISSSL="Email_IsSSL";
	
	public static final String EMAIl_PORT="Email_Port";
	
	
	public static final String WEBSITE_URL="Website_URL";
	
	public static Map<String,String> Admin_Setting_Map=new Hashtable<String,String>();
}
