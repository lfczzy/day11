package com.ztcx.videoplay.activity;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ztcx.videoplay.BmobVideoDetailActivity;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.VideoDetailActivity;
import com.ztcx.videoplay.adapter.WatchHistoryAdapter;
import com.ztcx.videoplay.base.NativeBaseListActivity;
import com.ztcx.videoplay.been.UserInfo;
import com.ztcx.videoplay.been.WatchHistory;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.utils.CommUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class WatchHistoryActivity extends NativeBaseListActivity<WatchHistory,WatchHistoryAdapter> {

    @Override
    public void setUpView() {
        super.setUpView();
        setVisibleTitleBar(AppConstant.MAIN_COLOR,"我的足迹");
    }

    @Override
    protected WatchHistoryAdapter setAdapter() {
        return new WatchHistoryAdapter(R.layout.item_ambitus_video,data);
    }

    @Override
    public void setUpData() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);

        UserInfo userInfo = BmobUser.getCurrentUser(this,UserInfo.class);
        BmobQuery<WatchHistory> query = new BmobQuery<>();
        query.addWhereEqualTo("user",userInfo);
        query.include("video");
        query.order("-updatedAt");
        query.findObjects(this, new FindListener<WatchHistory>() {
            @Override
            public void onSuccess(List<WatchHistory> list) {
                data = list;
                adapter.setNewData(list);

                if (adapter.getData().size() == 0){
                    mRlNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        if (AppConstant.VIDEO_TYPE_BMOB.equals(this.adapter.getItem(position).getType())){
            CommUtils.jumpPlayVideo(this,this.adapter.getItem(position).getVideo());
        }else {
            CommUtils.jumpPlayVideo(this,data.get(position).getNextUrl());
        }
    }
}
