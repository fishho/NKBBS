package com.cfish.rvb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.cfish.rvb.GroupActivity;
import com.cfish.rvb.R;
import com.cfish.rvb.adapter.GroupAdapter;
import com.cfish.rvb.bean.Group;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.JsonParse;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RequestParams params;
    private View view;
    private RecyclerView groupRv;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GroupFragment() {
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
        View view = inflater.inflate(R.layout.fragment_group,container,false);
        // Inflate the layout for this fragment
        //initData();
        groupRv = (RecyclerView)view.findViewById(R.id.group_rv);
        groupRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        return view;
    }

    public void initData() {
        getData();
    }

    public void getData() {
        String uid = (CommonData.user.getUid() == null)? "-1":CommonData.user.getUid();
        Log.d("Dfish","groupFragment uid "+uid);
        params =  new RequestParams();
        params.add("uid",uid);
        //params.add("type","group_num");
        //params.add("type","all_group_names");
        params.add("type","active_groups");
//        HttpUtil.post(CommonData.groupURL,params,new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.d("Dfish Group SS", response.toString());
//                List<Group> groups = res
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                Log.d("Dfish Group FF",errorResponse.toString());
//            }
//        });
        HttpUtil.post(CommonData.groupURL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish","GroupFragment Fail"+responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish", "GroupFragment SS" + responseString);
                List<Group> groups =JsonParse.parseGoupList(JSON.parseObject(responseString));
                Collections.sort(groups);
//                for (Group group : groups) {
//                    Log.d("Dfish group",group.getLabel()+"1");
//                }
                GroupAdapter adapter = new GroupAdapter(getActivity(),groups);
                groupRv.setAdapter(adapter);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //initData();
            jsoupHot();

        } else {
            Log.d("Dfish","GroupFragment is not visible");
        }
    }

    private void jsoupHot() {
        HttpUtil.get("http://bbs.nankai.edu.cn/index.php/main", new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish","group jsoup FF"+statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish","group jsoup SS"+statusCode);
                List<Group> groupList = new ArrayList<>();
                Document doc = Jsoup.parse(responseString);
                Elements eactiveGroups = doc.select("#active-groups > ul > li a.text");
                //http://bbs.nankai.edu.cn/group/view/1
                for (Element element : eactiveGroups) {
                    //String img_src = element.getElementsByClass("lazy").first().attr("data-src");
                    String img_src = element.attr("href").substring(36);
                    Group group = new Group();
                    group.setGid(img_src);
                    group.setName(element.text());
                    Log.d("Dfish", "group Fragment jsoup imgSrc" + img_src);
                    groupList.add(group);
                }
                GroupAdapter adapter = new GroupAdapter(getActivity(),groupList);
                groupRv.setAdapter(adapter);

                adapter.setOnItemClickListener(new GroupAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View v, String data) {
                        String[] s=data.split("`");
                        Intent toGroup = new Intent(getActivity(), GroupActivity.class);
                        toGroup.putExtra("gid", s[0]);
                        toGroup.putExtra("title",s[1]);
                        getActivity().startActivity(toGroup);
                    }
                });

            }
        });
    }
}
