package com.demo.hard;

import java.io.File;

/**
 * 遍历硬盘的信息
 * 
 * @author cxy @ www.cxyapi.com
 */
public class HardDiskInfo {
	public static void main(String[] args) {
		File[] roots = File.listRoots();
		long HDAll = 0;
		for (File file : roots) {
			System.out.println(file.getPath() + "信息如下:");
			System.out.println("空闲未使用 = " + file.getFreeSpace() / 1024 / 1024
					/ 1024 + "G");// 空闲空间
			System.out.println("已经使用 = "
					+ (file.getTotalSpace() - file.getFreeSpace()) / 1024
					/ 1024 / 1024 + "G");// 可用空间
			System.out.println("总容量 = " + file.getTotalSpace() / 1024 / 1024
					/ 1024 + "G");// 总空间
			System.out.println();
		}

		// System.out.println(File.separator);

		System.out.println("==============================================");

		String rootPath = "C:";
		String fatherNodePath = "";
		String fullPath = rootPath + File.separator + fatherNodePath;
		File HDD = new File(fullPath);
		File[] fileList = HDD.listFiles();
		System.out.println(fullPath + "目录结构：");
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				System.out.println(fileList[i].getName() + "(文件夹)");
			} else if (fileList[i].isFile()) {
				System.out.println(fileList[i].getName() + "(文件)");
			}
		}
	}
}
