# 秒杀系统

## 业务逻辑

* 商家更新库存,根据时间信息选择是否暴露秒杀接口

* 用户执行秒杀操作，md5码验证通过，重复秒杀（表主键约束），秒杀结束（数据库更新判断），秒杀成功（数据库更新成功）

## 技术栈

* 前端：html5+bootstrap+thymeleaf

* 后端：Spring Boot+MyBatis+MySql+Redis+RabbitMQ

## 前端交互流程

秒杀商品列表

![image](http://skyrimgo.oss-cn-hangzhou.aliyuncs.com/goods.png)

秒杀商品详情页

![image](https://skyrimgo.oss-cn-hangzhou.aliyuncs.com/good.png)

## 数据库表结构设计

![image](https://skyrimgo.oss-cn-hangzhou.aliyuncs.com/sql.png)

## Redis与数据库的交互

* 查询时：先查缓存，缓存中没有则查询数据库，更新缓存。
* 更新时：数据库是否更新作为秒杀成功的判断依据，数据库更新缓存也要更新。

## RabbitMQ优化

* 消息队列发送秒杀Id与用户手机

* 监听的接收器消费消息队列中的数据

* RabbitMQ发送接口回调自身确认消费信息。

## 压力测试

压力测试选用Jmeter，商品数量20，并发量1000

* 非消息队列聚合结果

![image](https://skyrimgo.oss-cn-hangzhou.aliyuncs.com/nomq-result.png)

* 消息队列聚合结果

![image](https://skyrimgo.oss-cn-hangzhou.aliyuncs.com/mq-result.png)

* 压力测试

![image](https://skyrimgo.oss-cn-hangzhou.aliyuncs.com/compare.png)
