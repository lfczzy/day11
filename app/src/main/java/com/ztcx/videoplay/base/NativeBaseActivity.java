package com.ztcx.videoplay.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.dialog.CommonDialogUtils;
import com.ztcx.videoplay.utils.AppManager;
import com.ztcx.videoplay.utils.BarTextColorUtils;
import com.ztcx.videoplay.utils.CommonHeadUtils;
import com.ztcx.videoplay.utils.ViewUtils;

public abstract class NativeBaseActivity extends AppCompatActivity implements View.OnClickListener {

    private View mContentView;
    protected TextView mTvTitleRight;
    private TextView mTvTitleCenter;
    protected ImageView mIvTitleLift;
    protected TextView mTvSingleStatusBar;
    private LinearLayout mLlCommentTopTitle;
    protected TextView mTvBottom;

    protected ImageView mIvLoading;
    public RelativeLayout mRlLoading;

    private RelativeLayout mRlTitle;
    public View mRlNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CommonHeadUtils.fullScreen(this);
        savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_base);
//        友盟统计
        PushAgent.getInstance(this).onAppStart();
        //进栈
        AppManager.getAppManager().addActivity(this);

        FrameLayout content = findViewById(R.id.content);
        content.removeAllViews();

        mContentView = LayoutInflater.from(this).inflate(setLayoutResourceID(),null);
        content.addView(mContentView);

        //初始化标题
        mTvTitleRight = findViewById(R.id.tv_title_right);
        mTvTitleCenter = findViewById(R.id.tv_comment_center);
        mIvTitleLift = findViewById(R.id.iv_title_left);
        mTvSingleStatusBar = findViewById(R.id.tv_single_statusBar);
        mLlCommentTopTitle = findViewById(R.id.ll_comment_top_title);
        mRlTitle = findViewById(R.id.rl_title);

        mTvBottom= findViewById(R.id.tv_bottom);

        mIvLoading = findViewById(R.id.iv_loading);
        mRlLoading = findViewById(R.id.rl_loading);
        mRlNoData = findViewById(R.id.rl_no_data);

        Glide.with(this).load(R.mipmap.loading).into(mIvLoading);

        init();
        setUpView();
        setUpData();

    }

    //显示状态栏
    protected void setVisibleStatusBar(String colorStr){
        int color = Color.parseColor(colorStr);
        ViewGroup.LayoutParams params = mTvSingleStatusBar.getLayoutParams();
        params.height = ViewUtils.getStatusBarHeight(this);
        mTvSingleStatusBar.setLayoutParams(params);
        mTvSingleStatusBar.setVisibility(View.VISIBLE);
        //设置状态栏的颜色
        if (color == Color.WHITE){
            BarTextColorUtils.StatusBarLightMode(this);

            //字体设为灰色
            mTvTitleCenter.setTextColor(getResources().getColor(R.color.common_textcolor_black_33));

            mIvTitleLift.setImageResource(R.mipmap.ic_back_black);
        }
        mLlCommentTopTitle.setBackgroundColor(color);
    }

    //显示标题栏
    protected void setVisibleTitleBar(String colorStr,String title){
        //显示状态栏
        setVisibleStatusBar(colorStr);
        //显示标题
        mRlTitle.setVisibility(View.VISIBLE);
        //设置标题
        mTvTitleCenter.setText(title);
        //点击返回键
        mIvTitleLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //设置底部
    protected void setBottomNull(){
        //设置底部按钮的高度
        int navigationBarHeight = BarTextColorUtils.getNavigationBarHeight(this);
        ViewGroup.LayoutParams paramsBottom = mTvBottom.getLayoutParams();
        paramsBottom.height = navigationBarHeight;
        mTvBottom.setLayoutParams(paramsBottom);
        mTvBottom.setVisibility(View.VISIBLE);
    }



    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    public abstract int setLayoutResourceID();

    /**
     * 一些View的相关操作
     */
    public abstract void setUpView();

    /**
     * 一些Data的相关操作
     */
    public abstract void setUpData();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    public void init() {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (isWifiProxy(this)){
            CommonDialogUtils dialogUtils = new CommonDialogUtils(this,"重要提示","当前环境异常，请立即退出APP!",false);
            dialogUtils.show();
            dialogUtils.setClicklistener(new CommonDialogUtils.ClickListenerInterface() {
                @Override
                public void doConfirm(CommonDialogUtils dialogUtils) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private static boolean isWifiProxy(Context mContext) {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(mContext);
            proxyPort = android.net.Proxy.getPort(mContext);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

}
