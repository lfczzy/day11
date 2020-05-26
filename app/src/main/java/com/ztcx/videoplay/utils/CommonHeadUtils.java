package com.ztcx.videoplay.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Method;


/**
 * Created by yinyanyang on 2018/1/10.
 */

public class CommonHeadUtils {

    /**
     * 设置页面最外层布局 FitsSystemWindows 属性
     *
     * @param activity
     */
    /**
     * 通过设置全屏，设置状态栏透明
     *
     * 已经适配华为刘海
     *
     * @param activity
     */
    public static void fullScreen(Activity activity) {
        if (hasNotchInScreenForHW(activity)){
            return;
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }


    //设置背景色
    public static void setHeadBackground(Activity a, View v, int colorId) {
        v.setBackgroundColor(a.getResources().getColor(colorId));
    }

    public static void setVOrG(boolean t, View... v) {
        for (int i = 0; i < v.length; i++) {
            if (t) {
                //设置view可见
                v[i].setVisibility(View.VISIBLE);
            } else {
                //设置view不可见
                v[i].setVisibility(View.GONE);
            }
        }
    }

    //通用非空判断之后设置文本
    public static void setTexts(TextView tv, Object object) {
        if (object != null) {
            tv.setText(object + "");
        }
    }

    //通用非空判断之后设置图片
    public static void setImgSrc(ImageView iv, Object obj) {
        if (obj instanceof Integer) {
            iv.setImageResource((Integer) obj);
        } else {
            iv.setImageDrawable((Drawable) obj);
        }
    }


    public static void allDone(Activity a, View v, int colorId, ImageView leftIv, Object imgSrc, TextView centerTv, Object o) {
        v.setVisibility(View.VISIBLE);
        setHeadBackground(a, v, colorId);
        setImgSrc(leftIv, imgSrc);
        setTexts(centerTv, o);
    }

    /**
     * 华为齐刘海
     * @param context 上下文
     * @return true 有齐刘海
     */
    public static boolean hasNotchInScreenForHW(Context context)
    {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e)
        { L.e("test", "hasNotchInScreen ClassNotFoundException"); }
        catch (NoSuchMethodException e)
        { L.e("test", "hasNotchInScreen NoSuchMethodException"); }
        catch (Exception e)
        { L.e("test", "hasNotchInScreen Exception"); }
        finally
        { return ret; }
    }
    /**
     * oppo
     * @param context 上下文
     * @return true 有齐刘海
     */
    public static boolean hasNotchInScreenForOPPO(Context context){
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * vivo
     * @param context 上下文
     * @return true 有齐刘海
     */
    public static boolean hasNotchInScreenForVIVO(Context context) {
        int NOTCH_IN_SCREEN_VOIO=0x00000020;//是否有凹槽
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class FtFeature = cl.loadClass("com.util.FtFeature");
            Method get = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) get.invoke(FtFeature, NOTCH_IN_SCREEN_VOIO);

        } catch (ClassNotFoundException e) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "hasNotchInScreen Exception");
        } finally {
            return ret;
        }
    }

}
