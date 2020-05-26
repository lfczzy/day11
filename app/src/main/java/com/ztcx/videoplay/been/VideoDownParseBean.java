package com.ztcx.videoplay.been;

import java.util.List;
import java.util.Map;

/**
 * 视频解析第二个界面公共类
 */
public class VideoDownParseBean {

    /*公共部分*/
    private String name;//名称
    private String score;//评分
    private String type;//类型
    private String year;//年代
    private String daoyan;//导演
    private String diqu;//地区
    private String yanyuan;//演员
    private String jianjie;//简介
    private String imgUrl;//图片链接
    //周边视频
    private List<Base360Video> recommendVideoList;
    //热门评论
    private List<Comment> commentList;


    /*电视需要的*/
    private String index; //集数 - 暂时没用到
    private List<String> indexList; //盛放集数
    private Map<String,String> jishuMap; //盛放集数+视频连接
    private List<XuanJi> xuanJiList;
    private String jishuTag;



    /*电影需要的播放链接*/
    private String playUrl;//电影里面的播放链接

    /*综艺需要的*/
    private String zhuchi;//主持人
    private String bochu;//播出
    private List<String> playUrlList;//播放链接集合
    private List<String> titleKeyList ;//作为 key 使用，获取下面对象的value
    private Map<String,String> titleImgMap; // 图片Url + 标题
    //最新档期
    private List<NewDangQi> newDangQiList;

    @Override
    public String toString() {
        return "VideoDownParseBean{" +
                "type='" + type + '\'' +
                ", year='" + year + '\'' +
                ", daoyan='" + daoyan + '\'' +
                ", diqu='" + diqu + '\'' +
                ", yanyuan='" + yanyuan + '\'' +
                ", jianjie='" + jianjie + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", index='" + index + '\'' +
                ", indexList=" + indexList +
                ", jishuMap=" + jishuMap +
                ", playUrl='" + playUrl + '\'' +
                ", zhuchi='" + zhuchi + '\'' +
                ", bochu='" + bochu + '\'' +
                ", playUrlList=" + playUrlList +
                ", titleKeyList=" + titleKeyList +
                ", titleImgMap=" + titleImgMap +
                '}';
    }

    public List<NewDangQi> getNewDangQiList() {
        return newDangQiList;
    }

    public void setNewDangQiList(List<NewDangQi> newDangQiList) {
        this.newDangQiList = newDangQiList;
    }

    public String getJishuTag() {
        return jishuTag;
    }

    public void setJishuTag(String jishuTag) {
        this.jishuTag = jishuTag;
    }

    public List<XuanJi> getXuanJiList() {
        return xuanJiList;
    }

    public void setXuanJiList(List<XuanJi> xuanJiList) {
        this.xuanJiList = xuanJiList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Base360Video> getRecommendVideoList() {
        return recommendVideoList;
    }

    public void setRecommendVideoList(List<Base360Video> recommendVideoList) {
        this.recommendVideoList = recommendVideoList;
    }
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBochu() {
        return bochu;
    }

    public void setBochu(String bochu) {
        this.bochu = bochu;
    }

    public List<String> getTitleKeyList() {
        return titleKeyList;
    }

    public void setTitleKeyList(List<String> titleKeyList) {
        this.titleKeyList = titleKeyList;
    }

    public List<String> getPlayUrlList() {
        return playUrlList;
    }

    public void setPlayUrlList(List<String> playUrlList) {
        this.playUrlList = playUrlList;
    }

    public Map<String, String> getTitleImgMap() {
        return titleImgMap;
    }

    public void setTitleImgMap(Map<String, String> titleImgMap) {
        this.titleImgMap = titleImgMap;
    }

    public String getZhuchi() {
        return zhuchi;
    }

    public void setZhuchi(String zhuchi) {
        this.zhuchi = zhuchi;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDaoyan() {
        return daoyan;
    }

    public void setDaoyan(String daoyan) {
        this.daoyan = daoyan;
    }

    public String getDiqu() {
        return diqu;
    }

    public void setDiqu(String diqu) {
        this.diqu = diqu;
    }

    public String getYanyuan() {
        return yanyuan;
    }

    public void setYanyuan(String yanyuan) {
        this.yanyuan = yanyuan;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }


    public List<String> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<String> indexList) {
        this.indexList = indexList;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Map<String, String> getJishuMap() {
        return jishuMap;
    }

    public void setJishuMap(Map<String, String> jishuMap) {
        this.jishuMap = jishuMap;
    }

    public static class Comment{
        private String userImage ;
        private String userName ;
        private String content ;

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "userImage='" + userImage + '\'' +
                    ", userName='" + userName + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }


    public static class XuanJi{
        private boolean ischeck;

        private String number;

        private String playUrl;


        public boolean isIscheck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }
    }
    public static class NewDangQi{
        //标题
        private String title;
        //图片
        private String thumbUrl;
        //播放链接
        private String playUrl;
        //档期
        private  String updateTime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }

        public void setThumbUrl(String thumbUrl) {
            this.thumbUrl = thumbUrl;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
