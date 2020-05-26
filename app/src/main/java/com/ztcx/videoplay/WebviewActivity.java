package com.ztcx.videoplay;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.utils.L;

import org.jsoup.helper.StringUtil;

import java.util.EventListener;
import java.util.Timer;
import java.util.TimerTask;

public class WebviewActivity extends NativeBaseActivity {

    private WebView mWebview;
    private String url;
    private String jxUrl;

    @Override
    public void init() {
        super.init();
        url = getIntent().getStringExtra("url");
        jxUrl = getIntent().getStringExtra("jxUrl");
    }

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_webview;
    }

    @Override
    public void setUpView() {

        setVisibleStatusBar("#666666");

        mWebview = findViewById(R.id.webview);
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView webView, String s) {
                super.onLoadResource(webView, s);
                L.e("qpf","加载--- " + webView.getProgress() + s.toString());
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);

//                findViewById(R.id.mrl_loading).setVisibility(View.GONE);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                L.e("qpf","拦截 -- " + s);

                if (s.equals("http://m.v.qq.com/search.html?keyWord=%E4%BC%9A%E5%91%98%E7%94%B5%E5%BD%B1")){
                    finish();
                }else if (s.contains(".migu")){ //咪咕的特殊处理
                    if (s.contains("/video/more-list")){
                        return super.shouldOverrideUrlLoading(webView, s);
                    }else if (s.contains("/miguH5/detail/detail.jsp?cid")){
                        webView.loadUrl(jxUrl+s);
                        return true;
                    }else {
                        return true;
                    }
                }else if (s.contains("m.mgtv")){ //芒果TV
                    if (s.contains("m.mgtv.com/b/")){
                        L.e("qpf","芒果tv -- " + url);
                        webView.loadUrl(jxUrl+s);
                        return true;
                    }else {
                        return true;
                    }
                }else if (s.contains("https://www.wasu.cn")){//优酷
                    if (s.startsWith("https://www.wasu.cn/wap/play/show/id/")){
                        webView.loadUrl(jxUrl+s);
                        return true;
                    }else if (s.startsWith("https://www.wasu.cn/wap/list/index/")){
                        return super.shouldOverrideUrlLoading(webView, s);
                    }else {
                        return true;
                    }
                }else if (s.startsWith("https://m.tv.sohu.com")){
                    if (s.startsWith("https://m.tv.sohu.com/filter/cid_1/subid_1000000")){
                        return super.shouldOverrideUrlLoading(webView, s);
                    }else if (s.contains(".shtml?aid=")){
                        webView.loadUrl(jxUrl+s);
                        return true;
                    }else {
                        return true;
                    }
                }else {
                    webView.loadUrl(jxUrl+s);
                    return true;
                }

//                return true;
               return super.shouldOverrideUrlLoading(webView, s);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url){
                url= url.toLowerCase();
                if(url.contains(".gif")
//                        && url.contains("https://cnzz.mmstat.com")
                        ){
//                        || url.contains(".jpg") || url.contains(".png")){
                    L.e("qpf","广告 -- " + url);
                    return new WebResourceResponse(null,null,null);//含有广告资源屏蔽请求
//                   return super.shouldInterceptRequest(view,"https://p0.ssl.qhimg.com/d/_hao360/default.png");//正常加载
                }else{
                    return super.shouldInterceptRequest(view,url);//正常加载
                }
            }


        });        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadUrl(url);

    }

    @Override
    public void setUpData() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mWebview.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
            mWebview.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }else {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
