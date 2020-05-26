package com.ztcx.videoplay.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.been.Collection;
import com.ztcx.videoplay.been.WatchHistory;
import com.ztcx.videoplay.utils.L;

import java.util.List;

public class CollectionAdapter extends BaseQuickAdapter<Collection,BaseViewHolder> {
    public CollectionAdapter(int layoutResId, @Nullable List<Collection> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Collection item) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.image_def_h);

        Glide.with(mContext).load(item.getImageUrl()).apply(requestOptions).into((ImageView) helper.getView(R.id.iv_video));

        L.e("qpf","图片链接 -- " + item.getImageUrl());
        helper.setText(R.id.tv_video_name,item.getName());
        helper.setText(R.id.tv_update_time,item.getUpdatedAt());

    }
}
