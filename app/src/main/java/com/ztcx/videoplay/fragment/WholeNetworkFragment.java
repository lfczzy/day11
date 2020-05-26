package com.ztcx.videoplay.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.ztcx.videoplay.GlideImageLoader;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.WebviewActivity;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.base.NativeBaseFragment;
import com.ztcx.videoplay.been.Appconfig;
import com.ztcx.videoplay.been.BannerBean;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.constant.SPConstant;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.KVUtils;
import com.ztcx.videoplay.utils.L;

import org.jsoup.helper.StringUtil;

import java.util.List;

/**
 * 全网vip
 */
public class WholeNetworkFragment extends NativeBaseFragment {
    private TextView mTvNotice;
    private Banner banner;
    private List<BannerBean> bmobBannerList;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_whole_network_vip;
    }

    @Override
    protected void setUpView() {
        setVisibleTitleBar(AppConstant.MAIN_COLOR,"免VIP电影");

        mTvNotice = getContentView().findViewById(R.id.tv_notice);

        String notice = KVUtils.query(SPConstant.NOTICE);
        if (!StringUtil.isBlank(notice)){
            mTvNotice.setText(notice);
            mTvNotice.setSelected(true);
        }

        getContentView().findViewById(R.id.ll_aqy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://m.iqiyi.com/search/termQuery.html?is_purchase=2%3Bshould&ctgname=%E7%94%B5%E5%BD%B1&data_type=1&graph_type=1_1_0_-1&real_query=%E4%BC%9A%E5%91%98%E7%94%B5%E5%BD%B1&mode=11";
                String name = "爱奇艺";
                String jxUrl = "https://jx.618g.com/?url=";
                Intent intent = new Intent(getActivity(),WebviewActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                intent.putExtra("jxUrl",jxUrl);
                startActivity(intent);

            }
        });
        getContentView().findViewById(R.id.ll_tencent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://m.v.qq.com/search/intents.html?context=%7B%22intention_id%22%3A%223%22%2C%22num%22%3A%2212%22%2C%22plat%22%3A%229%22%2C%22pver%22%3A%224%22%2C%22query%22%3A%22%E4%BC%9A%E5%91%98%E7%94%B5%E5%BD%B1%22%2C%22req_type%22%3A%223%22%7D&title=%05%E4%BC%9A%E5%91%98%E7%94%B5%E5%BD%B1%06";
                String name = "腾讯视频";
                String jxUrl = "https://jx.618g.com/?url=";
                Intent intent = new Intent(getActivity(),WebviewActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                intent.putExtra("jxUrl",jxUrl);
                startActivity(intent);

            }
        });
        getContentView().findViewById(R.id.ll_youku).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.wasu.cn/wap/list/index/cid/1";
                String name = "华数TV";
                String jxUrl = "https://jx.618g.com/?url=";
                Intent intent = new Intent(getActivity(),WebviewActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                intent.putExtra("jxUrl",jxUrl);
                startActivity(intent);

            }
        });
        getContentView().findViewById(R.id.ll_migu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.migu.cn/video/more-list-pic/42806.html?migu_p=h5&type=st";
                String name = "咪咕";
                String jxUrl = "https://jx.618g.com/?url=";
                Intent intent = new Intent(getActivity(),WebviewActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                intent.putExtra("jxUrl",jxUrl);
                startActivity(intent);

            }
        });
        getContentView().findViewById(R.id.ll_mangguo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://m.mgtv.com/sort/3/-a4-----------.html?channelId=3";
                String name = "芒果TV";
                String jxUrl = "https://jx.618g.com/?url=";
                Intent intent = new Intent(getActivity(),WebviewActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                intent.putExtra("jxUrl",jxUrl);
                startActivity(intent);

            }
        });
        getContentView().findViewById(R.id.ll_souhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://m.tv.sohu.com/filter/cid_1/subid_1000000/o_1.shtml";
                String name = "搜狐视频";
                String jxUrl = "https://jx.618g.com/?url=";
                Intent intent = new Intent(getActivity(),WebviewActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                intent.putExtra("jxUrl",jxUrl);
                startActivity(intent);

            }
        });
//        getContentView().findViewById(R.id.ll_kejiwo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = "https://m.tv.sohu.com/filter/cid_1/subid_1000000/o_1.shtml";
//                String name = "科技窝影视";
//                String jxUrl = "https://jx.618g.com/?url=";
//                Intent intent = new Intent(getActivity(),WebviewActivity.class);
//                intent.putExtra("url",url);
//                intent.putExtra("name",name);
//                intent.putExtra("jxUrl",jxUrl);
//                startActivity(intent);
//
//            }
//        });

        setBanner();


    }

    private void setBanner() {
        Appconfig appconfig = new Gson().fromJson(KVUtils.query(SPConstant.APPCONFIG_JSON),Appconfig.class);
        bmobBannerList = appconfig.getBannerBeanList();

        banner = getContentView().findViewById(R.id.banner);
        final ViewGroup.LayoutParams params = banner.getLayoutParams();
        params.width = MyApp.windowwidth;
        params.height = params.width * 330 / 720;
        banner.setLayoutParams(params);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setDelayTime(5000);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = bmobBannerList.get(position);
                String type = bannerBean.getBannerType();
                L.e("qpf","bannertype == " + type);
                if (AppConstant.BANNER_TYPE_QQ.equals(type)){
                    CommUtils.goToQQ(getMContext(),bannerBean.getUrl());
                }else {
                    CommUtils.jumpPlayVideo(getActivity(),bmobBannerList.get(position).getUrl());
                }


            }
        });
    }

    @Override
    protected void setUpData() {
        banner.setImages(bmobBannerList);
        banner.start();
    }
}
