package com.cfish.rvb.adapter;

/**
 * Created by GKX100217 on 2016/2/26.
 */
import java.util.List;
import java.util.Map;

import com.cfish.rvb.bean.Group;
import com.cfish.rvb.util.CommonData;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cfish.rvb.R;
public class GroupAdapter extends Adapter<ViewHolder> implements OnClickListener {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Group> mGroups;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public GroupAdapter(Context mContext, List<Group> mGroups) {
        this.mContext = mContext;
        this.mGroups = mGroups;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void refreshData(List<Group> mGroups) {
        this.mGroups = mGroups;
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v, (String)v.getTag());
        }
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return mGroups.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        // TODO Auto-generated method stub
        String url = CommonData.groupCover+mGroups.get(position).getGid()+".jpg";
        ItemHolder itemholder = (ItemHolder) vh;
        itemholder.draweeView.setImageURI(Uri.parse(url));
        Log.d("url",url);
        itemholder.itemView.setTag(mGroups.get(position).getGid());
        itemholder.name.setText(mGroups.get(position).getName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup v, int arg1) {
        // TODO Auto-generated method stub
        View view =  mLayoutInflater.inflate(R.layout.item_group, null);
        ItemHolder vh = new ItemHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    public class ItemHolder extends ViewHolder{

        SimpleDraweeView draweeView;
        TextView name ;
        public ItemHolder(View v) {
            super(v);
            // TODO Auto-generated constructor stub
            //Typeface typeFace =Typeface.createFromAsset(mContext.getAssets(),"fonts/shusong.ttf");
            draweeView = (SimpleDraweeView)v.findViewById(R.id.icon);
            name = (TextView)v.findViewById(R.id.group_name);
           // name.setTypeface(typeFace);
        }

    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View v,String data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
