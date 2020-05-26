package com.ztcx.videoplay.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.been.Tag;

import java.util.List;

public class TagAdapter extends BaseQuickAdapter<Tag, BaseViewHolder> {
    public TagAdapter(int layoutResId, @Nullable List<Tag> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tag item) {
        TextView mTvTag = helper.getView(R.id.tv_tag);
        mTvTag.setText(item.getName());
        if (item.isCheck()){
            mTvTag.setBackgroundResource(R.drawable.shape_tag_bg_check);
            mTvTag.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            mTvTag.setBackgroundResource(R.drawable.shape_tag_bg);
            mTvTag.setTextColor(mContext.getResources().getColor(R.color.main_color));
        }


    }
}
