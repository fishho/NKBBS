package com.cfish.rvb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cfish.rvb.adapter.ActionAdapter;
import com.cfish.rvb.adapter.NotificationAdapter;
import com.cfish.rvb.bean.Article;
import com.cfish.rvb.util.JsonParse;

public class NotificationListActivity extends AppCompatActivity {
    private NotificationAdapter adapter;
    private RecyclerView notificationList;
    private String news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        news  = getIntent().getExtras().getString("news");
        //news = "{'ajax_status':1,'news':[{'uid':'2785','num':'412001','status':'4','history_status':'1','id':'2881769','reply_id':'280011','ctime':'2016-04-14 08:55:59','count':'2','name':'2016春季日剧3，Love Song'}],'count':1}";
        notificationList = (RecyclerView)findViewById(R.id.notification_lis);
        adapter =  new NotificationAdapter(this,JsonParse.parseNotice(news).getNews());
        notificationList.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, String data) {
                String[] s = data.split("`");
                Intent intent = new Intent();
                intent.setClass(NotificationListActivity.this, ArticleActivity.class);
                intent.putExtra("g_a_id", s[0]);
                intent.putExtra("title",s[1]);
                NotificationListActivity.this.startActivity(intent);
            }
        });
        notificationList.setAdapter(adapter);

    }
}
