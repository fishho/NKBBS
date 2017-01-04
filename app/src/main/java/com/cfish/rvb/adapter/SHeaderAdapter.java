package com.cfish.rvb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cfish.rvb.R;
import com.cfish.rvb.bean.Article;
import com.cfish.rvb.bean.Author;
import com.cfish.rvb.bean.Reply;
import com.cfish.rvb.bean.SiteArticle;
import com.cfish.rvb.bean.SiteReply;
import com.cfish.rvb.util.ImageGetterUtil;
import com.cfish.rvb.view.RichText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SHeaderAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<SiteReply> replyList;
    private SiteArticle header;
    private Author author;
    private OnRecyclerViewItemClickListener onItemClickListener = null;
    private View view;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public SHeaderAdapter(Context mContext, SiteArticle header, Author author, List<SiteReply> replyList) {
        this.mContext = mContext;
        this.header = header;
        this.author = author;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.replyList = replyList;
    }
    public void refreshData(List<SiteReply> replyList) {
        this.replyList = replyList;
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            view = mLayoutInflater.inflate(R.layout.item_reply, parent, false);
            ItemHolder vh = new ItemHolder(view);
            //view.setOnClickListener(this);
            vh.replyContent.setOnClickListener(this);
            vh.actQuote.setOnClickListener(this);
            vh.actReply.setOnClickListener(this);
            vh.replyer.setOnClickListener(this);
            return vh;
        } else if (viewType == TYPE_HEADER) {
            view = mLayoutInflater.inflate(R.layout.webview_content,parent,false);
            HeaderHolder vh = new HeaderHolder(view);
            vh.dis.setOnClickListener(this);
            vh.fav.setOnClickListener(this);
            vh.recommend.setOnClickListener(this);
            vh.author.setOnClickListener(this);
            vh.nvReply.setOnClickListener(this);
            return vh;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure type is right");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            SiteReply reply = replyList.get(position-1);
            ItemHolder itemHolder = (ItemHolder)holder;
            ImageGetterUtil getter = new ImageGetterUtil(mContext);
//            if (itemHolder == null) {
//                return;
//            }
            Log.d("HeaderAdapter", "reply item");
            if (reply.getCreatime() == null) {
                itemHolder.actQuote.setVisibility(View.GONE);
                itemHolder.actReply.setVisibility(View.GONE);
                itemHolder.replyer.setVisibility(View.GONE);
                itemHolder.replyContent.setText(R.string.noreply);
                itemHolder.replyContent.setClickable(false);

            }else {
                itemHolder.replyTime.setText(reply.getCreatime().substring(5, 16));
                //itemHolder.replyContent.setText(Html.fromHtml(reply.getContent(), getter, null));
                itemHolder.replyContent.setRichText(reply.getContent());
                Log.d("Dfish", "reply content" + reply.getContent());
                itemHolder.actReply.setTag(reply.getS_a_id() + "`" + reply.getUser().name);
                itemHolder.replyer.setTag(reply.getUser_id() + "`" + reply.getUser().name);
                itemHolder.replyContent.setTag(reply.getS_r_id() + "`" + reply.getUser().name);
                //设置用户名，可能回复其他用户的情况出现
//                Log.d("aaaaaaaaaaaaaa", reply.getNoname());
//                if (reply.getNoname().equals("1")) {
//                    itemHolder.replyer.setText("匿名");
//                    itemHolder.replyer.setClickable(false);
//                } else {
//                    itemHolder.replyer.setText(reply.getUser().name);
//                }
                itemHolder.replyer.setText(reply.getUser().name);

                //有对应错乱的现象？
                String p_g_r_id = reply.getP_s_r_id();
                Log.d("Dfish","p_g_r_id"+p_g_r_id);
                if (p_g_r_id != null){
                    String theReplyer ="" ;
                    for (SiteReply item :replyList){

                        if( p_g_r_id.equals(item.getS_r_id())){
                            theReplyer = item.getUser().name;
//                            if(item.getNoname().equals("1")) {
//                                itemHolder.toRepley.setClickable(false);
//                                theReplyer= "匿名";
//                            }
                            break;
                        }
                    }
                    itemHolder.toRepley.setVisibility(View.VISIBLE);
                    itemHolder.toRepley.setText(" ▶ "+theReplyer);
                } else {
                    itemHolder.toRepley.setVisibility(View.GONE);
                }


                itemHolder.actQuote.setTag(reply.getS_r_id()+"`"+itemHolder.replyContent.getText());
            }
        } else if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder)holder;
