package com.ztcx.videoplay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ztcx.videoplay.R;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.constant.AppConstant;

public class BuyVipActivity extends NativeBaseActivity {

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_buy_vip;
    }

    @Override
    public void setUpView() {
        setVisibleTitleBar(AppConstant.MAIN_COLOR,"会员购买");
    }

    @Override
    public void setUpData() {

    }
}
