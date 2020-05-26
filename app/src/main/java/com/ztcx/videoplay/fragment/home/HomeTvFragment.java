package com.ztcx.videoplay.fragment.home;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.ztcx.videoplay.GlideImageLoader;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.adapter.HomeTvAdapter;
import com.ztcx.videoplay.adapter.RecommendAdapter;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.base.NativeBaseFragment;
import com.ztcx.videoplay.been.HomeMovie;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.constant.HttpConstant;
import com.ztcx.videoplay.utils.GetVideoList;
import com.ztcx.videoplay.utils.L;

/**
 * 首页电影页面
 */
public class HomeTvFragment extends NativeBaseFragment {
    private HomeMovie homeMovie;
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    L.e("qpf","成功 -- " + homeMovie.toString());
                    banner.setImages(homeMovie.getBannerBeanList());
                    banner.start();

                    mRvNewMovie.setAdapter(new HomeTvAdapter(R.layout.item_home_tv,homeMovie.getNewMovieList()));
                    mRvRankingMovie.setAdapter(new RecommendAdapter(R.layout.item_recommend,homeMovie.getRankingMovieList()));
//                    mRvRecommendMovie.setAdapter(new RecommendAdapter(R.layout.item_recommend,homeMovie.getRecommendMovieList()));
//                    mRvHotMovie.setAdapter(new RecommendAdapter(R.layout.item_recommend,homeMovie.getHotAllMovieList()));
                    break;
                case 301:
                    break;
            }
        }
    };
    private Banner banner;
    private RecyclerView mRvNewMovie;
    private RecyclerView mRvRankingMovie;
    private RecyclerView mRvRecommendMovie;
    private RecyclerView mRvHotMovie;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_movie;
    }

    @Override
    protected void setUpView() {
//        setVisibleTitleBar(AppConstant.MAIN_COLOR,"搜索 筛选");

        mRvNewMovie = getContentView().findViewById(R.id.rv_new_movie);
        mRvNewMovie.setLayoutManager(new GridLayoutManager(getMContext(),2));
        mRvRankingMovie = getContentView().findViewById(R.id.rv_ranking_movie);
        mRvRankingMovie.setLayoutManager(new LinearLayoutManager(getMContext(),LinearLayoutManager.HORIZONTAL,false));
        mRvRecommendMovie = getContentView().findViewById(R.id.rv_recommend_movie);
        mRvRecommendMovie.setLayoutManager(new LinearLayoutManager(getMContext(),LinearLayoutManager.HORIZONTAL,false));
        mRvHotMovie = getContentView().findViewById(R.id.rv_hot_movie);
        mRvHotMovie.setLayoutManager(new LinearLayoutManager(getMContext(),LinearLayoutManager.HORIZONTAL,false));

        banner = getContentView().findViewById(R.id.banner);
        ViewGroup.LayoutParams params = banner.getLayoutParams();
        params.width = MyApp.windowwidth;
        params.height = params.width * 77 / 310;
        banner.setLayoutParams(params);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setDelayTime(5000);
    }

    @Override
    protected void setUpData() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    homeMovie = GetVideoList.getHomeMovie(HttpConstant.HOME_MOVIE);
                    L.e("qpf","获取到了 -- " + homeMovie.toString());
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = homeMovie;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    L.e("qpf", "获取到了 -- 错误 -- " + e.toString());
                    //重试
                    Message message = new Message();
                    message.what = 301;
                    handler.sendMessage(message);
                }
            }
        };
        t.start();

    }
}
