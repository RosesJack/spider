package com.spider.filter;

import java.io.File;

public class FileFilter {
	/**
	 * ����ļ�С�ڹ涨���Ⱦ�ɾ��
	 * 
	 * @param file
	 */
	public void fileFilter(File file, long length) {
		if (file.length() < length) {
			file.delete();
		}
	}
}
