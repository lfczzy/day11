package com.ztcx.videoplay.intef;

import java.io.File;

/**
 * Created by icebox12 on 2017/a/13.
 *
 * 下载监听
 */
public interface DownListener {
    void onSucceeded(File file);
    void onProgress(int progress);
}
