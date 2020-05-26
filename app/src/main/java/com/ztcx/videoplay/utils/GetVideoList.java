package com.ztcx.videoplay.utils;

import android.support.constraint.solver.widgets.ResolutionNode;

import com.ztcx.videoplay.been.BannerBean;
import com.ztcx.videoplay.been.Base360Video;
import com.ztcx.videoplay.been.HomeBean;
import com.ztcx.videoplay.been.HomeMovie;
import com.ztcx.videoplay.been.SearchVideo;
import com.ztcx.videoplay.been.StarInfo;
import com.ztcx.videoplay.been.Tag;
import com.ztcx.videoplay.been.ThemeVideo;
import com.ztcx.videoplay.been.VideoDownParseBean;
import com.ztcx.videoplay.constant.HttpConstant;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

public class GetVideoList {

	private static String BOOT = "http://www.btanv.com/search";
	private static String BOOT_URL = "http://www.btanv.com";



	/**
	 * 获取所有界面的视频数据
	 */
	public static List<Base360Video> getItemUrl(String URL) {

		List<Base360Video> videosBeanList = new ArrayList<Base360Video>();
		try {
			Document document = JsoupUtils.jsoupParse(URL);
			Element ulParent = document.getElementsByClass("s-tab-main").get(0);
			Element thumbs = ulParent.getElementsByTag("ul").get(0);
			Elements items = thumbs.getElementsByClass("item");
			for (Element item : items) {
				Element aFirst = item.getElementsByTag("a").get(0);
				String hrefText = HttpConstant.PARSE_BOOT + aFirst.attr("href");// 进入跳转到播放界面的链接

				//第一个 div
				Element imgUpdateInfo = aFirst.getElementsByTag("div").get(0);
				String imgUrl = imgUpdateInfo.getElementsByTag("img").get(0).attr("src");// 图片
				String updateInfo = imgUpdateInfo.getElementsByClass("hint").get(0).text(); //跟新信息

				//第二个 div
				Element textInfo = aFirst.getElementsByClass("detail").get(0);
				String title = textInfo.getElementsByClass("s1").get(0).text();//影片名称

				// 创建对象
				Base360Video videoData = new Base360Video();


				videoData.setImgUrl(imgUrl);
				videoData.setVideoName(title);
				videoData.setUpdateInfo(updateInfo);
				videoData.setNextUrl(hrefText);
				videosBeanList.add(videoData);
			}
			return videosBeanList;
		} catch (Exception e) {
			e.printStackTrace();
			return videosBeanList;
		}

	}

	/**
	 * 获取所有界面的视频数据
	 */
	public static ThemeVideo getThemeVideo(String URL) {
		L.e("qpf","请求的地址 -- " + URL);
		ThemeVideo themeVideo = new ThemeVideo();

		List<Base360Video> videosBeanList = new ArrayList<Base360Video>();
		try {
			Document document = JsoupUtils.jsoupParse(URL);
			Element ulParent = document.getElementsByClass("s-tab-main").get(0);
			Element thumbs = ulParent.getElementsByTag("ul").get(0);
			Elements items = thumbs.getElementsByClass("item");
			for (Element item : items) {
				Element aFirst = item.getElementsByTag("a").get(0);
				String hrefText = HttpConstant.PARSE_BOOT + aFirst.attr("href");// 进入跳转到播放界面的链接

				//第一个 div
				Element imgUpdateInfo = aFirst.getElementsByTag("div").get(0);
				String imgUrl = imgUpdateInfo.getElementsByTag("img").get(0).attr("src");// 图片
				String updateInfo = imgUpdateInfo.getElementsByClass("hint").get(0).text(); //跟新信息

				//第二个 div
				Element textInfo = aFirst.getElementsByClass("detail").get(0);
				String title = textInfo.getElementsByClass("s1").get(0).text();//影片名称

				// 创建对象
				Base360Video videoData = new Base360Video();


				videoData.setImgUrl(imgUrl);
				videoData.setVideoName(title);
				videoData.setUpdateInfo(updateInfo);
				videoData.setNextUrl(hrefText);
				videosBeanList.add(videoData);
			}

			/**
			 * 获取标签
			 */

			try{
				Elements tagMapDoc = document.getElementsByClass("s-filter-item js-s-filter");
				Map<String,List<Tag>> tagMap = new HashMap<>();
				for (Element e:tagMapDoc){
					String key = e.getElementsByClass("type").text();
					List<Tag> tags = new ArrayList<>();
					Elements tagsDoc = e.getElementsByClass("js-tongjip");
					for (Element eTag : tagsDoc) {
						Tag tag = new Tag();
						String name = eTag.text();
						String href = eTag.attr("href");
						String[] kvs = href.split("\\?")[1].split("&");
						String key1 = null;
						String value1 = null;
						for (String kv : kvs) {
							if (!kv.contains("all")){
								key1 = kv.split("=")[0];
								value1 = kv.split("=")[1];
							}
						}
						tag.setName(name);
						tag.setKey(key1);
						tag.setValue(value1);
						tags.add(tag);
					}

					Tag tagAll = new Tag();
					tagAll.setValue("all");
					tagAll.setKey(tags.get(0).getKey());
					tagAll.setName("全部");
					tags.add(0,tagAll);

					tagMap.put(key,tags);
					themeVideo.setTagMap(tagMap);
				}

			}catch (Exception e){
				L.e("qpf","获取标签错误 -- " + e.toString());}


			themeVideo.setVideoList(videosBeanList);
			return themeVideo;
		} catch (Exception e) {
			e.printStackTrace();
			return themeVideo;
		}

	}


