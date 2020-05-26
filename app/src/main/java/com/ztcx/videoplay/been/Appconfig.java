package com.ztcx.videoplay.been;

import java.lang.ref.PhantomReference;
import java.util.List;

import cn.bmob.v3.BmobObject;

public class Appconfig extends BmobObject {
    //客服QQ
    private String service_qq;
    //商务合作
    private String business_qq;
    //banner
    private List<BannerBean> bannerBeanList;
    //公告
    private String notice;
    //解析
    private String jx_url;
    //启动页
    private String splash_image;
    //更新相关
    private UpdateBeen updateBeen;

    public UpdateBeen getUpdateBeen() {
        return updateBeen;
    }

    public void setUpdateBeen(UpdateBeen updateBeen) {
        this.updateBeen = updateBeen;
    }

    public String getService_qq() {
        return service_qq;
    }

    public void setService_qq(String service_qq) {
        this.service_qq = service_qq;
    }

    public String getBusiness_qq() {
        return business_qq;
    }

    public void setBusiness_qq(String business_qq) {
        this.business_qq = business_qq;
    }

    public List<BannerBean> getBannerBeanList() {
        return bannerBeanList;
    }

    public void setBannerBeanList(List<BannerBean> bannerBeanList) {
        this.bannerBeanList = bannerBeanList;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getJx_url() {
        return jx_url;
    }

    public void setJx_url(String jx_url) {
        this.jx_url = jx_url;
    }

    public String getSplash_image() {
        return splash_image;
    }

    public void setSplash_image(String splash_image) {
        this.splash_image = splash_image;
    }

    public static class UpdateBeen{
        //新版本地址
        private String apk_url;
        //当前版本
        private int version;
        //是否强制更新
        private boolean isForce;
        //版本名
        private String versionName;
        //更新说明
        private String update_explain;

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getUpdate_explain() {
            return update_explain;
        }

        public void setUpdate_explain(String update_explain) {
            this.update_explain = update_explain;
        }

        public String getApk_url() {
            return apk_url;
        }

        public void setApk_url(String apk_url) {
            this.apk_url = apk_url;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public boolean isForce() {
            return isForce;
        }

        public void setForce(boolean force) {
            isForce = force;
        }
    }
}
