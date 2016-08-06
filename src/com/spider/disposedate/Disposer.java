package com.spider.disposedate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Disposer {
	/**
	 * 把网页字节码,转换成字符串结果
	 * 
	 * @param is
	 * @return
	 */
	public String streamToReader(InputStream is) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuilder result = new StringBuilder();
		String str = "";
		try {
			while ((str = br.readLine()) != null) {
				result.append(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * 获取URL 网址的 输出流
	 * 
	 * @param string
	 *            网址
	 * @return InputStream 输出流
	 */
	public InputStream getInputStream(String string) {
		try {
			URL curl = new URL(string);
			HttpURLConnection conn = (HttpURLConnection) curl.openConnection();
			conn.setReadTimeout(3000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
			conn.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language",
					"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");

			if (conn.getResponseCode() == 200 || conn.getResponseCode() == 302) {
				return conn.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 需要对返回的list判断是否为空 用来处理网页源代码 用list集合来返回网页中符合要求的url
	 * 
	 * @param result
	 *            网页源代码
	 * @param start
	 *            截取地址的开始
	 * @param end
	 *            截取地址的结束
	 * @return
	 */

	public List<String> urls(String result, String start, String end) {
		List<String> list = new ArrayList<String>();
		int slength = start.length();
		int elength = end.length();
		int index1 = -1;
		int index2 = -1;
		String url = "";
		String url0 = "";
		while (true) {
			index1 = result.indexOf(start); // 返回查询的开始索引
			if (index1 == -1) {
				break;
			}
			index2 = result.indexOf(end, index1 + slength); // 返回查询的结束索引
			if (index2 == -1) {
				break;
			}
			url = result.substring(index1 + slength, index2);
			// System.out.println("网址-----" + url);
			// +slength 是为了防止截取的时候,start 为 src=" 之类时 返回网址错误,之后有需求可以再改;
			result = result.substring(index2);
			url0 = url.replace("/", "");
			if (url.length() - url0.length() > 6) {
				continue;
			}
			// 如果存在超过// / / / 五个/ 就不收录
			list.add(url);
		}
		return list;
	}

	/**
	 * 返回一个请求的host
	 * 
	 * @param string
	 * @return
	 */
	public String URLhost(String string) {
		URL url = null;
		try {
			url = new URL(string);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String host = url.getHost();
		return host;
	}

	/**
	 * 获取图片内容的大小
	 * 
	 * @param string
	 *            图片地址
	 * @return 图片大小
	 */
	public long getImageLength(String string) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(string);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(3000);
			conn.setRequestMethod("GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
		return conn.getContentLength();
	}

	/**
	 * 对网址进行去重
	 * 
	 * @param list
	 * @param mainList
	 * @return
	 */
	public List<String> remove(List<String> list, List<String> mainList) {
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			if (mainList.contains(iter.next())) {
				iter.remove();
			}
		}
		return list;
	}

}
