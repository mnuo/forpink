package com.mnuo.forpink.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
	public static void fileChannelTest() throws Exception{
		//根据输入/输出流获取文件通道
		FileInputStream fis = new FileInputStream(new File(""));
		//获取文件流通道
		FileChannel inchannel = fis.getChannel();
		
		FileOutputStream fos = new FileOutputStream(new File(""));
		FileChannel outchannel = fos.getChannel();
		
		//创建随机访问对象
		RandomAccessFile file = new RandomAccessFile("", "rw");
		FileChannel randomChannel = file.getChannel();
		
		
	}
}
