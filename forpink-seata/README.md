### SEATA 
AT 事务: 
        
        一阶段就是, 准备sql执行本地事务,记录回滚日志
        二阶段, 提交全局事务, 回滚全局事务
 
 实行顺序: 
            
            获取全局事务实例, 开启全局事务, 解析sql, 获取事务前数据, 执行事务, 获取执行后的数据, 将前后数据写入undo_log表, 获取全局事务锁, 提交本地事务,其他分支事务同样解析sql, 执行本地事务, 回滚日志写入undo-log表, 后去全局事务锁, 如果上一个事务, 占用锁, 则需等待, 必须获取全局事务锁才能提交本地事务.如果某个分支事务执行失败, 则标记全局事务失败, 对各个单体进行回滚, 执行成功这标记全局事务为完成, 通知各个单体事务执行完成


+ mybatis 版本:
        
        forpink-seata
        |-------- seata-common-mybatis
        |-------- account-service 
        |-------- order-service
        |-------- storage-service
    
 + JPA 版
 
        forpink-seata
        |-------- seata-account 
        |-------- seata-order
        |-------- seata-storage
        