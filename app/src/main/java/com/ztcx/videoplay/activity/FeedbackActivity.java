package com.ztcx.videoplay.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ztcx.videoplay.R;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.been.Feedback;
import com.ztcx.videoplay.been.UserInfo;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.utils.CommUtils;

import org.jsoup.helper.StringUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * 问题反馈页面
 */
public class FeedbackActivity extends NativeBaseActivity {

    private EditText mEdtContent;
    private EditText mEdtQq;
    private TextView mTvPush;

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_feedback;
    }

    @Override
    public void setUpView() {
        setVisibleTitleBar(AppConstant.MAIN_COLOR,"问题反馈");

        mEdtContent = findViewById(R.id.edt_content);
        mEdtQq = findViewById(R.id.edt_qq);
        mTvPush = findViewById(R.id.tv_push);

    }

    Boolean isCheck = false;
    @Override
    public void setUpData() {
        mTvPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCheck){
                    return;
                }
                isCheck = true;
                String content = mEdtContent.getText().toString().trim();
                String qq = mEdtQq.getText().toString().trim();

                if (StringUtil.isBlank(content)){
                    Toast.makeText(FeedbackActivity.this,"请输入反馈内容！",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (StringUtil.isBlank(qq)){
                    Toast.makeText(FeedbackActivity.this,"请输入qq号，以便与您取得联系方式！",Toast.LENGTH_SHORT).show();
                    return;
                }

                Feedback feedback = new Feedback();
                feedback.setContent(content);
                feedback.setQq(qq);
                feedback.setUserInfo(BmobUser.getCurrentUser(FeedbackActivity.this,UserInfo.class));
                feedback.save(FeedbackActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(FeedbackActivity.this,"感谢您的反馈，我们将尽快与您取得联系！",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        isCheck = false;
                        Toast.makeText(FeedbackActivity.this,"错误：" + s,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }


}
