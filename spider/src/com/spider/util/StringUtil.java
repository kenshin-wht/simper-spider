package com.spider.util;

public class StringUtil {
	
	/**
	 * 网页标题转换
	 * @param title
	 * @return
	 */
	public static String conviterTitle(String title) {
		return title
				.replace(" ", "_")
				.replace("*", "_")
				.replace(",", "_")
				.replace("?", "_")
				.replace("/", "_")
				.replace("\\", "_")
				.replace("“", "_")
				.replace("\"", "_")
				.replace("|", "_")
				.replace("<", "_")
				.replace(">", "_");
	}

	/**
	 * 判断输入是否是数字
	 * @param num
	 * @return
	 */
	public static boolean isNum(String num) {
		try {
			Integer.parseInt(num.trim());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * 判断是否包含关键词
	 * @param title
	 * @param wordList
	 * @return
	 */
	public static boolean contain(String title, String wordList) {
		if(wordList == null && "".equals(wordList.trim())) return true;
		String[] split = wordList.split(";");
		for (String string : split) {
			if(title.contains(string)) return true;
		}
		return false;
	}
	
}
