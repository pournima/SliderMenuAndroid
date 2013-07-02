package com.slidingdemo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class LoginWebService {

	public static String authenticate(String... login_params) {

		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("username",login_params[0] ));
		params.add(new BasicNameValuePair("password",login_params[1]));

		WebService webService = new WebService("https://www.bonziteam.com/api.php?cmd=signin",params);
		String response = webService.webPost(params);
		if(response != null)
			Log.e("Bonzi", response.toString());

		return response;
	}

}
