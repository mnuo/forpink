package com.mnuo.forpink;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import ch.qos.logback.core.filter.Filter;

public class StatisticLine {
	public static void main(String[] args) {
		long lines = getFiles("F:/mon/workspaces/hwagain-2020/hwagain-salary-trainee/src/main/java/com/hwagain/st/standard");
		long lines1 = getFiles("F:/mon/workspaces/hwagain-2020/hwagain-csrm/src/main/java/com/hwagain/csrm/common");
		long lines2 = getFiles("F:/mon/workspaces/hwagain-2020/hwagain-csrm/src/main/java/com/hwagain/csrm/plan");
		long lines3 = getFiles("F:/mon/workspaces/hwagain-2020/hwagain-csrm/src/main/java/com/hwagain/csrm/dispatch");
		long lines4 = getFiles("F:/mon/workspaces/hwagain-2020/hwagain-fell/src/main/java/com/hwagain/fell/efficiency");
		long lines5 = getFiles("F:/mon/workspaces/hwagain-2020/hwagain-white-mud/src/main/java/com/hwagain/white/mud");
		long lines6 = getFiles("F:/mon/workspaces/forpink");
		long lines7 = getFiles("F:/mon/workspaces/framework/framework-hr");
		long lines8 = getFiles("F:/mon/workspaces/framework/framework-file");
		long lines9 = getFiles("F:/mon/workspaces/framework/hwagain-exsys");
		long lines10 = getFiles("F:/mon/workspaces/hwagain-system/weixin-web");
		long lines11 = getFiles("F:/mon/workspaces/hwagain-system/transport-snapshot-web");
		long lines12 = getFiles("F:/mon/workspaces/hwagain-2020/hwagain-sale-policy");
		long lines13 = getFiles("F:/mon/workspaces/hwagain-2020/hwagain-account-checking");
		long lines14 = getFiles("F:/mon/workspaces/hwagain-system/order-meal-web");
		long lines15 = getFiles("F:/mon/workspaces/hwagain-2020/hwagain-inspect/src/main/java/com/hwagain/inspect/survey");
		long lines16 = getFiles("F:/mon/workspaces/hwagain-eagle/eagle-web/src/main/java/com/hwagain/eagle/comparephoto");
		long lines17 = getFiles("F:/mon/workspaces/hwagain-system/quality-check-web/src/main/java/com/hwagain/system/compare");
		long lines18 = getFiles("F:/mon/workspaces/hwagain-sp/sp-web/src/main/java/com/hwagain/sp/policy");
		long lines19 = getFiles("F:/mon/workspaces/hwagain-sp/sp-web/src/main/java/com/hwagain/sp/k3");
		System.out.println(lines);
		System.out.println(lines1);
		System.out.println(lines2);
		System.out.println(lines3);
		System.out.println(lines4);
		System.out.println(lines5);
		System.out.println(lines6);
		System.out.println(lines7);
		System.out.println(lines8);
		System.out.println(lines9);
		System.out.println(lines+lines1+lines2+lines3+lines4+lines5+lines6+lines7+lines8+lines9
				+lines10+lines11+lines12+lines13+lines14+lines15+lines16+lines17+lines18+lines19);
	}
	public static long getFiles(String path){
		long sum = 0;
		File file = new File(path);
		if (file.exists()) {
			if(file.isDirectory()){
				File[] files = file.listFiles();
				if(files != null && files.length > 0){
					for (File file2 : files) {
						sum += getFiles(file2.getAbsolutePath());
					}
				}
			}else{
				String name = file.getName();
				if(!name.contains(".java")){
					return 0;
				}
//		        try {
//		            FileReader fileReader = new FileReader(file);
//		            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
//		            lineNumberReader.skip(Long.MAX_VALUE);
//		            long lines = lineNumberReader.getLineNumber() + 1;
//		            fileReader.close();
//		            lineNumberReader.close();
		        	long lines = readFile(file);
		            System.out.println(file.getName()+ ": " + lines);
		            sum += lines;
//		        } catch (IOException e) {
//		            e.printStackTrace();
//		        }
			}
	    }
	    return sum;
	}
	public static long readFile(File file) {
		FileReader fr = null;
		BufferedReader br = null;

		int count = 0;

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			String value = br.readLine();
			while (value != null) {
				if (!"".equals(value)) {
					count++;
				}
				value = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return count;
	}
}
