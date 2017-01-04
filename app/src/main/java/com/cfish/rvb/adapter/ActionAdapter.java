package com.cfish.rvb.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfish.rvb.OnRecyclerViewItemClickListener;
import com.cfish.rvb.R;

import java.util.List;
import java.util.Map;

/**
 * Created by GKX100217 on 2016/3/11.
 */
public class ActionAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private LayoutInflater mLayouInflater;
    private View view;
    private List actionList;
    private Context mContext;
    private OnRecyclerViewItemClickListener mListener;

    public ActionAdapter(Context mContext, List actionList) {
        this.mContext = mContext;
        this.actionList = actionList;
        mLayouInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view  = mLayouInflater.inflate(R.layout.item_simple,parent,false);
        view.setOnClickListener(this);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        if (itemHolder == null) {
            return;
        }
        Map<String, String> action = (Map)actionList.get(position);
        Log.d("AAAA",action.get("link"));
        itemHolder.behavior.setOnClickListener(this);
        itemHolder.behavior.setTag(action.get("link"));
        itemHolder.behavior.setText(action.get("title"));
    }

    @Override
    public int getItemCount() {
        return actionList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView behavior;
        public ItemHolder(View itemView) {
            super(itemView);
            behavior = (TextView)itemView.findViewById(R.id.behavior);
            Typeface typeFace =Typeface.createFromAsset(mContext.getAssets(),"fonts/shusong.ttf");
            behavior.setTypeface(typeFace);
        }
    }


    @Override
    public void onClick(View v) {
        if(mListener != null) {
            mListener.onItemClick(v,(String)v.getTag());
            Log.d("hhhhh","TAG"+v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }
}
