package com.cc.dfish.nkbbs.adapter;

/**
 * Created by dfish on 2016/2/3.
 */
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
//import android.support.v7.widget.CardView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cc.dfish.nkbbs.R;

public class ReplyAdapter extends SimpleAdapter {
    private Context mContext;
    private int mResource;
    private List<? extends Map<String,?>> mData;
    public ReplyAdapter(Context context, List<? extends Map<String, ?>> data,
                        int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mContext = context;
        this.mResource = resource;
        this.mData = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView =LayoutInflater.from(mContext).inflate(R.layout.item_reply, null);
        }

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.replyer =(TextView)convertView.findViewById(R.id.replyer);
        viewHolder.rep_content = (TextView)convertView.findViewById(R.id.rep_content);
        viewHolder.rep_time = (TextView)convertView.findViewById(R.id.rep_time);
        viewHolder.replyer.setText(mData.get(position).get("replyer").toString());
        viewHolder.rep_content.setText((Spanned)mData.get(position).get("rep_content"));
        viewHolder.rep_time.setText(mData.get(position).get("rep_time").toString());
        return convertView;
    }

    class ViewHolder {
        TextView act_rep;
        TextView act_quote;
        TextView replyer;
        TextView rep_content;
        TextView rep_time;
    }

}
