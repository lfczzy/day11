package com.ztcx.videoplay.utils;

import android.Manifest;
import android.app.Activity;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.ztcx.videoplay.app.MyApp;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by LCH on 2018/9/6.
 *
 * 获取地理信息相关的方法
 */
public class GDUtils {

    //声明AMapLocationClientOption对象
    private static AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    private static AMapLocationClient mLocationClient = null;
    private static MyAMapLocationListener mLocationListener;
    private static AMapLocationCallback callback;


    /**
     * 获取定位信息
     */
    public static void getPCD(Activity activity, final AMapLocationCallback callback){ GDUtils.callback = callback;

        getPermission(activity, new OnPermission() {
            @Override
            public void onHasPermission() {
                initGD();
            }

            @Override
            public void onNotPermission() {
            }
        });

    }

    public static void initGD(){
        if (mLocationListener == null)
            mLocationListener = new MyAMapLocationListener(callback);

        mLocationListener.setaMapLocationCallback(callback);

        //初始化定位
        if (mLocationClient == null)
            mLocationClient = new AMapLocationClient(MyApp.getAppContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);


        //初始化AMapLocationClientOption对象
        if (mLocationOption == null)
            mLocationOption = new AMapLocationClientOption();

        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        AMapLocationClient locationClient = null;
        if(null != locationClient){
            locationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            locationClient.stopLocation();
            locationClient.startLocation();
        }
        //        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        //        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    //获取权限
    private static void getPermission(Activity activity, OnPermission onPermission) {
        /***/
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(activity, permissions)) {
            onPermission.onHasPermission();
        } else {
            onPermission.onNotPermission();
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(activity, "您需要开启权限才能正常使用！", 1, permissions);
        }

    }

    interface OnPermission{
        void onHasPermission();
        void onNotPermission();
    }


}
