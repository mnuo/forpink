package com.mnuo.forpink.nio;

import java.nio.IntBuffer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UseBuffer {
	static IntBuffer intBuffer = null;
	/** 
	 * 创建缓冲区
	 */
	public static void allocateTest(){
		intBuffer = IntBuffer.allocate(20);
		log.debug("------------after allocate--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
	}
	/**
	 * 写入缓冲区
	 */
	public static void putTest(){
		for (int i = 0; i < 5; i++) {
			intBuffer.put(i);
		}
		log.debug("------------after put--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
	}
	/**
	 * 缓冲区转成读模式
	 */
	public static void flipTest(){
		intBuffer.flip();
		log.debug("------------after flip--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
	}
	/**
	 * 读取缓冲区
	 * @param args
	 */
	public static void getTest(){
		for (int i = 0; i < 2; i++) {
			int j = intBuffer.get();
			log.debug("j=" + j) ;
		}
		log.debug("------------after get 2 int--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
		
		for(int i = 0;i < 3;i ++){
			int j = intBuffer.get();
			log.debug("j="+j);
		}
		log.debug("------------after get 3 int--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
		
	}
	/**
	 * 倒带
	 * @param args
	 */
	public static void rewindTest(){
		intBuffer.rewind();
		log.debug("------------after rewind--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
	}
	/**
	 * 重读
	 * @param args
	 */
	public static void reRead(){
		for (int i = 0; i < 5; i++) {
			if(i == 2){
				//临时保存, 标记
				intBuffer.mark();
			}
			int j = intBuffer.get();
			log.debug("j="+j);
			
		}
		log.debug("------------after rewind--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
	}
	/**
	 * 重置
	 */
	public static void resetTest(){
		intBuffer.reset();
		log.debug("------------after reset--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
		for (int i = 0; i < 3; i++) {
			int j = intBuffer.get();
			log.debug("j=" + j);
		}
	}
	public static void compactTest(){
		intBuffer.compact();
		log.debug("------------after compact--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
	}
	public static void clearTest(){
		intBuffer.clear();
		log.debug("------------after reset--------------");
		log.debug("position=" + intBuffer.position());
		log.debug("limit=" + intBuffer.limit());
		log.debug("capacity=" + intBuffer.capacity());
	}
	public static void main(String[] args) {
		allocateTest();
		putTest();
		flipTest();
		getTest();
		rewindTest();
		reRead();
		resetTest();
		clearTest();
		compactTest();
	}
}
