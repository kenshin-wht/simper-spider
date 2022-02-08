package com.spider.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.spider.pojo.MyFile;

public class FileUtil {

	
	/**
	 * 保存数据到文件
	 * @param file 目标文件
	 * @param text 数据
	 * @param isAppend 时候追加
	 * @return 操作结果
	 */
	public static boolean save(File file, String text, boolean isAppend) {//ture加到后面false加到前面
		try(BufferedWriter br = new BufferedWriter(new FileWriter(file, isAppend))) {
			br.write(text);//写入文件
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 创建目录树
	 * @param basePath
	 * @return
	 */
	public static DefaultTreeModel createTree(String basePath) {
		File file = new File(basePath);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new MyFile(file.getName(), file));
		createTree(file, root);
		return new DefaultTreeModel(root);//返回根节点
	}
	
	/**
	 * 根据文件目录创建目录树结构
	 * @param file
	 * @param treeNode
	 */
	private static void createTree(File file, DefaultMutableTreeNode treeNode) {
		File[] files = file.listFiles();//文件列表
		if(files != null) {
			for (File f : files) {
				if(f.isDirectory()) {//是文件夹
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(new MyFile(f.getName(), f));
					treeNode.add(node);
					createTree(f, node);//迭代
				} else {
					treeNode.add(new DefaultMutableTreeNode(new MyFile(f.getName(), f)));//文件直接加上
				}
			}
		}	
	}
}