//            headerHolder.webContent.getSettings().setBlockNetworkImage(false);
//            headerHolder.webContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            headerHolder.webContent.getSettings().setJavaScriptEnabled(true);
            headerHolder.webContent.getSettings().setDomStorageEnabled(true);
            headerHolder.webContent.setWebChromeClient(new WebChromeClient());
            headerHolder.webContent.setWebViewClient(new WebViewClient() {


                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    Log.d("Dfish", "article webcontent pageFinished");
                }
            });
            String preContent = "";
            try {
                InputStream input = mContext.getAssets().open("content.html");
                preContent = getStringFromInputStream(input);
                Log.d("Dfish", "preContent " + preContent);
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String contents = header.getContent();
            String title = header.getName();
            if (contents.contains("src=\"data")) {
                contents.replace("src=\"data", "src=\"http://bbs.nankai.edu.cn/data");
            }
            headerHolder.webContent.loadDataWithBaseURL("http://bbs.nankai.edu.cn/",
                    preContent.replace("#content#", contents)
                            .replace("#title#", title)
                            .replace("&lt;img", "<img")
                            .replace("&gt;", ">"), "text/html", "UTF-8", "");

            headerHolder.click.setText("点击" + header.getClick_num());
            headerHolder.rep.setText("回复" + header.getReply_num());
            headerHolder.recommend.setText("赞" + header.getRecommend_num());
//            headerHolder.dis.setText("踩" + header.getDeter_num());
            headerHolder.fav.setText("收藏"+header.getFav_num());
//            if(header.getAnonymous().equals("1")) {
//                headerHolder.author.setText("匿名");
//                headerHolder.author.setClickable(false);
//            } else {
                headerHolder.author.setText(author.name);
                headerHolder.author.setTag(author.uid+"`"+author.name);
//            }


        }

    }

    @Override
    public int getItemCount() {
        return replyList.size()+1;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            switch (v.getId()) {
                case R.id.act_quote:
                    onItemClickListener.onItemClick(v,v.getTag().toString(),1);
                    break;
                case R.id.replyer:
                    onItemClickListener.onItemClick(v,v.getTag().toString(),2);
                    break;
                case R.id.author:
                    onItemClickListener.onItemClick(v,v.getTag().toString(),3);
                    break;
                case R.id.nv_reply:
                    onItemClickListener.onItemClick(v,"`",4);
                    break;
                case R.id.act_rep:
                    if (v.getTag() != null) {
                        onItemClickListener.onItemClick(v, v.getTag().toString(), 5);
                    }
                    break;
                default:
//                    if (v.getTag() != null) {
//                        onItemClickListener.onItemClick(v, v.getTag().toString(), 5);
//                    }
                    break;
            }
        }
    }

    public void reFreshData(List<SiteReply> replyList) {
        this.replyList = replyList;
        this.notifyDataSetChanged();
    }
    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView actReply;
        TextView actQuote;
        TextView replyer;
        RichText replyContent;
        TextView replyTime;
        TextView toRepley;

        public ItemHolder(View itemView) {
            super(itemView);
            actReply = (TextView)itemView.findViewById(R.id.act_rep);
            actQuote =(TextView)itemView.findViewById(R.id.act_quote);
            replyer = (TextView)itemView.findViewById(R.id.replyer);
            replyContent = (RichText)itemView.findViewById(R.id.rep_content);
            replyContent.setRichText("");
            replyTime = (TextView)itemView.findViewById(R.id.rep_time);
            toRepley = (TextView)itemView.findViewById(R.id.toreplyer);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder{
        WebView webContent;
        TextView click,dis,rep,fav,recommend,author,nvReply;
        public HeaderHolder(View itemView) {
            super(itemView);
            this.webContent = (WebView)itemView.findViewById(R.id.webContent);
            this.click = (TextView)itemView.findViewById(R.id.click_cnt);
            this.dis = (TextView)itemView.findViewById(R.id.dis_cnt);
            this.rep = (TextView)itemView.findViewById(R.id.rep_cnt);
            this.fav = (TextView)itemView.findViewById(R.id.fav_cnt);
            this.recommend = (TextView)itemView.findViewById(R.id.recommend_cnt);
            this.author = (TextView)itemView.findViewById(R.id.author);
            this.nvReply = (TextView)itemView.findViewById(R.id.nv_reply);
        }
    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View v,String data,int flag);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    private static String getStringFromInputStream(InputStream a_is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

}
