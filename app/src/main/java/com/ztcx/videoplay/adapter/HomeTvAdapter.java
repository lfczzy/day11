package com.ztcx.videoplay.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

import org.jsoup.helper.StringUtil;

import java.util.List;

public class HomeTvAdapter extends BaseQuickAdapter<Base360Video,BaseViewHolder> {

    public HomeTvAdapter(int layoutResId, @Nullable List<Base360Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Base360Video item) {

        LinearLayout mLlItem = helper.getView(R.id.ll_item);
        ViewGroup.LayoutParams params = mLlItem.getLayoutParams();
        params.width = MyApp.windowwidth / 2 - DisplayTools.dip2px(10,mContext);
        mLlItem.setLayoutParams(params);

        RelativeLayout mRlImageWH = helper.getView(R.id.rl_image_w_h);
        ViewGroup.LayoutParams paramsImage = mRlImageWH.getLayoutParams();
        paramsImage.width = params.width;
        paramsImage.height = params.width * 100 / 180;
        mRlImageWH.setLayoutParams(paramsImage);

        //视频图片
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.image_def_h);

        Glide.with(mContext).load(item.getImgUrl()).apply(requestOptions).into((ImageView) helper.getView(R.id.iv_video));

        //视频名称
        helper.setText(R.id.tv_video_name,item.getVideoName());
        //副标题
        helper.setText(R.id.tv_subtitle,item.getSubTitle());
        //更新集数
        helper.setText(R.id.tv_new_ji,item.getUpdateInfo());

        if (!StringUtil.isBlank(item.getScore())){
            helper.setText(R.id.tv_score,item.getScore());
            helper.getView(R.id.tv_score).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.tv_score).setVisibility(View.GONE);
        }


    }
}