	/**
	 * 获取播放电视剧播放链接+详细介绍
	 */
	public static VideoDownParseBean getTVStartUrl(String url) throws Exception {

		VideoDownParseBean bean = new VideoDownParseBean();
		Document document = JsoupUtils.jsoupParse(url);
		try {
			String imgUrl = document.getElementsByClass("s-cover").get(0).getElementsByTag("a").get(0)
					.getElementsByTag("img").get(0).attr("src");
			bean.setImgUrl(imgUrl);

			Element jianjie = document.getElementsByClass("item-desc-wrap g-clear js-open-wrap").get(0);

			Element content = document.getElementsByClass("g-clear item-wrap").get(0);
			Elements items = content.getElementsByClass("item");


			int i = 0;
			for (Element item : items) {
				String text = item.text();
				System.out.println(text);
				switch (i) {
					case 0:
						bean.setType(text);
						break;
					case 1:
						bean.setYear(text);
						break;
					case 2:
						bean.setDiqu(text);
						break;
					case 3:
						bean.setDaoyan(text);
						break;
					case 4:
						bean.setYanyuan(text);
						break;
				}
				i++;
			}
			bean.setJianjie(jianjie.text());

			List<String> indexList = new ArrayList<>();
			Map<String, String> map = new LinkedHashMap<String, String>();
			Collections.reverse(indexList);
			bean.setJishuMap(map);
			bean.setIndexList(indexList);
		} catch (Exception e) {
			L.e("qpf","公用部分出错 -- " + e.toString());
		}

		/**
		 * 选集
		 */
		try{
			List<VideoDownParseBean.XuanJi> xuanJiList = new ArrayList<>();
			Elements xjElements = document.getElementsByClass("num-tab-main g-clear js-tab").get(0).getElementsByTag("a");
			for (Element e:xjElements){
				if (!"#".equals(e.attr("href"))){
					VideoDownParseBean.XuanJi xuanJi = new VideoDownParseBean.XuanJi();
					xuanJi.setNumber(e.attr("data-num"));
					xuanJi.setPlayUrl(e.attr("href"));
					xuanJiList.add(xuanJi);
				}
			}
			bean.setXuanJiList(xuanJiList);

		}catch (Exception e){
			L.e("qpf","选集错误 -- " + e.toString());
		}

		//总集数
		try{
			//总集数
			String jishuTag = document.getElementsByClass("tag").get(0).text();
			bean.setJishuTag(jishuTag);
		}catch (Exception e){}



		/**
		 * 名称、得分
		 */
		try{
			String videoName = document.getElementsByClass("title-left g-clear").get(0).getElementsByTag("h1").text();

			bean.setName(videoName);
		}catch (Exception e){
			L.e("qpf","名称错误 -- " + e.toString());
		}
		/**
		 * 得分
		 */
		try{
			String score = document.getElementsByClass("s-title-right").get(0).getElementsByClass("s").text();
			bean.setScore(score);
		}catch (Exception e){
			L.e("qpf","得分错误 -- " + e.toString());
		}


		/**
		 * 热门评论
		 */
		try{
			Elements elements = document.getElementsByClass("m-comment-list").get(0).getElementsByTag("li");
			L.e("qpf","评论个数 -- " + elements.size());
			List<VideoDownParseBean.Comment> commentList = new ArrayList<>();
			for (Element e : elements) {
				String userImage  = e.getElementsByClass("userinfo_img").get(0).attr("data-src");
				String userName = e.getElementsByClass("userinfo_name").get(0).text();
				String content_a = e.getElementsByClass("m-comment-item-bottom js-m-comment-content").get(0).text();
				VideoDownParseBean.Comment comment = new VideoDownParseBean.Comment();
				comment.setUserImage(userImage);
				comment.setUserName(userName);
				comment.setContent(content_a);
				commentList.add(comment);
				L.e("qpf","热门评论 -- " + comment.toString());
			}
			bean.setCommentList(commentList);
		}catch (Exception e){L.e("qpf","热门评论 错误 -- " + e.toString());}


		try{
			/**
			 * 猜你喜欢
			 */
			List<Base360Video> recommendVideoList = new ArrayList<>();
			Element mCBodyRight = document.getElementsByClass("c-body-right").get(0);
			L.e("qpf","整个右边部分 -- " + mCBodyRight.text());
			Element mSGuessJsGuess = mCBodyRight.getElementsByClass("s-guess-list g-clear js-list").get(0);
			L.e("qpf","猜你喜欢部分 -- " + mSGuessJsGuess.html());
			Elements xihuanList = mSGuessJsGuess.getElementsByTag("li");
			L.e("qpf","猜你喜欢部分个数 -- " + xihuanList.size());
			for (Element e:xihuanList){
				String name = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("title");
				String playUrl_a = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");
				String thumbUrl = e.getElementsByTag("img").get(0).attr("data-src");
				Base360Video recommendVideo = new Base360Video();
				recommendVideo.setNextUrl(HttpConstant.PARSE_BOOT + playUrl_a);
				recommendVideo.setVideoName(name);
				recommendVideo.setImgUrl(thumbUrl);
				recommendVideoList.add(recommendVideo);
			}
			bean.setRecommendVideoList(recommendVideoList);
		}catch (Exception e){
			L.e("qpf","猜你喜欢 错误 -- " + e.toString());
		}

		return bean;
	}

