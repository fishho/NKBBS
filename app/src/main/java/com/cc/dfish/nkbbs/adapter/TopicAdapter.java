package com.cc.dfish.nkbbs.adapter;

/**
 * Created by dfish on 2016/2/3.
 */
import java.util.List;

import com.cc.dfish.nkbbs.R;
import com.cc.dfish.nkbbs.bean.Topic;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicAdapter extends Adapter<ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<Topic> mTopics;
    private LayoutInflater mLayoutInflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public TopicAdapter(Context mContext, List<Topic> mTopics) {
        this.mContext = mContext;
        this.mTopics = mTopics;
        mLayoutInflater =  LayoutInflater.from(mContext);
    }

    public void refreshData(List<Topic> topics) {
        this.mTopics = topics;
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return mTopics.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        Topic mTopic = mTopics.get(position);
        ItemHolder itemholder = (ItemHolder) vh;
        if (mTopic == null) {
            return ;
        }
        itemholder.author.setText(mTopic.getAuthor());
        itemholder.title.setText(mTopic.getName());
        itemholder.reply.setText("回复:"+mTopic.getReply_num());
        itemholder.time.setText(mTopic.getCreatime().subSequence(5, 16));
        itemholder.aid.setText(mTopic.getG_a_id());
        itemholder.itemView.setTag(mTopic.getG_a_id());
        if(mTopic.getGroupname().equals("南开之声") ) {
            itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.nk));
        }else if(mTopic.getGroupname().equals("Water (我是水牛我怕谁)")){
            itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.water));
        }else if(mTopic.getGroupname().equals("数码")){
            itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.digi));
        }else if(mTopic.getGroupname().equals("二手交换（官方）")){
            itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.buy));
        }else if(mTopic.getGroupname().equals("树洞")){
            itemholder.group.setBackgroundColor(mContext.getResources().getColor(android.R.color.black));
        }else if(mTopic.getGroupname().equals("鹊桥")){
            itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
        }else {
            itemholder.group.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            Log.d("color",mTopic.getGroupname());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        // TODO Auto-generated method stub
        View view = mLayoutInflater.inflate(R.layout.item_topic,null);
        ItemHolder vh = new ItemHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    public class ItemHolder extends ViewHolder{
        TextView author,title,reply,time,aid;
        ImageView group;
        SimpleDraweeView head;
        public ItemHolder(View itemV) {
            super(itemV);
            Typeface typeFace =Typeface.createFromAsset(mContext.getAssets(),"fonts/myfont.ttf");
            //head = (SimpleDraweeView)itemV.findViewById(R.id.head);
            author = (TextView)itemV.findViewById(R.id.author);
            title = (TextView)itemV.findViewById(R.id.title);
            group = (ImageView)itemV.findViewById(R.id.group);
            reply = (TextView)itemV.findViewById(R.id.reply);
            time = (TextView)itemV.findViewById(R.id.time);
            aid = (TextView)itemV.findViewById(R.id.aid);
            author.setTypeface(typeFace);
            title.setTypeface(typeFace);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View v,String data);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (String)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}