package com.ztcx.videoplay.been;

import java.util.List;

/**
 * 明星详情
 */
public class StarInfo {
    //明星的图片
    private String image;
    //姓名
    private String name;
    //别名
    private String alias;
    //属性：
    private List<String> attributeList;
    //简介
    private String intro;

    @Override
    public String toString() {
        return "StarInfo{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", attributeList=" + attributeList +
                ", intro='" + intro + '\'' +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<String> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<String> attributeList) {
        this.attributeList = attributeList;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
