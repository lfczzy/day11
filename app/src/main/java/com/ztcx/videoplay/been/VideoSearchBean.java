package com.ztcx.videoplay.been;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 视频解析第一个界面公共类
 */
public class VideoSearchBean implements MultiItemEntity {


    /*针对搜索的*/
    private String searchType;//搜索类型
    private String imgUrl;//图片链接
    private String videoName; //视频名称
    private String nextUrl;//下一级的URL


    private String title;//标题
    private String type;//类型
    private String year;//年代
    private String daoyan;//导演
    private String diqu;//地区
    private String yanyuan;//演员
    private String jianjie;//简介

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDaoyan() {
        return daoyan;
    }

    public void setDaoyan(String daoyan) {
        this.daoyan = daoyan;
    }

    public String getDiqu() {
        return diqu;
    }

    public void setDiqu(String diqu) {
        this.diqu = diqu;
    }

    public String getYanyuan() {
        return yanyuan;
    }

    public void setYanyuan(String yanyuan) {
        this.yanyuan = yanyuan;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }
}