	/**
	 * 获取播放电视剧播放链接+详细介绍
	 */
	public static VideoDownParseBean getDongManStartUrl(String url) throws Exception {

		VideoDownParseBean bean = new VideoDownParseBean();
		Document document = JsoupUtils.jsoupParse(url);

		try {

			String imgUrl = document.getElementsByClass("s-cover").get(0).getElementsByTag("a").get(0)
					.getElementsByTag("img").get(0).attr("src");
			bean.setImgUrl(imgUrl);

			Element jianjie = document.getElementsByClass("item-desc-wrap g-clear js-open-wrap").get(0);

			Element content = document.getElementsByClass("g-clear item-wrap").get(0);
			Elements items = content.getElementsByClass("item");


			int i = 0;
			for (Element item : items) {
				String text = item.text();
				System.out.println(text);
				switch (i) {
					case 0:
						bean.setType(text);
						break;
					case 1:
						bean.setYear(text);
						break;
					case 2:
						bean.setDiqu(text);
						break;
					case 3:
						bean.setDaoyan(text);
						break;
					case 4:
						bean.setYanyuan(text);
						break;
				}
				i++;
			}
			bean.setJianjie(jianjie.text());

			List<String> indexList = new ArrayList<>();
			Map<String, String> map = new LinkedHashMap<String, String>();
			Collections.reverse(indexList);
			bean.setJishuMap(map);
			bean.setIndexList(indexList);
		} catch (Exception e) {
			L.e("qpf","公用部分出错 -- " + e.toString());
		}

		/**
		 * 选集
		 */
		try{
			List<VideoDownParseBean.XuanJi> xuanJiList = new ArrayList<>();
			try{
				Elements xjElements1 = document.getElementsByClass("m-series-number-container g-clear").get(0).getElementsByTag("a");
				for (Element e:xjElements1){
					if (!(e.attr("href")).startsWith("#")){
						VideoDownParseBean.XuanJi xuanJi = new VideoDownParseBean.XuanJi();
						xuanJi.setNumber(e.attr("data-num"));
						xuanJi.setPlayUrl(e.attr("href"));
						xuanJiList.add(xuanJi);
					}
				}
			}catch (Exception e){}

			if (xuanJiList.size() == 0){
				Elements xjElements = document.getElementsByClass("js-series-part m-series-number-container g-clear").get(0).getElementsByTag("a");
				for (Element e:xjElements){
					if (!(e.attr("href")).startsWith("#")){
						VideoDownParseBean.XuanJi xuanJi = new VideoDownParseBean.XuanJi();
						xuanJi.setNumber(e.attr("data-num"));
						xuanJi.setPlayUrl(e.attr("href"));
						xuanJiList.add(xuanJi);
					}
				}
			}
			bean.setXuanJiList(xuanJiList);

		}catch (Exception e){
			L.e("qpf","选集错误 -- " + e.toString());
		}

		//总集数
		try{
			//总集数
			String jishuTag = document.getElementsByClass("tag").get(0).text();
			bean.setJishuTag(jishuTag);
		}catch (Exception e){
			L.e("qpf","总集数错误 -- " + e.toString());
		}

		/**
		 * 名称、得分
		 */
		try{
			String videoName = document.getElementsByClass("title-left g-clear").get(0).getElementsByTag("h1").text();
			bean.setName(videoName);
		}catch (Exception e){
			L.e("qpf","名称错误 -- " + e.toString());
		}
		/**
		 * 得分
		 */
		try{
			String score = document.getElementsByClass("s-title-right").get(0).getElementsByClass("s").text();
			bean.setScore(score);
		}catch (Exception e){
			L.e("qpf","得分错误 -- " + e.toString());
		}


		/**
		 * 热门评论
		 */
		try{
			Elements elements = document.getElementsByClass("m-comment-list").get(0).getElementsByTag("li");
			L.e("qpf","评论个数 -- " + elements.size());
			List<VideoDownParseBean.Comment> commentList = new ArrayList<>();
			for (Element e : elements) {
				String userImage  = e.getElementsByClass("userinfo_img").get(0).attr("data-src");
				String userName = e.getElementsByClass("userinfo_name").get(0).text();
				String content_a = e.getElementsByClass("m-comment-item-bottom js-m-comment-content").get(0).text();
				VideoDownParseBean.Comment comment = new VideoDownParseBean.Comment();
				comment.setUserImage(userImage);
				comment.setUserName(userName);
				comment.setContent(content_a);
				commentList.add(comment);
				L.e("qpf","热门评论 -- " + comment.toString());
			}
			bean.setCommentList(commentList);
		}catch (Exception e){L.e("qpf","热门评论 错误 -- " + e.toString());}


		try{
			/**
			 * 猜你喜欢
			 */
			List<Base360Video> recommendVideoList = new ArrayList<>();
			Element mCBodyRight = document.getElementsByClass("c-body-right").get(0);
			L.e("qpf","整个右边部分 -- " + mCBodyRight.text());
			Element mSGuessJsGuess = mCBodyRight.getElementsByClass("s-guess-list g-clear js-list").get(0);
			L.e("qpf","猜你喜欢部分 -- " + mSGuessJsGuess.html());
			Elements xihuanList = mSGuessJsGuess.getElementsByTag("li");
			L.e("qpf","猜你喜欢部分个数 -- " + xihuanList.size());
			for (Element e:xihuanList){
				String name = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("title");
				String playUrl_a = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");
				String thumbUrl = e.getElementsByTag("img").get(0).attr("data-src");
				Base360Video recommendVideo = new Base360Video();
				recommendVideo.setNextUrl(HttpConstant.PARSE_BOOT + playUrl_a);
				recommendVideo.setVideoName(name);
				recommendVideo.setImgUrl(thumbUrl);
				recommendVideoList.add(recommendVideo);
			}
			bean.setRecommendVideoList(recommendVideoList);
		}catch (Exception e){
			L.e("qpf","猜你喜欢 错误 -- " + e.toString());
		}

		return bean;
	}


