package com.cfish.rvb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfish.rvb.OnRecyclerViewItemClickListener;
import com.cfish.rvb.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by GKX100217 on 2016/6/22.
 */
public class ColorAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context mContext;
    private int[] mColors;
    private LayoutInflater mLayoutInflater;

    public ColorAdapter(Context context, int[] colors) {
        mContext = context;
        mColors = colors;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_group,null);
        ItemHolder vh = new ItemHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.colorView.setBackgroundColor(mColors[position]);
        String[] themeTitles =  mContext.getResources().getStringArray(R.array.theme_titles);
        itemHolder.colorName.setText(themeTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mColors.length;
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick(v,"");
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView colorView;
        TextView colorName;
        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
