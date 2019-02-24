package com.jh.xysp.util;


public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "F:/eclipse-image";
		} else {
			basePath = "/home/xiangzepro/";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	public static String getShopImagePath(long shopId) {
		String imgagePath="/upload/item/shop/"+shopId+"/";
		return imgagePath.replace("/", seperator);
	}
}
