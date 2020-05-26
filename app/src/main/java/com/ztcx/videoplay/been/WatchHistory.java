package com.ztcx.videoplay.been;

import cn.bmob.v3.BmobObject;

public class WatchHistory extends BmobObject {

    //用户
    private UserInfo user;
    //base360video
    private String imageUrl;
    private String name;
    private String nextUrl;
    private String type;
    private Base360Video video;

    public Base360Video getVideo() {
        return video;
    }

    public void setVideo(Base360Video video) {
        this.video = video;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}
