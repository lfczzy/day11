package com.ztcx.videoplay.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.VideoDetailActivity;
import com.ztcx.videoplay.adapter.TagAdapter;
import com.ztcx.videoplay.adapter.ZongYiAdapter;
import com.ztcx.videoplay.base.BaseListLazyLoadFragment;
import com.ztcx.videoplay.been.Base360Video;
import com.ztcx.videoplay.been.Tag;
import com.ztcx.videoplay.been.ThemeVideo;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.constant.HttpConstant;
import com.ztcx.videoplay.dialog.ButtomDialogView;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.GetVideoList;
import com.ztcx.videoplay.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 综艺
 */
public class ZongYiFragment extends BaseListLazyLoadFragment<Base360Video,ZongYiAdapter> {
    private List<Base360Video> resultList = new ArrayList<>();
    private ThemeVideo themeVideo;
    private Map<String, List<Tag>> tagMap;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mRlLoading.setVisibility(View.GONE);
                    if (currentPage == 1){
                        adapter.setNewData(newData);
                        refreshLayout.finishRefresh();
                    }else {
                        if (newData != null)
                            adapter.addData(newData);
                        refreshLayout.finishLoadMore();
                    }

                    if (newData == null || newData.size() == 0){
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                    if (tagMap == null){
                        tagMap = themeVideo.getTagMap();
                        for (String value:tagMap.keySet()){
                            addHeader(value,tagMap.get(value));
                        }
                    }

                    data = adapter.getData();
                    break;
                case 301:
                    mRlLoading.setVisibility(View.GONE);
                    Toast.makeText(getMContext(),"解析错误！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public String cat="all";
    public String act="all";
    private String area="all";
    private String rank="all";
    public int currentPage = 1;
    private LinearLayout headerView;
    private List<Base360Video> newData;
    private List<TagAdapter> tagAdapters = new ArrayList<>();

    @Override
    protected ZongYiAdapter setAdapter() {
        return new ZongYiAdapter(R.layout.item_out_tv_play,data);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
//        setVisibleTitleBar(AppConstant.MAIN_COLOR,"综艺");

//        refreshLayout.setEnableLoadMore(false);
//        refreshLayout.setEnableRefresh(false);

        recyclerView.setLayoutManager(new GridLayoutManager(getMContext(),4));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                ButtomDialogView dialogView = new ButtomDialogView(getActivity(),ZongYiFragment.this.adapter.getItem(position));
                dialogView.show();
                return true;
            }
        });
    }

    @Override
    protected void setUpData() {

    }


    @Override
    protected View setHeaderView() {
        headerView = (LinearLayout) LayoutInflater.from(getMContext()).inflate(R.layout.item_tag_header,null);
        return headerView;
    }

    private void addHeader(String value, final List<Tag> tags) {
        String key = tags.get(0).getKey();
        if ("cat".equals(key) || !"all".equals(cat)){
            for (int i = 0;i < tags.size();i++){
                Tag t = tags.get(i);
                if (cat.equals(t.getValue())){
                    tags.get(i).setCheck(true);
                }
            }
        }else {
            tags.get(0).setCheck(true);
        }

        View item_tag = LayoutInflater.from(getMContext()).inflate(R.layout.header_tag, null);
        TextView mTvType = item_tag.findViewById(R.id.tv_type);
        mTvType.setText(value);

        RecyclerView mRvTags = item_tag.findViewById(R.id.rv_tags);
        mRvTags.setLayoutManager(new LinearLayoutManager(getMContext(),LinearLayoutManager.HORIZONTAL,false));
        final com.ztcx.videoplay.adapter.TagAdapter tagAdapter = new com.ztcx.videoplay.adapter.TagAdapter(R.layout.item_tag_view_small,tags);
        mRvTags.setAdapter(tagAdapter);

        tagAdapters.add(tagAdapter);

        tagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (Tag tag:tags){
                    tag.setCheck(false);
                }
                tags.get(position).setCheck(true);
                tagAdapter.notifyDataSetChanged();
//               http://www.360kan.com/zongyi/list?cat=all&act=all&area=all
                String key = tags.get(position).getKey();
                String value = tags.get(position).getValue();
                if ("cat".equals(key)){
                    cat = value;
                }else if ("area".equals(key)){
                    area = value;
                }else if ("act".equals(key)){
                    act = value;
                }
                currentPage = 1;
                getVideo();
                L.e("qpf","tag -- key -- " + tags.get(position).getKey() + "value -- " + tags.get(position).getValue());
            }
        });
        headerView.addView(item_tag);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        CommUtils.jumpPlayVideo(getActivity(),this.adapter.getItem(position));
    }

    /**
     * 获取数据   cat:类型   year:年代  area:地区  act：明星  pageno:页码  cat=all&act=all&area=all&rank=rankhot&pageno=2
     */
    public void getVideo() {
        mRlLoading.setVisibility(View.VISIBLE);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    themeVideo = GetVideoList.getThemeVideo(HttpConstant.ZONGYI + "?cat="+cat+"&act="+act+"&area="+area+"&rank="+rank+"&pageno="+currentPage);
                    resultList = themeVideo.getVideoList();
                    newData = resultList;
                    L.e("qpf","获取到了 -- " + resultList.size());
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = resultList;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    L.e("qpf", "获取到了 -- 错误 -- " + e.toString());
                    //重试
                    Message message = new Message();
                    message.what = 301;
                    handler.sendMessage(message);
                }
            }
        };
        t.start();

        if (!cat.equals("all") && tagMap != null){
            List<Tag> tags = tagMap.get("类型:");

            //复位
            for (Tag tag : tags) {
                tag.setCheck(false);
            }
            //选中
            for (int i = 0;i < tags.size();i++){
                Tag tag = tags.get(i);
                if (cat.equals(tag.getValue())){
                    tags.get(i).setCheck(true);
                }
            }
            //显示
            for (TagAdapter adapter:tagAdapters){
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        currentPage = 1;
        getVideo();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        currentPage++;
        getVideo();
    }

    @Override
    public void lazyLoad() {
        L.e("qpf","懒加载 -- lazyLoad");
        currentPage = 1;
        getVideo();
        L.e("qpf","懒加载 -- getVideo");
    }
}
