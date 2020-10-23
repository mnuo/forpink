package com.mnuo.forpink.utils;

import com.mnuo.forpink.utils.entity.TableToEntityMysql;

public class GegenricCode {
	public static void main(String[] args) {
		String[] tableArr = { 
//				"account",
				"cp_wood_heap"
		};
		TableToEntityMysql.exportBeansBatch(tableArr);
	}
}
