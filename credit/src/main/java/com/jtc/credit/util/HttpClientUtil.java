package com.jtc.credit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.jtc.credit.commons.MyException;

public class HttpClientUtil {	
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    
    public static String sendPost(String uri, String charset) {
    	if(uri.startsWith("https://")){
    		return sendHttpsPost(uri,charset,null,"");
    	}
    	else{
    		return sendHttpPost(uri,charset,null,"");
    	}
    	
	}
    
    public static String sendPost(String uri, String charset, Map<String,Object> paramMap) {
    	return sendPost(uri,charset,null,paramMap);
	}
    
    public static String sendPost(String uri, String charset,Map<String,String> headerMap, Map<String,Object> paramMap) {
    	StringBuilder sb=new StringBuilder();	
    	if(paramMap!=null){
						
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {  
				sb.append(entry.getKey()+"="+String.valueOf(entry.getValue())+"&");	                
            } 			
				
		}    	
    	
    	if(uri.startsWith("https://")){
    		return sendHttpsPost(uri,charset,null,sb.toString());
    	}
    	else{
    		return sendHttpPost(uri,charset,null,sb.toString());
    	}
	}

	/**
	 * 
	 * @param uri
	 * @param paramStr
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param charset
	 * @return
	 */
	public static String sendHttpPost(String uri, String charset,Map<String,String> headerMap, String paramStr) {
		String result = null;
		PrintWriter out = null;
		InputStream in = null;
		try {
			URL url = new URL(uri);
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();					
			
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在   
			// http正文内，因此需要设为true, 默认情况下是false; 
			if(paramStr!=null&&!paramStr.isEmpty()){
				urlcon.setDoInput(true);								
			}
			else{
				urlcon.setDoInput(false);	
			}
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			urlcon.setDoOutput(true);
			// Post 请求不能使用缓存
			urlcon.setUseCaches(false);
			urlcon.setRequestMethod("POST");
			if(headerMap!=null){
				for (String key : headerMap.keySet()) {
					urlcon.setRequestProperty(key, headerMap.get(key));
				}
			}
			
									
			urlcon.connect();// 获取连接
			if(paramStr!=null&&!paramStr.isEmpty()){								
				out = new PrintWriter(urlcon.getOutputStream());
				out.print(paramStr);
				out.flush();
			}
			
			
			in = urlcon.getInputStream();	
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					in, charset));
			StringBuffer bs = new StringBuffer();
			String line = null;
			while ((line = buffer.readLine()) != null) {
				bs.append(line).append(System.getProperty("line.separator"));
			}
			result = bs.toString();		
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// 发生网络异常  
            throw new MyException(1002);  
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// 未知异常  
            throw new MyException(1004);  
		} finally {
			try {
				if (null != in)
					in.close();
				if (null != out)
					out.close();
			} catch (Exception e2) {				
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param uri
	 * @param paramStr
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param charset
	 * @return
	 */
	public static String sendHttpsPost(String uri, String charset,Map<String,String> headerMap, String paramStr) {
		String result = null;
		PrintWriter out = null;
		InputStream in = null;
		try {
			URL url = new URL(uri);
			HttpsURLConnection urlcon = (HttpsURLConnection) url.openConnection();					
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化    
            TrustManager[] tm = { new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");    
            sslContext.init(null, tm, new java.security.SecureRandom());    
            // 从上述SSLContext对象中得到SSLSocketFactory对象    
            SSLSocketFactory ssf = sslContext.getSocketFactory();    
            urlcon.setSSLSocketFactory(ssf);
            
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在   
			// http正文内，因此需要设为true, 默认情况下是false; 
			if(paramStr!=null&&!paramStr.isEmpty()){
				urlcon.setDoInput(true);								
			}
			else{
				urlcon.setDoInput(false);	
			}
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			urlcon.setDoOutput(true);
			// Post 请求不能使用缓存
			urlcon.setUseCaches(false);
			urlcon.setRequestMethod("POST");
			if(headerMap!=null){
				for (String key : headerMap.keySet()) {
					urlcon.setRequestProperty(key, headerMap.get(key));
				}
			}
			
									
			urlcon.connect();// 获取连接
			if(paramStr!=null&&!paramStr.isEmpty()){								
				out = new PrintWriter(urlcon.getOutputStream());
				out.print(paramStr);
				out.flush();
			}
			
			
			in = urlcon.getInputStream();	
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					in, charset));
			StringBuffer bs = new StringBuffer();
			String line = null;
			while ((line = buffer.readLine()) != null) {
				bs.append(line).append(System.getProperty("line.separator"));
			}
			result = bs.toString();		
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// 发生网络异常  
            throw new MyException(1002);  
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// 未知异常  
            throw new MyException(1004);  
		} finally {
			try {
				if (null != in)
					in.close();
				if (null != out)
					out.close();
			} catch (Exception e2) {				
			}
		}
		return result;
	}
	
	private static class MyX509TrustManager implements X509TrustManager {  
  
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
        }  
          
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
        }  
     
        public X509Certificate[] getAcceptedIssuers() {  
            return null;  
        }  
  
	}  
}
