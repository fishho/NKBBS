package com.cfish.rvb.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfish.rvb.R;
import com.cfish.rvb.bean.Behavior;
import com.cfish.rvb.bean.UserInfo;

import java.util.List;

/**
 * Created by GKX100217 on 2016/3/4.
 */
public class UserInfoAdapter extends RecyclerView.Adapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Behavior> behaviorList;
    private UserInfo header;
    private View view;
    private String string = "";
    private String does = "";
    private String time = "";
    private String name = "";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public UserInfoAdapter(Context context,List<Behavior> behaviorList, UserInfo header) {
        this.mContext = context;
        this.behaviorList = behaviorList;
        this.header = header;
        this.mLayoutInflater = LayoutInflater.from(context);
    }
    public UserInfoAdapter(Context context,List<Behavior> behaviorList) {
        this.mContext = context;
        this.behaviorList = behaviorList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position ==0) {
//            return TYPE_HEADER;
//        }
//        return TYPE_ITEM;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = mLayoutInflater.inflate(R.layout.item_simple,null,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder)holder;
        if (itemHolder == null) {
            return;
        }
        Behavior behavior =behaviorList.get(position);
        name= behavior.getName();
        time = behavior.getTime();
        int status = Integer.parseInt(behavior.getStatus());
        switch (status){
            case 1:
                string = mContext.getString(R.string.actionConcern);
                does = String.format(string, time, name);
                //itemHolder.behavior.setBackgroundResource(R.color.pink);
                break;
            case 2:
                string = mContext.getString(R.string.concernSite);
                does = String.format(string, time, name);
                //itemHolder.behavior.setBackgroundResource(R.color.pink);
                break;
            case 3:
                string = mContext.getString(R.string.sitePost);
                does = String.format(string, time, name, behavior.getArticle_name());
                //itemHolder.behavior.setBackgroundResource(R.color.nk);
                break;
            case 4:
                string = mContext.getString(R.string.siteReply);
                does = String.format(string, time, name, behavior.getArticle_name());
               // itemHolder.behavior.setBackgroundResource(R.color.buy);
                break;
            case 5:
                string = mContext.getString(R.string.siteRecommend);
                does = String.format(string, time, name, behavior.getArticle_name());
                //itemHolder.behavior.setBackgroundResource(R.color.red);
                break;
            case 6:
                string = mContext.getString(R.string.concernGroup);
                does = String.format(string, time, name);
               // itemHolder.behavior.setBackgroundResource(R.color.pink);
                break;
            case 7:
                string = mContext.getString(R.string.becomeGroup);
                does = String.format(string, time, name);
                //itemHolder.behavior.setBackgroundResource(R.color.digi);
                break;
            case 8:
                string = mContext.getString(R.string.groupPost);
                does = String.format(string, time, name, behavior.getArticle_name());
                //itemHolder.behavior.setBackgroundResource(R.color.nk);
                break;
            case 9:
                string = mContext.getString(R.string.groupReply);
                does = String.format(string, time, name, behavior.getArticle_name());
                //itemHolder.behavior.setBackgroundResource(R.color.buy);
                break;
            case 10:
                string = mContext.getString(R.string.groupRecommend);
                does = String.format(string, time, name, behavior.getArticle_name());
                //itemHolder.behavior.setBackgroundResource(R.color.red);
                break;
            case 11:
                string = mContext.getString(R.string.siteFav);
                does = String.format(string, time, name, behavior.getArticle_name());
                //itemHolder.behavior.setBackgroundResource(R.color.primary_light);
                break;
            case 12:
                string = mContext.getString(R.string.groupFav);
                does = String.format(string, time, name, behavior.getArticle_name());
                //itemHolder.behavior.setBackgroundResource(R.color.primary_light);
                break;
            default:
                break;
        }
        //Behavior behaviors = behaviorList.get(position);
        itemHolder.behavior.setText(does);
    }

    @Override
    public int getItemCount() {
        return behaviorList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView behavior;
        public ItemHolder(View itemView) {
            super(itemView);
            Typeface typeFace =Typeface.createFromAsset(mContext.getAssets(),"fonts/myfont.ttf");
            behavior = (TextView)itemView.findViewById(R.id.behavior);
            behavior.setTypeface(typeFace);
        }
    }

//    public class HeadHolder extends RecyclerView.ViewHolder {
//        TextView
//
//        public HeadHolder(View itemView) {
//            super(itemView);
//            cardView = (CardView)itemView.findViewById()
//        }
//    }
}

