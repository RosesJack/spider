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
	 * ����ҳ�ֽ���,ת�����ַ������
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
	 * ��ȡURL ��ַ�� �����
	 * 
	 * @param string
	 *            ��ַ
	 * @return InputStream �����
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
	 * ��Ҫ�Է��ص�list�ж��Ƿ�Ϊ�� ����������ҳԴ���� ��list������������ҳ�з���Ҫ���url
	 * 
	 * @param result
	 *            ��ҳԴ����
	 * @param start
	 *            ��ȡ��ַ�Ŀ�ʼ
	 * @param end
	 *            ��ȡ��ַ�Ľ���
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
			index1 = result.indexOf(start); // ���ز�ѯ�Ŀ�ʼ����
			if (index1 == -1) {
				break;
			}
			index2 = result.indexOf(end, index1 + slength); // ���ز�ѯ�Ľ�������
			if (index2 == -1) {
				break;
			}
			url = result.substring(index1 + slength, index2);
			// System.out.println("��ַ-----" + url);
			// +slength ��Ϊ�˷�ֹ��ȡ��ʱ��,start Ϊ src=" ֮��ʱ ������ַ����,֮������������ٸ�;
			result = result.substring(index2);
			url0 = url.replace("/", "");
			if (url.length() - url0.length() > 6) {
				continue;
			}
			// ������ڳ���// / / / ���/ �Ͳ���¼
			list.add(url);
		}
		return list;
	}

	/**
	 * ����һ�������host
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
	 * ��ȡͼƬ���ݵĴ�С
	 * 
	 * @param string
	 *            ͼƬ��ַ
	 * @return ͼƬ��С
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
	 * ����ַ����ȥ��
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
