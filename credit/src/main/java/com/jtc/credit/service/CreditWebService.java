package com.jtc.credit.service;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFactory;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.geeksfinance.credit.pub.util.CryptoUtils;

public class CreditWebService {
	private String serviceUrl="http://www.elecredit.com/element_credit/ws/EncryptedService?wsdl";
	
	
	public String invokeService(String message) throws Exception {

		// 设置 XPathFactory实现类，解决在Tomcat中运行时JaxWsDynamicClientFactory创建createClient， 时抛出XPathFactory#newInstance() failed to create an XPathFactory错误的问题  
		System.setProperty(XPathFactory.DEFAULT_PROPERTY_NAME + ":" + XPathFactory.DEFAULT_OBJECT_MODEL_URI, "com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl"); 
		
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		
		Client client = factory.createClient(serviceUrl,ClassLoader.getSystemClassLoader());
		HTTPConduit conduit = (HTTPConduit) client.getConduit();  
	    HTTPClientPolicy policy = new HTTPClientPolicy();  
	    long timeout = 60  * 1000;//  
	    policy.setConnectionTimeout(timeout);  
	    policy.setReceiveTimeout(timeout);  
	    conduit.setClient(policy);
	    return invokeService(getPublicKey(client), getKey(), message, client);			    
	}
	
	
	//说明：获取服务公钥 
	public String getPublicKey(Client client) throws Exception {
		QName opName = new QName("http://ws.api.credit.geeksfinance.com/", "getPublicKey");
		Object[] objects = client.invoke(opName, new Object[] {});
		return (String) objects[0];
	}

	//说明：调用服务接口
	//key ：客户端生成的 DES 密钥，使用 RSA 公钥进行加密的结果
	//message：使用 DES 密钥加的请求报文
	//返回值：使用 DES密钥加的响应报文
	public String invokeService(String key, String message,Client client) throws Exception {
		QName opName = new QName("http://ws.api.credit.geeksfinance.com/", "invokeService");
		Object[] objects = client.invoke(opName, new Object[] { key, message });
		return (String) objects[0];
	}

	public String invokeService(String publicKey, String key, String message,Client client) throws Exception {
		
		PublicKey pk = CryptoUtils.getRSAPublicKey(Base64.getMimeDecoder().decode(publicKey));

		SecretKey sk = CryptoUtils.createDESKey(key.getBytes("UTF-8"));
		String encryptedKey = Base64.getMimeEncoder().encodeToString(CryptoUtils.rsaEncrypt(pk, key.getBytes("UTF-8")));

		String encryptedMessage =Base64.getMimeEncoder().encodeToString(CryptoUtils.desEncrypt(sk, message.getBytes("UTF-8")));
		String encryptedResult = invokeService(encryptedKey, encryptedMessage, client);
		return new String(CryptoUtils.desDecrypt(sk, Base64.getMimeDecoder().decode(encryptedResult)), "UTF-8");
	}
	private String getKey() {
		Random random = new Random();
		int length = 32 + random.nextInt(33);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; ++i) {
			sb.append((char) randomCharValue(random));
		}
		return sb.toString();
	}

	private int randomCharValue(Random random) {
		switch (random.nextInt(3)) {
		case 0:
			return random.nextInt(10) + '0';
		case 1:
			return random.nextInt(26) + 'A';
		default:
			return random.nextInt(26) + 'a';
		}
	}
	

}
