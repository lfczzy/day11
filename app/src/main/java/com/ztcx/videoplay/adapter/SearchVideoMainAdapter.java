package com.ztcx.videoplay.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.been.Base360Video;

import org.jsoup.helper.StringUtil;

import java.util.List;

public class SearchVideoMainAdapter extends BaseQuickAdapter<Base360Video,BaseViewHolder> {
    public SearchVideoMainAdapter(int layoutResId, @Nullable List<Base360Video> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Base360Video item) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.image_def);

        Glide.with(mContext).load(item.getImgUrl()).apply(requestOptions).into((ImageView) helper.getView(R.id.iv_img_url));

        helper.setText(R.id.tv_video_name,item.getVideoName());

        helper.setText(R.id.tv_type_name,item.getTypeName());

        helper.setText(R.id.tv_area,item.getArea());

        helper.setText(R.id.tv_yanyuan,item.getActor());

        helper.setText(R.id.tv_detail,item.getDescription());

        if (!StringUtil.isBlank(item.getUpdateInfo())){
            helper.setText(R.id.tv_update_info,item.getUpdateInfo());
        }

    }
}
