package com.spider.filter;

import java.util.Iterator;
import java.util.List;

public class Filter {
	/**
	 * ��ַ������ ��������list��ֱ�Ӵ���ҳԴ�����ȡ������ ���ص�list�Ǿ��������
	 * 
	 * @param list
	 * @return
	 */
	public List<String> hrefFilter(List<String> list) {
		Iterator<String> iter = list.iterator();
		String href = "";
		while (iter.hasNext()) {
			href = iter.next();
			if (href == null || "".equals(href) || href.endsWith(".js")
					|| href.endsWith(".jpg") || href.endsWith(".png")
					|| href.contains("javascript") || href.startsWith("#")
					|| href.endsWith(".icn") || href.endsWith(".gif")
					|| href.contains("{") || href.contains("[")) {
				iter.remove();
			}
		}
		return list;
	}

	/**
	 * ͼƬ������,ֻ������ͼƬ��ʽ��β����ַ
	 * 
	 * @param list
	 * @return
	 */
	public List<String> imageFilter(List<String> list) {
		Iterator<String> iter = list.iterator();
		String src = "";
		String host = "";
		// Disposer disp = new Disposer();
		while (iter.hasNext()) {
			src = iter.next();
			if (src.endsWith(".jpg") || src.endsWith(".gif")
					|| src.endsWith(".png") || src.endsWith(".icn")
					|| src.endsWith(".jpeg")) {
			} else {
				iter.remove();
			}
		}
		return list;
	}
}
