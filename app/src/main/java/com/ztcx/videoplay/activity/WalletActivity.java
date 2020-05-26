package com.ztcx.videoplay.activity;

import com.ztcx.videoplay.R;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.constant.AppConstant;

public class WalletActivity extends NativeBaseActivity {

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_wallet;
    }

    @Override
    public void setUpView() {

        setVisibleTitleBar(AppConstant.MAIN_COLOR,"科技窝钱包");

    }

    @Override
    public void setUpData() {

    }
}
