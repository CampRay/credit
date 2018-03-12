package com.jtc.credit.commons;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.jtc.credit.dto.TemaiMessage;
public class EMailTool {
	
	 public static void send(TemaiMessage temaiMessage,boolean html){
		try {
			JavaMailSender javaMailSenderImpl=getJavaMailSenderImpl();
			MimeMessage message = javaMailSenderImpl.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
            messageHelper.setFrom(new InternetAddress(SystemConstants.Admin_Setting_Map.get(SystemConstants.EMAIL_NAME)));
            if(temaiMessage.getTo() !=null && !temaiMessage.getTo().isEmpty()){
            	messageHelper.setTo(temaiMessage.getTo());
            }
            if(temaiMessage.getSubject() !=null){
            	messageHelper.setSubject(temaiMessage.getSubject());
            }
            if(temaiMessage.getText() !=null){
            	messageHelper.setText(temaiMessage.getText(),html);
            }
			javaMailSenderImpl.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	public static JavaMailSenderImpl getJavaMailSenderImpl(){
	    JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
	    javaMailSenderImpl.setHost(SystemConstants.Admin_Setting_Map.get(SystemConstants.EMAIL_HOST));
	    javaMailSenderImpl.setUsername(SystemConstants.Admin_Setting_Map.get(SystemConstants.EMAIL_NAME));
	    javaMailSenderImpl.setPassword(SystemConstants.Admin_Setting_Map.get(SystemConstants.EMAIl_PASSWORD));
	    String email_isSSL=SystemConstants.Admin_Setting_Map.get(SystemConstants.EMAIl_ISSSL);
	    String email_port=SystemConstants.Admin_Setting_Map.get(SystemConstants.EMAIl_PORT);
	    boolean isSSL=false;
	    int port=25;
	    try{
		    isSSL=new Boolean(email_isSSL);		    
		    port=new Integer(email_port);    			    	
			javaMailSenderImpl.setPort(port);	    			    
	    }
    	catch(Exception e){}
	    Properties properties = new Properties();
	    properties.setProperty("mail.smtp.auth", "true");
	    properties.setProperty("mail.smtp.timeout", "30000");
	    if(isSSL){
//		    properties.setProperty("mail.smtp.port", email_port);//设置端口  
//	        properties.setProperty("mail.smtp.socketFactory.port", email_port);//设置ssl端口  
//	        properties.setProperty("mail.smtp.socketFactory.fallback", "false");  
	        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    }
	    
	    javaMailSenderImpl.setJavaMailProperties(properties);
	    return javaMailSenderImpl;
     }

}
