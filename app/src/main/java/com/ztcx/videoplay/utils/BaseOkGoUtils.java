package com.ztcx.videoplay.utils;

import android.app.Activity;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.ztcx.videoplay.intef.DownListener;

import java.io.File;

/**
 * 网络请求公共类
 */
public class BaseOkGoUtils {
    /**
     *
     * @param context 上下文
     * @param fileUrl 下载完整url
     * @param destFileDir  SD路径
     * @param destFileName  文件名
     * （我们从服务器端获取到的数据都是相对的地址）例如： "filepath": "/movie/20180511/1526028508.txt"
     */
    public static void downloadFile(final Activity context, final String fileUrl, String destFileDir, String destFileName, final DownListener downListener){
        OkGo.<File>get(fileUrl).tag(context).execute(new FileCallback(destFileDir,destFileName) { //文件下载时指定下载的路径以及下载的文件的名称
            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                L.e("qpf","下载 -- onStart -- " + request);
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                File file = response.body();
                if (response != null)
                    downListener.onSucceeded(response.body());
                L.e("qpf","下载 -- onSuccess -- " + response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                L.e("qpf","下载 -- onFinish -- ");
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);

                Toast.makeText(context,"更新异常",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                float  dLProgress= progress.fraction;
                downListener.onProgress((int)(dLProgress*100));

                L.e("qpf","下载 -- downloadProgress -- " + progress);
            }
        });
    }

}
