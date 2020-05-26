package com.ztcx.videoplay.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.been.Classify;

import java.util.List;

public class ReclassifyAdapter extends BaseQuickAdapter<Classify.Reclassify, BaseViewHolder> {

    public ReclassifyAdapter(int layoutResId, @Nullable List<Classify.Reclassify> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Classify.Reclassify item) {

        helper.setText(R.id.tv_name,item.getName());

    }
}
