package com.cfish.rvb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfish.rvb.R;
import com.cfish.rvb.bean.Letter;
import com.cfish.rvb.util.CommonData;

import java.util.List;

/**
 * Created by GKX100217 on 2016/3/19.
 */
public class MessageAdapter extends RecyclerView.Adapter {
    private List<Letter> letterList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private View view;
    private int length;

    public MessageAdapter(Context context, List<Letter> letterList) {
        this.context = context;
        this.letterList = letterList;
        mLayoutInflater = LayoutInflater.from(context);
    }
    public void refresh(List list){
        this.letterList = list;
        this.notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = mLayoutInflater.inflate(R.layout.item_message,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Letter letter = letterList.get(length-position-1);
        MyHolder vh = (MyHolder)holder;
        //if ()
        if (letter.getSendid().equals(CommonData.user.getUid())){
            vh.messageR.setText(Html.fromHtml(letter.getContent()));
            vh.messageL.setVisibility(View.GONE);
        } else {
            vh.messageL.setText(Html.fromHtml(letter.getContent()));
            vh.messageR.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        length = letterList.size();
        return length;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView messageL,messageR;
        public MyHolder(View itemView) {
            super(itemView);
            messageL = (TextView)itemView.findViewById(R.id.message_Left);
            messageR = (TextView)itemView.findViewById(R.id.message_right);
        }
    }
}
