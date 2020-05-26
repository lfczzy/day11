package com.ztcx.videoplay.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.VideoDetailActivity;
import com.ztcx.videoplay.activity.SearchActivity;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.been.Base360Video;
import com.ztcx.videoplay.been.SearchVideo;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.DisplayTools;
import com.ztcx.videoplay.utils.L;

import java.util.List;

public class SearchVideoAdapter extends BaseQuickAdapter<SearchVideo.SpecialVideo,BaseViewHolder> {
    public SearchVideoAdapter(int layoutResId, @Nullable List<SearchVideo.SpecialVideo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SearchVideo.SpecialVideo item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_all, item.getSubTitle().replace(">>",""));
        RecyclerView mRvVideo = helper.getView(R.id.rv_video);
        mRvVideo.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));

        RecommendAdapter adapter = new RecommendAdapter(R.layout.item_recommend,item.getVideoList());
        mRvVideo.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommUtils.jumpPlayVideo(mContext,item.getVideoList().get(position));
            }
        });


    }
}
