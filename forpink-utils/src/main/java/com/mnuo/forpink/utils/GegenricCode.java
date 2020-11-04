package com.mnuo.forpink.utils;

import com.mnuo.forpink.utils.entity.TableToEntityMysql;

public class GegenricCode {
	public static void main(String[] args) {
		String[] tableArr = { 
				"inout_exception_report_review",
				"inout_exception_report_name_list"
		};
		TableToEntityMysql.exportBeansBatch(tableArr);
	}
}
