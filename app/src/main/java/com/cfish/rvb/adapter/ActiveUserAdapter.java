package com.cfish.rvb.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfish.rvb.OnRecyclerViewItemClickListener;
import com.cfish.rvb.R;
import com.cfish.rvb.bean.ActiveUser;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by GKX100217 on 2016/4/1.
 */
public class ActiveUserAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ActiveUser> userList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public ActiveUserAdapter(Context mContext, List<ActiveUser> userList) {
        this.mContext = mContext;
        this.userList = userList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_user, parent, false);
        ItemHolder vh = new ItemHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder)holder;
        ActiveUser user = userList.get(position);
        itemHolder.active.setText(user.getActive());
        itemHolder.userName.setText(user.getName());
        itemHolder.userAvatar.setImageURI(Uri.parse(user.getImgSrc()));
        itemHolder.itemView.setTag(user.getGid()+"`"+user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView userAvatar ;
        TextView active;
        TextView userName;
        public ItemHolder(View itemView) {
            super(itemView);
            userAvatar = (SimpleDraweeView)itemView.findViewById(R.id.user_avatar);
            active = (TextView)itemView.findViewById(R.id.active);
            userName = (TextView)itemView.findViewById(R.id.userName);
        }
    }
    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener != null) {
            onRecyclerViewItemClickListener.onItemClick(v,v.getTag().toString());
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener mListener) {
        onRecyclerViewItemClickListener = mListener;
    }
}
