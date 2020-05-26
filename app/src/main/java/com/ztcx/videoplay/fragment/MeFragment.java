package com.ztcx.videoplay.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztcx.videoplay.R;
import com.ztcx.videoplay.activity.BuyVipActivity;
import com.ztcx.videoplay.activity.CollectionActivity;
import com.ztcx.videoplay.activity.FeedbackActivity;
import com.ztcx.videoplay.activity.SplashActivity;
import com.ztcx.videoplay.activity.WalletActivity;
import com.ztcx.videoplay.activity.WatchHistoryActivity;
import com.ztcx.videoplay.base.NativeBaseFragment;
import com.ztcx.videoplay.been.Appconfig;
import com.ztcx.videoplay.been.Collection;
import com.ztcx.videoplay.been.UserInfo;
import com.ztcx.videoplay.been.WatchHistory;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.constant.SPConstant;
import com.ztcx.videoplay.dialog.CommonDialogUtils;
import com.ztcx.videoplay.utils.APKVersionCodeUtils;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.DataCleanManager;
import com.ztcx.videoplay.utils.KVUtils;
import com.ztcx.videoplay.utils.L;
import com.ztcx.videoplay.utils.UpdateApkUtils;
import com.ztcx.videoplay.view.UpdateView;

import java.sql.DatabaseMetaData;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

@SuppressLint("ValidFragment")
public class MeFragment extends NativeBaseFragment {
    TextView mTvNikeName;
    private TextView mTvDataSize;
    private TextView mTvVersion;

    Handler handler;
    private TextView mTvHistoryCount;
    private TextView mTvCollectCount;
    private UpdateView updateView;

    Handler handlerFragment = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    L.e("qpf","下载进度 -- "  + msg.obj);
                    updateView.setUpdateProgress("下载进度（"+msg.obj+"%）");
                    if ("100".equals(msg.obj)){
                        updateView.setUpdateProgress("去安装");
                    }
                    break;
            }
        }
    };



    @SuppressLint("ValidFragment")
    public MeFragment(Handler handler) {
        this.handler = handler;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void setUpView() {

        setVisibleTitleBar(AppConstant.MAIN_COLOR,"个人中心");
        mTvNikeName = getContentView().findViewById(R.id.tv_nikename);
        mTvHistoryCount = getContentView().findViewById(R.id.tv_history_count);
        mTvCollectCount = getContentView().findViewById(R.id.tv_collect_count);

        mTvDataSize = getContentView().findViewById(R.id.tv_data_size);
        mTvVersion = getContentView().findViewById(R.id.tv_version);

        getContentView().findViewById(R.id.ll_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),WatchHistoryActivity.class));
            }
        });
        getContentView().findViewById(R.id.ll_collect_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CollectionActivity.class));
            }
        });
        getContentView().findViewById(R.id.ll_wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),WalletActivity.class));
            }
        });

        getContentView().findViewById(R.id.ll_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),FeedbackActivity.class));
            }
        });

        getContentView().findViewById(R.id.ll_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataCleanManager.clearAllCache(getActivity())){
                    try {
                        mTvDataSize.setText(DataCleanManager.getTotalCacheSize(getActivity()));
                        Toast.makeText(getActivity(),"清除成功！",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }

            }
        });

        getContentView().findViewById(R.id.ll_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发现新版本
                final Appconfig.UpdateBeen updateBeen = new Gson().fromJson(KVUtils.query(SPConstant.UPDATE_INFO_JSON),Appconfig.UpdateBeen.class);
                if (updateBeen.getVersion() > APKVersionCodeUtils.getVersionCode(getMContext())){
                    final String download_url = updateBeen.getApk_url();
                    String content = updateBeen.getUpdate_explain();
                    boolean cancel = updateBeen.isForce();

                    updateView = new UpdateView(getActivity(), "发现新版本！", content, cancel);
                    updateView.setClicklistener(new UpdateView.ClickListenerInterface() {
                        @Override
                        public void doConfirm(UpdateView dialogUtils) {
                            UpdateApkUtils.NetUntils(download_url,
                                    getActivity(),handlerFragment);
                        }

                        @Override
                        public void doCancel(UpdateView dialogUtils) {
                            updateView.dismiss();
                        }
                    });
                    updateView.show();
                }else {
                    Toast.makeText(getActivity(),"暂未发现新版本！",Toast.LENGTH_SHORT).show();
                }
            }
        });


        /**
         * 客服
         */
        getContentView().findViewById(R.id.ll_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommUtils.goToQQ(getActivity(),KVUtils.query(SPConstant.SERVICE_QQ));
            }
        });

        /**
         * 商务合作
         */
        getContentView().findViewById(R.id.ll_hezuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommUtils.goToQQ(getActivity(),KVUtils.query(SPConstant.BUSINESS_QQ));
            }
        });

        getContentView().findViewById(R.id.tv_buy_vip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),BuyVipActivity.class));
            }
        });

        //退出当前账号
        getContentView().findViewById(R.id.ll_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonDialogUtils dialogUtils = new CommonDialogUtils(getActivity(),"提示","您确认要退出登录当前账号吗？");
                dialogUtils.show();

                dialogUtils.setClicklistener(new CommonDialogUtils.ClickListenerInterface() {
                    @Override
                    public void doConfirm(CommonDialogUtils dialogUtils) {
                        BmobUser.logOut(getActivity());
                        handler.sendMessage(Message.obtain());
                        dialogUtils.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        UserInfo userInfo = BmobUser.getCurrentUser(getMContext(),UserInfo.class);
        mTvNikeName.setText(userInfo.getNikeName());
        try {
            mTvDataSize.setText(DataCleanManager.getTotalCacheSize(getMContext()));
        } catch (Exception e) {
            mTvDataSize.setText(0+"KB");
            e.printStackTrace();
        }
        mTvVersion.setText("当前版本号："+APKVersionCodeUtils.getVerName(getMContext()));


        //获取浏览记录
        BmobQuery<WatchHistory> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(getMContext(),UserInfo.class));
        query.count(getMContext(), WatchHistory.class, new CountListener() {
            @Override
            public void onSuccess(int i) {
                mTvHistoryCount.setText(i+"");
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

        //获取收藏记录
        BmobQuery<Collection> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("user", BmobUser.getCurrentUser(getMContext(),UserInfo.class));
        query1.count(getMContext(), Collection.class, new CountListener() {
            @Override
            public void onSuccess(int i) {
                mTvCollectCount.setText(i+"");
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    @Override
    protected void setUpData() {

    }



}
