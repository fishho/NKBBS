package com.cfish.rvb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.cfish.rvb.R;
import com.cfish.rvb.SiteArticleActivity;
import com.cfish.rvb.adapter.NewsAdapter;
import com.cfish.rvb.bean.SiteDetails;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.JsonParse;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SiteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SiteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "SiteFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RequestParams params, paramsTest;
    private NewsAdapter adapter;
    private List<Map<String,String>> newsList;
    private RecyclerView siteArticleRv;
    private View view;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SiteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SiteFragment newInstance(String param1, String param2) {
        SiteFragment fragment = new SiteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SiteFragment() {
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
        view = inflater.inflate(R.layout.fragment_site, container, false);
        initView();
        //initData();
        initAction();
        view.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
        return view;
    }

    public void initView() {
        siteArticleRv = (RecyclerView)view.findViewById(R.id.site_article_rv);
        siteArticleRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initData() {
        newsList = new ArrayList<>();
        adapter = new NewsAdapter(getActivity(),newsList);
        siteArticleRv.setAdapter(adapter);
        getDada();
    }

    public void initAction() {

    }

    public void getDada() {
        String uid = (CommonData.user.getUid() == null)? "-1" : CommonData.user.getUid();
        Log.d(TAG, "SiteFragment uid = " + uid);
        params = new RequestParams();
        params.add("uid",uid);
        params.add("type","get_site_articles");
        HttpUtil.post(CommonData.siteURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG, "SiteFragment SS " + response);
                //List<Topic> topics = JsonParse.parseGroupArticles(JSON.parseObject(responseString));
//                for (Topic topic : topics) {
//                    topicList.add(topic);
//                }
//                topicList.addAll(topics);
//                adapter.refreshData(topicList);
                JSONArray data =null;
                Map<String,String> map = new HashMap<>();
                try {
                    data = response.getJSONArray("data");
                    for (int i=0;i<data.length();i++) {
                        map = new HashMap();
                        map.put("sid",data.getJSONObject(i).getString("sid"));
                        map.put("s_a_id",data.getJSONObject(i).getString("s_a_id"));
                        map.put("author",data.getJSONObject(i).getString("author"));
                        map.put("creatime",data.getJSONObject(i).getString("creatime"));
                        map.put("reply",data.getJSONObject(i).getString("reply_num"));
                        map.put("name",data.getJSONObject(i).getString("name"));
                        map.put("signature",data.getJSONObject(i).getString("signature"));
                        newsList.add(map);
                    }
                } catch (JSONException e) {
                    Log.e(TAG,e.getMessage(),e);
                }


                adapter.refreshData(newsList);
                adapter.setOnItemClickListener(new NewsAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View v, String data) {
                        Intent intent = new Intent(getActivity(), SiteArticleActivity.class);
                        intent.putExtra("s_a_id", data);
                        getActivity().startActivity(intent);
//                        paramsTest = new RequestParams();
//                        paramsTest.add("uid","-1");
//                        paramsTest.add("reply_order", CommonData.user.getReply_order());
//                        paramsTest.add("type", "get_site_article");
//                        paramsTest.add("s_a_id", data);
//                        Log.d(TAG, "onItemClick: "+data);
//                        HttpUtil.post(CommonData.siteURL, paramsTest, new TextHttpResponseHandler() {
//                            @Override
//                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                                Log.d(TAG, "onFailure: "+responseString);
//                            }
//
//                            @Override
//                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                                  //  Log.d("DDDDD", responseString);
//                                if (responseString.length() > 4000){
//                                    Log.d("Dfish","Article part1"+responseString.length()+responseString.substring(0,4000));
//                                    Log.d("Dfish","Article part2"+responseString.substring(4000));
//
//                                    if (responseString.startsWith("<")) {
//                                        responseString = responseString.substring(responseString.indexOf("{"),responseString.length());
//                                    }
//                                    com.alibaba.fastjson.JSONObject resp = com.alibaba.fastjson.JSON.parseObject(responseString);
//                                    if (resp.getString("status").equals("0")) {
//                                        Snackbar.make(view, "找不见了", Snackbar.LENGTH_SHORT).show();
//                                    } else {
//                                        SiteDetails details = JsonParse.parseSiteDetails(resp);
//                                        Log.d("Dddd",details.getArticle().getContent());
//                                    }
//                                }
//                            }
//                        });
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d(TAG, " siteFragment FF" + errorResponse);
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            initData();
        } else {
            Log.d(TAG,"SiteFragment is not visible");
        }
    }
}
