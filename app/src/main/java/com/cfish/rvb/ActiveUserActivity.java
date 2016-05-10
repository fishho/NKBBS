package com.cfish.rvb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cfish.rvb.adapter.ActiveUserAdapter;
import com.cfish.rvb.adapter.GroupAdapter;
import com.cfish.rvb.bean.ActiveUser;
import com.cfish.rvb.bean.Group;
import com.cfish.rvb.util.HttpUtil;
import com.loopj.android.http.TextHttpResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ActiveUserActivity extends AppCompatActivity {
    private RecyclerView rvActive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_user);
        rvActive = (RecyclerView)findViewById(R.id.rv_active);
        rvActive.setLayoutManager(new GridLayoutManager(this,3));
        jsoupActive();
    }


    private void jsoupActive() {
        HttpUtil.get("http://bbs.nankai.edu.cn/index.php/main", new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish", "group jsoup FF" + statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish", "group jsoup SS" + statusCode);
                List<ActiveUser> userList = new ArrayList<>();
                Document doc = Jsoup.parse(responseString);
                Element eactiveGroup= doc.select("#active-users > div.img_box").first();//firs > li a.text");
                Elements activeUsers = eactiveGroup.select("span.img_content");
                //http://bbs.nankai.edu.cn/group/view/1
                for (Element element : activeUsers) {
                    //String img_src = element.getElementsByClass("lazy").first().attr("data-src");
//                    <span class="img_content"> <a href="user/index/434" title="活跃度:10
//                     总积分:2290"> " +
//                            "<img alt="" class="lazy" data-src="/data/uploads/avatar/2015/small/434.jpg">" +
//                            " </a> <a class="text" href="user/index/434">光里的小草</a> </span>

                    Elements descrip = element.select("a");
//                    Element name = descrip.last();
//                    Element title = descrip.first();
                    String imgSrc = "http://bbs.nankai.edu.cn"+
                            element.select("img").first().attr("data-src").replace("small","big");
                    String gid = descrip.first().attr("href").substring(11);
                    String activeDegree = descrip.first().attr("title").substring(4, 6).trim();
                    String userName = descrip.last().text();
                    ActiveUser activeUser = new ActiveUser();
                    activeUser.setName(userName);
                    activeUser.setActive(activeDegree);
                    activeUser.setGid(gid);
                    activeUser.setImgSrc(imgSrc);
                    userList.add(activeUser);
                    Log.d("Dfish","img_src = "+gid+" activeDegree="+activeDegree+" userName="+userName);
                }
//                GroupAdapter adapter = new GroupAdapter(getActivity(), groupList);
//                groupRv.setAdapter(adapter);
                ActiveUserAdapter adapter = new ActiveUserAdapter(ActiveUserActivity.this,userList);
                adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View v, String data) {
                        String[] s = data.split("`");
                        Intent intent =  new Intent();
                        intent.setClass(ActiveUserActivity.this,UserInfoActivity.class);
                        intent.putExtra("user_id", s[0]);
                        intent.putExtra("title", s[1]);
                        ActiveUserActivity.this.startActivity(intent);
                    }
                });
                rvActive.setAdapter(adapter);


            }
        });
    }
}
