package com.cfish.rvb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.cfish.rvb.adapter.MessageAdapter;
import com.cfish.rvb.bean.Letter;
import com.cfish.rvb.bean.MsgData;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.JsonParse;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MessageActivity extends AppCompatActivity {
    private RequestParams paramsGet,paramsPost;
    private MessageAdapter adapter;
    private RecyclerView messageRv;
    private List<Letter> letters;
    private ImageButton post;
    private EditText content;
    private String frendid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageRv = (RecyclerView)findViewById(R.id.message_RecyclerView);
        post = (ImageButton)findViewById(R.id.post);
        getMsg();

    }

    public void getMsg() {
        paramsGet = new RequestParams();
        String uid = (CommonData.user.getUid() == null)? "-1" : CommonData.user.getUid();
        Log.d("Dfish", "Message uid = " + uid);
        paramsGet.add("uid", uid);
        paramsGet.add("type", "get_onemsg");
        paramsGet.add("currpage", "1");
        paramsGet.add("friendid","28072");
        HttpUtil.post(CommonData.userURL, paramsGet, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish", "Message ff " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish", "Message ss " + responseString);
                MsgData msgData = JsonParse.parseMsg(JSON.parseObject(responseString));
                letters = msgData.getLetters();
                //String name = msgData.getFriend().getName();
//                for (Letter letter :letters) {
//                    Log.d("Dfish","Message sendid "+name+letter.getSendid());
//
//                }
                adapter = new MessageAdapter(MessageActivity.this, letters);
                messageRv.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                messageRv.setAdapter(adapter);
            }
        });
    }

    public void postMsg() {
        paramsPost = new RequestParams();
        String uid = (CommonData.user.getUid() == null)? "-1" : CommonData.user.getUid();
        Log.d("Dfish", "Message uid = " + uid);
        paramsPost.add("uid", uid);
        paramsPost.add("type", "send_msg");
        paramsPost.add("recid", frendid);
        paramsPost.put("content", Html.toHtml(content.getText()));
        paramsPost.add("isnew", "new");
        HttpUtil.post(CommonData.userURL, paramsPost, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish","post message fail "+responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish","post message success "+responseString);
            }
        });
    }
}
