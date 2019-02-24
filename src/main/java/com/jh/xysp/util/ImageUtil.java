package com.jh.xysp.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jh.xysp.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * @author wzsy006
 *
 */
/**
 * @author wzsy006
 *
 */
public class ImageUtil {
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
	private static final Random r = new Random();

	public File commonsMultipartFileToFile(CommonsMultipartFile cfile) {
		File file = null;
		try {
			// 获取前端传递过来的文件名
			file = new File(cfile.getOriginalFilename());
			// 将cfile转换为file
			cfile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static String generateThumbnails(ImageHolder thumbnail, String destPath) {
		// 拼接后的新文件的相对路径
		String relativeAddr = null;
		try {
			// 1.为了防止图片的重名，不采用用户上传的文件名，系统内部采用随机命名的方式
			String realFileName = getRandomFileName();
			// 2.获取用户上传的文件的扩展名,用于拼接新的文件名
			String fileExtensionName = getFileExtension(thumbnail.getImageName());
			// 3.校验目标目录是否存在，不存在创建目录
			makeDirPath(destPath);
			// 4.拼接新的文件名
			relativeAddr = destPath + realFileName + fileExtensionName;
			// 绝对路径的形式创建文件
			// 绝对路径的形式创建文件
            String basePath = PathUtil.getImgBasePath();
			File destFile = new File(basePath + relativeAddr);
			// 5.给源文件加水印后输出到目标文件
			Thumbnails.of(thumbnail.getImage()).size(500, 500)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
					.outputQuality(0.8).toFile(destFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建水印图片失败：" + e.toString());
		}
		return relativeAddr;
	}

	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	private static String getRandomFileName() {
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	
	/**storePath是文件路径还是目录路径
	 * 如果是文件路径就删除该文件，
	 * 如果是目录路径就删除该目录下所有文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath=new File(PathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
				File files[]=fileOrPath.listFiles();
				for(int i=0;i<files.length;i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

	public static List<String> generateNormalImgs(List<ImageHolder> prodImgDetailList, String relativePath) {
		 int count = 0;
	        List<String> relativeAddrList = new ArrayList<String>();
	        if (prodImgDetailList != null && prodImgDetailList.size() > 0) {
	        	makeDirPath(relativePath);
	            for (ImageHolder imgeHolder : prodImgDetailList) {
	                // 1.为了防止图片的重名，不采用用户上传的文件名，系统内部采用随机命名的方式
	                String randomFileName = getRandomFileName();
	                // 2.获取用户上传的文件的扩展名,用于拼接新的文件名
	                String fileExtensionName = getFileExtension(imgeHolder.getImageName());
	                // 3.拼接新的文件名 :相对路径+随机文件名+i+文件扩展名
	                String relativeAddr = relativePath + randomFileName + count + fileExtensionName;
	                count++;
	                // 4.绝对路径的形式创建文件
	                String basePath = PathUtil.getImgBasePath();
	                File destFile = new File(basePath + relativeAddr);
	                try {
	                    // 5. 不加水印 设置为比缩略图大一点的图片（因为是商品详情图片），生成图片
	                    Thumbnails.of(imgeHolder.getImage()).size(600, 300).outputQuality(0.5).toFile(destFile);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    throw new RuntimeException("创建图片失败：" + e.toString());
	                }
	                // 将图片的相对路径名称添加到list中
	                relativeAddrList.add(relativeAddr);
	            }
	        }
	        return relativeAddrList;
	}
}
