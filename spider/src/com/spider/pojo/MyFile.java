package com.spider.pojo;

import java.io.File;

/**
 * 树节点
 * @author 千羽
 *
 */
public class MyFile {
	/**
	 * 文件名
	 */
	private String name;
	
	/**
	 * 文件路径
	 */
	private File file;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public MyFile(String name, File file) {
		super();
		this.name = name;
		this.file = file;
	}

	public MyFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
