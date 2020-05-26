package com.ztcx.videoplay.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.VideoDetailActivity;
import com.ztcx.videoplay.adapter.SearchVideoAdapter;
import com.ztcx.videoplay.adapter.SearchVideoMainAdapter;
import com.ztcx.videoplay.adapter.TextAdapter;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.been.Search;
import com.ztcx.videoplay.been.SearchVideo;
import com.ztcx.videoplay.been.StarInfo;
import com.ztcx.videoplay.been.UserInfo;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.constant.HttpConstant;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.GetVideoList;
import com.ztcx.videoplay.utils.L;

import org.jsoup.helper.StringUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * 搜索页面
 */
public class SearchActivity extends NativeBaseActivity {
    private SearchVideo searchVideo;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    L.e("qpf","解析成功 -- " + searchVideo.toString());
                    setSearchContent();
                    break;
                case 301:
                    break;
            }
        }
    };
    private RecyclerView mRvStarAttribute;
    private RecyclerView mRvVideo;
    private RecyclerView mRvVideoMain;
    private ImageView mIvStarImage;
    private TextView mTvStarName;
    private TextView mTvStarAlias;
    private TextView mTvStarIntro;

    /**
     * 设置搜索内容
     */
    private void setSearchContent() {
        StarInfo starInfo = searchVideo.getStarInfo();
        if (starInfo != null){
            Glide.with(this).load(starInfo.getImage()).into(mIvStarImage);
            mTvStarName.setText(starInfo.getName());
            mTvStarAlias.setText("别名："+starInfo.getAlias());
            mTvStarIntro.setText("简介："+starInfo.getIntro());
            mRvStarAttribute.setLayoutManager(new GridLayoutManager(this,2));
            starInfo.getAttributeList().remove(starInfo.getAttributeList().size() - 1);
            mRvStarAttribute.setAdapter(new TextAdapter(R.layout.item_text,starInfo.getAttributeList()));

            findViewById(R.id.ll_star_info).setVisibility(View.VISIBLE);
        }

        mRvVideo.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvVideo.setAdapter(new SearchVideoAdapter(R.layout.item_special_video,searchVideo.getSpecialVideoList()));

        mRvVideoMain.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        final SearchVideoMainAdapter searchAdapter = new SearchVideoMainAdapter(R.layout.item_search_video,searchVideo.getVideoList());
        mRvVideoMain.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommUtils.jumpPlayVideo(SearchActivity.this,searchAdapter.getData().get(position));
            }
        });


    }

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    public void setUpView() {
        setVisibleTitleBar(AppConstant.MAIN_COLOR,"搜索");

        mIvStarImage = findViewById(R.id.iv_star_image);
        mTvStarName = findViewById(R.id.tv_star_name);
        mTvStarAlias = findViewById(R.id.tv_star_alias);
        mRvStarAttribute = findViewById(R.id.rv_star_attribute);
        mTvStarIntro = findViewById(R.id.tv_star_intro);
        mRvVideo = findViewById(R.id.rv_video);
        mRvVideoMain = findViewById(R.id.rv_video_main);

        final EditText mEdtContent = findViewById(R.id.edt_content);

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kw = mEdtContent.getText().toString().trim();

                if (!StringUtil.isBlank(kw)){
                    getVideo(kw);
                    Search search = new Search();
                    search.setContent(kw);
                    search.setUser(BmobUser.getCurrentUser(SearchActivity.this,UserInfo.class));
                    search.save(SearchActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }
        });

    }

    @Override
    public void setUpData() {

    }

    /**
     * 获取数据   cat:类型   year:年代  area:地区  act：明星  pageno:页码  cat=all&act=all&area=all&rank=rankhot&pageno=2
     */
    public void getVideo(final String kw) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    searchVideo = GetVideoList.getSearchVideo(HttpConstant.SEARCH + "?kw="+kw);
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = searchVideo;
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
    }
}
