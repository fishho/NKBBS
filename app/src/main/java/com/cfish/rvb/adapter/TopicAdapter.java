package com.cfish.rvb.adapter;

import java.util.List;

import com.cfish.rvb.ArticleActivity;
import com.cfish.rvb.GroupActivity;
import com.cfish.rvb.R;
import com.cfish.rvb.UserInfoActivity;
import com.cfish.rvb.bean.Topic;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopicAdapter extends Adapter<ViewHolder> implements View.OnClickListener {
	private Context mContext;
	private List<Topic> mTopics;
	private LayoutInflater mLayoutInflater;
    private View view;
	private OnRecyclerViewItemClickListener mOnItemClickListener = null;
	public TopicAdapter(Context mContext, List<Topic> mTopics) {
		this.mContext = mContext;
		this.mTopics = mTopics;
		mLayoutInflater =  LayoutInflater.from(mContext);
	}	
	
	public void refreshData(List<Topic> topics) {
		this.mTopics = topics;
		this.notifyDataSetChanged();
	}
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mTopics.size();
	}
	
	@Override
	public void onBindViewHolder(ViewHolder vh, int position) {
		Topic mTopic = mTopics.get(position);
		ItemHolder itemholder = (ItemHolder) vh;
		if (mTopic == null) {
			return ;
		}
        String gid = mTopic.getGid();
		itemholder.head.setImageURI(Uri.parse("http://bbs.nankai.edu.cn/data/uploads/cover/group/" + gid + ".jpg"));
		Log.d("Dfish", "TopicAdapter group id is"+mTopic.getGid());
//		String author = mTopic.getAnonymous().equals("1") ? "":mTopic.getAuthor();
//		itemholder.author.setText(author);
		itemholder.author.setText(mTopic.getAuthor());
		itemholder.title.setText(mTopic.getName());
		if (mTopic.getTop().equals("1")){
			Log.d("Dfish","TopicAdapter ,is top ,set title to the gold color");
			itemholder.title.setTextColor(mContext.getResources().getColor(R.color.golden));
		}
		itemholder.reply.setText(mTopic.getReply_num());
        itemholder.click.setText(mTopic.getClick_num());
		itemholder.time.setText(mTopic.getCreatime().subSequence(5, 16));
		itemholder.recommend.setText(mTopic.getRecommend_num());
		itemholder.fav.setText(mTopic.getFav_num());
		itemholder.deter.setText(mTopic.getDeter_num());
		itemholder.fav.setText(mTopic.getFav_num());
		//itemholder.aid.setText(mTopic.getG_a_id());
		itemholder.itemView.setTag(R.id.article,mTopic.getG_a_id()+"`"+mTopic.getName()+"`"+mTopic.getGid());
		itemholder.head.setTag(R.id.ggroup,mTopic.getGid()+"`"+mTopic.getGroupname());
		itemholder.author.setTag(R.id.user,mTopic.getUser_id()+"`"+mTopic.getAuthor());
        switch (Integer.valueOf(gid)){
            case 1:
                //二手交换官方
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.buy));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.truebuy));
                break;
            case 5:
                //鹊桥
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.truepink));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
                break;
            case 8:
                //数码
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.buy));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.digi));
                break;
            case 10:
                //sos
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.truered));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.icons));
                break;
            case 19:
                //招聘
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.truered));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                break;
            case 22:
                //南开之声
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.accent));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.nk));
                break;
            case 27:
                //water
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.truewater));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.water));
                break;
            case 46:
                //新兵营
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.trueblack));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.buy));
                break;
            case 86:
                //树洞
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.trueblack));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                break;
            case 101:
                //租房
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.common));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.buy));
                break;
            case 159:
                //二手书籍交换
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.book));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.icons));
                break;
            default:
                itemholder.group.setBackgroundColor(mContext.getResources().getColor(R.color.common));
                itemholder.foot.setBackgroundColor(mContext.getResources().getColor(R.color.common));
                Log.d("color",mTopic.getGroupname());
        }
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		// TODO Auto-generated method stub
		view = mLayoutInflater.inflate(R.layout.item_topic,viewGroup,false);
		ItemHolder vh = new ItemHolder(view);
		view.setOnClickListener(this);
        vh.author.setOnClickListener(this);
        vh.head.setOnClickListener(this);

		return vh;
	}

	public class ItemHolder extends ViewHolder{
		TextView author,title,reply,click,time,recommend,deter,fav;
		ImageView group;
		SimpleDraweeView head;
		LinearLayout foot;
		public ItemHolder(View itemV) {
			super(itemV);
			//Typeface typeFace =Typeface.createFromAsset(mContext.getAssets(),"fonts/myfont.ttf");
			head = (SimpleDraweeView)itemV.findViewById(R.id.head);
			author = (TextView)itemV.findViewById(R.id.author);
			title = (TextView)itemV.findViewById(R.id.title);
			group = (ImageView)itemV.findViewById(R.id.group);
            click = (TextView)itemV.findViewById(R.id.click);
			reply = (TextView)itemV.findViewById(R.id.reply);
            //reply.setCompoundDrawables(mContext.getResources().getDrawable(R.mipmap.ic_more), null, null, null);
            //reply.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_more,0,0,0);
			time = (TextView)itemV.findViewById(R.id.time);
			//aid = (TextView)itemV.findViewById(R.id.aid);
			//author.setTypeface(typeFace);
			//title.setTypeface(typeFace);
			recommend = (TextView)itemV.findViewById(R.id.recommend_num);
			fav = (TextView)itemV.findViewById(R.id.fav_num);
			deter = (TextView)itemV.findViewById(R.id.deter_num);
			foot = (LinearLayout)itemV.findViewById(R.id.foot);
		}		
	}
	
	public static interface OnRecyclerViewItemClickListener {
		void onItemClick(View v,String data,int flag);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//Intent intent = new Intent();
		if(mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.author:
                    mOnItemClickListener.onItemClick(v, (String) v.getTag(R.id.user),2);
					//intent.setClass(mContext, UserInfoActivity.class);
                    break;
                case R.id.head:
                    mOnItemClickListener.onItemClick(v, (String)v.getTag(R.id.ggroup),1);
                    Log.d("Dfish", (String) v.getTag(R.id.ggroup)+"click on head");
					//intent.setClass(mContext, GroupActivity.class);

                    break;
                default:
                    mOnItemClickListener.onItemClick(v, (String)v.getTag(R.id.article),0);
					//intent.setClass(mContext, ArticleActivity.class);
					//intent.putExtra("g_a_id",)
                    break;
            }

		}
	}
	
	public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}
}
