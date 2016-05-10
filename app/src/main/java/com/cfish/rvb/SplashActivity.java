package com.cfish.rvb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cfish.rvb.R;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.NetWorkHelper;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.SocketImpl;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int DISMISS_DELAY = 2000;

    private boolean Loged;

    private SimpleDraweeView welcomeImg;
    private TextView hello;
    String date;

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_splash);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 ");
        String date = formatter.format(new Date());
        Uri uri = Uri.parse("http://area.sinaapp.com/bingImg");
        //http://s.tu.ihuan.me/bgc/16-03-16.png
        //http://tu.ihuan.me/api/bing
        Log.d("Dfish", "welcome img uri "+uri.toString());
        welcomeImg = (SimpleDraweeView)findViewById(R.id.welcome_img);
        if (clearCache(date)) {
            //welcomeImg.destroyDrawingCache();
            Fresco.getImagePipeline().clearCaches();
            Log.d("Dfish","clear pics cahces");
        }
        welcomeImg.setImageURI(uri);
        hello = (TextView)findViewById(R.id.hello);
        hello.setText(date+getString(R.string.hello));

        hello.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mHideHandler.postDelayed(mHidePart2Runnable, DISMISS_DELAY);

    }




    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            autoLogin();
        }
    };


    private final Handler mHideHandler = new Handler();

    public void autoLogin() {
        if(!NetWorkHelper.isNetworkAvailable(this) || !NetWorkHelper.checkNetState(this)  ){
            Toast.makeText(this,"网络不可用",Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params =  new RequestParams();
        String userName,passWord;
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_LOGIN",0);
        userName = sharedPreferences.getString("SHARED_NAME","");
        passWord = sharedPreferences.getString("SHARED_PASSWORD","");
        if (userName.equals("")||passWord.equals("")) {
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, LoginActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();;
        } else {
            params.add("name",userName);
            params.add("pwd",passWord);
            params.add("remember","true");

            HttpUtil.post(CommonData.loginURl,params,new JsonHttpResponseHandler(){
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d("Dfish", "Login failure status code"+statusCode);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.d("Dfish", "Login success "+response.toString());
                    try {
                        JSONObject info = response.getJSONObject("data");
                        CommonData.user.setName(info.getString("name"));
                        CommonData.user.setBig_avatar(info.getString("big_avatar"));
                        CommonData.user.setScore(info.getString("score"));
                        CommonData.user.setUid(info.getString("uid"));
                        CommonData.user.setReply_order(info.getString("reply_order"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                }
            });
        }
    }

    public boolean clearCache(String date) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("TIME",0);
        if (sharedPreferences.getString("saved_time","").equals(date)) {
            Log.d("Dfish", "pics not update "+sharedPreferences.getString("saved_time", ""));
            return false;
        } else {
            sharedPreferences.edit().putString("saved_time",date).commit();
            Log.d("Dfish", "pics update "+sharedPreferences.getString("saved_time", ""));
            return true;
        }

    }
}
