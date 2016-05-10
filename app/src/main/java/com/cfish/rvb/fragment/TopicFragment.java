package com.cfish.rvb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.cfish.rvb.ArticleActivity;
import com.cfish.rvb.GroupActivity;
import com.cfish.rvb.R;
import com.cfish.rvb.UserInfoActivity;
import com.cfish.rvb.adapter.TopicAdapter;
import com.cfish.rvb.bean.Topic;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.JsonParse;
import com.cfish.rvb.util.NetWorkHelper;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "TopicFragment";
    private RequestParams params;
    private TopicAdapter adapter;
    private int currentPage = 1;
    private boolean isLoading = false;
    private List<Topic> topicList;
    private RecyclerView topicRv;
    private MyProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;
    private BaseAttacher baseAttacher;
    private View view;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopicFragment newInstance(String param1, String param2) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TopicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topic, container, false);
        initView();
        initData();
        initAction();
        view.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
        return view;
    }

    public void initView() {
        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        topicRv = (RecyclerView)view.findViewById(R.id.topic_rv);
        progressBar = (MyProgressBar)view.findViewById(R.id.progressbar);
//        swipeRefresh.setColorSchemeColors(android.R.color.holo_blue_light,android.R.color.holo_red_light,
//                android.R.color.holo_orange_dark,android.R.color.holo_green_light,android.R.color.holo_purple);

        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_dark, android.R.color.holo_green_light, android.R.color.holo_purple);
        topicRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initData() {
        if(!NetWorkHelper.isNetworkAvailable(getActivity()) || !NetWorkHelper.checkNetState(getActivity())  ){
            return;
        }
        topicList = new ArrayList<>();
        adapter = new TopicAdapter(getActivity(),topicList);
        topicRv.setAdapter(adapter);
        swipeRefresh.setRefreshing(true);
        //topicRv.setOnScrollChangeListener();//?回到顶部？
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
        Log.d("Dfish","TopicFragment page = "+page);
        String uid=  (CommonData.user.getUid()== null)? "-1":CommonData.user.getUid() ;
        Log.d("Dfish","TopicFragment uid = "+uid);
        params = new RequestParams();
        params.add("uid", uid);
        params.add("type", "get_group_article_list");
        params.add("gid", "all");
        params.add("page", "" + page);
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore CStore = new PersistentCookieStore(getActivity());
        HttpUtil.setCookieStore(CStore);
        HttpUtil.post(CommonData.groupURL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish", "TopicFrament post fail ,status code " + statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish", "TopicFragment post success ,response is "+responseString);
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                if (page == 1) {
                    topicList.clear();
                    currentPage = 1;
                }
                List<Topic> topics = JsonParse.parseGroupArticles(JSON.parseObject(responseString));
                for (Topic topic:topics) {
                    topicList.add(topic);
                }
                //topicList.addAll(topics);
                if (adapter == null) {
                    adapter = new TopicAdapter(getActivity(),topicList);
                    topicRv.setAdapter(adapter);
                } else {
                    adapter.refreshData(topicList);
                }
                swipeRefresh.setRefreshing(false);
                adapter.setOnItemClickListener(new TopicAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View v, String data, int flag) {
                        //Snackbar.make(view, data, Snackbar.LENGTH_SHORT).show();
                        String[] s=data.split("`");
                        Intent intent = new Intent();
                        Bundle localBundle = new Bundle();
                        switch (flag) {
                            //click on username;
                            case 2:
                                intent.setClass(getActivity(), UserInfoActivity.class);
                                localBundle.putString("user_id", s[0]);
                                localBundle.putString("title",s[1]);
                                break;
                            //click on group avatar
                            case 1:
                                intent.setClass(getActivity(), GroupActivity.class);
                                localBundle.putString("gid", s[0]);
                                localBundle.putString("title",s[1]);
                                break;
                            case 0:
                                intent.setClass(getActivity(), ArticleActivity.class);
                                localBundle.putString("g_a_id", s[0]);
                                localBundle.putString("title",s[1]);
                                localBundle.putString("g_id",s[2]);
                                break;
                            default:
                                break;
                        }
                        intent.putExtras(localBundle);
                        getActivity().startActivity(intent);
                    }
                });
            }
        });
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser){
//            initData();
//        } else {
//            Log.d("Dfish","TopicFragment is not visible");
//        }
//    }
}
