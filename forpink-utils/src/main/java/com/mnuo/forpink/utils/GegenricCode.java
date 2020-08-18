package com.mnuo.forpink.utils;

import com.mnuo.forpink.utils.entity.TableToEntityMysql;

public class GegenricCode {
	public static void main(String[] args) {
		String[] tableArr = { "cp_mac_wait_cert",
                "cp_worker_wait_cert",
              "cp_worker_wait_cert_item" };
		TableToEntityMysql.exportBeansBatch(tableArr);
	}
}
