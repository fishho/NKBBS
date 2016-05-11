package com.cfish.rvb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "TopFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RequestParams params;
    private TopicAdapter adapter;
    private List<Topic> topicList;
    private RecyclerView topicRv;
    private Button day,week,month;
    private View view;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopFragment newInstance(String param1, String param2) {
        TopFragment fragment = new TopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TopFragment() {
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
        view = inflater.inflate(R.layout.fragment_top, container, false);
        initView();
        //initData();
        initAction();
        view.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
        return view;
    }

    public void initView() {
        topicRv = (RecyclerView)view.findViewById(R.id.topic_rv);
        topicRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        day = (Button)view.findViewById(R.id.day);
        month = (Button)view.findViewById(R.id.month);
        week = (Button)view.findViewById(R.id.week);
    }

    public void initData() {
        topicList = new ArrayList<>();
        params = new RequestParams();
        params.add("time","day");
        getDada(true); //call setAdapter
    }

    public void initAction() {
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.remove("time");
                params.add("time","day");
                Log.d(TAG,"get day top");
                getDada(false); //adapter refreshData;
                day.setTextColor(getResources().getColor(R.color.golden));
                week.setTextColor(getResources().getColor(android.R.color.white));
                month.setTextColor(getResources().getColor(android.R.color.white));
            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.remove("time");
                params.add("time", "week");
                Log.d(TAG,"get week top");
                getDada(false); //adapter refreshData;
                week.setTextColor(getResources().getColor(R.color.golden));
                day.setTextColor(getResources().getColor(android.R.color.white));
                month.setTextColor(getResources().getColor(android.R.color.white));
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.remove("time");
                params.add("time","month");
                Log.d(TAG,"get month top");
                getDada(false); //adapter refreshData;
                month.setTextColor(getResources().getColor(R.color.golden));
                week.setTextColor(getResources().getColor(android.R.color.white));
                day.setTextColor(getResources().getColor(android.R.color.white));
            }
        });
    }

    public void getDada(final boolean flag) {
        String uid = (CommonData.user.getUid() == null)? "-1" : CommonData.user.getUid();
        Log.d(TAG,"TOP uid = "+uid);
        params.add("uid",uid);
        params.add("type","get_topten");
        HttpUtil.post(CommonData.groupURL,params, new TextHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "FF" + statusCode + headers);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish Top", "SS"+responseString);
                topicList = JsonParse.parseGroupArticles(JSON.parseObject(responseString));
//                for (Topic topic : topics) {
//                    topicList.add(topic);
//                }
                if (flag){
                    adapter = new TopicAdapter(getActivity(),topicList);
                    topicRv.setAdapter(adapter);
                } else {
                    adapter.refreshData(topicList);
                }
                //topicList.addAll(topics);
                //adapter.refreshData(topicList);
                adapter.setOnItemClickListener(new TopicAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View v, String data, int flag) {
                        //Snackbar.make(view, data, Snackbar.LENGTH_SHORT).show();
                        String[] s = data.split("`", 2);
                        Intent intent = new Intent();
                        Bundle localBundle = new Bundle();
                        switch (flag) {
                            //click on username;
                            case 2:
                                intent.setClass(getActivity(), UserInfoActivity.class);
                                localBundle.putString("user_id", s[0]);
                                localBundle.putString("title", s[1]);
                                break;
                            //click on group avatar
                            case 1:
                                intent.setClass(getActivity(), GroupActivity.class);
                                localBundle.putString("gid", s[0]);
                                localBundle.putString("title", s[1]);
                                break;
                            case 0:
                                intent.setClass(getActivity(), ArticleActivity.class);
                                localBundle.putString("g_a_id", s[0]);
                                localBundle.putString("title", s[1]);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            initData();
        } else {
            Log.d(TAG,"TopFragment is not visible");
        }
    }
}
