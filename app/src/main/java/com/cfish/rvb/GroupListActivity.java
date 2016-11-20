package com.cfish.rvb;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cfish.rvb.adapter.ItemClickListener;
import com.cfish.rvb.adapter.SectionedExpandableLayoutHelper;
import com.cfish.rvb.adapter.SimpleGroupAdapter;
import com.cfish.rvb.bean.Group;
import com.cfish.rvb.bean.Item;
import com.cfish.rvb.bean.Section;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.JsonParse;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dfish on 2016/11/19.
 */
public class GroupListActivity extends AppCompatActivity implements ItemClickListener {

    private String TAG = GroupListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RequestParams params;
    public static final String mainS = "本站类";
    public static final String serve = "服务类";
    public static final String trade = "交易类";
    public static final String other = "其他类";
    public static final String collage = "院系类";
    public static final String life = "生活类";
    public static final String club = "社团类";
    public static final String hometown = "同乡类";
    private ArrayList<Item> mainList = new ArrayList<>();
    private ArrayList<Item> serveList = new ArrayList<>();
    private ArrayList<Item> tradeList = new ArrayList<>();
    private ArrayList<Item> otherList = new ArrayList<>();
    private ArrayList<Item> collageList = new ArrayList<>();
    private ArrayList<Item> lifeList = new ArrayList<>();
    private ArrayList<Item> homeList = new ArrayList<>();
    private ArrayList<Item> clubList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        setTitle("小组");
        //setting the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this,
                mRecyclerView, this, 3);

        String uid = (CommonData.user.getUid() == null)? "-1":CommonData.user.getUid();
        Log.d(TAG, "PostActivity"+uid);
        params =  new RequestParams();
        params.add("uid",uid);
        //params.add("type","group_num");//{"status":1,"error":"","data":369}
        params.add("type","all_group_names");
        //params.add("type","group_names");

        HttpUtil.post(CommonData.groupURL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish","GroupFragment Fail"+responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish", "GroupFragment SS" + responseString);
                final List<Group> groups = JsonParse.parseGoupList(JSON.parseObject(responseString));
                String label;
                String groupName;
                int id;
                Item item;
                for (Group group : groups) {
                    label = group.getLabel();
                    groupName = group.getName();
                    id = Integer.valueOf(group.getGid());
                    item = new Item(groupName,id);
                    Log.d(TAG,groupName+id);
                    if (label.equals(mainS)) {
                        mainList.add(item);
                    } else if (label.equals(serve)) {
                        serveList.add(item);
                    } else if (label.equals(trade)) {
                        tradeList.add(item);
                    } else if (label.equals(other)) {
                        otherList.add(item);
                    } else if (label.equals(collage)) {
                        collageList.add(item);
                    } else if (label.equals(life)) {
                        lifeList.add(item);
                    } else if (label.equals(club)) {
                        clubList.add(item);
                    } else if (label.equals(hometown)) {
                        homeList.add(item);
                    }
                }
            }
        });

        sectionedExpandableLayoutHelper.addSection(mainS, mainList);

        sectionedExpandableLayoutHelper.addSection(serve, serveList);

        sectionedExpandableLayoutHelper.addSection(life, lifeList);

        sectionedExpandableLayoutHelper.addSection(trade, tradeList);

        sectionedExpandableLayoutHelper.addSection(club, clubList);

        sectionedExpandableLayoutHelper.addSection(collage, collageList);

        sectionedExpandableLayoutHelper.addSection(hometown, homeList);

        sectionedExpandableLayoutHelper.notifyDataSetChanged();

        //checking if adding single item works
        //sectionedExpandableLayoutHelper.addItem("Ice cream", new Item("Tutti frutti",5));
        //sectionedExpandableLayoutHelper.notifyDataSetChanged();
    }



    @Override
    public void itemClicked(Item item) {
        Toast.makeText(this, "Item: " + item.getName() + " clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        Bundle localBundle = new Bundle();
        intent.setClass(this, GroupActivity.class);
        localBundle.putString("gid", item.getId()+"");
        localBundle.putString("title", item.getName());
        intent.putExtras(localBundle);
        startActivity(intent);
    }

    @Override
    public void itemClicked(Section section) {
        //Toast.makeText(this, "Section: " + section.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }
}

