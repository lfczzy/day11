package com.ztcx.videoplay.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.been.Base360Video;
import com.ztcx.videoplay.been.VideoDownParseBean;
import com.ztcx.videoplay.utils.DisplayTools;
import com.ztcx.videoplay.utils.L;

import org.jsoup.helper.StringUtil;

import java.util.List;

public class XuanJiAdapter extends BaseQuickAdapter<VideoDownParseBean.XuanJi,BaseViewHolder> {
    public XuanJiAdapter(int layoutResId, @Nullable List<VideoDownParseBean.XuanJi> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDownParseBean.XuanJi item) {

        TextView mTvJi = helper.getView(R.id.tv_ji);

        if (!StringUtil.isBlank(item.getNumber())){
            mTvJi.setText(item.getNumber());
        }else {
            mTvJi.setText("缺");
        }

        if (item.isIscheck()){
            mTvJi.setTextColor(mContext.getResources().getColor(R.color.white));
            mTvJi.setBackgroundResource(R.drawable.shape_xuanji_check);
        }else {
            mTvJi.setTextColor(mContext.getResources().getColor(R.color.main_color));
            mTvJi.setBackgroundResource(R.drawable.shape_xuanji_bg_def);
        }



    }
}