	/**
	 * 获取电影播放链接+详情介绍
	 */
	public static VideoDownParseBean getMoveStartUrl(String url) throws Exception {

		VideoDownParseBean bean = new VideoDownParseBean();
		try {
			Document document = JsoupUtils.jsoupParse(url);

			try {
				Element jishu = document.getElementsByClass("top-list-btns g-clear").get(0);
				Element a = jishu.getElementsByTag("a").get(0);
				String playUrl = a.attr("href");//播放链接
				bean.setPlayUrl(playUrl);
			}catch (Exception e){}

			Element tupian = document.getElementsByClass("g-playicon s-cover-img").get(0);
			Element img = tupian.getElementsByTag("img").get(0);
			String imgUrl = img.attr("src");//图片链接
			bean.setImgUrl(imgUrl);

			Element content = document.getElementsByClass("g-clear item-wrap").get(0);
			Elements items = content.getElementsByClass("item");

			Element jianjie = document.getElementsByClass("item-desc-wrap g-clear js-open-wrap").get(0);
			bean.setJianjie(jianjie.text());

			/**
			 * 名称、得分
			 */
			String videoName = document.getElementsByClass("title-left g-clear").get(0).getElementsByTag("h1").text();
			String score = document.getElementsByClass("s-title-right").get(0).getElementsByClass("s").text();

			bean.setName(videoName);
			bean.setScore(score);
			L.e("qpf","影片名称 -- " + videoName + "得分 -- " + score);

			/**
			 * 热门评论
			 */
			try{
				Elements elements = document.getElementsByClass("m-comment-list").get(0).getElementsByTag("li");
				L.e("qpf","评论个数 -- " + elements.size());
				List<VideoDownParseBean.Comment> commentList = new ArrayList<>();
				for (Element e : elements) {
					String userImage  = e.getElementsByClass("userinfo_img").get(0).attr("data-src");
					String userName = e.getElementsByClass("userinfo_name").get(0).text();
					String content_a = e.getElementsByClass("m-comment-item-bottom js-m-comment-content").get(0).text();
					VideoDownParseBean.Comment comment = new VideoDownParseBean.Comment();
					comment.setUserImage(userImage);
					comment.setUserName(userName);
					comment.setContent(content_a);
					commentList.add(comment);
					L.e("qpf","热门评论 -- " + comment.toString());
				}
				bean.setCommentList(commentList);
			}catch (Exception e){L.e("qpf","错误 -- " + e.toString());}


			/**
			 * 猜你喜欢
			 */
			List<Base360Video> recommendVideoList = new ArrayList<>();
			Element mCBodyRight = document.getElementsByClass("c-body-right").get(0);
			L.e("qpf","整个右边部分 -- " + mCBodyRight.text());
			Element mSGuessJsGuess = mCBodyRight.getElementsByClass("s-guess-list g-clear js-list").get(0);
			L.e("qpf","猜你喜欢部分 -- " + mSGuessJsGuess.html());
			Elements xihuanList = mSGuessJsGuess.getElementsByTag("li");
			L.e("qpf","猜你喜欢部分个数 -- " + xihuanList.size());
			for (Element e:xihuanList){
				String name = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("title");
				String playUrl_a = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");
				String thumbUrl = e.getElementsByTag("img").get(0).attr("data-src");
                Base360Video recommendVideo = new Base360Video();
				recommendVideo.setNextUrl(HttpConstant.PARSE_BOOT + playUrl_a);
				recommendVideo.setVideoName(name);
				recommendVideo.setImgUrl(thumbUrl);
				recommendVideoList.add(recommendVideo);
			}
			bean.setRecommendVideoList(recommendVideoList);
			int i = 0;
			for (Element item : items) {
				String text = item.text();
				System.out.println(text);
				switch (i) {
					case 0:
						bean.setType(text);
						break;
					case 1:
						bean.setYear(text);
						break;
					case 2:
						bean.setDiqu(text);
						break;
					case 3:
						bean.setDaoyan(text);
						break;
					case 4:
						bean.setYanyuan(text);
						break;
				}
				i++;
			}
		} catch (Exception e) {
		}
		return bean;
	}


	/**
	 * 获取综艺播放链接+详情介绍
	 */
	public static VideoDownParseBean getZongYiStartUrl(String url) throws Exception {

		VideoDownParseBean bean = new VideoDownParseBean();
		Document document = JsoupUtils.jsoupParse(url);

		/**
		详情
		 */
        try{
            Elements desc = document.getElementsByClass("base-item-wrap g-clear").get(0).getElementsByTag("p");
            System.out.println(desc);
            String type = null;
            String year = null;
            String diqu = null;
            String bochu = null;
            String mingxin = null;
            for (int i = 0; i < desc.size(); i++){
                Element e = desc.get(i);
				String content = e.text();
				if (content.contains("地区")){
					diqu = content;
				}else if (content.contains("类型")){
					type = content;
				}else if (content.contains("明星")){
					mingxin = content;
				}else if (content.contains("播出")){
					bochu = content;
				}else if (content.contains("年代")){
					year = content;
				}
            }
            bean.setDiqu(diqu);
            bean.setYear(year);
            bean.setType(type);
            bean.setDaoyan(bochu);
            bean.setYanyuan(mingxin);
            String videoName = document.getElementsByClass("title-left g-clear").get(0)
                    .getElementsByTag("h1").get(0).text();
            bean.setName(videoName);
        }catch (Exception e){
            L.e("qpf","获取详情失败 -- " + e.toString());
        }
        /**
         * 得分
         */
        try{
            String score = document.getElementsByClass("s-title-right").get(0).getElementsByClass("s").text();
            bean.setScore(score);
        }catch (Exception e){
            L.e("qpf","得分错误 -- " + e.toString());
        }
        /**
         * 热门评论
         */
        try{
            Elements elements = document.getElementsByClass("m-comment-list").get(0).getElementsByTag("li");
            L.e("qpf","评论个数 -- " + elements.size());
            List<VideoDownParseBean.Comment> commentList = new ArrayList<>();
            for (Element e : elements) {
                String userImage  = e.getElementsByClass("userinfo_img").get(0).attr("data-src");
                String userName = e.getElementsByClass("userinfo_name").get(0).text();
                String content_a = e.getElementsByClass("m-comment-item-bottom js-m-comment-content").get(0).text();
                VideoDownParseBean.Comment comment = new VideoDownParseBean.Comment();
                comment.setUserImage(userImage);
                comment.setUserName(userName);
                comment.setContent(content_a);
                commentList.add(comment);
                L.e("qpf","热门评论 -- " + comment.toString());
            }
            bean.setCommentList(commentList);
        }catch (Exception e){L.e("qpf","热门评论 错误 -- " + e.toString());}

        try{
            /**
             * 猜你喜欢
             */
            List<Base360Video> recommendVideoList = new ArrayList<>();
            Element mCBodyRight = document.getElementsByClass("c-body-right").get(0);
            L.e("qpf","整个右边部分 -- " + mCBodyRight.text());
            Element mSGuessJsGuess = mCBodyRight.getElementsByClass("s-guess-list g-clear js-list").get(0);
            L.e("qpf","猜你喜欢部分 -- " + mSGuessJsGuess.html());
            Elements xihuanList = mSGuessJsGuess.getElementsByTag("li");
            L.e("qpf","猜你喜欢部分个数 -- " + xihuanList.size());
            for (Element e:xihuanList){
                String name = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("title");
                String playUrl_a = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");
                String thumbUrl = e.getElementsByTag("img").get(0).attr("data-src");
                Base360Video recommendVideo = new Base360Video();
                recommendVideo.setNextUrl(HttpConstant.PARSE_BOOT + playUrl_a);
                recommendVideo.setVideoName(name);
                recommendVideo.setImgUrl(thumbUrl);
                recommendVideoList.add(recommendVideo);
            }
            bean.setRecommendVideoList(recommendVideoList);
        }catch (Exception e){
            L.e("qpf","猜你喜欢 错误 -- " + e.toString());
        }

		/**
		 * 最新档期
		 */
		try{
			List<VideoDownParseBean.NewDangQi> newDangQiList = new ArrayList<>();
			Elements documents = document.getElementsByClass("list w-newfigure-list g-clear").get(0).getElementsByTag("li");
			for (Element e :documents) {
				VideoDownParseBean.NewDangQi newDangQi = new VideoDownParseBean.NewDangQi();
				String name = e.attr("title");
				String playUrl = e.getElementsByTag("a").get(0).attr("href");
				String imageUrl = e.getElementsByTag("img").get(0).attr("data-src");
				String updateTime = e.getElementsByClass("w-newfigure-hint").get(0).text();
				newDangQi.setTitle(name);
				newDangQi.setPlayUrl(playUrl);
				newDangQi.setThumbUrl(imageUrl);
				newDangQi.setUpdateTime(updateTime);
				newDangQiList.add(newDangQi);
			}
			bean.setNewDangQiList(newDangQiList);
		}catch (Exception e){}

		/**
		 * 	总集数
		 */
		try{
			String jishuTag = document.getElementsByClass("tag").get(0).text();
			bean.setJishuTag(jishuTag);
		}catch (Exception e){
			L.e("qpf","总集数错误 -- " + e.toString());
		}


		return bean;
	}



