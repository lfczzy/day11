package com.ztcx.videoplay.been;

import java.util.List;

public class SearchVideo {

    //明星信息
    private StarInfo starInfo;

    //专题视频信息
    private List<SpecialVideo> specialVideoList;

    //视频列表
    private List<Base360Video> videoList;

    public List<Base360Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Base360Video> videoList) {
        this.videoList = videoList;
    }

    @Override
    public String toString() {
        return "SearchVideo{" +
                "starInfo=" + starInfo +
                ", specialVideoList=" + specialVideoList +
                '}';
    }

    public StarInfo getStarInfo() {
        return starInfo;
    }

    public void setStarInfo(StarInfo starInfo) {
        this.starInfo = starInfo;
    }

    public List<SpecialVideo> getSpecialVideoList() {
        return specialVideoList;
    }

    public void setSpecialVideoList(List<SpecialVideo> specialVideoList) {
        this.specialVideoList = specialVideoList;
    }

    public static class SpecialVideo{
        private String title;
        private String subTitle;
        private List<Base360Video> videoList;
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

        public List<Base360Video> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<Base360Video> videoList) {
            this.videoList = videoList;
        }

        @Override
        public String toString() {
            return "SpecialVideo{" +
                    "title='" + title + '\'' +
                    ", subTitle='" + subTitle + '\'' +
                    ", videoList=" + videoList +
                    '}';
        }
    }
}
