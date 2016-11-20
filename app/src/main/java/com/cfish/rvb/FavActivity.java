package com.cfish.rvb;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cfish.rvb.adapter.SimpleTopicAdapter;
import com.cfish.rvb.bean.Article;
import com.cfish.rvb.bean.Group;
import com.cfish.rvb.bean.Topic;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FavActivity extends AppCompatActivity {
    private RecyclerView simpleTopics;
    private RequestParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        setTitle("收藏");
        simpleTopics = (RecyclerView)findViewById(R.id.simple_topics);
        getData(1);

    }

    public void getData(int page) {
        params =  new RequestParams();
        params.put("fav_page",page);
        params.put("active", "fav");
        Log.d("Dfish", params.toString());
        HttpUtil.get("http://bbs.nankai.edu.cn/user/index/" + CommonData.user.getUid(), params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish", "fav fail" + statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish", "fav success" + responseString);
                List<Topic> topicList = new ArrayList<Topic>();
                Document doc = Jsoup.parse(responseString);
                Elements topics= doc.select("#left > div:nth-child(5) > div.tab_body");
                Log.d("Dfish",topics.toString());
                for (Element element : topics) {
                    Topic topic =  new Topic();
                    topic.setName(element.select(".tab_name").first().text());
                    topic.setG_a_id(element.attr("id"));
                    topicList.add(topic);
                }
                SimpleTopicAdapter adapter = new SimpleTopicAdapter(FavActivity.this,topicList);
                setRecyclerView(adapter);


            }
        });

    }

    public void setRecyclerView(SimpleTopicAdapter adapter) {
        simpleTopics.setLayoutManager(new LinearLayoutManager(FavActivity.this));
        simpleTopics.setAdapter(adapter);
        adapter.setOnItemClickListener(new SimpleTopicAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, String data) {
                Snackbar.make(v,"heihei",Snackbar.LENGTH_SHORT).show();
                String[] s=data.split("`",2);
                Log.d("Dfish",s[0]+","+s[1]);

                Intent intent = new Intent();
                Bundle localBundle = new Bundle();
                intent.setClass(FavActivity.this, ArticleActivity.class);
                localBundle.putString("g_a_id", s[0]);
                localBundle.putString("title",s[1]);
                intent.putExtras(localBundle);
                FavActivity.this.startActivity(intent);
            }
        });
    }
}
