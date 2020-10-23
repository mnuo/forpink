
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
    |-- forpink-learning  
    
##### 权限服务(security+oauth2+jwt)

    oauth2授权模式:
    + 1、授权码模式：第三方Web服务器端应用与第三方原生App
    + 2、简化模式：第三方单页面应用
    + 3、密码模式：第一方单页应用与第一方原生App
    + 4、客户端模式：没有用户参与的，完全信任的服务器端服务


#####  github恢复丢失的绿格子
    vi test.sh 修改对应的
    git clone --bare https://github.com/user/repo.git
    cd repo.git
    ./test.sh 
    git push --force --tags origin refs/heads/*


##### redis缓存穿透,雪崩,击穿
+ 穿透: 查询不存在数据库的数据,导致数据库压力甚至压垮数据库。解决方案是: 缓存空值

        public Goods searchArcleById(Long goodsId){
            Object obj = redisTemplate.opsForValue().get(goodsId+"");
            if(obj != null){
                return (Goods) obj;
            }
            Goods goods = goodsService.findById(goodsId);
            if(goods != null){
                redisTemplate.opsForValue().set(goodsId+"", goods, 60,TimeUnit.MINUTES);
            } else {
                redisTemplate.opsForValue().set(goodsId+"", null, 60, TimeUnit.MINUTES);
            }
            return goods;
        }

+ 雪崩: 在一段时间内, 缓存集中失效, 导致大量并发请求数据库

    public void searchArctById(Long goodsId){
        Object obj = redisTemplate.opsForValue().get(goodsId+"");
        if(obj != null){
            return (Goods) obj;
        }
        Goods goods = goodsService.findById(goodsId);
        if(goods != null){
           if(goods.getCategory().equals("高速纸机")){//热门
               Random r = new Random();
               int time = 3600 + r.next(3600);
               redisTemplate.opsForValue().set(goodsId+"", goods, time, TimeUnit.SECONDS);
           }
           if(goods.getCategory().equals("1575")){//冷门
               Random r = new Random();
               int time = 600 + r.next(600);
               redisTemplate.opsForValue().set(goodsId+"", goods, time, TimeUnit.SECONDS);
           }
        } else {
            redisTemplate.opsForValue().set(goodsId+"", null, 60, TimeUnit.MINUTES);
        }

+ 击穿: 一个key非常热门, 扛着大并发, 当这个key失效的时候, 大量请求穿过缓存访问数据库.设置这个key为永不失效.

+ 缓存预热: 在系统上线后,将相关的缓存数据直接加载到缓存系统.解决思路: 
    - 直接写个缓存刷新页面
    - 数据量不大, 在系统启动的时候自行加载
    - 定时刷新缓存
    
+ 缓存更新: 常见的两个缓存策略: 
    - 定时去清理过期的缓存
    - 当有用户过来的时候, 在判断缓存是否过期, 过期的话就去底层系统, 获取数据并更新到缓存. 



+ mutex key 互斥锁真心用不上.


























