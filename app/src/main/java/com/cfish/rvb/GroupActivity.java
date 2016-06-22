package com.cfish.rvb;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cfish.rvb.R;
import com.cfish.rvb.adapter.TopicAdapter;
import com.cfish.rvb.bean.Topic;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.JsonParse;
import com.cfish.rvb.view.MyProgressBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GroupActivity extends AppCompatActivity {
    private RequestParams params;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private String gid,canAnonymous;
    private Boolean flagConcern = false; //concern or unconcern group
    private TopicAdapter adapter;
    private int currentPage = 1;
    private boolean isLoading = false;
    private List<Topic> topicList;
    private RecyclerView topicRv;
    private MyProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;
    private BaseAttacher baseAttacher;
    private View view;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        this.gid = getIntent().getExtras().getString("gid");
        setTitle(getIntent().getExtras().getString("title"));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        fab = (FloatingActionButton) findViewById(R.id.fab);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue outValue = new TypedValue();
            this.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            fab.setBackgroundResource(outValue.resourceId);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("gid", gid);
                intent.putExtra("canAnonymous",canAnonymous);
                intent.setClass(GroupActivity.this, PostActivity.class);
                //GroupActivity.this.startActivity(intent); //idea:for result and refresh?
                //idea impl
                startActivityForResult(intent,2);
            }
        });

        initView();
        initData();
        initAction();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==2 ) {
            getData(1);
        }
    }

    public void initView() {
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_g);
        topicRv = (RecyclerView)findViewById(R.id.topic_rv_g);
        progressBar = (MyProgressBar)findViewById(R.id.progressbar_g);
//        swipeRefresh.setColorSchemeColors(android.R.color.holo_blue_light,android.R.color.holo_red_light,
//                android.R.color.holo_orange_dark,android.R.color.holo_green_light,android.R.color.holo_purple);

        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_dark, android.R.color.holo_green_light, android.R.color.holo_purple);
        topicRv.setLayoutManager(new LinearLayoutManager(GroupActivity.this));
    }
    public void initData() {
        topicList = new ArrayList<>();
        adapter = new TopicAdapter(GroupActivity.this,topicList);
        topicRv.setAdapter(adapter);
        swipeRefresh.setRefreshing(true);
        getData(1);
    }

    public void initAction() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(1);
            }
        });
        baseAttacher = Mugen.with(topicRv, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                isLoading = true;
                progressBar.setVisibility(View.VISIBLE);
                currentPage++;
                getData(currentPage);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        }).start();
    }

    public void getData(final int page) {
        String uid=  (CommonData.user.getUid()== null)? "-1":CommonData.user.getUid() ;
        Log.d("Dfish", "uid = " + uid);
        params = new RequestParams();
        params.add("uid", uid);
        params.add("type", "get_group_article_list");
        params.add("gid", gid);
        Log.d("Dfish","gid"+gid);
        params.add("page", "" + page);
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore CStore = new PersistentCookieStore(context);
//        //HttpUtil.setCookieStore(CStore);
//        CStore.clear();
//        HttpUtil.setCookieStore(CStore);
//        //HttpUtil.setCookieStore(context);
        HttpUtil.post(CommonData.groupURL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish", "FF" + statusCode + headers);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish", "2" + responseString);
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                if (page == 1) {
                    topicList.clear();
                }
                JSONObject resp = JSON.parseObject(responseString);
                List<Topic> topics = JsonParse.parseGroupArticles(resp);
                String concern = resp.getJSONObject("data").getString("concern");
                canAnonymous = resp.getJSONObject("data").getJSONObject("group").getString("anonymous");
                Log.d("Dfish",canAnonymous);
                if (concern.equals("1")) {
                    fab.setVisibility(View.VISIBLE);
                    flagConcern = true;
                }
                for (Topic topic : topics) {
                    topicList.add(topic);
                }
                //topicList.addAll(topics);
                if (adapter == null) {
                    adapter = new TopicAdapter(context, topicList);
                    topicRv.setAdapter(adapter);
                } else {
                    adapter.refreshData(topicList);
                }
                swipeRefresh.setRefreshing(false);
                adapter.setOnItemClickListener(new TopicAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View v, String data, int flag) {
                        String[] s=data.split("`");
                        Intent intent = new Intent();
                        Bundle localBundle = new Bundle();
                        switch (flag) {
                            //click on username;
                            case 2:
                                intent.setClass(GroupActivity.this, UserInfoActivity.class);
                                localBundle.putString("user_id", s[0]);
                                localBundle.putString("title",s[1]);
                                break;
                            //click on group avatar
                            case 1:
                                intent.setClass(GroupActivity.this, GroupActivity.class);
                                localBundle.putString("gid", s[0]);
                                localBundle.putString("title",s[1]);
                                break;
                            case 0:
                                intent.setClass(GroupActivity.this, ArticleActivity.class);
                                localBundle.putString("g_a_id", s[0]);
                                localBundle.putString("title",s[1]);
                                break;
                            default:
                                break;
                        }

                        intent.putExtras(localBundle);
                        GroupActivity.this.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_concern:
                RequestParams paramsConcern = new RequestParams();
                final String snackText;
                //设置flag,取消 or 关注，true or false;
                if (flagConcern) {
                    paramsConcern.add("thing", "unconcern");
                    snackText = "取消成功";
                    flagConcern = false;
                } else {
                    paramsConcern.add("thing", "concern");
                    snackText = "关注成功";
                    flagConcern = true;
                }

                paramsConcern.add("gid", gid);
                HttpUtil.post("http://bbs.nankai.edu.cn/group/action", paramsConcern,new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("Dfish",responseString+"操作失败F");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("Dfish",responseString+"操作成功S");
                        Snackbar.make(toolbar,snackText,Snackbar.LENGTH_SHORT).show();
                        if(flagConcern) {
                            fab.show();
                        } else {
                            fab.hide();
                        }
                    }
                });
                break;

        }
        return false;
    }

}
