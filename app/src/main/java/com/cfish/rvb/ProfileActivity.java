package com.cfish.rvb;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cfish.rvb.util.CommonData;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    TextView nickname,sig;
    SimpleDraweeView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_profile);
        avatar = (SimpleDraweeView)findViewById(R.id.avatar_self);
        nickname = (TextView) findViewById(R.id.nick);
        sig = (TextView) findViewById(R.id.sig);
        avatar.setImageURI(Uri.parse("http://bbs.nankai.edu.cn/data/uploads/avatar/" + CommonData.user.getBig_avatar()));
        nickname.setText(CommonData.user.getName());
        sig.setText(CommonData.user.getSignature());
    }
}
