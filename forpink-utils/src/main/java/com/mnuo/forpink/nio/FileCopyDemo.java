package com.mnuo.forpink.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class FileCopyDemo {
	public static void main(String[] args) {
//		copyFile("d:/export (1).xml", "d:/abcdefg/hijkl/aa1.xml");
//		copayFileNio("d:/export (1).xml", "d:/abcdefg/hijkl/aa1.xml");
		fastNioCopy("d:/export (1).xml", "d:/abcdefg/hijkl/aa2.xml");
		
	}
	
	//NIO
	public static void copayFileNio(String sourcePath, String targetPath){
		File targetFile = new File(targetPath);
		File sourceFile = new File(sourcePath);
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel ichannel = null;
		FileChannel ochannel = null;
		
		long starttime = System.currentTimeMillis();
		
		try {
			if(!targetFile.exists()){
				File parent = targetFile.getParentFile();
				if(!parent.exists()){
					parent.mkdirs();
				}
					targetFile.createNewFile();
			}
			fis = new FileInputStream(sourceFile);
			fos = new FileOutputStream(targetFile);
			
			ichannel = fis.getChannel();
			ochannel = fos.getChannel();
			
			ByteBuffer bb = ByteBuffer.allocate(1024);
			int length;
			while((length = ichannel.read(bb)) != -1){
				bb.flip();
				int outLength = 0;
				while((outLength = ochannel.write(bb)) != 0){
					log.info("写入字节数: " + outLength);
				}
				//清除buf, 编程写入模式
				bb.flip();
			}
			//强制写入磁盘
			ochannel.force(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ichannel.close();
				ochannel.close();
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long entime = System.currentTimeMillis();
		log.info("耗时: " + (entime - starttime));
	}
	
	public static void fastNioCopy(String sourcePath, String targetPath){
		File targetFile = new File(targetPath);
		File sourceFile = new File(sourcePath);
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel ichannel = null;
		FileChannel ochannel = null;
		
		long starttime = System.currentTimeMillis();
		
		try {
			if(!targetFile.exists()){
				File parent = targetFile.getParentFile();
				if(!parent.exists()){
					parent.mkdirs();
				}
					targetFile.createNewFile();
			}
			fis = new FileInputStream(sourceFile);
			fos = new FileOutputStream(targetFile);
			
			ichannel = fis.getChannel();
			ochannel = fos.getChannel();
			
			long size = ichannel.size();
			long pos = 0;
			long count = 0;
			
			while(pos < size){
//				每次复制1024个字节, 没有就复制剩余的
				count = size-pos > 1024 ? 1024 : size - pos;
				pos += ochannel.transferFrom(ichannel, pos, count);
			}
			
			//强制写入磁盘
			ochannel.force(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ichannel.close();
				ochannel.close();
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long entime = System.currentTimeMillis();
		log.info("耗时: " + (entime - starttime));
	}
	
	//OIO
	public static void copyFile(String sourcePath, String targetPath){
		File targetFile = new File(targetPath);
		File sourceFile = new File(sourcePath);
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		long starttime = System.currentTimeMillis();
		try {
			if(!targetFile.exists()){
				File parent = targetFile.getParentFile();
				if(!parent.exists()){
					parent.mkdirs();
				}
				targetFile.createNewFile();
			}
			fis = new FileInputStream(sourceFile);
			fos = new FileOutputStream(targetFile);
			
			byte[] b = new byte[1024];
			int data;
			while((data = fis.read(b)) != -1){
				fos.write(b, 0, data);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		log.debug("时间: " + (endTime - starttime));
	}
	
}
