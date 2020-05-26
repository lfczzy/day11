package com.ztcx.videoplay.been;

import java.util.List;
import java.util.Map;

public class ThemeVideo {
    //主题视频
    private List<Base360Video> videoList;
    private Map<String,List<Tag>> tagMap;

    public List<Base360Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Base360Video> videoList) {
        this.videoList = videoList;
    }

    public Map<String, List<Tag>> getTagMap() {
        return tagMap;
    }

    public void setTagMap(Map<String, List<Tag>> tagMap) {
        this.tagMap = tagMap;
    }
}
