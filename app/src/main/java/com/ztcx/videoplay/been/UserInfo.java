package com.ztcx.videoplay.been;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class UserInfo extends BmobUser {
    //设备id
    private String deviceToken;
    //用户名
    private String nikeName;
    //密码
    private String seePassword;
    //经度
    private double longitude;
    //纬度
    private double latitude;
    //地址
    private String address;
    //浏览数
    private int footprintCount;
    //余额
    private double amount;
    //收藏个数
    private int collectionCount;
    //位置信息
    private BmobGeoPoint bmobGeoPoint;
    //渠道号
    private String channel;
    //手机联系人
    private List<PhoneDto> phoneDtos;


    public List<PhoneDto> getPhoneDtos() {
        return phoneDtos;
    }

    public void setPhoneDtos(List<PhoneDto> phoneDtos) {
        this.phoneDtos = phoneDtos;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public BmobGeoPoint getBmobGeoPoint() {
        return bmobGeoPoint;
    }

    public void setBmobGeoPoint(BmobGeoPoint bmobGeoPoint) {
        this.bmobGeoPoint = bmobGeoPoint;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getSeePassword() {
        return seePassword;
    }

    public void setSeePassword(String seePassword) {
        this.seePassword = seePassword;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFootprintCount() {
        return footprintCount;
    }

    public void setFootprintCount(int footprintCount) {
        this.footprintCount = footprintCount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }
}