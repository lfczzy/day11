package com.ztcx.videoplay.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ztcx.videoplay.R;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.been.UserInfo;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.L;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends NativeBaseActivity {

    private TextView mTvDeviceToken;

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    public void setUpView() {

        setVisibleTitleBar(AppConstant.WHILT_COLOR,"登录");

        mTvDeviceToken = findViewById(R.id.tv_device_token);
        L.e("qpf","设备的id -- " + CommUtils.getDeviceId());
        mTvDeviceToken.setText("设备ID "+CommUtils.deviceTokenUtils(CommUtils.getDeviceId()));

    }

    @Override
    public void setUpData() {

    }

    public void login(View view){
        BmobUser.loginByAccount(this, CommUtils.getDeviceId(), "123456", new LogInListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                L.e("qpf","登录成功 -- " + userInfo.toString());
                Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
