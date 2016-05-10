package com.cfish.rvb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cfish.rvb.adapter.ActionAdapter;

import java.util.List;

public class MsgListActivity extends AppCompatActivity {
    private RecyclerView msgListRv;
    private ActionAdapter adapter;
    private List<String> msgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);
        msgListRv = (RecyclerView)findViewById(R.id.msg_list);
        msgListRv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ActionAdapter(this,msgList);
        msgListRv.setAdapter(adapter);
    }
}
