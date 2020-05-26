package com.ztcx.videoplay.utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtils {

	private static String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13";
	private static String phoneAgent = "MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

	public static Document jsoupParse(String url){
		Document document = null;
		try {
		 document = Jsoup.connect(url).timeout(5000).userAgent(userAgent).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return document;
		}
		if (document!=null) {
			return document;
		}

		return document;

	}

}