	/**获取首页的数据*/
	public static HomeBean getOutHome(String url){
		HomeBean bean =	new HomeBean();
		Document document = JsoupUtils.jsoupParse(url);
		/**
		 * 获取banner数据
		 */
		List<BannerBean> bannerBeanList = new ArrayList<>();
		try{
			Elements bannerDocList = document.getElementById("tj-topslider")
					.getElementsByClass("b-topslider-item js-g-slide-item g-slide-item");
			for (Element e : bannerDocList) {
				BannerBean banner = new BannerBean();
				//横幅广告
				Element bannerDoc = e.getElementsByClass("p1 g-playicon js-playicon").get(0);
				//图片
				String image = bannerDoc.getElementsByTag("img").attr("src");
				//跳转链接
				String bannerUrl = bannerDoc.attr("href");
				//标题
				String title = bannerDoc.getElementsByClass("title").get(0).text();
				//副标题
				String subTitle = bannerDoc.getElementsByClass("desc").get(0).text();

				banner.setImage(image);
				banner.setUrl(bannerUrl);
				banner.setTitle(title);
				banner.setSubTitle(subTitle);

				bannerBeanList.add(banner);
			}
		}catch (Exception e){
			L.e("qpf","解析banner出错" + e.toString());}

		bean.setBannerList(bannerBeanList);

		/**
		 * 获取电视标签
		 */
		try{
			Elements tagDocList = document.getElementById("js-dianshi")
					.getElementsByClass("p-mod-desc");

			List<Tag> tagList = new ArrayList<>();
			for (Element e : tagDocList) {
				Tag tag = new Tag();
				String name = e.text();
				String value = null;
				String key = null;
				String href = e.attr("href");
				if (href.contains("cat=")){
					String[] key_value = href.split("&");
					for (String kv:key_value){
						if (kv.contains("cat=")){
							key = kv.split("=")[0];
							value = kv.split("=")[1];
						}
					}
				}

				tag.setName(name);
				tag.setKey(key);
				tag.setValue(value);
				tagList.add(tag);
			}
			bean.setTvTtagList(tagList);
		}catch (Exception e){L.e("qpf","解析获取电视标签出错" + e.toString());}

		/**
		 * 电视列表
		 */
		try{
			List<Base360Video> tvList = new ArrayList<>();
			Elements tvListDoc = document.getElementsByClass("list g-clear w-newfigure-list js-list").get(0).getElementsByTag("li");
			for (Element e:tvListDoc){
				Base360Video homeTV = new Base360Video();
				String title = e.getElementsByClass("s1").get(0).text();
				try{
					String score = e.getElementsByClass("s2").get(0).text();
					homeTV.setScore(score);
				}catch (Exception e1){}
				String subtitle = e.getElementsByClass("w-newfigure-desc").get(0).text();
				String playUrl = e.getElementsByTag("a").get(0).attr("href");
				String newJI = e.getElementsByClass("w-newfigure-hint").get(0).text();
				String imageUrl = e.getElementsByTag("img").get(0).attr("data-src");
				//获取评分

				homeTV.setImgUrl(imageUrl);
				homeTV.setSubTitle(subtitle);
				homeTV.setVideoName(title);
				homeTV.setNextUrl(playUrl);
				homeTV.setUpdateInfo(newJI);

				tvList.add(homeTV);
			}

			bean.setHomeTvList(tvList);

		}catch (Exception e){L.e("qpf","解析获取首页电视签出错" + e.toString());}

		/**
		 * 获取电影标签
		 */
		try{
			Elements tagDocList = document.getElementsByClass("p-mod remendy").get(0)
					.getElementsByClass("p-mod-desc");

			List<Tag> tagList = new ArrayList<>();
			for (Element e : tagDocList) {
				Tag tag = new Tag();
				String name = e.text();
				String value = null;
				String key = null;
				String href = e.attr("href");
				if (href.contains("cat=")){
					String[] key_value = href.split("&");
					for (String kv:key_value){
						if (kv.contains("cat=")){
							key = kv.split("=")[0];
							value = kv.split("=")[1];
						}
					}
				}

				tag.setName(name);
				tag.setKey(key);
				tag.setValue(value);
				tagList.add(tag);
			}
			bean.setMovieTagList(tagList);
		}catch (Exception e){L.e("qpf","解析获取电视标签出错" + e.toString());}

		/**
		 * 电影列表
		 */
		try{
			List<Base360Video> movieList = new ArrayList<>();
			Elements tvListDoc = document.getElementsByClass("content rmcontent").get(0).getElementsByTag("li");
			for (Element e:tvListDoc){
				Base360Video homeMovie = new Base360Video();
				String title = e.getElementsByClass("s1").get(0).text();
				String subtitle = e.getElementsByClass("w-newfigure-desc").get(0).text();
				String playUrl = e.getElementsByTag("a").get(0).attr("href");
//				String newJI = e.getElementsByClass("w-newfigure-hint").get(0).text();
				String imageUrl = e.getElementsByTag("img").get(0).attr("data-src");
				try{
					String score = e.getElementsByClass("s2").get(0).text();
					homeMovie.setScore(score);
				}catch (Exception e1){}

				homeMovie.setImgUrl(imageUrl);
				homeMovie.setSubTitle(subtitle);
				homeMovie.setVideoName(title);
				homeMovie.setNextUrl(playUrl);
//				homeMovie.setUpdateInfo(newJI);

				movieList.add(homeMovie);
			}
			bean.setHomeMovieList(movieList);

		}catch (Exception e){L.e("qpf","解析获取首页电影出错" + e.toString());}


		/**
		 * 动漫标签
		 */
		try{
			Elements tagDocList = document.getElementsByClass("dongman g-clear").get(0)
					.getElementsByClass("p-mod-desc");

			List<Tag> tagList = new ArrayList<>();
			for (Element e : tagDocList) {
				Tag tag = new Tag();
				String name = e.text();
				String value = null;
				String key = null;
				String href = e.attr("href");
				if (href.contains("cat=")){
					String[] key_value = href.split("&");
					for (String kv:key_value){
						if (kv.contains("cat=")){
							key = kv.split("=")[0];
							value = kv.split("=")[1];
						}
					}
				}

				tag.setName(name);
				tag.setKey(key);
				tag.setValue(value);
				tagList.add(tag);
			}
			bean.setDongmanTagList(tagList);
		}catch (Exception e){L.e("qpf","解析获取动漫标签出错" + e.toString());}

		/**
		 * 动漫列表
		 */
		try{
			List<Base360Video> dongmanList = new ArrayList<>();
			Elements tvListDoc = document.getElementsByClass("content content-list").get(0).getElementsByTag("li");
			for (Element e:tvListDoc){
				Base360Video homeDongman = new Base360Video();
				String title = e.getElementsByClass("s1").get(0).text();
				String subtitle = e.getElementsByClass("w-newfigure-desc").get(0).text();
				String playUrl = e.getElementsByTag("a").get(0).attr("href");
				String newJI = e.getElementsByClass("w-newfigure-hint").get(0).text();
				String imageUrl = e.getElementsByTag("img").get(0).attr("data-src");
				try{
					String score = e.getElementsByClass("s2").get(0).text();
					homeDongman.setScore(score);
				}catch (Exception e1){}
				homeDongman.setImgUrl(imageUrl);
				homeDongman.setSubTitle(subtitle);
				homeDongman.setVideoName(title);
				homeDongman.setNextUrl(playUrl);
				homeDongman.setUpdateInfo(newJI);

				dongmanList.add(homeDongman);
			}
			bean.setHomeDongManList(dongmanList);

		}catch (Exception e){L.e("qpf","解析获取首页动漫列表出错" + e.toString());}
		/**
		 * 综艺标签
		 */
		try{
			Elements tagDocList = document.getElementsByClass("zongyi g-clear").get(0)
					.getElementsByClass("p-mod-desc");

			List<Tag> tagList = new ArrayList<>();
			for (Element e : tagDocList) {
				Tag tag = new Tag();
				String name = e.text();
				String value = null;
				String key = null;
				String href = e.attr("href");
				if (href.contains("cat=")){
					String[] key_value = href.split("&");
					for (String kv:key_value){
						if (kv.contains("cat=")){
							key = kv.split("=")[0];
							value = kv.split("=")[1];
						}
					}
				}

				tag.setName(name);
				tag.setKey(key);
				tag.setValue(value);
				tagList.add(tag);
			}
			bean.setZongyiTagList(tagList);
		}catch (Exception e){L.e("qpf","解析获取综艺标签出错" + e.toString());}

		/**
		 * 综艺列表
		 */
		try{
			List<Base360Video> zongyiList = new ArrayList<>();
			Elements tvListDoc = document.getElementsByClass("content zycontent").get(0).getElementsByTag("li");
			for (Element e:tvListDoc){
				Base360Video homeZingyi = new Base360Video();
				String title = e.getElementsByClass("s1").get(0).text();
				String subtitle = e.getElementsByClass("w-newfigure-desc").get(0).text();
				String playUrl = e.getElementsByTag("a").get(0).attr("href");
				String newJI = e.getElementsByClass("w-newfigure-hint").get(0).text();
				String imageUrl = e.getElementsByTag("img").get(0).attr("data-src");

				try{
					String score = e.getElementsByClass("s2").get(0).text();
					homeZingyi.setScore(score);
				}catch (Exception e1){}

				homeZingyi.setImgUrl(imageUrl);
				homeZingyi.setSubTitle(subtitle);
				homeZingyi.setVideoName(title);
				homeZingyi.setNextUrl(playUrl);
				homeZingyi.setUpdateInfo(newJI);

				zongyiList.add(homeZingyi);
			}
			bean.setHomeZongyiList(zongyiList);

		}catch (Exception e){L.e("qpf","解析获取首页综艺列表出错" + e.toString());}

		/**
		 * 获取热搜榜
		 */
		List<String> hotList = new ArrayList<>();
		try{
			Elements hotDocList = document.getElementsByClass("eb-suggest-item js-item");
			for (Element e:hotDocList){
				hotList.add(e.text());
			}

			L.e("qpf","热搜 -- "+hotList.toString());
		}catch (Exception e){L.e("qpf","获取热搜错误 -- " + e.toString());}
		bean.setHotSearch(hotList);
		return bean;
	}

