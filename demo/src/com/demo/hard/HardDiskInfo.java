package com.demo.hard;

import java.io.File;

/**
 * ����Ӳ�̵���Ϣ
 * 
 * @author cxy @ www.cxyapi.com
 */
public class HardDiskInfo {
	public static void main(String[] args) {
		File[] roots = File.listRoots();
		long HDAll = 0;
		for (File file : roots) {
			System.out.println(file.getPath() + "��Ϣ����:");
			System.out.println("����δʹ�� = " + file.getFreeSpace() / 1024 / 1024
					/ 1024 + "G");// ���пռ�
			System.out.println("�Ѿ�ʹ�� = "
					+ (file.getTotalSpace() - file.getFreeSpace()) / 1024
					/ 1024 / 1024 + "G");// ���ÿռ�
			System.out.println("������ = " + file.getTotalSpace() / 1024 / 1024
					/ 1024 + "G");// �ܿռ�
			System.out.println();
		}

		// System.out.println(File.separator);

		System.out.println("==============================================");

		String rootPath = "C:";
		String fatherNodePath = "";
		String fullPath = rootPath + File.separator + fatherNodePath;
		File HDD = new File(fullPath);
		File[] fileList = HDD.listFiles();
		System.out.println(fullPath + "Ŀ¼�ṹ��");
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				System.out.println(fileList[i].getName() + "(�ļ���)");
			} else if (fileList[i].isFile()) {
				System.out.println(fileList[i].getName() + "(�ļ�)");
			}
		}
	}
}
