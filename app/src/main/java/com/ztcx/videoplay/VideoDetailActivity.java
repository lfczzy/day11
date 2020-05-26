package com.ztcx.videoplay;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonArray;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.ztcx.videoplay.adapter.CommentAdapter;
import com.ztcx.videoplay.adapter.NewDangQiAdapter;
import com.ztcx.videoplay.adapter.RecommendAdapter;
import com.ztcx.videoplay.adapter.XuanJiAdapter;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.been.Base360Video;
import com.ztcx.videoplay.been.VideoDownParseBean;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.constant.SPConstant;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.GetVideoList;
import com.ztcx.videoplay.utils.KVUtils;
import com.ztcx.videoplay.utils.L;

import org.jsoup.helper.StringUtil;

/**
 * 电影详情页面
 */
public class VideoDetailActivity extends NativeBaseActivity {
    private String detailUrl;
    private String videoType;
    private VideoDownParseBean resultList;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    setVideoDetail();
                    break;
                case 301:
                    getVideo(detailUrl);
                    break;
            }
        }
    };
    private TextView mTvVideoName;
    private TextView mTvArea;
    private TextView mTvStyle;
    private TextView mTvDirect;
    private TextView mTvLeadingRole;
    private TextView mTvScore;
    private RecyclerView mRvComment;
    private RecyclerView mRvRecommend;
    private RecyclerView mRvXuanji;
    private RecyclerView mRvDangQi;
    private TextView mTvTotalJi;
    private TextView mTvTotalQi;
    private com.tencent.smtt.sdk.WebView mWebView;
    private RelativeLayout mRlVideoMainView;
    private Base360Video base360Video;

    private String jx_url;


    //获取视频

    @Override
    public void init() {
        super.init();
        detailUrl = getIntent().getStringExtra("detailUrl");
        videoType = getIntent().getStringExtra("videoType");
        base360Video = (Base360Video) getIntent().getSerializableExtra("baseVideo");
        if (base360Video != null){
            CommUtils.addHistory(this,base360Video);
            detailUrl = base360Video.getNextUrl();
        }


        if (detailUrl.contains("www.360kan.com/m/")){
            videoType = AppConstant.VIDEO_TYPE_MOVIE;
        }else if (detailUrl.contains("www.360kan.com/tv/")){
            videoType = AppConstant.VIDEO_TYPE_TV;
        }else if (detailUrl.contains("www.360kan.com/va")){
            videoType = AppConstant.VIDEO_TYPE_ZONGIY;
        }else if (detailUrl.contains("www.360kan.com/ct")){
            videoType = AppConstant.VIDEO_TYPE_DONGMAN;
        }

        jx_url = KVUtils.query(SPConstant.JX_URL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVisibleStatusBar(AppConstant.BLACK_COLOR);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void setUpView() {
        mTvVideoName = findViewById(R.id.tv_video_name);
        mTvArea = findViewById(R.id.tv_area);
        mTvStyle = findViewById(R.id.tv_style);
        mTvDirect = findViewById(R.id.tv_direct);
        mTvLeadingRole = findViewById(R.id.tv_leading_role);
        mTvScore = findViewById(R.id.tv_score);
        mTvTotalJi = findViewById(R.id.tv_total_ji);
        mTvTotalQi = findViewById(R.id.tv_total_qi);

        mRvComment = findViewById(R.id.rv_comment);
        mRvComment.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvRecommend = findViewById(R.id.rv_recommend);
        mRvRecommend.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRvXuanji = findViewById(R.id.rv_xuanji);
        mRvXuanji.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRvDangQi = findViewById(R.id.rv_new_dangqi);
        mRvDangQi.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mRlVideoMainView = findViewById(R.id.rl_video_main_view);
        ViewGroup.LayoutParams params = mRlVideoMainView.getLayoutParams();
        params.width = MyApp.windowwidth;
        params.height = params.width * MyApp.windowwidth / MyApp.windowheight;
        mRlVideoMainView.setLayoutParams(params);

        mWebView = findViewById(R.id.webview);

        findViewById(R.id.ic_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void setUpData() {
        getVideo(detailUrl);
    }

    /**
     * 获取要播放的数据
     */
    public void getVideo(final String url) {
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    if (AppConstant.VIDEO_TYPE_MOVIE.equals(videoType)){
                        resultList =  GetVideoList.getMoveStartUrl(url);
                    }else if (AppConstant.VIDEO_TYPE_TV.equals(videoType)){
                        resultList =  GetVideoList.getTVStartUrl(url);
                    }else if (AppConstant.VIDEO_TYPE_ZONGIY.equals(videoType)){
                        resultList =  GetVideoList.getZongYiStartUrl(url);
                    }else if (AppConstant.VIDEO_TYPE_DONGMAN.equals(videoType)){
                        resultList =  GetVideoList.getDongManStartUrl(url);
                    }
                    Message message = Message.obtain();

                    L.e("qpf","播放链接 -- " + resultList.toString());
                    if(resultList != null){
                        message.what = 0;
                        message.obj = resultList;
                    }else{
                        message.what = 301;
                    }
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void setVideoDetail() {
        mTvVideoName.setText(resultList.getName());
        if (StringUtil.isBlank(resultList.getScore())){
            findViewById(R.id.ll_score).setVisibility(View.GONE);
        }else {
            mTvScore.setText(resultList.getScore());
        }
        mTvArea.setText(resultList.getDiqu());
        String style = resultList.getType();
        mTvStyle.setText(style);
        mTvDirect.setText(resultList.getDaoyan());
        mTvLeadingRole.setText(resultList.getYanyuan());

        if (StringUtil.isBlank(resultList.getDiqu()) && StringUtil.isBlank(resultList.getType())){
            findViewById(R.id.ll_type_area).setVisibility(View.GONE);
        }
        if (StringUtil.isBlank(resultList.getDaoyan())){
            findViewById(R.id.ll_direct).setVisibility(View.GONE);
        }
        if (StringUtil.isBlank(resultList.getYanyuan())){
            findViewById(R.id.ll_leading_role).setVisibility(View.GONE);
        }
        //选集
        if (resultList.getXuanJiList() == null || resultList.getXuanJiList().size() == 0){
            findViewById(R.id.ll_xuanji).setVisibility(View.GONE);
        }else {
            resultList.setPlayUrl(resultList.getXuanJiList().get(0).getPlayUrl());
            resultList.getXuanJiList().get(0).setIscheck(true);
            final XuanJiAdapter xuanjiAdapter = new XuanJiAdapter(R.layout.item_xuanji,resultList.getXuanJiList());
            mRvXuanji.setAdapter(xuanjiAdapter);
            if (!StringUtil.isBlank(resultList.getJishuTag())) {
                mTvTotalJi.setText(resultList.getJishuTag());
            }
            findViewById(R.id.ll_xuanji).setVisibility(View.VISIBLE);

            xuanjiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    for (VideoDownParseBean.XuanJi xuanJi:resultList.getXuanJiList()){
                        xuanJi.setIscheck(false);
                    }

                    resultList.getXuanJiList().get(position).setIscheck(true);
                    xuanjiAdapter.notifyDataSetChanged();
                    resultList.setPlayUrl(resultList.getXuanJiList().get(position).getPlayUrl());
                    mWebView.loadUrl(jx_url+resultList.getPlayUrl());
                }
            });
        }


        if (resultList.getCommentList() == null || resultList.getCommentList().size() == 0){
            findViewById(R.id.ll_comment).setVisibility(View.GONE);
        }else {
            //热门评论
            mRvComment.setAdapter(new CommentAdapter(R.layout.item_comment,resultList.getCommentList()));
        }

        //猜你喜欢
        if (resultList.getRecommendVideoList() == null
                || resultList.getRecommendVideoList().size() == 0){
            findViewById(R.id.ll_recommend).setVisibility(View.GONE);
        }else {
            RecommendAdapter adapter = new RecommendAdapter(R.layout.item_recommend, resultList.getRecommendVideoList());
            mRvRecommend.setAdapter(adapter);

            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    getVideo(resultList.getRecommendVideoList().get(position).getNextUrl());
                }
            });
        }


        //最新档期
        if (videoType.equals(AppConstant.VIDEO_TYPE_ZONGIY)){
            findViewById(R.id.ll_zy_new).setVisibility(View.VISIBLE);
            NewDangQiAdapter newDangqiAdapter = new NewDangQiAdapter(R.layout.item_ambitus_video,resultList.getNewDangQiList());
            mRvDangQi.setAdapter(newDangqiAdapter);
            mTvTotalQi.setText(resultList.getJishuTag());
            resultList.setPlayUrl(resultList.getNewDangQiList().get(0).getPlayUrl());
            newDangqiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    resultList.setPlayUrl(resultList.getNewDangQiList().get(position).getPlayUrl());
                    mWebView.loadUrl(jx_url+resultList.getPlayUrl());
                }
            });

        }

        mWebView.loadUrl(jx_url+resultList.getPlayUrl());
        mWebView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        mWebView.getSettings().setJavaScriptEnabled(true);

       mWebView.setWebViewClient(new WebViewClient(){
           @Override
           public void onLoadResource(WebView webView, String s) {
               super.onLoadResource(webView, s);
               L.e("qpf","加载--- " + webView.getProgress() + s.toString());
           }

           @Override
           public void onPageFinished(WebView webView, String s) {
               super.onPageFinished(webView, s);

               findViewById(R.id.mrl_loading).setVisibility(View.GONE);

           }

           @Override
           public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                L.e("qpf","拦截 -- " + s);
               return true;
//               return super.shouldOverrideUrlLoading(webView, s);
           }

           @Override
           public WebResourceResponse shouldInterceptRequest(WebView view, String url){
               url= url.toLowerCase();
               if(url.contains(".gif") || url.contains(".jpg") || url.contains(".png")){
                   L.e("qpf","广告 -- " + url);
                   return new WebResourceResponse(null,null,null);//含有广告资源屏蔽请求
//                   return super.shouldInterceptRequest(view,"https://p0.ssl.qhimg.com/d/_hao360/default.png");//正常加载
               }else{
                   return super.shouldInterceptRequest(view,url);//正常加载
               }
           }


       });

    }




    @Override
    protected void onDestroy() {

        //在Activity销毁的时候同时销毁WebView
        //如没有此操作，可能会出现，当你在网页上播放一个视频的时候，直接按home键退出应用，视频仍在播放
        if (mWebView != null) {
            mWebView.destroy();
//            mFl_web_view_layout.removeView(mWebView);
            mWebView = null;
        }

        super.onDestroy();
    }

}
