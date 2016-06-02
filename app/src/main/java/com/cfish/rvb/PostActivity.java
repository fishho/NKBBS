package com.cfish.rvb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.HttpUtil;
import com.cfish.rvb.util.Uri2Path;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private String gid,canAnonymous; //
    private ImageButton picture,anonymous,post;
    private ProgressBar uploadProgressbar;
    private EditText name,content;
    private RequestParams params,paramsUp;
    private String formatContent,formatImg;
    private String nameContent;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        this.gid = getIntent().getExtras().getString("gid");
        this.canAnonymous = getIntent().getExtras().getString("canAnonymous");
        Log.d("Dfish", "PostActivity gid" + gid+ " canAnonymous" +canAnonymous);

        picture = (ImageButton)findViewById(R.id.picture);
        anonymous = (ImageButton)findViewById(R.id.anonymous);
        post = (ImageButton)findViewById(R.id.post);
        name = (EditText)findViewById(R.id.name);
        content = (EditText)findViewById(R.id.content);
        uploadProgressbar = (ProgressBar) findViewById(R.id.upload_progress);
        picture.setOnClickListener(this);
        post.setOnClickListener(this);
        if (canAnonymous.equals("1")) {
            anonymous.setOnClickListener(this);
        } else {
            Log.d("Dfish","canAnonymous"+canAnonymous);
            anonymous.setVisibility(View.GONE);
        }
        params = new RequestParams();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            Log.d("Dish","requestCode:"+requestCode);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.picture :
                //Snackbar.make(v, "请在回复中添加图片", Snackbar.LENGTH_SHORT).show();//use new api,no need any more
                //select pictures from local gallery,android post it to
                //the server
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 2);

                break;
            case R.id.post :
                Snackbar.make(v,"提交",Snackbar.LENGTH_SHORT).show();
                //get edittext content and post to the sever
                //if successed,refresh the view;
                postArticleNew();
                break;
            default:
                break;
        }
    }

//    private void postArticle() {
//        String uid=  (CommonData.user.getUid()== null)? "-1":CommonData.user.getUid() ;
//        Log.d("Dfish","PostActivity uid = "+uid);
//        params.add("uid", uid);
//        params.add("anonymous", "0");
//        params.add("type", "post_article");
//        params.add("gid", gid);
//        params.add("name", name.getText().toString());
//        StringBuilder out = new StringBuilder();
//        int len = content.getText().length();
//        tryEncode(out, content.getText(), 0, len);
//        //params.add("content", out.toString());
//        params.put("content", Html.toHtml(content.getText()));//it works fine
//        //params.put("content",Html.fromHtml(Html.toHtml(content.getText())));
//        //params.put("content",Html.toHtml( name.getText()));
//        params.add("isnew", "new");
//        HttpUtil.post(CommonData.groupURL,params,new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.d("Dfish", "PostActivity " + response);
//                setResult(RESULT_OK);
//                PostActivity.this.finish();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                Log.d("Dfish", "PostActivity " + errorResponse);
//            }
//        });
//    }


    private void postArticle() {
        String uid=  (CommonData.user.getUid()== null)? "-1":CommonData.user.getUid() ;
        Log.d("Dfish","PostActivity uid = "+uid);
        params.add("uid", uid);
        params.add("anonymous", "0");
        params.add("type", "post_article");
        params.add("gid", gid);
        params.add("name", name.getText().toString());
        StringBuilder out = new StringBuilder();
        int len = content.getText().length();
        tryEncode(out, content.getText(), 0, len);
        //params.add("content", out.toString());
        //params.put("content", Html.toHtml(content.getText()));//it works fine
        //params.put("content",Html.fromHtml(Html.toHtml(content.getText())));
        params.put("content","<p><img src='data/uploads/picture/image/20160327/14590540902690.jpg' style='float:none;' title='p1723941733.jpg'/></p>");
        params.add("isnew", "new");
        HttpUtil.post(CommonData.groupURL,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Dfish", "PostActivity " + response);
                setResult(RESULT_OK);
                PostActivity.this.finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("Dfish", "PostActivity " + errorResponse);
            }
        });
    }

    public void postArticleNew() {
        //此处uid的问题，只是从cookie?
        params.add("thing","post_article");
        params.add("gid",gid);
        params.add("name",name.getText().toString());
        params.add("content",content.getText().toString());
        params.add("anonymous","false");//设置匿名选项
        params.add("vid","-1"); //位置参数
        params.add("tags","");
        params.add("edit","0"); //0表示新文章，其他对应g_a_id;

        HttpUtil.post("http://bbs.nankai.edu.cn/group/action", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Dfish", "PostActivityNew " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Dfish", "PostActivityNew " + responseString);
                setResult(RESULT_OK);
                PostActivity.this.finish();
            }
        });

    }




    public void upImage(File file,String name){
        uploadProgressbar.setVisibility(View.VISIBLE);
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
                uploadProgressbar.setVisibility(View.GONE);
                picture.setVisibility(View.VISIBLE);
                Log.d("Dfish","upload success"+response);
                String img = null;
                try {
                    img = response.getString("url");
                    formatImg = "<img src=\"http://bbs.nankai.edu.cn/data/"+img+"\"/>";
                    content.append(formatImg);
                    Snackbar.make(content,"图片上传成功",Snackbar.LENGTH_SHORT).show();
                    Log.d("Dfish",content.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Dfish","formatImg:"+formatImg);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("Dfish","upload fail");
            }
        });
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
//            }
//            else
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
}
