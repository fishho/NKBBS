package com.cfish.rvb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfish.rvb.OnRecyclerViewItemClickListener;
import com.cfish.rvb.R;
import com.cfish.rvb.bean.Message;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by GKX100217 on 2016/4/14.
 */
public class NotificationAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<Message.News> newsList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private OnRecyclerViewItemClickListener mItemClickListener;
    public NotificationAdapter(Context mContext, List newsList) {
        this.newsList = newsList;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_simple,parent,false);
        ItemHolder itemHolder = new ItemHolder(view);
        itemHolder.itemView.setOnClickListener(this);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder vh = (ItemHolder)holder;
        if (vh == null) {
            return;
        } else {
            Message.News news = newsList.get(position);
            vh.itemView.setTag(news.getNum()+"`"+news.getName());
            StringBuilder stringBuilder = new StringBuilder();
            int status = Integer.parseInt(news.getStatus());
            switch (status) {
                // many types of message
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    stringBuilder.append(news.getName()).append("有 ").append(news.getCount())
                            .append("个新回复");
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    break;
                default:
                    break;

            }
            vh.name.setText(stringBuilder.toString());


        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v,v.getTag().toString());
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView name ;
        public ItemHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.behavior);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
