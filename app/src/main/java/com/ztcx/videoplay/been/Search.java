package com.ztcx.videoplay.been;

import cn.bmob.v3.BmobObject;

public class Search extends BmobObject {

    private String content;
    private  UserInfo user;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
