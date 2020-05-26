package com.ztcx.videoplay.fragment.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.activity.RegisterActivity;
import com.ztcx.videoplay.activity.SearchActivity;
import com.ztcx.videoplay.activity.WatchHistoryActivity;
import com.ztcx.videoplay.base.NativeBaseFragment;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.fragment.AnimeFragment;
import com.ztcx.videoplay.fragment.MovieFragment;
import com.ztcx.videoplay.fragment.TVPlayFragment;
import com.ztcx.videoplay.fragment.ZongYiFragment;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.JsoupUtils;
import com.ztcx.videoplay.utils.L;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.time.temporal.JulianFields;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class HomeMainFragment extends NativeBaseFragment implements OnTabSelectListener {
    public SlidingTabLayout tabLayout;
    public ArrayList<NativeBaseFragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "首页", "电影", "电视剧"
            , "综艺", "动漫"
    };
    private MyPagerAdapter mAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_main;
    }

    @Override
    protected void setUpView() {
        setVisibleStatusBar(AppConstant.MAIN_COLOR);

        mFragments.add(new HomeFragment());
        mFragments.add(new MovieFragment());
        mFragments.add(new TVPlayFragment());
        mFragments.add(new ZongYiFragment());
        mFragments.add(new AnimeFragment());

        ViewPager vp = getContentView().findViewById(R.id.vp);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);
//        vp.setOffscreenPageLimit(5);

        tabLayout = getContentView().findViewById(R.id.tl_7);
        tabLayout.setViewPager(vp);
        tabLayout.setOnTabSelectListener(this);

        vp.setCurrentItem(0);

        getContentView().findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.e("qpf","开始解析 -- ");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Document document = jsoupParse("https://cdn.yangju.vip/k/?url=https://www.iqiyi.com/v_19rsfhgzfw.html");
                            L.e("qpf","解析成功 -- " + document.getElementsByTag("body").get(0).html());
                        }catch (Exception e){
                            L.e("qpf","解析失败 -- " + e.toString());
                        }

                    }
                }).start();
            }
        });

        getContentView().findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommUtils.isLogin(getActivity())){
                    startActivity(new Intent(getActivity(),SearchActivity.class));
                }else{
                    CommUtils.jumpNoLogin(getActivity());
                }
            }
        });

        getContentView().findViewById(R.id.iv_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CommUtils.isLogin(getActivity())){
                    startActivity(new Intent(getActivity(),WatchHistoryActivity.class));
                }else{
                    CommUtils.jumpNoLogin(getActivity());
                }
            }
        });
    }

    public static Document jsoupParse(String url){
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(5000).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return document;
        }
        if (document!=null) {
            return document;
        }

        return document;

    }

    @Override
    protected void setUpData() {

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
