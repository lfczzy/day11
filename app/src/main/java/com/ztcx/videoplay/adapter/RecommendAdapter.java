package com.ztcx.videoplay.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.been.Base360Video;
import com.ztcx.videoplay.been.VideoDownParseBean;
import com.ztcx.videoplay.utils.DisplayTools;
import com.ztcx.videoplay.utils.L;

import java.util.List;

public class RecommendAdapter extends BaseQuickAdapter<Base360Video,BaseViewHolder> {
    public RecommendAdapter(int layoutResId, @Nullable List<Base360Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Base360Video item) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.image_def);

        Glide.with(mContext).load(item.getImgUrl()).apply(requestOptions).into((ImageView) helper.getView(R.id.iv_img_url));

        L.e("qpf","图片链接 -- " + item.getImgUrl());
        helper.setText(R.id.tv_video_name,item.getVideoName());

    }
}
