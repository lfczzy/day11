package com.ztcx.videoplay;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.been.Base360Video;
import com.ztcx.videoplay.utils.CommUtils;

import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

public class BmobVideoDetailActivity extends NativeBaseActivity {

    private VideoPlayer videoPlayer;
    private String playerUrl;
    private Base360Video base360Video;

    private TextView mTvVideoName;
    private TextView mTvArea;
    private TextView mTvStyle;
    private TextView mTvDirect;
    private TextView mTvLeadingRole;
    private TextView mTvDetail;
    private TextView mTvScore;
    private RelativeLayout mRl;

    @Override
    public void init() {
        super.init();
        base360Video = (Base360Video) getIntent().getSerializableExtra("baseVideo");

        playerUrl = base360Video.getNextUrl();
        //浏览记录
        CommUtils.addHistory(this, base360Video);
    }

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_bmob_video_detail;
    }

    @Override
    public void setUpView() {

        mTvVideoName = findViewById(R.id.tv_video_name);
        mTvArea = findViewById(R.id.tv_area);
        mTvStyle = findViewById(R.id.tv_style);
        mTvDirect = findViewById(R.id.tv_direct);
        mTvLeadingRole = findViewById(R.id.tv_leading_role);
        mTvDetail = findViewById(R.id.tv_detail);
        mTvScore = findViewById(R.id.tv_score);

        videoPlayer = findViewById(R.id.nice_video_player);
        videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
        videoPlayer.setUp(playerUrl, null);
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle(base360Video.getVideoName());
//        controller.setLength(98000);
//        Glide.with(this)
//                .load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg")
//                .placeholder(R.drawable.image_default)
//                .crossFade()
//                .into(controller.imageView());
        //设置中间播放按钮是否显示
        controller.setCenterPlayer(false, R.drawable.ic_player_center_start);
        controller.setTopPadding(24.0f);
        videoPlayer.setController(controller);

        videoPlayer.start();


        mRl = findViewById(R.id.rl);


        ImageView mIvAd = findViewById(R.id.iv_ad);
        ViewGroup.LayoutParams params = mIvAd.getLayoutParams();
        params.width = MyApp.windowwidth;
        params.height = params.width * 40 / 100;
        mIvAd.setLayoutParams(params);

        Glide.with(this).load(R.mipmap.test_ad).into(mIvAd);

    }

    @Override
    public void setUpData() {
        mTvVideoName.setText(base360Video.getVideoName());
        mTvArea.setText(base360Video.getArea());
        mTvStyle.setText(base360Video.getTypeName());
        mTvDirect.setText(base360Video.getDirector());
        mTvLeadingRole.setText(base360Video.getActor());
        mTvDetail.setText(base360Video.getDescription());
        mTvScore.setText(base360Video.getScore());
    }


    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().suspendVideoPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        } else {
            VideoPlayerManager.instance().releaseVideoPlayer();
        }
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        VideoPlayerManager.instance().resumeVideoPlayer();
    }



}


