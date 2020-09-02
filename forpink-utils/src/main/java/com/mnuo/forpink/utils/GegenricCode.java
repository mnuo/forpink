package com.mnuo.forpink.utils;

import com.mnuo.forpink.utils.entity.TableToEntityMysql;

public class GegenricCode {
	public static void main(String[] args) {
		String[] tableArr = { 
				"base_road_zone",
				"base_cut_map_info"
		};
		TableToEntityMysql.exportBeansBatch(tableArr);
	}
}
