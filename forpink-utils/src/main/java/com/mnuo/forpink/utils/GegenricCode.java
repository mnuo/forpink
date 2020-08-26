package com.mnuo.forpink.utils;

import com.mnuo.forpink.utils.entity.TableToEntityMysql;

public class GegenricCode {
	public static void main(String[] args) {
		String[] tableArr = { 
				"co_cut_volume_item",
				"co_cut_volume_level",
				"co_cut_volume_level_input",
				"co_cut_volume_level_item"
		};
		TableToEntityMysql.exportBeansBatch(tableArr);
	}
}
