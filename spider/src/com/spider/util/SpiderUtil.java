package com.spider.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author JHC
 *
 */
public final class SpiderUtil {
	
	/**
	 * 获取页面所有URL地址
	 * @param url 目标页面URL 
	 * @return 页面内包含的url描述以及地址
	 * @throws Exception 
	 */
	public static List<String> getAllIncludeUrlsByPage(String url) throws Exception {
		List<String> urls = new ArrayList<>();
		// 抓取界面
		Document doc = get(url);
		// 获取<a>标签
		Elements aTag = doc.select("a[href]");
		Iterator<Element> iterator = aTag.iterator();
		while(iterator.hasNext()) {
			Element next = iterator.next();
			String attr = next.attr("href");
			if(attr == null || "".equals(attr.trim()) || attr.startsWith("javascript:") || attr.startsWith("#")) continue;
			else if(!attr.startsWith("http") && !attr.startsWith("/")) attr = url + attr;
			else if(attr.startsWith("/")) {
				String headerUrl = "";
				String[] split = url.split("/");
				for(int i = 0; i < 3; i++) headerUrl += split[i] + "/";
				attr = headerUrl.substring(0, headerUrl.lastIndexOf("/")) + attr;
			}
			urls.add(attr);
		}
		return urls;
	}

	
	/**
	 * 根据url抓取界面
	 * @param url 带抓取url地址
	 * @return 抓取界面的文档信息
	 * @throws Exception 抓取失败异常
	 */
	public static Document get(String url) throws Exception {
		Connection conn = Jsoup.connect(url).timeout(5000);
		conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.header("Accept-Encoding", "gzip, deflate, sdch");
		conn.header("Accept-Language", "zh-CN,zh;q=0.8");
		conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		Document doc = conn.get();
		return doc;
	}
}
