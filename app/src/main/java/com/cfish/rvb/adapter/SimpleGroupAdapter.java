package com.cfish.rvb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cfish.rvb.R;
import com.cfish.rvb.bean.Group;

import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by dfish on 2016/11/20.
 */
public class SimpleGroupAdapter extends BaseAdapter {

    private List<Group> mGroups;
    private Context context;
    private LayoutInflater layoutInflater;

    public SimpleGroupAdapter(Context context, List<Group> groups) {
        this.context = context;
        this.mGroups = groups;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mGroups.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mGroups.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_simple,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.behavior);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mGroups.get(position).getName());
        viewHolder.textView.setTag(mGroups.get(position).getGid());
        return convertView;
    }

    public class ViewHolder {
        public TextView textView;
        public  View viewDiv;
    }
}
