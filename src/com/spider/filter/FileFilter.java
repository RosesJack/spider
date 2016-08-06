package com.spider.filter;

import java.io.File;

public class FileFilter {
	/**
	 * 如果文件小于规定长度就删除
	 * 
	 * @param file
	 */
	public void fileFilter(File file, long length) {
		if (file.length() < length) {
			file.delete();
		}
	}
}
