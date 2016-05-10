package com.cfish.rvb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfish.rvb.R;
import com.cfish.rvb.bean.Topic;

import java.util.List;

/**
 * Created by GKX100217 on 2016/3/16.
 */
public class SimpleTopicAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Topic> topicList;
    private View view;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;
    public SimpleTopicAdapter(Context context,List topicList) {
        this.mContext = context;
        this.topicList = topicList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void refreshData(List<Topic> topicList) {
        this.topicList = topicList;
        this.notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = mLayoutInflater.inflate(R.layout.item_simple,parent,false);
        ItemHolder itemHolder = new ItemHolder(view);
        view.setOnClickListener(this);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Topic mTopic = topicList.get(position);
        ItemHolder itemHolder = (ItemHolder)holder;
        if (mTopic == null) {
            return;
        }
        itemHolder.topicTitle.setText(mTopic.getName());
        itemHolder.itemView.setTag(mTopic.getG_a_id()+"`"+mTopic.getName());

    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewItemClickListener != null) {
            mOnRecyclerViewItemClickListener.onItemClick(v, (String)v.getTag());
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView topicTitle;
        public ItemHolder(View itemView) {
            super(itemView);
            topicTitle = (TextView)itemView.findViewById(R.id.behavior);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View v,String data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnRecyclerViewItemClickListener = listener;
    }

}
