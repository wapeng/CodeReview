package com.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadeUtil {
	
	/**
	 * 文件上传公共方法(不解析) 返回Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> upLoad(HttpServletRequest request, String path) {
		// 文件上传绝对路径
		String savePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		// 文件引用路径
		String saveUrl = request.getContextPath() + "/upload/";
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
			return null;
		}
		
		Iterator<FileItem> it = items.iterator();

		// 不存在目录则新建目录
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		// 以参数名为key 值为value的map
		Map<String, Object> map = new HashMap<String, Object>();

		while (it.hasNext()) {
			FileItem item = it.next();
			//普通参数
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = null;
				try {
					value = item.getString("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				map.put(name, value);
			//文件
			} else {
				String name = item.getFieldName();
				String fileName = item.getName();
				
				//文件后缀
				String fileType = fileName.substring(fileName.lastIndexOf("."));

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + fileType;

				String realPath = savePath + newFileName;
				String realUrl = saveUrl + newFileName;
				File uploadFile = new File(realPath);
				try {
					item.write(uploadFile);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				map.put(name, realUrl);
			}
		}

		return map;
	}

}
