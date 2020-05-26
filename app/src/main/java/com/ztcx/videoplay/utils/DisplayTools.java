package com.ztcx.videoplay.utils;

import android.content.Context;

import com.ztcx.videoplay.app.MyApp;

/**
 * Created by Administrator on 2017/10/25.
 */

public class DisplayTools {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */

    public static int px2dip(float pxValue) {
    final float scale = MyApp.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);

    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */

    public static int dip2px(float dipValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);

    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */


    public static int px2sp(float pxValue) {
        final float scale = MyApp.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);

    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */


    public static int sp2px(float spValue) {
        final float scale = MyApp.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);

    }

}
