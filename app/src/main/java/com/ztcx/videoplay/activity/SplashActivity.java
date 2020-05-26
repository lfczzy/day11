package com.ztcx.videoplay.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.ztcx.videoplay.MainActivity;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.been.Appconfig;
import com.ztcx.videoplay.been.BannerBean;
import com.ztcx.videoplay.been.PhoneDto;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.constant.SPConstant;
import com.ztcx.videoplay.utils.AMapLocationCallback;
import com.ztcx.videoplay.utils.APKVersionCodeUtils;
import com.ztcx.videoplay.utils.GDUtils;
import com.ztcx.videoplay.utils.KVUtils;
import com.ztcx.videoplay.utils.L;
import com.ztcx.videoplay.utils.PhoneUtils;
import com.ztcx.videoplay.utils.UpdateApkUtils;
import com.ztcx.videoplay.view.UpdateView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends NativeBaseActivity implements  EasyPermissions.PermissionCallbacks {

    private ImageView mIvSplash;
    UpdateView updateView;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    L.e("qpf","下载进度 -- "  + msg.obj);
                    updateView.setUpdateProgress("下载进度（"+msg.obj+"%）");
                    if ("100".equals(msg.obj)){
                        updateView.setUpdateProgress("去安装");
                    }
                    break;
            }
        }
    };

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    public void setUpView() {
        mIvSplash = findViewById(R.id.iv_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //加载启动页
                RequestOptions re = new RequestOptions();
                re.placeholder(R.mipmap.splash);
                Glide.with(SplashActivity.this).load(KVUtils.query(SPConstant.SPLASH_STR)).apply(re).into(mIvSplash);
            }
        },1000);

    }

    @Override
    public void setUpData() {
        //开启定位
        GDUtils.getPCD(this, new AMapLocationCallback() {
            @Override
            public void callback(AMapLocation aMapLocation) {
                MyApp.aMapLocation = aMapLocation;
                getAppconfig();
                L.e("qpf","定位信息 -- " + aMapLocation.getDescription() + "");
            }
        });
    }

    /**
     * 获取appconfig
     */
    private void getAppconfig(){
        BmobQuery<Appconfig> query = new BmobQuery<>();
        query.findObjects(this, new FindListener<Appconfig>() {
            @Override
            public void onSuccess(List<Appconfig> list) {
                Appconfig appconfig = list.get(0);
                KVUtils.add(SPConstant.APPCONFIG_JSON,new Gson().toJson(appconfig));
                KVUtils.add(SPConstant.SPLASH_STR,appconfig.getSplash_image());
                KVUtils.add(SPConstant.NOTICE,appconfig.getNotice());
                KVUtils.add(SPConstant.BUSINESS_QQ,appconfig.getBusiness_qq());
                KVUtils.add(SPConstant.SERVICE_QQ,appconfig.getService_qq());
                KVUtils.add(SPConstant.JX_URL,appconfig.getJx_url());
                KVUtils.add(SPConstant.UPDATE_INFO_JSON,new Gson().toJson(appconfig.getUpdateBeen()));

                final Appconfig.UpdateBeen updateBeen = appconfig.getUpdateBeen();
                if (updateBeen.getVersion() > APKVersionCodeUtils.getVersionCode(SplashActivity.this)
                        && !updateBeen.getVersionName().equals(KVUtils.query("version"))){
                    final String download_url = updateBeen.getApk_url();
                    String content = updateBeen.getUpdate_explain();
                    boolean cancel = updateBeen.isForce();

                    updateView = new UpdateView(SplashActivity.this, "升级提示！", content, cancel);
                    updateView.setClicklistener(new UpdateView.ClickListenerInterface() {
                        @Override
                        public void doConfirm(UpdateView dialogUtils) {
                            UpdateApkUtils.NetUntils(download_url,
                                    SplashActivity.this,handler);
                        }

                        @Override
                        public void doCancel(UpdateView dialogUtils) {
                            dialogUtils.dismiss();
                            KVUtils.add("version",updateBeen.getVersionName());
                            startMainActivity();
                        }
                    });
                    updateView.show();
                }else {
                    startMainActivity();
                }


            }

            @Override
            public void onError(int i, String s) {
                startMainActivity();
            }
        });
    }



    private void startMainActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        },3000);
    }

    /***
     * 禁用返回按钮
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            return true;
        }else {
            return super.dispatchKeyEvent(event);
        }
    }


    //用户拒绝时候会调用该方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    //允许权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        L.e("QPF","允许权限 -- " + perms.size());

        if (perms.size() == 4){
            GDUtils.getPCD(this, new AMapLocationCallback() {
                @Override
                public void callback(AMapLocation aMapLocation) {
                    MyApp.aMapLocation = aMapLocation;
                    getAppconfig();
                }
            });
            L.e("QPF","允许权限 -- onPermissionsDenied");
            L.e("QPF","手机联系人 -- " + PhoneUtils.getPhone(this).toString());
        }else {
            L.e("QPF","允许权限 -- 请开启相关的权限 -- ");
        }
    }

    //拒绝权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //未开启权限
        L.e("QPF","拒绝权限 -- onPermissionsDenied");
        GDUtils.getPCD(this, new AMapLocationCallback() {
            @Override
            public void callback(AMapLocation aMapLocation) {
                MyApp.aMapLocation = aMapLocation;
                getAppconfig();
            }
        });
    }
}
