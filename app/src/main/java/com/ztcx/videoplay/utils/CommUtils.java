package com.ztcx.videoplay.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztcx.videoplay.BmobVideoDetailActivity;
import com.ztcx.videoplay.VideoDetailActivity;
import com.ztcx.videoplay.activity.LoginActivity;
import com.ztcx.videoplay.activity.RegisterActivity;
import com.ztcx.videoplay.been.Appconfig;
import com.ztcx.videoplay.been.Base360Video;
import com.ztcx.videoplay.been.Collection;
import com.ztcx.videoplay.been.StarInfo;
import com.ztcx.videoplay.been.UserInfo;
import com.ztcx.videoplay.been.WatchHistory;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.constant.SPConstant;

import org.jsoup.helper.StringUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommUtils {

    //相关
    public static String deviceTokenUtils(String deviceToken){
        String str = null;

        if (StringUtil.isBlank(deviceToken) || deviceToken.length() < 13) {
            return str;
        }
        //相关
        str = deviceToken.substring(0,3);
        str += "****";
        str += deviceToken.substring(deviceToken.length() - 4,deviceToken.length());
        return str;
    }

    public static String getDeviceId() {
        String deviceID= "";
        try{
            //一共13位  如果位数不够可以继续添加其他信息
            deviceID = ""+Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                    Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                    Build.USER.length() % 10;
        }catch (Exception e){
            return "";
        }
        return deviceID;
    }

    public static boolean isLogin(Context context){
        UserInfo userInfo = BmobUser.getCurrentUser(context,UserInfo.class);
        if (userInfo != null){
            return true;
        }
        return false;
    }

    //是否注册
    private static boolean isCheck = false;
    public static void jumpNoLogin(final Context context){
        if (isCheck){
            return;
        }
        isCheck = true;
        BmobQuery<BmobUser> query = new BmobQuery<>();
        query.addWhereEqualTo("username",CommUtils.getDeviceId());
        query.findObjects(context, new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                if (list.size() == 0){
                    context.startActivity(new Intent(context,RegisterActivity.class));
                }else {
                    context.startActivity(new Intent(context,LoginActivity.class));
                }

                isCheck = false;
            }

            @Override
            public void onError(int i, String s) {
                context.startActivity(new Intent(context,RegisterActivity.class));
                isCheck = false;
            }
        });
    }

    /**
     * 历史记录
     */
    public static void addHistory(final Context context, Base360Video base360Video){
        final UserInfo userInfo = BmobUser.getCurrentUser(context,UserInfo.class);
        final WatchHistory history = new WatchHistory();
        history.setUser(userInfo);
        history.setImageUrl(base360Video.getImgUrl());
        history.setName(base360Video.getVideoName());
        history.setNextUrl(base360Video.getNextUrl());
        history.setType(base360Video.getVideoType());
        if (AppConstant.VIDEO_TYPE_BMOB.equals(history.getType())){
            history.setVideo(base360Video);
        }
        history.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                L.e("qpf","添加浏览记录成功！");
            }

            @Override
            public void onFailure(int i, String s) {
                L.e("qpf","添加浏览记录失败！ -- " + s);
            }
        });
    }

    /**
     * 添加收藏
     */
    public static void addCollection(final Context context, Base360Video base360Video){
        final UserInfo userInfo = BmobUser.getCurrentUser(context,UserInfo.class);
        final Collection collection = new Collection();
        collection.setUser(userInfo);
        collection.setImageUrl(base360Video.getImgUrl());
        collection.setName(base360Video.getVideoName());
        collection.setNextUrl(base360Video.getNextUrl());
        collection.setType(base360Video.getVideoType());
        if (AppConstant.VIDEO_TYPE_BMOB.equals(collection.getType())){
            collection.setVideo(base360Video);
        }
        collection.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context,"收藏成功！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                L.e("qpf","添加收藏失败！ -- " + s);
            }
        });
    }

    /**
     * 跳转
     */
    public static void jumpPlayVideo(Context activity,Base360Video base360Video){
        if (isLogin(activity)){
            if (AppConstant.VIDEO_TYPE_BMOB.equals(base360Video.getVideoType())){
                Intent intent = new Intent(activity,BmobVideoDetailActivity.class);
                intent.putExtra("baseVideo",base360Video);
                activity.startActivity(intent);
            }else {
                Intent intent = new Intent(activity,VideoDetailActivity.class);
                intent.putExtra("baseVideo",base360Video);
                activity.startActivity(intent);
            }
        }else {
            jumpNoLogin(activity);
        }
    }
    /**
     * 跳转
     */
    public static void jumpPlayVideo(Context activity,String detailUrl){
        if (isLogin(activity)){
            Intent intent = new Intent(activity,VideoDetailActivity.class);
            intent.putExtra("detailUrl",detailUrl);
            activity.startActivity(intent);
        }else {
            jumpNoLogin(activity);
        }
    }

    /**
     * 跳转html方法
     */
    public static void goToQQ(Context context,String qq) {
        String url = "mqqwpa://im/chat?chat_type=wpa&uin="+qq+"&version=1";
        Uri uri = Uri.parse(url);   //指定网址
        /*Intent intent = new Intent(this,WebViewActivity.class);
        intent.putExtra("url", url);*/
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);           //指定Action
        intent.setData(uri);                            //设置Uri*/
        context.startActivity(intent);
    }

    /****************
     *
     * 发起添加群流程。群号：小谢&amp;朋友(200379886) 的 key 为： aY99DLoHtabqmu0RvzNECM9I-17IANxO
     * 调用 joinQQGroup(aY99DLoHtabqmu0RvzNECM9I-17IANxO) 即可发起手Q客户端申请加群 小谢&amp;朋友(200379886)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public static boolean joinQQGroup(Context context,String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

}
