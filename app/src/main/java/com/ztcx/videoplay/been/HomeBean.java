package com.ztcx.videoplay.been;

import java.util.List;

public class HomeBean {

    private List<BannerBean> bannerList;
    private List<Tag> tvTtagList;
    private List<Tag> movieTagList;
    private List<Tag> dongmanTagList;
    private List<Tag> zongyiTagList;
    private List<Base360Video> homeTvList;
    private List<Base360Video> homeMovieList;
    private List<Base360Video> homeDongManList;
    private List<Base360Video> homeZongyiList;

    private List<String> hotSearch;

    public List<String> getHotSearch() {
        return hotSearch;
    }

    public void setHotSearch(List<String> hotSearch) {
        this.hotSearch = hotSearch;
    }

    public List<Tag> getZongyiTagList() {
        return zongyiTagList;
    }

    public void setZongyiTagList(List<Tag> zongyiTagList) {
        this.zongyiTagList = zongyiTagList;
    }

    public List<Base360Video> getHomeZongyiList() {
        return homeZongyiList;
    }

    public void setHomeZongyiList(List<Base360Video> homeZongyiList) {
        this.homeZongyiList = homeZongyiList;
    }

    public List<Base360Video> getHomeDongManList() {
        return homeDongManList;
    }
    public void setHomeDongManList(List<Base360Video> homeDongManList) {
        this.homeDongManList = homeDongManList;
    }
    public List<Tag> getDongmanTagList() {
        return dongmanTagList;
    }
    public void setDongmanTagList(List<Tag> dongmanTagList) {
        this.dongmanTagList = dongmanTagList;
    }
    public List<Base360Video> getHomeMovieList() {
        return homeMovieList;
    }
    public void setHomeMovieList(List<Base360Video> homeMovieList) {
        this.homeMovieList = homeMovieList;
    }
    public List<Tag> getMovieTagList() {
        return movieTagList;
    }
    public void setMovieTagList(List<Tag> movieTagList) {
        this.movieTagList = movieTagList;
    }
    public List<Tag> getTvTtagList() {
        return tvTtagList;
    }
    public void setTvTtagList(List<Tag> tvTtagList) {
        this.tvTtagList = tvTtagList;
    }
    public List<Base360Video> getHomeTvList() {
        return homeTvList;
    }
    public void setHomeTvList(List<Base360Video> homeTvList) {
        this.homeTvList = homeTvList;
    }
    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "bannerList=" + bannerList +
                '}';
    }
}
