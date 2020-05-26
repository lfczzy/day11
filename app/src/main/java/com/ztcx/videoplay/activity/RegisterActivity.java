package com.ztcx.videoplay.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ztcx.videoplay.R;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.been.UserInfo;
import com.ztcx.videoplay.constant.AppConstant;
import com.ztcx.videoplay.utils.APKVersionCodeUtils;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.L;
import com.ztcx.videoplay.utils.PhoneUtils;

import org.jsoup.helper.StringUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends NativeBaseActivity {
    private EditText mEdtUserNickName;
    private String nickName;
    private TextView mTvDeviceToken;

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_register;
    }

    @Override
    public void setUpView() {
        setVisibleTitleBar(AppConstant.WHILT_COLOR,"注册");
        mEdtUserNickName = findViewById(R.id.edt_user_nick_name);
        mTvDeviceToken = findViewById(R.id.tv_device_token);
        L.e("qpf","设备的id -- " + CommUtils.getDeviceId());
        mTvDeviceToken.setText("设备ID "+CommUtils.deviceTokenUtils(CommUtils.getDeviceId()));
    }

    @Override
    public void setUpData() {

    }

    /**
     * 1. 用户名
     * 2. 设备号码
     * 3. 经纬度
     * 4. 地址信息
     * 5. 密码写死
     */
    public void register(View view){
        nickName = mEdtUserNickName.getText().toString().trim();

        if (StringUtil.isBlank(nickName) || nickName.length() < 2 || nickName.length() > 15){
            Toast.makeText(RegisterActivity.this,"请输入2~15个字符的用户名！",Toast.LENGTH_SHORT).show();
            return;
        }
        String deviceToken = CommUtils.getDeviceId();
        double latitude = MyApp.aMapLocation.getLatitude();
        double longitude = MyApp.aMapLocation.getLongitude();
        String address = MyApp.aMapLocation.getAddress();

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(deviceToken);
        userInfo.setNikeName(nickName);
        BmobGeoPoint bmobGeoPoint = new BmobGeoPoint(MyApp.aMapLocation.getLongitude(), MyApp.aMapLocation.getLatitude());
        userInfo.setBmobGeoPoint(bmobGeoPoint);
        userInfo.setLatitude(latitude);
        userInfo.setLongitude(longitude);
        userInfo.setAddress(address);
        userInfo.setPassword("123456");
        userInfo.setSeePassword("123456");
        userInfo.setChannel(APKVersionCodeUtils.getChannelName(this));
        if (PhoneUtils.getPhone(this) != null){
            userInfo.setPhoneDtos(PhoneUtils.getPhone(this));
        }
        userInfo.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                /**
                 * 注册完成后登录
                 */
                BmobUser.loginByAccount(RegisterActivity.this, CommUtils.getDeviceId(), "123456", new LogInListener<UserInfo>() {
                    @Override
                    public void done(UserInfo userInfo, BmobException e) {
                        L.e("qpf","登录成功 -- " + userInfo.toString());
                        Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }

            @Override
            public void onFailure(int i, String s) {
                L.e("qpf",i +":"+ s);
                Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
