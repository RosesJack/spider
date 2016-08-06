package com.spider.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import com.spider.disposedate.Disposer;

public class DownImage {
	int ii;

	public void downImage(List<String> listSrc) {
		File file = null;
		File dirs = null;
		FileOutputStream fos = null;
		InputStream is = null;
		byte[] b = new byte[1024];
		int len = -1;
		int index1 = -1;
		int index2 = -1;
		int index3 = -1;
		String parent = "";
		String child = "";
		Disposer dd = new Disposer();
		UUID uuid = null;
		int num = listSrc.size();
		for (String string : listSrc) {
			System.out.println("图片地址" + string);
			try {
				if (dd.getInputStream(string) != null) { // 如果正常获取来了图片输入流
					uuid = UUID.randomUUID();
					is = dd.getInputStream(string);
					if (is == null) {
						continue;
					}
					if (dd.getImageLength(string) < 1024 * 90) { // 如果图片小于100k
																	// 就不下载
						continue;
					}
					index1 = string.indexOf("//");
					index2 = string.indexOf("/", index1 + 2);
					index3 = string.lastIndexOf("/");
					parent = "e:/image/"
							+ string.substring(index1 + 2, index2 + 1).replace(
									":", "");
					dirs = new File(parent);
					dirs.mkdirs();
					child = "/" + uuid + string.substring(index3 + 1);
					file = new File(parent, child);
					fos = new FileOutputStream(file);
					while ((len = is.read(b)) != -1) {
						fos.write(b, 0, len);
					}
					System.out.println(child);
					is.close();
					fos.close();

				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	public void downLoads(int size, int num, final List<String> listSrc) {
		final int times = (size - 1) / num;
		for (ii = 0; ii < num; ii++) {
			new Thread() {
				@Override
				public void run() {
					File file = null;
					File dirs = null;
					FileOutputStream fos = null;
					InputStream is = null;
					byte[] b = new byte[1024];
					int len = -1;
					int index1 = -1;
					int index2 = -1;
					int index3 = -1;
					String parent = "";
					String child = "";
					Disposer dd = new Disposer();
					UUID uuid = null;
					String string = "";
					for (int j = times * ii; j < times * (ii + 1); j++) {
						string = listSrc.get(j);
						System.out.println(Thread.currentThread() + "图片地址"
								+ string);
						try {
							if (dd.getInputStream(string) != null) { // 如果正常获取来了图片输入流
								uuid = UUID.randomUUID();
								is = dd.getInputStream(string);
								if (is == null) {
									continue;
								}
								if (dd.getImageLength(string) < 1024 * 80) { // 如果图片小于100k
																				// 就不下载
									continue;
								}
								index1 = string.indexOf("//");
								index2 = string.indexOf("/", index1 + 2);
								index3 = string.lastIndexOf("/");
								parent = "e:/image/"
										+ string.substring(index1 + 2,
												index2 + 1).replace(":", "");
								dirs = new File(parent);
								dirs.mkdirs();
								child = "/" + uuid
										+ string.substring(index3 + 1);
								file = new File(parent, child);
								fos = new FileOutputStream(file);
								while ((len = is.read(b)) != -1) {
									fos.write(b, 0, len);
								}
								System.out.println(child);
								is.close();
								fos.close();

							}

						} catch (Exception e) {
							e.printStackTrace();

						}

					}

				}
			}.start();
			;
		}
	}

}
