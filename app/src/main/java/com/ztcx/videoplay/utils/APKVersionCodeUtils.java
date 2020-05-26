package com.ztcx.videoplay.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.umeng.analytics.AnalyticsConfig;


/**
 * 获取当前版本号和版本名称
 */
public class APKVersionCodeUtils {
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 版本名
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取渠道名称
     * @param ctx
     * @return
     */
    public static String getChannelName(Context ctx){
        String channelName = AnalyticsConfig.getChannel(ctx);
        return channelName;
    }
}
