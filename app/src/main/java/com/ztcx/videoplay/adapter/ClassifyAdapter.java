package com.ztcx.videoplay.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.widget.MsgView;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.been.Classify;

import java.util.List;

public class ClassifyAdapter extends BaseQuickAdapter<Classify, BaseViewHolder> {

    Handler handler;

    public ClassifyAdapter(int layoutResId, @Nullable List<Classify> data,Handler handler) {
        super(layoutResId, data);
        this.handler = handler;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Classify item) {

        helper.setText(R.id.tv_title,item.getTitle());

        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        ReclassifyAdapter adapter = new ReclassifyAdapter(R.layout.item_reclassify,item.getReclassify());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Message msg = Message.obtain();
                msg.what = getData().indexOf(item);
                msg.obj = adapter.getData().get(position);
                handler.sendMessage(msg);
            }
        });

    }
}