	//获取搜索数据
	public static SearchVideo getSearchVideo (String url){
		SearchVideo searchVideo = new SearchVideo();
		Document document = JsoupUtils.jsoupParse(url);

		/**
		 * 明星详情
		 */
		StarInfo starInfo = new StarInfo();
		try{
			Element starDoc = document.getElementsByClass("js-logger g-clear w-star").get(0);
			//明星的图片
			String image = starDoc.getElementsByClass("w-star-imglink").get(0)
                    .getElementsByTag("img").get(0)
					.attr("src");
			//姓名
			String name = starDoc.getElementsByClass("w-star-title").get(0).text();
			//别名
			String alias = starDoc.getElementsByClass("w-star-alias").get(0).text();
			//属性：
			List<String> attributeList = new ArrayList<>();
			Elements attributeListDoc = starDoc.getElementsByClass("g-clear").get(0)
					.getElementsByTag("dd");
			for (Element e : attributeListDoc) {
				attributeList.add(e.text());
			}
			//简介
			String intro = starDoc.getElementsByClass("w-star-intro").get(0).getElementsByTag("dd").get(0).text();

			starInfo.setName(name);
			starInfo.setImage(image);
			starInfo.setAlias(alias);
			starInfo.setAttributeList(attributeList);
            starInfo.setIntro(intro);
            searchVideo.setStarInfo(starInfo);
        }catch (Exception e){L.e("qpf","解析明星出错 -- " + starInfo.toString());}

		/**
		 * 电影详情
		 */
		List<SearchVideo.SpecialVideo> specialVideoList = new ArrayList<>();
		try{
			//标题
			Elements starBarName = document.getElementsByClass("star-bar");
			List<String> titleList = new ArrayList<>();
			for (Element e : starBarName) {
				titleList.add(e.text());
			}

			//电影
			Elements specialListDoc = document
					.getElementsByClass("a-engine-mfigure-list a-engine-mfigure-list-all g-clear");
			for (int i = 0;i < specialListDoc.size();i ++) {
                //标题
			    SearchVideo.SpecialVideo specialVideo = new SearchVideo.SpecialVideo();
                specialVideo.setTitle(titleList.get(i).split(" ")[0]);
                try{ specialVideo.setSubTitle(titleList.get(i).split(" ")[1]);}catch (Exception e){}

                List<Base360Video> videoList = new ArrayList<>();
                Elements videoListDoc = specialListDoc.get(i).getElementsByClass("w-mfigure");
                for (Element element :videoListDoc) {
                    Base360Video video = new Base360Video();
                    //跳转地址
                    String playUrl = element.getElementsByClass("w-mfigure-imglink g-playicon js-playicon").get(0)
                            .attr("href");
                    //图片地址
                    String image = element.getElementsByTag("img").get(0).attr("data-src");
                    //电影名称
                    String title = element.getElementsByClass("w-mfigure-title").get(0).text();
                    String score = null;
                    try{score = element.getElementsByClass("w-mfigure-score").get(0).text();}catch (Exception e){}
                    String info = null;
                    try{info = element.getElementsByClass("w-mfigure-hint").get(0).text();}catch (Exception e){}
                    video.setNextUrl(playUrl);
                    video.setImgUrl(image);
                    video.setVideoName(title);
                    video.setScore(score);
                    video.setUpdateInfo(info);
                    videoList.add(video);
                }
                specialVideo.setVideoList(videoList);

                specialVideoList.add(specialVideo);
			}
            searchVideo.setSpecialVideoList(specialVideoList);

            /**
             * 获取视频列表
             */
            List<Base360Video> videoList = new ArrayList<>();
            try{
                //图片 名称 评分 播放页面地址 地区 导演 简介
                Elements searchMovieListDoc  = document.getElementById("js-longvideo").getElementsByClass("js-longitem index-dianying js-index-dianying  g-clear");
                Elements searchTvListDoc = document.getElementById("js-longvideo").getElementsByClass("js-dianshi index-dianshi  g-clear");
                Elements searchZongyiListDoc = document.getElementById("js-longvideo").getElementsByClass("js-longitem index-zongyi  js-zongyi g-clear");
                Elements searchDongmanListDoc = document.getElementById("js-longvideo").getElementsByClass("js-longitem index-dongman js-m-dongman  g-clear");

                List<Base360Video> movieList = new ArrayList<>();
                List<Base360Video> tvList = new ArrayList<>();
                List<Base360Video> zongyiList = new ArrayList<>();
                List<Base360Video> dongmanList = new ArrayList<>();

                try{
                    for (Element e:searchMovieListDoc){
                        String name = e.getElementsByClass("title").get(0).getElementsByTag("b").text();
                        String typeDesc = e.getElementsByClass("title").get(0)
                                .getElementsByClass("playtype").get(0).text();
                        String image = e.getElementsByClass("g-playicon js-playicon").get(0)
                                .getElementsByTag("img").attr("src");
                        String playUrl = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");
                        String area = e.getElementsByClass("area").get(0).text();
                        String actor = e.getElementsByClass("actor").get(0).text();
                        String description = e.getElementsByClass("m-description").get(0).getElementsByTag("p").get(0).text();

                        Base360Video video = new Base360Video();
                        video.setVideoName(name);
                        video.setTypeName(typeDesc);
                        video.setImgUrl(image);
                        video.setNextUrl(playUrl);
                        video.setArea(area);
                        video.setActor(actor);
                        video.setDescription(description);
                        movieList.add(video);
                    }

                }catch (Exception e){L.e("qpf","搜索电影错误 -- " + e.toString());}

                try{  for (Element e:searchTvListDoc){
                    String name = e.getElementsByClass("title").get(0).getElementsByTag("b").text();
                    String typeDesc = e.getElementsByClass("title").get(0)
                            .getElementsByClass("playtype").get(0).text();
                    String image = e.getElementsByClass("g-playicon js-playicon").get(0)
                            .getElementsByTag("img").attr("src");
                    String playUrl = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");
                    String area = e.getElementsByClass("area").get(0).text();
                    String actor = e.getElementsByClass("actor").get(0).text();
                    String description = e.getElementsByClass("m-description").get(0).getElementsByTag("p").get(0).text();

                    Base360Video video = new Base360Video();
                    video.setVideoName(name);
                    video.setTypeName(typeDesc);
                    video.setImgUrl(image);
                    video.setNextUrl(playUrl);
                    video.setArea(area);
                    video.setActor(actor);
                    video.setDescription(description);
                    tvList.add(video);
                }}catch (Exception e){L.e("qpf","搜索电视错误 -- " + e.toString());}

                try{
                    for (Element e:searchZongyiListDoc){
                        String name = e.getElementsByClass("title").get(0).getElementsByTag("b").text();
                        String typeDesc = e.getElementsByClass("title").get(0)
                                .getElementsByClass("playtype").get(0).text();
                        String image = e.getElementsByClass("g-playicon js-playicon").get(0)
                                .getElementsByTag("img").attr("src");
                        String playUrl = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");
                        String area = e.getElementsByClass("area").get(0).text();
                        String actor = e.getElementsByClass("actor").get(0).text();
                        String description = e.getElementsByClass("m-description").get(0).getElementsByTag("p").get(0).text();

                        Base360Video video = new Base360Video();
                        video.setVideoName(name);
                        video.setTypeName(typeDesc);
                        video.setImgUrl(image);
                        video.setNextUrl(playUrl);
                        video.setArea(area);
                        video.setActor(actor);
                        video.setDescription(description);
                        zongyiList.add(video);
                    }
                }catch (Exception e){L.e("qpf","搜索综艺错误 -- " + e.toString());}

                try{
                    for (Element e:searchDongmanListDoc){
                        String name = e.getElementsByClass("title").get(0).getElementsByTag("b").text();
                        String typeDesc = e.getElementsByClass("title").get(0)
                                .getElementsByClass("playtype").get(0).text();
                        String image = e.getElementsByClass("g-playicon js-playicon").get(0)
                                .getElementsByTag("img").attr("src");
                        String playUrl = e.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");
                        String area = e.getElementsByClass("area").get(0).text();
                        String actor = e.getElementsByClass("actor").get(0).text();
                        String description = e.getElementsByClass("m-description").get(0).getElementsByTag("p").get(0).text();

                        Base360Video video = new Base360Video();
                        video.setVideoName(name);
                        video.setTypeName(typeDesc);
                        video.setImgUrl(image);
                        video.setNextUrl(playUrl);
                        video.setArea(area);
                        video.setActor(actor);
                        video.setDescription(description);
                        dongmanList.add(video);
                    }

                }catch (Exception e){L.e("qpf","搜索动漫错误 -- " + e.toString());}

                videoList.addAll(movieList);
                videoList.addAll(tvList);
                videoList.addAll(zongyiList);
                videoList.addAll(dongmanList);

                searchVideo.setVideoList(videoList);
            }catch (Exception e){}
        }catch (Exception e){L.e("qpf","解析视频出错 -- " + e.toString());}


		return searchVideo;
	}

