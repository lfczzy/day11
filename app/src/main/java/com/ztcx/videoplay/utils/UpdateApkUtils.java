package com.ztcx.videoplay.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;

import com.ztcx.videoplay.intef.DownListener;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by LCH on 2018/10/16.
 *
 * 更新
 */
public class UpdateApkUtils {
    private static boolean isDownFinish;
    private static boolean isDowing;
    private static File apkFile;

    /**
     * 下载文件
     * @param downloadUrl 下载链接
     * @param context 上下文
     */
    public static void NetUntils(final String downloadUrl, final Activity context, final Handler handler) {
        //获取权限
        getPermission(context, new OnPermission() {
            @Override
            public void onHasPermission() {
                //下载完成
                if (isDownFinish){
                    installApkNew(context,apkFile);
                }
                //正在下载
                if (isDowing){
                    return;
                }
                //获取读写权限
                String filePath = Environment.getExternalStorageDirectory().getPath();
                String fileName = "bbb.apk";
                deleteFile(new File(filePath, fileName));
                BaseOkGoUtils.downloadFile(context, downloadUrl, filePath, fileName, new DownListener() {

                    @Override
                    public void onSucceeded(File file) {
                        installApkNew(context,file);
                        isDownFinish = true;
                        UpdateApkUtils.apkFile = file;

                        //这里可以将下载好的文件保存起来，版本号、文件名
                    }
                    @Override
                    public void onProgress(int progress) {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = progress+"";
                        handler.sendMessage(msg);
                        isDowing = true;
                    }
                });
            }

            @Override
            public void onNotPermission() {

            }
        });
    }

    /**
     * 删除单个文件
     *
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(File file) {
        try{
            //伤处文件
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    L.e("qpf","删除成功！");
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }



    /**
     * 安装apk
     * @param context
     * @param apkFile
     */
    public static void installApkNew(Context context, File apkFile) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", apkFile);//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(install);
        } else{
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(install);
        }
    }


    //获取权限
    private static void getPermission(Activity activity, OnPermission onPermission) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(activity, permissions)) {
            onPermission.onHasPermission();
        } else {
            onPermission.onNotPermission();
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(activity, "您需要开启文件读写权限！", 1, permissions);
        }

    }

    interface OnPermission{
        void onHasPermission();
        void onNotPermission();
    }

}
