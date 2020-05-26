package com.ztcx.videoplay.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.been.VideoDownParseBean;
import com.ztcx.videoplay.utils.L;

import java.util.List;

public class NewDangQiAdapter extends BaseQuickAdapter<VideoDownParseBean.NewDangQi,BaseViewHolder> {
    public NewDangQiAdapter(int layoutResId, @Nullable List<VideoDownParseBean.NewDangQi> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDownParseBean.NewDangQi item) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.image_def_h);

        Glide.with(mContext).load(item.getThumbUrl()).apply(requestOptions).into((ImageView) helper.getView(R.id.iv_video));

        L.e("qpf","图片链接 -- " + item.getThumbUrl());
        helper.setText(R.id.tv_video_name,item.getTitle());
        helper.setText(R.id.tv_update_time,item.getUpdateTime());

    }
}
