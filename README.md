# 项目介绍

1. eureka-configer-server     配置注册中心
2. eureka-gateway-service     网关中心
3. eureka-server              注册中心
4. eye-muscle-component       组件和实体类统一存放的地方一些服务的统一配置和类
5. eye-muscle-reverse         用于mybatis-plus进行生成的代码的辅助项目
6. eye-muscle-util            工具包
7. file-consumer-server       用于监听rabbitmq队列然后调用python代码进行处理图片
8. file-handler-server        用于处理文件解压和文件下载的服务
9. verification-center-server 服务验证中心对保护资源的统一验证和生成token

   ## 项目需要的软件介绍


   1. banZip 需要进行调用cmd命令进行加压缩和解压缩
   2. rabbitmq 消息队列中间件
   3. mysql
   4. redis （未来可能会加上）
