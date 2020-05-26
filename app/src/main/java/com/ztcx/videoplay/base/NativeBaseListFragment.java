package com.ztcx.videoplay.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ztcx.videoplay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有fragment的基类  列表页
 */
public abstract class NativeBaseListFragment<T,BA extends BaseQuickAdapter> extends NativeBaseFragment implements OnLoadMoreListener, OnRefreshListener,BaseQuickAdapter.OnItemClickListener {

    protected RecyclerView recyclerView;
    protected SmartRefreshLayout refreshLayout;
    protected List<T> data = new ArrayList<>();
    protected BA adapter;
    private FrameLayout mFlFoot;
    private FrameLayout mFlHeader;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_list_base;
    }

    @Override
    protected void setUpView() {
        recyclerView = getContentView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        refreshLayout = (SmartRefreshLayout)getContentView().findViewById(R.id.refreshLayout);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
        adapter = setAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        mFlFoot = getContentView().findViewById(R.id.fl_foot);
        mFlHeader = getContentView().findViewById(R.id.fl_header);
        addFoot(setFootView());
        addHeader(setHeaderView());
    }

    protected View setFootView(){
        return null;
    }
    protected View setHeaderView(){
        return null;
    }

    private void addFoot(View view){
        if (view != null){
            mFlFoot.removeAllViews();
            mFlFoot.addView(view);
        }
    }

    private void addHeader(View view){
        if (view != null){
            mFlHeader.removeAllViews();
            mFlHeader.addView(view);
        }
    }

    protected abstract BA setAdapter();

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
