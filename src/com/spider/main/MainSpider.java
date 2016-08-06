package com.spider.main;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.spider.disposedate.Disposer;
import com.spider.download.DownImage;
import com.spider.filter.Filter;

public class MainSpider {
	/**
	 * 应该有一个总的list 用来存放所有 url 还有一个 当前页面的list 访问一个就移出一个
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Disposer disp = new Disposer();
		Filter filter = new Filter();
		DownImage down = new DownImage();
		int count = 0;
		List<String> mainImageList = new ArrayList<String>();
		List<String> mainList = new ArrayList<String>();// 总的list
		List<String> hrefList = new ArrayList<String>();
		List<String> srcList = new ArrayList<String>();
		List<String> srcList1 = new ArrayList<String>();
		List<String> srcList2 = new ArrayList<String>();
		String startURL = "http://lady.163.com/";// 基网址
		mainList.add(startURL);
		String result = "";
		for (int i = 0; i < mainList.size(); i++) {
			System.out.println(count++);
			System.out.println(mainList.get(i));
			InputStream is = disp.getInputStream(mainList.get(i));
			if (is == null) {
				System.out.println("没有响应");
				continue;
			}
			result = disp.streamToReader(is);// 得到网页源代码
			// 对源代码进行处理
			hrefList = disp.urls(result, "href=\"", "\"");
			hrefList = filter.hrefFilter(hrefList); // 对href进行提纯
			hrefList = disp.remove(hrefList, mainList);// 对hreflist进行去重
			mainList.addAll(hrefList);// 把访问过的网址都存在mainList里
			srcList1 = disp.urls(result, "src=\"", "\"");
			srcList2 = disp.urls(result, "original=\"", "\"");
			srcList.addAll(filter.imageFilter(hrefList));
			srcList.addAll(filter.imageFilter(srcList1));
			srcList.addAll(filter.imageFilter(srcList2));
			srcList = disp.remove(srcList, mainImageList); // 对图片地址进行去重
			mainImageList.addAll(srcList);
			// 对image地址进行 采集和提纯
			// down.downLoads(srcList.size(), 3, srcList);
			down.downImage(srcList);
		}
	}

}
