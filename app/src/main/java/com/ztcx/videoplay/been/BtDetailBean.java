package com.ztcx.videoplay.been;

import java.util.List;

/**
 * bt
 */
public class BtDetailBean {


    private String time;

    private String size;

    private String fileNumber;

    private String xunleiUrl;

    private String ciliUrl;

    private List<String> nameList;

    private List<String> sizeList;

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<String> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<String> sizeList) {
        this.sizeList = sizeList;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getXunleiUrl() {
        return xunleiUrl;
    }

    public void setXunleiUrl(String xunleiUrl) {
        this.xunleiUrl = xunleiUrl;
    }

    public String getCiliUrl() {
        return ciliUrl;
    }

    public void setCiliUrl(String ciliUrl) {
        this.ciliUrl = ciliUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


}