	//获取首页电影
	public static HomeMovie getHomeMovie(String url){
		HomeMovie homeMovie = new HomeMovie();
		Document document = JsoupUtils.jsoupParse(url);
		List<BannerBean> bannerBeans = new ArrayList<>();
		try{
			Elements bannerListDoc = document.getElementsByClass("b-topslidernew-item js-slide-item");
			for (Element e : bannerListDoc) {
				//图片
				String imageStyle = e.getElementsByClass("b-topslidernew-img js-slide-img").get(0).attr("style");
				String image = imageStyle.substring(imageStyle.indexOf("(")+1,imageStyle.indexOf(")"));
				//跳转链接
				String playUrl = e.getElementsByTag("a").attr("href");
				BannerBean bannerBean = new BannerBean();
				bannerBean.setImage(image);
				bannerBean.setUrl(playUrl);
				bannerBeans.add(bannerBean);
			}
		}catch (Exception e){
			L.e("qpf","获取banner失败 -- " + e.toString());}
			homeMovie.setBannerBeanList(bannerBeans);

			List<Base360Video> newMovieList = getMovieList(document,"jingcaituijian-list w-newfigure-list g-clear js-list");
			List<Base360Video> rankingMovieList = getMovieList(document,"w-newfigure-list g-clear js-view on");;
			List<Base360Video> recommendMovieList = getMovieList(document,"m-mod4-list w-newfigure-list g-clear js-list");;
			List<Base360Video> hotMovieList = getMovieList(document,"jm-mod4-list w-newfigure-list g-clear js-list");;
			List<Base360Video> hotAllMovieList = getMovieList(document,"m-mod4-list w-newfigure-list g-clear js-list");;

			homeMovie.setNewMovieList(newMovieList);
			homeMovie.setRankingMovieList(rankingMovieList);

			homeMovie.setHotAllMovieList(hotAllMovieList);
			homeMovie.setHotMovieList(hotMovieList);
			homeMovie.setRecommendMovieList(recommendMovieList);


		return homeMovie;
	}

	private static List<Base360Video> getMovieList(Document document,String className){
		List<Base360Video> newMovieList = new ArrayList<>();
		try{
			Elements movieDocList = document.getElementsByClass(className)
					.get(0).getElementsByTag("li");
			for (Element e : movieDocList) {
				String image = e.getElementsByTag("img").get(0).attr("data-src");
				String name = e.getElementsByClass("s1").get(0).text();
				String subname = e.getElementsByClass("w-newfigure-desc").get(0).text();
				String playUrl = e.getElementsByClass("js-link").get(0).attr("href");
				Base360Video video = new Base360Video();
				video.setVideoName(name);
				video.setSubTitle(subname);
				video.setImgUrl(image);
				video.setNextUrl(playUrl);
				newMovieList.add(video);
			}
		}catch (Exception e){
		}
		return newMovieList;
	}


}
