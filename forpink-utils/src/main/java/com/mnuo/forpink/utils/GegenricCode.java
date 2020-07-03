package com.mnuo.forpink.utils;

import com.mnuo.forpink.utils.entity.TableToEntityMysql;

public class GegenricCode {
	public static void main(String[] args) {
		String[] tableArr = { "permission_info", "role_info", "role_permission", "user_role", "users" };
		TableToEntityMysql.exportBeansBatch(tableArr);
	}
}
