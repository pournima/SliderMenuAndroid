package com.slidingdemo;

import android.os.AsyncTask;
import android.util.Log;

public class LoginTask extends AsyncTask<String, Integer, String> {
	
	private MainActivity mContext;
	
	public LoginTask(MainActivity context){
		mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return LoginWebService.authenticate(params);
	}
	
	protected void onPostExecute(String result) {
        if ( result != null )
            Log.v("Bonzi", result);
        /* else {
            context.DisplayToast("Can't connect right now, try after some time!");
        }*/
    }

}
