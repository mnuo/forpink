
###### 项目结构
    forpink
    |-- forpink-core      核心服务(所有服务使用)
    |-- forpink-web-common web公共服务(web服务使用)
    |-- forpink-job       springboot+redis多实例定时任务(示例)
    |-- forpink-utils     工具类
    |-- auth-service      权限服务(security+oauth2+jwt)
    |-- gateway-service   网关服务
    |-- sso-service       登录
    |-- forpink-seckill   分布式锁(秒杀)
    |-- test.sh           github恢复丢失的绿格子    
    
##### 权限服务(security+oauth2+jwt)

    oauth2授权模式:
    + 1、授权码模式：第三方Web服务器端应用与第三方原生App
    + 2、简化模式：第三方单页面应用
    + 3、密码模式：第一方单页应用与第一方原生App
    + 4、客户端模式：没有用户参与的，完全信任的服务器端服务
</font>

#####  github恢复丢失的绿格子
    vi test.sh 修改对应的
    git clone --bare https://github.com/user/repo.git
    cd repo.git
    ./test.sh 
    git push --force --tags origin refs/heads/*











