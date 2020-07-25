package com.mnuo.forpink.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class FileNioReadDemo {
	
	public static void nioReadFile(String fileName){
		try {
			RandomAccessFile file = new RandomAccessFile(fileName, "rw");
			FileChannel channel = file.getChannel();
			
			ByteBuffer buf = ByteBuffer.allocate(1024);
			int length = -1;
			
			while((length = channel.read(buf)) != -1){
				buf.flip();
				byte[] bytes = buf.array();
				String str = new String(bytes, 0, length);
				log.info(str);
			}
			file.close();
			channel.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void readFile(String fileName){
		File file = new File(fileName);
		try {
			Reader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String data = null;
			while ((data = br.readLine()) != null) {
				log.debug(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		readFile("d:/export (1).xml");
		nioReadFile("d:/export (1).xml");
	}
}
