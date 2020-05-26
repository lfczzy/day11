package com.ztcx.videoplay.been;

public class BannerBean {

    //图片
    private String image;
    //跳转链接
    private String url;
    //标题
    private String title;
    //副标题
    private String subTitle;
    //类型
    private String bannerType;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", bannerType='" + bannerType + '\'' +
                '}';
    }
}
