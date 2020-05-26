package com.ztcx.videoplay.been;

import cn.bmob.v3.BmobObject;

public class Base360Video extends BmobObject{
    private String area; //地址
    private String actor; //导演
    private String description;//简介
    private String imgUrl;//图片链接
    private String updateInfo; //最新的更新信息
    private String videoName; //视频名称
    private String nextUrl;//下一级的URL
    private String subTitle;//二级标题
    private String score;//评分
    private String title;//名称
    private String typeName;//每一种类型的名称
    private String videoType;//视频类型

    private String director;//导演
    private boolean isRecommend;//是否推荐
    private int sort;//排序

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public String toString() {
        return "VideoUpParseBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", updateInfo='" + updateInfo + '\'' +
                ", videoName='" + videoName + '\'' +
                ", nextUrl='" + nextUrl + '\'' +
                '}';
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
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


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
