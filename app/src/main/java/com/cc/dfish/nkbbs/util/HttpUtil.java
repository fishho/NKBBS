package com.cc.dfish.nkbbs.util;

/**
 * Created by dfish on 2016/2/3.
 */
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


public class HttpUtil {
    private  static AsyncHttpClient httpClient =new AsyncHttpClient();
    private static String sessionId = null;
    private static PersistentCookieStore cookieStore ;
    static {
        httpClient.setTimeout(5000);
    }
    public static AsyncHttpClient getClient(){
        return httpClient;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        HttpUtil.sessionId = sessionId;
    }

    public static PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    public static void setCookieStore(PersistentCookieStore cookieStore) {
        HttpUtil.cookieStore = cookieStore;
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
        httpClient.get(url, params,resp);
    }

    public static void get(String url,JsonHttpResponseHandler resp) {
        httpClient.get(url, resp);
    }

    public static void get(String url,RequestParams params,JsonHttpResponseHandler resp){
        httpClient.get(url,params,resp);
    }

    public static void get(String url,BinaryHttpResponseHandler resp){
        httpClient.get(url, resp);
    }

    public static void post(String url,RequestParams params,JsonHttpResponseHandler resp){
        httpClient.post(url, params,resp);
    }

    public static void post(String url,RequestParams params,AsyncHttpResponseHandler resp){
        httpClient.post(url,params, resp);
    }

    public static void post(String url,TextHttpResponseHandler resp) {
        httpClient.get(url, resp);
    }

    public static void post(String url,RequestParams params,TextHttpResponseHandler resp) {
        httpClient.get(url, params,resp);
    }

}
