package com.cfish.rvb.util;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


public class HttpUtil {
	private  static AsyncHttpClient httpClient =new AsyncHttpClient();
	static {
		httpClient.setTimeout(5000);
	}
	public static void setCookieStore(Context context) {
        PersistentCookieStore CStore =  new PersistentCookieStore(context);
        httpClient.setCookieStore(CStore);
    }
    public static void setCookieStore(PersistentCookieStore cookieStore) {
        httpClient.setCookieStore(cookieStore);
    }
	public static void get(String url,AsyncHttpResponseHandler resp){
		httpClient.get(url, resp);
	}
	
	public static void get(String url,RequestParams params,AsyncHttpResponseHandler resp){
		httpClient.get(url,params,resp);
	}
	
	public static void get(String url,TextHttpResponseHandler resp) {
		httpClient.get(url, resp);
	}
	
	public static void get(String url,RequestParams params,TextHttpResponseHandler resp) {
		httpClient.get(url, params, resp);
	}
	
	public static void get(String url,JsonHttpResponseHandler resp) {
		httpClient.get(url, resp);
	}
	
	public static void get(String url,RequestParams params,JsonHttpResponseHandler resp){
		httpClient.get(url, params, resp);
	}
	
	public static void get(String url,BinaryHttpResponseHandler resp){
		httpClient.get(url, resp);
	}

	public static void post(String url,RequestParams params,JsonHttpResponseHandler resp){
		httpClient.post(url, params, resp);

	}
	
	public static void post(String url,RequestParams params,AsyncHttpResponseHandler resp){
		httpClient.post(url, params, resp);
	}
	
	public static void post(String url,TextHttpResponseHandler resp) {
		httpClient.post(url, resp);
	}
	
	public static void post(String url,RequestParams params,TextHttpResponseHandler resp) {
		httpClient.post(url, params, resp);
	}


	
}
