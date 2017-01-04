package com.cfish.rvb;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cfish.rvb.adapter.ActionAdapter;
import com.cfish.rvb.adapter.UserInfoAdapter;
import com.cfish.rvb.bean.Behavior;
import com.cfish.rvb.bean.UserInfo;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.JsonParse;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import com.alibaba.fastjson.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class UserInfoActivity extends AppCompatActivity {
    private String TAG = "UserInfoActivity";
    private RequestParams params,paramsHead;
    private String user_id,title;
    private String userInfo = "get_userinfo";
    private RecyclerView behaviorRv;
    private UserInfoAdapter adapter;
    private ActionAdapter adaptera;
    private SimpleDraweeView user_img,gender_img;
    private TextView rank,signature,score;
    private Button sendMessage;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_user_info);
        this.user_id = getIntent().getExtras().getString("user_id");
        this.title = getIntent().getExtras().getString("title");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.mipmap.ic_arrow_left);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleGravity(Gravity.CENTER);
        behaviorRv = (RecyclerView)findViewById(R.id.behavior_RV);
        user_img = (SimpleDraweeView)findViewById(R.id.user_img);
        gender_img = (SimpleDraweeView)findViewById(R.id.gender);
        rank = (TextView)findViewById(R.id.rank);
        signature = (TextView)findViewById(R.id.signature);
        score = (TextView)findViewById(R.id.score);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMessage = new Intent();
                toMessage.putExtra("title",title);
                toMessage.putExtra("friendId",user_id);
                toMessage.setClass(UserInfoActivity.this,MessageActivity.class);
                startActivity(toMessage);
            }
        });
//        sendMessage = (Button) findViewById(R.id.send_message);
//        sendMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toMessage = new Intent();
//                toMessage.setClass(UserInfoActivity.this,MessageActivity.class);
//                startActivity(toMessage);
//            }
//        });
//        ControllerListener listener = new BaseControllerListener(){
//            @Override
//            public void onFailure(String id, Throwable throwable) {
//                super.onFailure(id, throwable);
//            }
//        };

//        GenericDraweeHierarchyBuilder builder =
//                new GenericDraweeHierarchyBuilder(getResources());
//        GenericDraweeHierarchy hierarchy = builder
//
//                .setOverlays()
//                .build();
//        GenericDraweeHierarchy hierarchy = user_img.getHierarchy();
//        hierarchy.set
        paramsHead = new RequestParams();
//       //"http://bbs.nankai.edu.cn/wap/user/34143"
//        HttpUtil.get("http://bbs.nankai.edu.cn/index.php/main", new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.d("Dfish", "HEAD IMG f" + responseString);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                String sub ;
//                for (int i = 0;i<responseString.length();i+=4096){
//                    if(i+4096 < responseString.length()){
//                        sub = responseString.substring(i,i+4096);
//                    } else {
//                        sub = responseString.substring(i,responseString.length());
//                    }
//
//                    Log.d("Dfish", "HEAD IMG s" + sub);
//                }
//            }
//        });
        initData();
        jsoupData();
    }

    private void initData() {
        String uid=  (CommonData.user.getUid()== null)? "-1":CommonData.user.getUid() ;
        Log.d(TAG, "uid = " + uid);
        params = new RequestParams();
        params.add("uid", uid);
        params.add("id", user_id);
        params.add("type",userInfo);
        HttpUtil.post(CommonData.userURL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "get userinfo FF " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, "get userInfo SS " + responseString);
//                JSONObject resp = JSON.parseObject(responseString);
//
//                UserInfo info = parseUserInfo(resp);
//                signature.setText(info.getSignature());
//                List<Behavior> behaviorList = info.getBehavior();
//                behaviorRv.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this));
//                adapter = new UserInfoAdapter(UserInfoActivity.this,behaviorList);
//                behaviorRv.setAdapter(adapter);

            }
        });
    }

    /**
     *抓取网页，用jsoup解析
     *
     */
    private void jsoupData() {
        HttpUtil.get(CommonData.wapUser + user_id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "personal page FF" + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, "personal page SS");
                List<Map> actionList = new ArrayList<>();
                Document doc = Jsoup.parse(responseString);
                Element head = doc.select("img.img-rounded").first();
                Elements infos = doc.select("div.ui-content > p");
                Elements my_news = doc.select("div.my_news");
                Element unconcern = doc.getElementById("unconcern");

                Elements privacys = doc.getElementsByClass("info_item");
                String gender = privacys.get(0).text().substring(3);
                Log.d(TAG, gender + (gender.equals("男")));
                if (gender.equals("男")) {
                    gender_img.setImageURI(Uri.parse("res:// /" + R.mipmap.ic_gender_male));
                } else if (gender.equals("女")) {
                    gender_img.setImageURI(Uri.parse("res:// /"+R.mipmap.ic_gender_female));
                }
                if (unconcern.hasText() && !unconcern.attr("style").equals("display:none")){
                    fab.show();
                } else {
                    fab.hide();
                }
                rank.setText(infos.get(2).text());
                signature.setText(privacys.get(4).text());
                score.setText(infos.get(1).text());
                for (Element element : my_news) {
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("title",element.text());
                    Log.d(TAG, "aad"+map.get("title"));
                    Elements links = element.getElementsByTag("a");
                    for (Element link : links) {
                        Log.d(TAG, "aaa"+link.text());
                    }
                    if (links.size()>1) {
                        map.put("link",links.get(1).attr("href"));
                    } else {
                        map.put("link","");
                    }

                    actionList.add(map);
                    Log.d(TAG, "my_news" + element.text());
                }
                //doc.getElementsByClass("");
                String headUrl = head.attr("src");
                Log.d(TAG, "try " + headUrl + "   " + head.toString());
                if (!headUrl.equals("http://bbs.nankai.edu.cn/data/uploads/avatar/000/00/00/xh.jpg")) {
                    user_img.setImageURI(Uri.parse(headUrl.replace("small", "big")));
                }
                behaviorRv.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this));
                adaptera = new ActionAdapter(UserInfoActivity.this, actionList);
                behaviorRv.setAdapter(adaptera);
                adaptera.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View v, String data) {

                        if (data!=null &&data.contains("site")){
                            Snackbar.make(v, data, Snackbar.LENGTH_SHORT).show();
                            String string = data.substring(data.lastIndexOf("/")+1,data.length());
                            Toast.makeText(UserInfoActivity.this, string,Toast.LENGTH_SHORT).show();
                        } else if (data!=null&& data.contains("wap")) {
                            Snackbar.make(v, data, Snackbar.LENGTH_SHORT).show();
                            String string = data.substring(data.lastIndexOf("/")+1,data.length());
                            Toast.makeText(UserInfoActivity.this, string,Toast.LENGTH_SHORT).show();
                        } else {
                            if (data!=null) {
                                Log.d("aaaaa",data);
                            }
                            Log.d("aaaaa","aaa+++aaa");
                            Snackbar.make(v, data, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aticle, menu);
        return true;
    }

    //parse userInfo
    private UserInfo parseUserInfo(JSONObject resp) {
        UserInfo userInfo = new UserInfo();
        JSONObject jsonData = resp.getJSONObject("data");
        userInfo = JSON.parseObject(jsonData.toJSONString(),UserInfo.class);
        return userInfo;
    }

}
