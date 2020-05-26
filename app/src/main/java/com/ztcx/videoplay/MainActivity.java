package com.ztcx.videoplay;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.ztcx.videoplay.app.MyApp;
import com.ztcx.videoplay.base.NativeBaseActivity;
import com.ztcx.videoplay.been.Classify;
import com.ztcx.videoplay.fragment.AnimeFragment;
import com.ztcx.videoplay.fragment.ClassifyFragment;
import com.ztcx.videoplay.fragment.MeFragment;
import com.ztcx.videoplay.fragment.MovieFragment;
import com.ztcx.videoplay.fragment.TVPlayFragment;
import com.ztcx.videoplay.fragment.WholeNetworkFragment;
import com.ztcx.videoplay.fragment.ZongYiFragment;
import com.ztcx.videoplay.fragment.home.HomeMainFragment;
import com.ztcx.videoplay.fragment.home.HomeTvFragment;
import com.ztcx.videoplay.utils.AMapLocationCallback;
import com.ztcx.videoplay.utils.CommUtils;
import com.ztcx.videoplay.utils.GDUtils;
import com.ztcx.videoplay.utils.L;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends NativeBaseActivity{

    private RadioGroup rg;
    private FragmentTransaction transaction;
    private Fragment[] fragments = new Fragment[5];
    private RadioButton rb0;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton lastRB;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            rb0.setChecked(true);
            if (msg.obj == null){
                return;
            }
            final Classify.Reclassify reclassify = (Classify.Reclassify) msg.obj;
            if (fragments[4] != null){
                ((HomeMainFragment)fragments[4]).tabLayout.setCurrentTab(msg.what+1);
            }
            switch (msg.what){
                case 0:
                    MovieFragment movieFragment = (MovieFragment) ((HomeMainFragment) fragments[4]).mFragments.get(msg.what+1);
                    movieFragment.cat = reclassify.getId();
                    movieFragment.currentPage = 1;
                    movieFragment.getVideo();
                    break;
                case 1:
                    TVPlayFragment tvPlayFragment = (TVPlayFragment) ((HomeMainFragment) fragments[4]).mFragments.get(msg.what+1);
                    tvPlayFragment.cat = reclassify.getId();
                    tvPlayFragment.currentPage = 1;
                    tvPlayFragment.getVideo();
                    break;
                case 2:
                    ZongYiFragment zongYiFragment = (ZongYiFragment) ((HomeMainFragment) fragments[4]).mFragments.get(msg.what+1);
                    zongYiFragment.cat = reclassify.getId();
                    zongYiFragment.currentPage = 1;
                    zongYiFragment.getVideo();
                    break;
                case 3:
                    AnimeFragment animeFragment = (AnimeFragment) ((HomeMainFragment) fragments[4]).mFragments.get(msg.what+1);
                    animeFragment.cat = reclassify.getId();
                    animeFragment.currentPage = 1;
                    animeFragment.getVideo();
                    break;
            }

        }
    };


    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    public void setUpView() {
        rg = findViewById(R.id.rg);
        showFragment(4);
        rb0 = findViewById(R.id.rb0);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);

        lastRB = rb0;
        rb0.setChecked(true);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rb0:
                        lastRB = rb0;
                        showFragment(4);
                        break;
                    case R.id.rb1:
                        lastRB = rb1;
                        showFragment(0);
                        break;
                    case R.id.rb2:
                        lastRB = rb2;
                        showFragment(1);
                        break;
                    case R.id.rb3:
                        if (!CommUtils.isLogin(MainActivity.this)){
                            lastRB.setChecked(true);
                            CommUtils.jumpNoLogin(MainActivity.this);
                            return;
                        }

                        lastRB = rb3;
                        showFragment(2);
                        break;
                    case R.id.rb4:
                        lastRB = rb4;
                        showFragment(3);
                        break;
                }
            }
        });



    }

    @Override
    public void setUpData() {
        L.e("qpf","SHA1值 -- " + sHA1(this) + "");

    }

    private void showFragment(final int position){

        transaction = getSupportFragmentManager().beginTransaction();
        hideFragment();
        if (fragments[position] == null){
            switch (position){
                case 0:
                    fragments[0] = new ClassifyFragment(handler);
                    break;
                case 1:
                    fragments[1] = new WholeNetworkFragment();
                    break;
                case 2:
                    fragments[2] = new MeFragment(handler);
                    break;
                case 3:
                    fragments[3] = new AnimeFragment();
                    break;
                case 4:
                    fragments[4] = new HomeMainFragment();
                    break;
            }
            transaction.add(R.id.fragment,fragments[position]);
        }else {
            transaction.show(fragments[position]);
        }
        transaction.commit();

    }

    private void hideFragment() {
        for (Fragment fra :fragments) {
            if (fra != null){
                transaction.hide(fra);
            }
        }
    }

    boolean mBackKeyPressed;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!mBackKeyPressed) {
                Toast.makeText(this, "再按一次退出应用",Toast.LENGTH_SHORT).show();
                mBackKeyPressed = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mBackKeyPressed = false;
                    }
                }, 2000);
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
