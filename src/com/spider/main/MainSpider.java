package com.spider.main;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.spider.disposedate.Disposer;
import com.spider.download.DownImage;
import com.spider.filter.Filter;

public class MainSpider {
	/**
	 * Ӧ����һ���ܵ�list ����������� url ����һ�� ��ǰҳ���list ����һ�����Ƴ�һ��
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Disposer disp = new Disposer();
		Filter filter = new Filter();
		DownImage down = new DownImage();
		int count = 0;
		List<String> mainImageList = new ArrayList<String>();
		List<String> mainList = new ArrayList<String>();// �ܵ�list
		List<String> hrefList = new ArrayList<String>();
		List<String> srcList = new ArrayList<String>();
		List<String> srcList1 = new ArrayList<String>();
		List<String> srcList2 = new ArrayList<String>();
		String startURL = "http://lady.163.com/";// ����ַ
		mainList.add(startURL);
		String result = "";
		for (int i = 0; i < mainList.size(); i++) {
			System.out.println(count++);
			System.out.println(mainList.get(i));
			InputStream is = disp.getInputStream(mainList.get(i));
			if (is == null) {
				System.out.println("û����Ӧ");
				continue;
			}
			result = disp.streamToReader(is);// �õ���ҳԴ����
			// ��Դ������д���
			hrefList = disp.urls(result, "href=\"", "\"");
			hrefList = filter.hrefFilter(hrefList); // ��href�����ᴿ
			hrefList = disp.remove(hrefList, mainList);// ��hreflist����ȥ��
			mainList.addAll(hrefList);// �ѷ��ʹ�����ַ������mainList��
			srcList1 = disp.urls(result, "src=\"", "\"");
			srcList2 = disp.urls(result, "original=\"", "\"");
			srcList.addAll(filter.imageFilter(hrefList));
			srcList.addAll(filter.imageFilter(srcList1));
			srcList.addAll(filter.imageFilter(srcList2));
			srcList = disp.remove(srcList, mainImageList); // ��ͼƬ��ַ����ȥ��
			mainImageList.addAll(srcList);
			// ��image��ַ���� �ɼ����ᴿ
			// down.downLoads(srcList.size(), 3, srcList);
			down.downImage(srcList);
		}
	}

}
