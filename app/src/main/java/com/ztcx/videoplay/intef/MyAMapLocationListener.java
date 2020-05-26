package com.ztcx.videoplay.intef;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.ztcx.videoplay.app.MyApp;

public class MyAMapLocationListener implements AMapLocationListener {

    private AMapLocationCallback aMapLocationCallback;

    public MyAMapLocationListener(AMapLocationCallback callback) {
        this.aMapLocationCallback = callback;
    }

    public void setaMapLocationCallback(AMapLocationCallback aMapLocationCallback) {
        this.aMapLocationCallback = aMapLocationCallback;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        MyApp.aMapLocation = aMapLocation;
        aMapLocationCallback.callback(aMapLocation);
    }
}
