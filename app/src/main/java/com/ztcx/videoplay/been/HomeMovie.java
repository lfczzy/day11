package com.ztcx.videoplay.been;

import org.jsoup.Connection;

import java.util.List;

/**
 * 首页电影
 */
public class HomeMovie {
    private List<BannerBean> bannerBeanList;
    private List<Base360Video> newMovieList;
    private List<Base360Video> rankingMovieList;
    private List<Base360Video> recommendMovieList;
    private List<Base360Video> hotMovieList;
    private List<Base360Video> hotAllMovieList;

    public List<BannerBean> getBannerBeanList() {
        return bannerBeanList;
    }

    public void setBannerBeanList(List<BannerBean> bannerBeanList) {
        this.bannerBeanList = bannerBeanList;
    }

    public List<Base360Video> getNewMovieList() {
        return newMovieList;
    }

    public void setNewMovieList(List<Base360Video> newMovieList) {
        this.newMovieList = newMovieList;
    }

    public List<Base360Video> getRankingMovieList() {
        return rankingMovieList;
    }

    public void setRankingMovieList(List<Base360Video> rankingMovieList) {
        this.rankingMovieList = rankingMovieList;
    }

    public List<Base360Video> getRecommendMovieList() {
        return recommendMovieList;
    }

    public void setRecommendMovieList(List<Base360Video> recommendMovieList) {
        this.recommendMovieList = recommendMovieList;
    }

    public List<Base360Video> getHotMovieList() {
        return hotMovieList;
    }

    public void setHotMovieList(List<Base360Video> hotMovieList) {
        this.hotMovieList = hotMovieList;
    }

    public List<Base360Video> getHotAllMovieList() {
        return hotAllMovieList;
    }

    public void setHotAllMovieList(List<Base360Video> hotAllMovieList) {
        this.hotAllMovieList = hotAllMovieList;
    }

    @Override
    public String toString() {
        return bannerBeanList.size() + ","+ newMovieList.size() + "," +rankingMovieList.size()+","+recommendMovieList.size()+","+
                hotMovieList.size() + ","+hotAllMovieList.size()+"HomeMovie{" +
                "bannerBeanList=" + bannerBeanList +
                ", newMovieList=" + newMovieList +
                ", rankingMovieList=" + rankingMovieList +
                ", recommendMovieList=" + recommendMovieList +
                ", hotMovieList=" + hotMovieList +
                ", hotAllMovieList=" + hotAllMovieList +
                '}';
    }
}
