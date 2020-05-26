package com.ztcx.videoplay.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.been.VideoDownParseBean;

import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<VideoDownParseBean.Comment,BaseViewHolder> {

    public CommentAdapter(int layoutResId, @Nullable List<VideoDownParseBean.Comment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDownParseBean.Comment item) {
        Glide.with(mContext).load(item.getUserImage()).into((ImageView) helper.getView(R.id.iv_user_logo));

        helper.setText(R.id.tv_user_name,item.getUserName());

        helper.setText(R.id.tv_content,item.getContent());

    }
}
