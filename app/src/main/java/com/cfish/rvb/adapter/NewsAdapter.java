package com.cfish.rvb.adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfish.rvb.R;
import com.cfish.rvb.util.CommonData;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by GKX100217 on 2016/2/25.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context mContext;
    private List<Map<String,String>> mData;
    private LayoutInflater mLayoutInflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public NewsAdapter(Context mContext, List<Map<String, String>> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }
    public void refreshData (List<Map<String,String>> dataList){
        this.mData = dataList;
        this.notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_news,parent,false);
        ItemHolder vh = new ItemHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    public class  ItemHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView time;
        TextView reply;
        TextView signature;
        TextView title;
        SimpleDraweeView head;

        public ItemHolder(View itemView) {
            super(itemView);
            Typeface typeFace =Typeface.createFromAsset(mContext.getAssets(),"fonts/shusong.ttf");
            head = (SimpleDraweeView)itemView.findViewById(R.id.head);
            author = (TextView)itemView.findViewById(R.id.author);
            reply = (TextView)itemView.findViewById(R.id.reply);
            time = (TextView)itemView.findViewById(R.id.time);
            signature = (TextView)itemView.findViewById(R.id.signature);
            title = (TextView)itemView.findViewById(R.id.title);
            signature.setTypeface(typeFace);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Map<String,String> map = mData.get(position);
        ItemHolder itemholder = (ItemHolder) holder;
        if (map == null) {
            return ;
        }
        //itemholder.head.setImageURI(Uri.parse(CommonData.siteCover+map.get("sid")+".jpg"));
        Log.d("Dfish", map.get("sid"));
        itemholder.title.setText(map.get("name"));
        itemholder.reply.setText(map.get("reply"));
        itemholder.author.setText(map.get("author"));
        itemholder.time.setText(map.get("creatime"));
        String[] s = map.get("creatime").split("-",3);
        itemholder.head.setImageURI(Uri.parse(CommonData.siteAvatar+s[0]+s[1]+"/"+map.get("s_a_id")+".jpg"));
        itemholder.signature.setText(map.get("signature"));
        itemholder.itemView.setTag(map.get("s_a_id"));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View v,String data);
    }
    @Override

    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
