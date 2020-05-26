package com.ztcx.videoplay.fragment.home;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.ztcx.videoplay.GlideImageLoader;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.activity.SearchActivity;
import com.ztcx.videoplay.adapter.HomeBmobAdapter;
import com.ztcx.videoplay.adapter.HomeMovieAdapter;
import com.ztcx.videoplay.adapter.HomeTvAdapter;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.base.NativeBaseFragment;
import com.ztcx.videoplay.been.Appconfig;
import com.ztcx.videoplay.been.BannerBean;
import com.ztcx.videoplay.been.Base360Video;
import com.ztcx.videoplay.been.HomeBean;
import com.ztcx.videoplay.been.UserInfo;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.constant.HttpConstant;
import com.ztcx.videoplay.constant.SPConstant;
import com.ztcx.videoplay.dialog.ButtomDialogView;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.DisplayTools;
import com.ztcx.videoplay.utils.GetVideoList;
import com.ztcx.videoplay.utils.KVUtils;
import com.ztcx.videoplay.utils.L;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class HomeFragment extends NativeBaseFragment {
    private HomeBean homeBean;
    private Banner banner;
    private TextView mTvBannerSubTitle;
    private TextView mTvBannerTitle;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    setHomeData();
                    mRlNoData.setVisibility(View.GONE);
                    mRlLoading.setVisibility(View.GONE);
                    break;
                case 301:
                    break;
            }
        }
    };
    private TagFlowLayout mFlowLayout;
    private TagFlowLayout mFlowLayoutMovie;
    private TagFlowLayout mFlowLayoutDongman;
    private TagFlowLayout mFlowLayoutZongyi;
    private RecyclerView mRvHomeTv;
    private RecyclerView mRvHomeMovie;
    private RecyclerView mRvHomeDongman;
    private RecyclerView mRvHomeZongyi;
    private HomeTvAdapter homeDongmanAdapter;
    private HomeTvAdapter homeZongyiAdapter;
    private NestedScrollView mScrollView;
    private RecyclerView mRvHomeNew;
    private HomeBmobAdapter homeBmobAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpView() {

        mRlNoData.setVisibility(View.VISIBLE);


        mTvTitleCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
                Toast.makeText(getActivity(),"搜索",Toast.LENGTH_SHORT).show();
            }
        });


        mTvBannerSubTitle = getContentView().findViewById(R.id.tv_banner_sub_title);
        mTvBannerTitle = getContentView().findViewById(R.id.tv_banner_title);

        mFlowLayout = getContentView().findViewById(R.id.id_flowlayout);
        mFlowLayoutMovie = getContentView().findViewById(R.id.id_flowlayout_movie);
        mFlowLayoutDongman = getContentView().findViewById(R.id.id_flowlayout_dongman);
        mFlowLayoutZongyi = getContentView().findViewById(R.id.id_flowlayout_zongyi);

        mRvHomeTv = getContentView().findViewById(R.id.rv_home_tv);
        mRvHomeTv.setLayoutManager(new GridLayoutManager(getMContext(),2));
        mRvHomeMovie = getContentView().findViewById(R.id.rv_home_movie);
        mRvHomeMovie.setLayoutManager(new GridLayoutManager(getMContext(),2));
        mRvHomeDongman = getContentView().findViewById(R.id.rv_home_dongman);
        mRvHomeDongman.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRvHomeZongyi= getContentView().findViewById(R.id.rv_home_zongyi);
        mRvHomeZongyi.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        mRvHomeNew= getContentView().findViewById(R.id.rv_home_new);
        mRvHomeNew.setLayoutManager(new LinearLayoutManager(getMContext(),LinearLayoutManager.HORIZONTAL,false));


        banner = getContentView().findViewById(R.id.banner);
        final ViewGroup.LayoutParams params = banner.getLayoutParams();
        params.width = MyApp.windowwidth;
        params.height = params.width * 330 / 720;
        banner.setLayoutParams(params);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setDelayTime(5000);

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvBannerTitle.setText(homeBean.getBannerList().get(position).getTitle());
                mTvBannerSubTitle.setText(homeBean.getBannerList().get(position).getSubTitle());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = homeBean.getBannerList().get(position);
                String type = bannerBean.getBannerType();
                L.e("qpf","bannertype == " + type);
                if (AppConstant.BANNER_TYPE_QQ.equals(type)){
                    CommUtils.goToQQ(getMContext(),bannerBean.getUrl());
                }else {
                    CommUtils.jumpPlayVideo(getActivity(),homeBean.getBannerList().get(position).getUrl());
                }


            }
        });
    }

    private void setHomeData() {
        Appconfig appconfig = new Gson().fromJson(KVUtils.query(SPConstant.APPCONFIG_JSON),Appconfig.class);
        List<BannerBean> bmobBannerList = appconfig.getBannerBeanList();
        if (bmobBannerList != null && bmobBannerList.size() > 0){
            homeBean.getBannerList().addAll(0,bmobBannerList);
        }

        banner.setImages(homeBean.getBannerList());
        banner.start();
        mFlowLayout.setAdapter(new TagAdapter(homeBean.getTvTtagList()) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View view = LayoutInflater.from(getMContext()).inflate(R.layout.item_tag_view,null);
                TextView tvTag = view.findViewById(R.id.tv_tag);
                tvTag.setText(homeBean.getTvTtagList().get(position).getName());
                return view;
            }
        });
        mFlowLayoutMovie.setAdapter(new TagAdapter(homeBean.getMovieTagList()) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View view = LayoutInflater.from(getMContext()).inflate(R.layout.item_tag_view,null);
                TextView tvTag = view.findViewById(R.id.tv_tag);
                tvTag.setText(homeBean.getMovieTagList().get(position).getName());
                return view;
            }
        });
        mFlowLayoutDongman.setAdapter(new TagAdapter(homeBean.getDongmanTagList()) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View view = LayoutInflater.from(getMContext()).inflate(R.layout.item_tag_view,null);
                TextView tvTag = view.findViewById(R.id.tv_tag);
                tvTag.setText(homeBean.getDongmanTagList().get(position).getName());
                return view;
            }
        });
        mFlowLayoutZongyi.setAdapter(new TagAdapter(homeBean.getZongyiTagList()) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View view = LayoutInflater.from(getMContext()).inflate(R.layout.item_tag_view,null);
                TextView tvTag = view.findViewById(R.id.tv_tag);
                tvTag.setText(homeBean.getZongyiTagList().get(position).getName());
                return view;
            }
        });
        final HomeTvAdapter homeTvAdapter = new HomeTvAdapter(R.layout.item_home_tv,homeBean.getHomeTvList());
        mRvHomeTv.setAdapter(homeTvAdapter);
        final HomeMovieAdapter homeMovieAdapter = new HomeMovieAdapter(R.layout.item_home_tv,homeBean.getHomeMovieList());
        mRvHomeMovie.setAdapter(homeMovieAdapter);

        homeDongmanAdapter = new HomeTvAdapter(R.layout.item_home_tv,homeBean.getHomeDongManList());
        setDongmanHeader(homeBean.getHomeDongManList().get(0));
        homeBean.getHomeDongManList().remove(0);
        mRvHomeDongman.setAdapter(homeDongmanAdapter);

        homeZongyiAdapter = new HomeTvAdapter(R.layout.item_home_tv, homeBean.getHomeZongyiList());
        setZongyiHeader(homeBean.getHomeZongyiList().get(0));
        homeBean.getHomeZongyiList().remove(0);
        mRvHomeZongyi.setAdapter(homeZongyiAdapter);


        homeTvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommUtils.jumpPlayVideo(getActivity(),homeBean.getHomeTvList().get(position));
            }
        });
        homeMovieAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommUtils.jumpPlayVideo(getActivity(),homeBean.getHomeMovieList().get(position));
            }
        });
        homeDongmanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommUtils.jumpPlayVideo(getActivity(),homeBean.getHomeDongManList().get(position));
            }
        });
        homeZongyiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommUtils.jumpPlayVideo(getActivity(),homeBean.getHomeZongyiList().get(position));
            }
        });

        homeTvAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                ButtomDialogView view1 = new ButtomDialogView(getMContext(),homeTvAdapter.getItem(position));
                view1.show();
                return true;
            }
        });
        homeMovieAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                ButtomDialogView view1 = new ButtomDialogView(getMContext(),homeMovieAdapter.getItem(position));
                view1.show();
                return true;
            }
        });
        homeDongmanAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                ButtomDialogView view1 = new ButtomDialogView(getMContext(),homeDongmanAdapter.getItem(position));
                view1.show();
                return true;
            }
        });
        homeZongyiAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                ButtomDialogView view1 = new ButtomDialogView(getMContext(),homeZongyiAdapter.getItem(position));
                view1.show();
                return true;
            }
        });

    }

    private void setDongmanHeader(final Base360Video item) {
        final View dongmanHeader = LayoutInflater.from(getMContext()).inflate(R.layout.item_home_tv,null);
        LinearLayout mLlItem = dongmanHeader.findViewById(R.id.ll_item);
        ViewGroup.LayoutParams params = mLlItem.getLayoutParams();
        params.width = MyApp.windowwidth - DisplayTools.dip2px(10,getMContext());
        mLlItem.setLayoutParams(params);
        RelativeLayout mRlImageWH = dongmanHeader.findViewById(R.id.rl_image_w_h);
        ViewGroup.LayoutParams paramsImage = mRlImageWH.getLayoutParams();
        paramsImage.width = params.width;
        paramsImage.height = params.width * 100 / 180;
        mRlImageWH.setLayoutParams(paramsImage);
        homeDongmanAdapter.addHeaderView(dongmanHeader);

        ImageView imageView = dongmanHeader.findViewById(R.id.iv_video);
        TextView mTvTitle = dongmanHeader.findViewById(R.id.tv_video_name);
        TextView mTvSubTitle = dongmanHeader.findViewById(R.id.tv_subtitle);
        TextView mTvNewJi = dongmanHeader.findViewById(R.id.tv_new_ji);

        //视频图片
        Glide.with(getMContext()).load(item.getImgUrl()).into(imageView);
        //视频名称
        mTvTitle.setText(item.getVideoName());
        //副标题
        mTvSubTitle.setText(item.getSubTitle());
        //更新集数
        mTvNewJi.setText(item.getUpdateInfo());

        dongmanHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommUtils.jumpPlayVideo(getActivity(),item);
            }
        });
    }

    private void setZongyiHeader(final Base360Video item) {
        View zingyiHeader = LayoutInflater.from(getMContext()).inflate(R.layout.item_home_tv,null);
        LinearLayout mLlItem = zingyiHeader.findViewById(R.id.ll_item);
        ViewGroup.LayoutParams params = mLlItem.getLayoutParams();
        params.width = MyApp.windowwidth - DisplayTools.dip2px(10,getMContext());
        mLlItem.setLayoutParams(params);
        RelativeLayout mRlImageWH = zingyiHeader.findViewById(R.id.rl_image_w_h);
        ViewGroup.LayoutParams paramsImage = mRlImageWH.getLayoutParams();
        paramsImage.width = params.width;
        paramsImage.height = params.width * 100 / 180;
        mRlImageWH.setLayoutParams(paramsImage);
        homeZongyiAdapter.addHeaderView(zingyiHeader);

        ImageView imageView = zingyiHeader.findViewById(R.id.iv_video);
        TextView mTvTitle = zingyiHeader.findViewById(R.id.tv_video_name);
        TextView mTvSubTitle = zingyiHeader.findViewById(R.id.tv_subtitle);
        TextView mTvNewJi = zingyiHeader.findViewById(R.id.tv_new_ji);

        //视频图片
        Glide.with(getMContext()).load(item.getImgUrl()).into(imageView);
        //视频名称
        mTvTitle.setText(item.getVideoName());
        //副标题
        mTvSubTitle.setText(item.getSubTitle());
        //更新集数
        mTvNewJi.setText(item.getUpdateInfo());

        zingyiHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommUtils.jumpPlayVideo(getActivity(),item);
            }
        });
    }



    @Override
    protected void setUpData() {
        //获取影院同步的视屏
        BmobQuery<Base360Video> query = new BmobQuery<>();
        query.order("-sort");
        query.findObjects(getMContext(), new FindListener<Base360Video>() {
            @Override
            public void onSuccess(List<Base360Video> list) {
                homeBmobAdapter = new HomeBmobAdapter(R.layout.item_home_bmob,list);
                mRvHomeNew.setAdapter(homeBmobAdapter);

                homeBmobAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                        ButtomDialogView dialogView = new ButtomDialogView(getActivity(),homeBmobAdapter.getItem(position));
                        dialogView.show();
                        return true;
                    }
                });

                homeBmobAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CommUtils.jumpPlayVideo(getActivity(),homeBmobAdapter.getData().get(position));
                    }
                });
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        mRlLoading.setVisibility(View.VISIBLE);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    homeBean = GetVideoList.getOutHome(HttpConstant.HOME);
                    L.e("qpf","获取到了 -- " + homeBean.toString());
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = homeBean;
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
