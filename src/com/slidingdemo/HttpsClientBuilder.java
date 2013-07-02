package com.slidingdemo;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class HttpsClientBuilder {
	/*public static DefaultHttpClient getBelieverHttpsClient(String url) {

        DefaultHttpClient client = null;

        SchemeRegistry Current_Scheme = new SchemeRegistry();
        Current_Scheme.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        try {
            Current_Scheme.register(new Scheme("https", new Naive_SSLSocketFactory(), 443));
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
        HttpParams Current_Params = new BasicHttpParams();
        int timeoutConnection = 200000;
        HttpConnectionParams.setConnectionTimeout(Current_Params, timeoutConnection);
        int timeoutSocket = 200000;
        HttpConnectionParams.setSoTimeout(Current_Params, timeoutSocket);
        ThreadSafeClientConnManager Current_Manager = new ThreadSafeClientConnManager(Current_Params, Current_Scheme);
        client = new DefaultHttpClient(Current_Manager, Current_Params);
        HttpPost httpPost = new HttpPost(url);


        String[] signals_parms = {"coach@coach.com","11111111"};
StringBuilder signed_value = new StringBuilder();

		for (int i = 0; i < signals_parms.length; i++) {
			signed_value.append(signals_parms[i]);
		}
        String str = signed_value.toString();

        str = MD5Class.hash_md5(str);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", "coach@coach.com"));
        nameValuePairs.add(new BasicNameValuePair("password", "11111111"));
        nameValuePairs.add(new BasicNameValuePair("hmac", str));
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = client.execute(httpPost);
			String str1 = EntityUtils.toString(response.getEntity());
			Log.i("test", str1);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return client;
	}*/

	public static class Naive_SSLSocketFactory extends SSLSocketFactory
	{
		protected SSLContext Cur_SSL_Context = SSLContext.getInstance("TLS");

		public Naive_SSLSocketFactory ()
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException
				{
			super(null);
			Cur_SSL_Context.init(null, new TrustManager[] { new X509_Trust_Manager() }, null);
				}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException
				{
			return Cur_SSL_Context.getSocketFactory().createSocket(socket, host, port, autoClose);
				}

		@Override
		public Socket createSocket() throws IOException
		{
			return Cur_SSL_Context.getSocketFactory().createSocket();
		}
	}

	private static class X509_Trust_Manager implements X509TrustManager
	{

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	};
}