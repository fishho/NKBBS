package com.cfish.rvb;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListView;

import com.cfish.rvb.bean.Message;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.JsonParse;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class GetMsgService extends Service {
    static final int UPDATE_INTERVAL = 1000*30;
    RequestParams params;
    private Timer timer = new Timer();

    public GetMsgService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Dfish","service onCreate");
        params = new RequestParams();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Dfish","service onStartCommand");
        repeatTask();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Dfish", "service OnDestroy");
    }

    public void repeatTask(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                task();
            }
        },0,UPDATE_INTERVAL);
    }

    public void task(){
        String uid=  (CommonData.user.getUid()== null)? "-1":CommonData.user.getUid() ;
        Log.d("Dfish", "GetMsgService uid = " + uid);
        params.add("uid", uid);
        params.add("type", "interaction_state");
        //HttpUtil.post(CommonData.userURL, params, responseHandler);
        HttpUtil.get("http://bbs.nankai.edu.cn/user/get_news",responseHandlerAjax);
    }

    JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.d("Dfish", "get GetMsg SS " + response.toString());
            String count ="0" ;
            try {
                 count = response.getJSONObject("data").getString("count");
                 Log.d("Dfish","count"+count);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!count.equals("0")) {
                Log.d("Dish","count"+count);
                Intent informIntent = new Intent();
                informIntent.setAction("android.intent.action.MSG");
                sendBroadcast(informIntent);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.d("Dfish", " get GetMsg FF " + statusCode);
        }
    };

    JsonHttpResponseHandler responseHandlerAjax = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.d("Dfish","get Msg ajax ss"+response.toString());
            try {
                if (!response.getString("count").equals("0")) {
                    Log.d("Dish","news count"+response.getString("count"));
                    Intent informIntent = new Intent();
                    informIntent.putExtra("news",response.toString());
                    informIntent.setAction("android.intent.action.MSG");
                    sendBroadcast(informIntent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.d("Dfish","get Msg ajax ff"+statusCode);
        }
    };
}
