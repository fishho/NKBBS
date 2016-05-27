package com.cfish.rvb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cfish.rvb.adapter.HeaderAdapter;
import com.cfish.rvb.bean.Article;
import com.cfish.rvb.bean.Author;
import com.cfish.rvb.bean.Details;
import com.cfish.rvb.bean.Reply;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.JsonParse;
import com.cfish.rvb.util.Uri2Path;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {
    private String g_a_id,formatImg,uid;
    private RecyclerView replyRv;
    private RequestParams params,paramsUp,paramsPost;
    private HeaderAdapter hAdapter;
    private EditText myReply;
    private ImageButton picture;
    private ImageButton post;
    private ImageButton noName;
    private ProgressBar progressBar,uploadProgress;
    private String p_g_r_id = "";
    private String g_id = "";
    private String isNoName = "0"; //是否匿名评论
    private boolean noNameChecked = false; //匿名是否勾选
    private boolean canAnonymous = false; //是否可以匿名
    private boolean canReply = false; //是否可以评论
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        setTitle(getIntent().getExtras().getString("title"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        replyRv = (RecyclerView) findViewById(R.id.reply_rv);
        progressBar = (ProgressBar) findViewById(R.id.progress_article);
        uploadProgress = (ProgressBar) findViewById(R.id.upload_progress);
        this.g_a_id = getIntent().getExtras().getString("g_a_id");
        this.g_id = getIntent().getExtras().getString("g_id");
        Log.d("Dfish","g_id from intent"+g_id);
        picture = (ImageButton)findViewById(R.id.picture);
        myReply = (EditText)findViewById(R.id.content);
        post = (ImageButton)findViewById(R.id.post);
        noName = (ImageButton)findViewById(R.id.noname);
        picture.setOnClickListener(this);
        post.setOnClickListener(this);
        noName.setOnClickListener(this);
        getData();
        if(g_id != null) {
            isCanAnonymous(g_id);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.picture :
                //Snackbar.make(v,"添加图片",Snackbar.LENGTH_SHORT).show();
                //select pictures from local gallery,android post it to
                //the server
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent,2);
                break;
            case R.id.noname :
                if(!noNameChecked) {
                    //v.setBackground(getResources().getDrawable(R.mipmap.ic_arrow_down));
                    noName.setImageResource(R.mipmap.ic_shield);
                    noNameChecked = true;
                } else {
                    //v.setBackground(getResources().getDrawable(R.mipmap.ic_emoticon_neutral));
                    noName.setImageResource(R.mipmap.ic_shield_outline);
                    noNameChecked = false;
                }

                break;
            case R.id.post :
                //Snackbar.make(v,"提交",Snackbar.LENGTH_SHORT).show();
                //get edittext content and post to the sever
                //if successed,refresh the view;
                String articleId = myReply.getText().toString();
                if (isNumeric(articleId)){
                    Intent intentArticle = new Intent();
                    intentArticle.setClass(this,ArticleActivity.class);
                    intentArticle.putExtra("title",articleId);
                    intentArticle.putExtra("g_a_id",articleId);
                    startActivity(intentArticle);
                }else {
                    postReply();
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d("Dfish","resulttCode:"+resultCode);
        }
        if (requestCode ==2 && data != null) {
            String uri = Uri2Path.parse(this,data.getData());
            Log.i("Dfish", "uri :" + uri);
            File file = new File(uri);
            String name = uri.substring(uri.lastIndexOf("/")+1,uri.length());
            Log.i("Dfish","pic name :"+name);
            upImage(file,name);
        }
    }

    private void getData() {
        params =  new RequestParams();
        uid=  (CommonData.user.getUid()== null)? "-1":CommonData.user.getUid() ;
        Log.d("Dfish", "uid = " + uid);
        params.add("uid", uid);
        params.add("reply_order", CommonData.user.getReply_order());
        params.add("type", "get_group_article");
        params.add("g_a_id", this.g_a_id);
        params.add("page", "1");
//        AsyncHttpClient client  = new AsyncHttpClient();
        PersistentCookieStore cStore = new PersistentCookieStore(this);
        //client.setCookieStore(cStore);
        HttpUtil.post(CommonData.groupURL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish", "article F" + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish", "article S" + statusCode + responseString);
                if (responseString.length() > 4000){
                    Log.d("Dfish","Article part1"+responseString.substring(0,4000));
                    Log.d("Dfish","Article part2"+responseString.substring(4000));
                }
//                String preContent = "";
//                try {
//                    InputStream input = getAssets().open("content.html");
//                    preContent = getStringFromInputStream(input);
//                    Log.d("Dfish", "preContent " + preContent);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                if (responseString.startsWith("<")) {
                    responseString = responseString.substring(responseString.indexOf("{"),responseString.length());
                }
                JSONObject resp = JSON.parseObject(responseString);
                if (resp.getString("status").equals("0")) {
                    Snackbar.make(myReply, "找不见了", Snackbar.LENGTH_SHORT).show();
                } else {
                    Details details = JsonParse.parseDetails(resp);
                    //Article article = JsonParse.parseArticle(resp);
                    //List<Tag> tagList = article.getG_a_tags();
                    Article article = details.getArticle();

                    //another way to get gid
                    g_id = article.getGid();
                    Log.d("Dfish","g_id another way"+g_id);

                    List<Reply> replyList = details.getReply();
                    Author author = details.getAuthor();
                    //List<Reply> replyList = JsonParse.parseReplys(resp);
                    Log.d("Dfish", "replist isempty?" + replyList.isEmpty() + " " + (replyList == null));
                    //Log.d("Dfish", "tag == null?" + (tagList == null));
//                for (Tag tag : tagList){
//                    Log.d("Tag",tag.getTag()+tag.getId()+"ss");
//                }
//                for (Reply reply : replyList) {
//                    Log.d("Dfish group",reply.getCreatime()+"1");
//                }
//                String contents = article.getContent();
//                String title = article.getName();
//                if (contents.contains("src=\"data")) {
//                    contents.replace("src=\"data", "src=\"http://bbs.nankai.edu.cn/data");
//                }
//                webContent.loadDataWithBaseURL("http://bbs.nankai.edu.cn/",
//                        preContent.replace("#content#", contents)
//                                .replace("#title#", title)
//                                .replace("&lt;img", "<img")
//                                .replace("&gt;", ">"), "text/html", "UTF-8", "");
//                webContent.loadUrl("https://baidu.com");
//                rep_cnt.setText(article.getReply_num());
//                lik_cnt.setText(article.getFav_num());
                    hAdapter = new HeaderAdapter(ArticleActivity.this, article, author, replyList);
                    hAdapter.setOnItemClickListener(new HeaderAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View v, String data, int flag) {
                            //Snackbar.make(v,data,Snackbar.LENGTH_SHORT).show();
                            String[] s = data.split("`", 2);
                            Intent intent = new Intent();
                            Bundle localBundle = new Bundle();
                            switch (flag) {
                                case 1:
                                    p_g_r_id = s[0];
                                    Log.d("Dfish", "p_g_r_id" + p_g_r_id);
                                    myReply.setText("");
                                    myReply.append("<blockquote>" + s[1].trim() + "</blockquote>");
                                    break;
                                case 2:
                                case 3:
                                    intent.setClass(ArticleActivity.this, UserInfoActivity.class);
                                    localBundle.putString("user_id", s[0]);
                                    localBundle.putString("title", s[1]);
                                    intent.putExtras(localBundle);
                                    ArticleActivity.this.startActivity(intent);
                                    break;
                                case 4:
                                    p_g_r_id = "";
                                    Log.d("Dfish", "p_g_r_id" + p_g_r_id);
                                    myReply.setText("");
                                    break;
                                case 5:
                                    p_g_r_id = s[0];
                                    Log.d("Dfish", "p_g_r_id" + p_g_r_id);
                                    myReply.clearComposingText();
                                    myReply.setText("");
                                    myReply.append("回复 " + s[1] + ": ");
                                    break;
                                default:
                                    break;
                            }

                        }
                    });
                    replyRv.setLayoutManager(new LinearLayoutManager(ArticleActivity.this));
                    replyRv.setAdapter(hAdapter);
                    Log.d("Dfish", "webcontent show");
                    progressBar.setVisibility(View.GONE);
                    Log.d("Dfish", "progressbar Gone");
                }
            }
        });
    }

    public void upImage(File file,String name){
        uploadProgress.setVisibility(View.VISIBLE);
        picture.setVisibility(View.GONE);
        paramsUp = new RequestParams();
        paramsUp.add("fileName",name);
        paramsUp.add("pictitle", name);
        paramsUp.add("dir","1");//1 or 2
        paramsUp.add("param1","value1");
        paramsUp.add("param2","value2");
        paramsUp.add("Filename","9.png");
        paramsUp.add("Upload","Submit Query");

        try {
            paramsUp.put("upfile", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpUtil.post(CommonData.uploadURL,paramsUp,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Dfish","upload success"+response);
                uploadProgress.setVisibility(View.GONE);
                picture.setVisibility(View.VISIBLE);
                String img = null;
                try {
                    img = response.getString("url");
                    formatImg = "<img src=\"http://bbs.nankai.edu.cn/data/"+img+"\"/>";
                    myReply.append(formatImg);
                    Snackbar.make(myReply,"图片上传成功",Snackbar.LENGTH_SHORT).show();
                    Log.d("Dfish",myReply.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Dfish","formatImg:"+formatImg);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("Dfish", "upload fail" + statusCode);
                Snackbar.make(picture,"上传失败",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void postReply(){
        paramsPost = new RequestParams();
        //paramsPost.setContentEncoding("GB2312");
        isNoName = noNameChecked? "1":"0";
        Log.d("Dfish","匿名回复"+isNoName);
        paramsPost.add("g_a_id", g_a_id);
        paramsPost.add("p_g_r_id",p_g_r_id);
        paramsPost.add("noname",isNoName);  //0 or 1
        paramsPost.add("isnew", "new");
        paramsPost.add("type", "post_reply");
        paramsPost.add("uid", uid);
        StringBuilder out = new StringBuilder();
        int len = myReply.getText().length();
        tryEncode(out, myReply.getText(), 0, len);
        paramsPost.put("content", out);

        //Log.d("Dfish", "uid" + uid + "g_a_id" + g_a_id + " " + out.toString());
        Log.d("Dfish", paramsPost.toString());
        PersistentCookieStore CStore = new PersistentCookieStore(this);
        AsyncHttpClient client =  new AsyncHttpClient();
        client.setCookieStore(CStore);
        HttpUtil.post(CommonData.groupURL,paramsPost,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("Dfish","postReply"+errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Dfish","postReply"+response);
                myReply.setText("");
                //getData();
                //adapter.notifyDataSetChanged();

                HttpUtil.post(CommonData.groupURL, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("Dfish", "article F" + responseString);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("Dfish", "article S" + responseString);
                        String preContent = "";
                        try {
                            InputStream input = getAssets().open("content.html");
                            preContent = getStringFromInputStream(input);
                            Log.d("Dfish", "preContent " + preContent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JSONObject resp = JSON.parseObject(responseString);
                        //Article article = JsonParse.parseArticle(resp);
                        //List<Tag> tagList = article.getG_a_tags();
                        List<Reply> replyList = JsonParse.parseReplys(resp);
                        Log.d("Dfish", "replist isempty?" + replyList.isEmpty() + replyList.toString() + " " + (replyList == null));

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(myReply.getWindowToken(), 0);

                        hAdapter.refreshData(replyList);
                    }
                });



            }
        });
    }

    public void isCanAnonymous(String gid) {
        RequestParams paramsForGroup =  new RequestParams();
        String uid=  (CommonData.user.getUid()== null)? "-1":CommonData.user.getUid() ;
        Log.d("Dfish", "uid = " + uid);
        paramsForGroup.add("uid", uid);
        paramsForGroup.add("type", "get_group_article_list");
        paramsForGroup.add("gid", gid);
        Log.d("Dfish","是否可以匿名小组"+gid);
        paramsForGroup.add("page", "1");
        HttpUtil.post(CommonData.groupURL, paramsForGroup, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish","是否可匿名FF");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish","是否可匿名"+responseString);
                JSONObject groupInfo = JSONObject.parseObject(responseString);
                String canBlack = groupInfo.getJSONObject("data").getJSONObject("group").getString("anonymous");
                if (!canBlack.equals("1")) {
                    noName.setVisibility(View.GONE);
                } else {
                    Log.d("Dfish","可以匿名？"+canBlack);
                }

            }
        });
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("Dfish","onstop");
//    }
//
//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        Log.d("Dfish", "onpostresume");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d("Dfish", "ondestroy");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("Dfish", "onpause");
//    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        Log.d("Dfish", "ononPostCreate");
//    }
//
//    @Override
//    public void onContentChanged() {
//        super.onContentChanged();
//        Log.d("Dfish", "onContentChanged");
//    }
//
//    @Override
//    public void onPanelClosed(int featureId, Menu menu) {
//        super.onPanelClosed(featureId, menu);
//        Log.d("Dfish", "onPanelClosed");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d("Dfish", "onRestart");
//    }
//
//    @Override
//    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        Log.d("Dfish", "onCreateView");
//        return super.onCreateView(parent, name, context, attrs);
//
//    }
//

//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d("Dfish", "onStart");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d("Dfish", "onreStart");
//    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Dfish", "onresume ");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();

                return true;

        }
        return false;
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


    private static void tryEncode(StringBuilder out, CharSequence text,
                                    int start, int end) {
        for (int i = start; i < end; i++) {
            char c = text.charAt(i);

//            if (c == '<') {
//                //out.append("&lt;");
//                out.append("&#").append((int) c).append(";");
//                Log.d("Dfish","sdc"+(int)c);
//            } else if (c == '>') {
//                //out.append("&gt;");
//                out.append("&#").append((int) c).append(";");
//            } else if (c == '&') {
//                //out.append("&amp;");
//                out.append("&#").append((int) c).append(";");
//            } else
            if (c >= 0xD800 && c <= 0xDFFF) {
                if (c < 0xDC00 && i + 1 < end) {
                    char d = text.charAt(i + 1);
                    if (d >= 0xDC00 && d <= 0xDFFF) {
                        i++;
                        int codepoint = 0x010000 | (int) c - 0xD800 << 10 | (int) d - 0xDC00;
                        out.append("&#").append(codepoint).append(";");
                    }
                }
            } else if (c > 0x7E || c < ' ') {
                out.append("&#").append((int) c).append(";");
            } else if (c == ' ') {
                while (i + 1 < end && text.charAt(i + 1) == ' ') {
                    out.append("&nbsp;");
                    i++;
                }

                out.append(' ');
            } else {
                out.append(c);
            }
        }
    }


    public static boolean isNumeric(String str){
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }
        return true;
    }



}
