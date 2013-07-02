package com.slidingdemo;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.slidingdemo.HttpsClientBuilder.Naive_SSLSocketFactory;

public class WebService {
	DefaultHttpClient httpClient;
	HttpContext localContext;
	HttpResponse response = null;
	HttpPost httpPost = null;
	HttpGet httpGet = null;
	List<NameValuePair> nvp;
	String webServiceUrl;
	private String strResponse;

	public WebService(String serviceName, List<NameValuePair> nvpair) {

		HttpParams myParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(myParams, 20000);
		HttpConnectionParams.setSoTimeout(myParams, 20000);

		/////////////////////////////////////////////////////////////////////////////////////
		SchemeRegistry Current_Scheme = new SchemeRegistry();
		Current_Scheme.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		try {
			Current_Scheme.register(new Scheme("https", new Naive_SSLSocketFactory(), 443));
			ThreadSafeClientConnManager Current_Manager = new ThreadSafeClientConnManager(myParams, Current_Scheme);
			httpClient = new DefaultHttpClient(Current_Manager,myParams);
			localContext = new BasicHttpContext();
			webServiceUrl = serviceName;
			nvp = nvpair;
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String webPost(List<NameValuePair> params) {
        String postUrl = webServiceUrl;

        httpPost = new HttpPost(postUrl);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException uee) {
           // Log.e("CHECKINFORGOOD", uee.getMessage());
        }
      //  Log.e("WebGetURL: ", postUrl);

        try {
        	
            response = httpClient.execute(httpPost);
        } catch (Exception e) {
            if (e.getMessage() != null) {
              //  Log.e("CHECKINFORGOOD", "httpClient.execute(httpPost) Exception: " + e.getMessage());
            } else {
               // Log.e("CHECKINFORGOOD", "httpClient.execute(httpPost) Exception: " + e.getClass().toString());
            }
        }

        try {
        	strResponse = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            if (e.getMessage() != null) {
                //Log.e("CHECKINFORGOOD", e.getMessage());
            } else {
               // Log.e("CHECKINFORGOOD", e.getClass().toString());
            }
        }

        return strResponse;
    }


}
