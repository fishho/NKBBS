package com.cfish.rvb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cfish.rvb.adapter.ViewPagerAdapter;
import com.cfish.rvb.bean.Message;
import com.cfish.rvb.fragment.GroupFragment;
import com.cfish.rvb.fragment.SiteFragment;
import com.cfish.rvb.fragment.TopFragment;
import com.cfish.rvb.fragment.TopicFragment;
import com.cfish.rvb.util.CommonData;
import com.cfish.rvb.util.JsonParse;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout main_tabs;
    private ViewPager main_pager;
    private View view;
    private SearchView searchView;
    private Intent intentService;
    private MsgReceiver receiver;
    private boolean haveMsg = false;
    private String news = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Intent intent = new Intent();
//                intent.setClass(getBaseContext(), LoginActivity.class);
//                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        SimpleDraweeView avatar = (SimpleDraweeView) view.findViewById(R.id.avatar);
        Log.d("Dfish","avatar"+CommonData.user.getBig_avatar());

        //in tourist mode , click the head image to login
        //in user mode ,click to open personal page
        avatar.setImageURI(Uri.parse("http://bbs.nankai.edu.cn/data/uploads/avatar/" + CommonData.user.getBig_avatar()));
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(MainActivity.this,LoginActivity.class);
                //startActivityForResult(intent,0);
                intent.setClass(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        TextView uName = (TextView) view.findViewById(R.id.uName);
        TextView scoreText = (TextView) view.findViewById(R.id.scoreText);
        TextView signature = (TextView) view.findViewById(R.id.signature);
        uName.setText(CommonData.user.getName());
        scoreText.setText(CommonData.user.getScore());
        signature.setText(CommonData.user.getSignature());

        main_tabs = (TabLayout) findViewById(R.id.main_tabs);
        main_pager = (ViewPager) findViewById(R.id.main_pager);

        intentService= new Intent(this,GetMsgService.class);
        startService(intentService);
        receiver = new MsgReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MSG");
        this.registerReceiver(receiver, filter);
        initMainCotent();
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(receiver);
        stopService(intentService);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        searchView = (SearchView)menu.findItem(R.id.ab_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(searchView, query, Snackbar.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchView.setIconified(true);
                }
                Toast.makeText(MainActivity.this, "焦点改变", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem message= menu.findItem(R.id.action_message);
        Log.d("Dfish","haveMsg"+haveMsg);
        if (haveMsg){
            message.setIcon(R.mipmap.ic_bell_ring_outline);
        }
        return super.onPrepareOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_message:
                item.setIcon(R.mipmap.ic_bell_outline);
                if (haveMsg) {
                    Intent intent = new Intent(this,NotificationListActivity.class);
                    intent.putExtra("news",news);
                    MainActivity.this.startActivity(intent);
                }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nv_home) {
            // Handle the camera action
        } else if (id == R.id.nv_top) {
            //活跃用户
            Intent activeIntent = new Intent();
            activeIntent.setClass(this,ActiveUserActivity.class);
            startActivity(activeIntent);

        } else if (id == R.id.nv_group) {
            Intent activeIntent = new Intent();
            activeIntent.setClass(this,GroupListActivity.class);
            startActivity(activeIntent);

        } else if (id == R.id.nv_person) {
            //收藏页面
            Intent personIntent = new Intent();
            personIntent.setClass(this,FavActivity.class);
            startActivity(personIntent);
        }/* else if (id == R.id.nv_setting) {
            //设置页面
            Intent settingIntent = new Intent();
            settingIntent.setClass(this,SettingsActivity.class);
            startActivity(settingIntent);
        }*/ else if (id == R.id.nv_about) {
            //关于
            Intent msgIntent = new Intent();
            msgIntent.setClass(this,AboutActivity.class);
            startActivity(msgIntent);
        } else if (id == R.id.nv_logout) {
            //注销
            SharedPreferences sharedPreferences = this.getSharedPreferences("SHARED_LOGIN",0);
            sharedPreferences.edit().clear().commit();
            CommonData.user.setUid("-1");
            Log.d("Dfish",CommonData.user.getUid());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initMainCotent() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Fragment groupFragment = new GroupFragment();
        Fragment siteFragment = new SiteFragment();
        Fragment topFragment = new TopFragment();
        Fragment topicFragment = new TopicFragment();
        adapter.addFragment(topicFragment,"首页");
        adapter.addFragment(topFragment, "十大");
        adapter.addFragment(groupFragment,"小组");
        adapter.addFragment(siteFragment,"主题站");
        main_pager.setAdapter(adapter);
        //main_pager.setOffscreenPageLimit(0);//取消viewpager的预加载,default 1,cannot apply to 0
        main_tabs.setupWithViewPager(main_pager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                Bundle bundle = data.getExtras();
                String name = bundle.getString("name");
                String score = bundle.getString("score");
                String avatarUrl = bundle.getString("avatar");
                String signature = bundle.getString("signature");
//                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//                View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
                //navigationView.removeHeaderView(view);
                SimpleDraweeView avatar = (SimpleDraweeView) view.findViewById(R.id.avatar);
                avatar.setImageURI(Uri.parse("http://bbs.nankai.edu.cn/data/uploads/avatar/" + avatarUrl));
                TextView uName = (TextView) view.findViewById(R.id.uName);
                TextView scoreText = (TextView) view.findViewById(R.id.scoreText);
                TextView signature1 = (TextView) view.findViewById(R.id.signature);
                uName.setText(name);
                scoreText.setText(score);
                signature1.setText(signature);
                break;
            default:
                break;
        }
    }




    public class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Dfish","onReceive");
            news = intent.getExtras().getString("news");
            Log.d("Dfish","MainActivity get news from service intent"+news);
            Message notices = JsonParse.parseNotice(news);
            Log.d("Dfish","news getName" + notices.getNews().get(0).getName());
            haveMsg =true;
            invalidateOptionsMenu();
        }
    }



}
