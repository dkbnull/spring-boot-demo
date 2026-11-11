<h1 align="center">
  Spring Boot Demo
</h1>

<p align="center">
  <a href="https://github.com/dkbnull/spring-boot-demo" target="_blank">
     <img src="https://img.shields.io/badge/GitHub-仓库-blue?logo=github">
  </a>
  <a href="https://gitee.com/dkbnull/spring-boot-demo" target="_blank">
     <img src="https://img.shields.io/badge/Gitee-仓库-red?logo=gitee">
  </a>
  <img src="https://img.shields.io/badge/License-MIT-yellow">
</p>

<p align="center">
  <img src="README.assets/WeChat.png">
</p>

## 开发环境

- JDK：1.8
- Spring Boot：2.7.18

## 模块说明

| 模块 | 端口 | 上下文路径 | 说明 |
| --- | --- | --- | --- |
| spring-boot-common | - | - | 公共模块，无启动类 |
| spring-boot-helloworld | 8081 | - | 第一个 Spring Boot 程序 |
| spring-boot-properties | 8082 | - | 读取 properties 配置文件 |
| spring-boot-aop | 8083 | - | 使用 AOP 实现拦截器 |
| spring-boot-file | 8084 | - | 文件上传与下载 |
| spring-boot-i18n | 8085 | - | 页面国际化 |
| spring-boot-swagger2 | 8086 | - | 整合 Swagger2 |
| spring-boot-security | 8087 | - | 整合 Spring Security |
| spring-boot-redis | 8088 | - | 整合 Redis |
| spring-boot-mybatis | 8089 | - | 整合 MyBatis |
| spring-boot-mybatis-plus | 8090 | - | 整合 MyBatis Plus |
| spring-boot-mybatis-dynamic | 8091 | - | MyBatis 配置多数据源 |
| spring-boot-mybatis-plus-dynamic | 8092 | - | MyBatis Plus 配置多数据源 |
| spring-boot-scheduled | 8093 | - | 定时任务 |
| spring-boot-mail | 8094 | - | 发送邮件 |
| spring-boot-logback | 8095 | - | 整合 Logback 记录日志 |
| spring-boot-log4j | 8096 | - | 自定义 log4j 日志输出 |
| spring-boot-elasticsearch | 8097 | - | 整合 Elasticsearch |
| spring-boot-websocket | 8098 | - | WebSocket 实时消息推送 |
| spring-boot-cors | 8099 | - | 跨域问题解决 |
| spring-boot-jar | 8100 | - | 引入外部 jar 包并打包（`<scope>system</scope>`） |
| spring-boot-dubbo-provider | 8101 | - | Dubbo 服务提供者 |
| spring-boot-dubbo-consumer | 8102 | - | Dubbo 服务消费者 |
| spring-boot-rabbitmq-producer | 8103 | - | RabbitMQ 生产者 |
| spring-boot-rabbitmq-consumer | 8104 | - | RabbitMQ 消费者 |

说明：

- `spring-boot-dubbo-provider` 未配置 `dubbo.protocol.port`，RPC 服务使用默认的 `20880` 端口。
- `spring-boot-swagger2` 需保留 `spring.mvc.pathmatch.matching-strategy: ant_path_matcher`，否则启动时 `documentationPluginsBootstrapper` 报空指针。

## 本地依赖

各模块的中间件连接信息直接写在配置文件里，本地启动前请准备好以下服务：

| 依赖 | 地址 | 使用模块 |
| --- | --- | --- |
| MySQL | `127.0.0.1:3306`，库 `test`、`test_master`、`test_slave` | spring-boot-mybatis、mybatis-plus、mybatis-dynamic、mybatis-plus-dynamic |
| Redis | `127.0.0.1:6379` | spring-boot-redis |
| RabbitMQ | `127.0.0.1:5672`，账号 `guest/guest`，vhost `/` | spring-boot-rabbitmq-producer、rabbitmq-consumer |
| ZooKeeper | `127.0.0.1:2181` | spring-boot-dubbo-provider、dubbo-consumer |
| Elasticsearch | `localhost:9200` | spring-boot-elasticsearch |
| SMTP | `smtp.qq.com` | spring-boot-mail，需自行填写邮箱地址与授权码 |

## 启动顺序

1. 启动本地中间件：MySQL、Redis、RabbitMQ、ZooKeeper、Elasticsearch，仅需启动对应模块用到的服务
2. spring-boot-dubbo-provider（8101）→ spring-boot-dubbo-consumer（8102）
3. spring-boot-rabbitmq-consumer（8104）→ spring-boot-rabbitmq-producer（8103），消费者先启动完成队列声明，再发送消息
4. 其余模块相互独立，按需启动

## 接口验证

| 场景 | 请求 |
| --- | --- |
| 第一个程序 | GET http://localhost:8081/gateway |
| 读取配置文件 | GET http://localhost:8082/gateway |
| AOP 拦截器 | POST http://localhost:8083/login |
| 文件上传页面 | GET http://localhost:8084/log-upload.html |
| 文件上传接口 | POST http://localhost:8084/log/upload |
| 国际化登录页 | GET http://localhost:8085/login.html |
| Swagger2 接口文档 | GET http://localhost:8086/swagger-ui.html |
| Security 登录页 | GET http://localhost:8087/login.html |
| Security 受保护接口 | GET http://localhost:8087/user/query |
| MyBatis 查询 | POST http://localhost:8089/mysql/test/select |
| MyBatis Plus 查询 | POST http://localhost:8090/user/query |
| MyBatis 多数据源查询 | POST http://localhost:8091/user/query |
| MyBatis Plus 多数据源查询 | POST http://localhost:8092/user/query |
| Logback 日志 | GET http://localhost:8095/gateway |
| log4j 日志 | POST http://localhost:8096/login |
| WebSocket 页面 | GET http://localhost:8098/index.html，连接地址 ws://localhost:8098/websocket/{terminalId} |
| 跨域登录页 | GET http://localhost:8099/login.html |
| 外部 jar 包 | GET http://localhost:8100/login |
| Redis | 运行 `RedisApplicationTest` 单元测试 |
| 定时任务 | 启动后观察控制台与日志输出 |
| 发送邮件 | 运行 `MailApplicationTest` 单元测试，需先填写邮箱账号与授权码 |
| Elasticsearch | 运行 `ElasticsearchApplicationTest` 单元测试，需先启动 Elasticsearch |
| RabbitMQ | 运行 `RabbitMqProducerApplicationTest` 单元测试，消费者在控制台输出日志 |
| Dubbo | 运行 `DubboConsumerApplicationTest` 单元测试 |

## 升级说明

博客文章基于 Spring Boot 2.0.9 编写，代码已适配 Spring Boot 2.7.18，主要差异如下：

- **端口统一重排**：由原各模块独立端口调整为 8081–8104 连续分配，并按功能分组，成对模块（dubbo、rabbitmq）相邻；同时移除了所有 `server.servlet.context-path`，访问路径统一为 `http://localhost:{端口}/`。
- **log4j 1.x 已被移除**：Spring Boot 2.7 不再提供 `spring-boot-starter-log4j`，`log4j`、`aop`、`scheduled` 三个模块改用 `ch.qos.reload4j:reload4j:1.2.27`，包名仍为 `org.apache.log4j`，代码无需改动。
- **Springfox 2.9.2 与 Boot 2.6+ 不兼容**：需设置 `spring.mvc.pathmatch.matching-strategy: ant_path_matcher`，否则启动时 `documentationPluginsBootstrapper` 报空指针；`springfox-swagger2` 与 `springfox-swagger-ui` 必须保持同一版本。
- **MySQL 驱动坐标变更**：`mysql:mysql-connector-java` 改为 `com.mysql:mysql-connector-j:8.4.0`，驱动类为 `com.mysql.cj.jdbc.Driver`，JDBC URL 必须显式指定 `serverTimezone`。
- **Dubbo 升级至 3.2.11**：服务暴露注解由 `@Service` 改为 `@DubboService`；ZooKeeper 升级至 3.9.5，Curator 升级至 5.6.0。
- **Elasticsearch 客户端 7.17.18**：需显式引入 `jakarta.json:jakarta.json-api:2.0.1`，否则运行时报 `ClassNotFoundException: jakarta.json.spi.JsonProvider`。
- **Spring Security 5.7+**：`WebSecurityConfigurerAdapter` 已废弃，本模块仍沿用该写法（Boot 2.7 下可正常使用），若后续升级至 Boot 3 需改为注册 `SecurityFilterChain` Bean。
- **MyBatis Plus 3.5.4**：`mybatis-plus-boot-starter` 与 `mybatis-plus-generator` 由父 POM 统一锁定版本；`mybatis-dynamic` 通过 `MasterDataSourceConfig`、`SlaveDataSourceConfig` 分别注册数据源与 Mapper 扫描路径，`mybatis-plus-dynamic` 则使用 `@DS` 注解切换数据源。

## Spring Boot 专栏

[CSDN](https://blog.csdn.net/dkbnull/category_9278145.html) · [知乎](https://zhuanlan.zhihu.com/c_1104766024125341696) · [微博](https://weibo.com/a/hot/7581491643095042_1.html)

## 相关文章

| 文章 | 阅读 |
| --- | --- |
| 第一个Spring Boot程序（spring-boot-helloworld） | [CSDN](https://blog.csdn.net/dkbnull/article/details/81806983) · [知乎](https://zhuanlan.zhihu.com/p/63766884) · [微信](https://mp.weixin.qq.com/s/T5tJb6_mzCQvfnMElR-txQ) · [微博](https://weibo.com/ttarticle/p/show?id=2309404274446708936566) |
| Spring Boot读取properties配置文件中的数据（spring-boot-properties） | [CSDN](https://blog.csdn.net/dkbnull/article/details/81953190) · [知乎](https://zhuanlan.zhihu.com/p/73059356) · [微信](https://mp.weixin.qq.com/s/swtkNq6CLMsP4uc4PgaVHg) · [微博](https://weibo.com/ttarticle/p/show?id=2309404275977214638710) |
| Spring Boot使用AOP实现拦截器（spring-boot-aop） | [CSDN](https://blog.csdn.net/dkbnull/article/details/82847647) · [知乎](https://zhuanlan.zhihu.com/p/95083475) · [微信](https://mp.weixin.qq.com/s/COFnrvWOcZPM7Ec-0bv98Q) · [微博](https://weibo.com/ttarticle/p/show?id=2309404288955024574905) |
| Spring Boot自定义log4j日志输出（spring-boot-log4j） | [CSDN](https://blog.csdn.net/dkbnull/article/details/84558062) · [知乎](https://zhuanlan.zhihu.com/p/95405807) · [微信](https://mp.weixin.qq.com/s/TY0lb921oZlvz2mnH_L2jg) · [微博](https://weibo.com/ttarticle/p/show?id=2309404310748749823379) |
| Spring Boot整合MyBatis连接数据库（spring-boot-mybatis） | [CSDN](https://blog.csdn.net/dkbnull/article/details/87278817) · [知乎](https://zhuanlan.zhihu.com/p/95635187) · [微信](https://mp.weixin.qq.com/s/TVtmbSBrctv9R9EucIggYg) · [微博](https://weibo.com/ttarticle/p/show?id=2309404339889012367676) |
| Spring Boot使用Schedule实现定时任务（spring-boot-scheduled） | [CSDN](https://blog.csdn.net/dkbnull/article/details/87659898) · [知乎](https://zhuanlan.zhihu.com/p/95813468) · [微信](https://mp.weixin.qq.com/s/qJZpCjcZYQWnmDkztiA_uQ) · [微博](https://weibo.com/ttarticle/p/show?id=2309404446645717696521) |
| Spring Boot整合Swagger2（spring-boot-swagger2） | [CSDN](https://blog.csdn.net/dkbnull/article/details/88380987) · [知乎](https://zhuanlan.zhihu.com/p/95993014) · [微信](https://mp.weixin.qq.com/s/hAg-sBKurC5QtcA5JFzjTQ) · [微博](https://weibo.com/ttarticle/p/show?id=2309404447241761849549) |
| Spring Boot实现文件上传与下载（spring-boot-file） | [CSDN](https://blog.csdn.net/dkbnull/article/details/88858717) · [知乎](https://zhuanlan.zhihu.com/p/96866094) · [微信](https://mp.weixin.qq.com/s/rd6yhfMjwU7Pn3c46bKj7w) · [微博](https://weibo.com/ttarticle/p/show?id=2309404448466020466817) |
| Spring Boot 整合Logback记录日志（spring-boot-logback） | [CSDN](https://blog.csdn.net/dkbnull/article/details/95390533) · [知乎](https://zhuanlan.zhihu.com/p/99188924) · [微信](https://mp.weixin.qq.com/s/YDDsMcvmQURn4D9J9kNfQQ) · [微博](https://weibo.com/ttarticle/p/show?id=2309404453119672057989) |
| Spring Boot整合WebSocket实现实时消息推送（spring-boot-websocket） | [CSDN](https://blog.csdn.net/dkbnull/article/details/125577993) · [知乎](https://zhuanlan.zhihu.com/p/536839230) · [微信](https://mp.weixin.qq.com/s/Je9QSWstU5bxDakFCILFZg) · [微博](https://weibo.com/ttarticle/p/show?id=2309404786910215406111) |
| Spring Boot整合MyBatis Plus连接数据库（spring-boot-mybatis-plus） | [CSDN](https://blog.csdn.net/dkbnull/article/details/136331111) · [知乎](https://zhuanlan.zhihu.com/p/684251625) · [微信](https://mp.weixin.qq.com/s/ZJTKX_gmn6ffsY7hNrspHQ) |
| Spring Boot整合MyBatis配置多数据源（spring-boot-mybatis-dynamic） | [CSDN](https://blog.csdn.net/dkbnull/article/details/136433910) · [知乎](https://zhuanlan.zhihu.com/p/685038746) · [微信](https://mp.weixin.qq.com/s/6q3xLMg-QFo5_6RsD1h4YA) |
| Spring Boot整合MyBatis Plus配置多数据源（spring-boot-mybatis-plus-dynamic） | [CSDN](https://blog.csdn.net/dkbnull/article/details/136611367) · [知乎](https://zhuanlan.zhihu.com/p/686275755) · [微信](https://mp.weixin.qq.com/s/e6g0K6n9Uu8GdSOqu6iNwg) |
| Spring Boot整合Spring Security（spring-boot-security） | [CSDN](https://blog.csdn.net/dkbnull/article/details/136920791) · [知乎](https://zhuanlan.zhihu.com/p/688341417) · [微信](https://mp.weixin.qq.com/s/0Tl3DVPTO9QqOI7bMcf6BA) |
| Spring Boot整合Redis（spring-boot-redis） | [CSDN](https://blog.csdn.net/dkbnull/article/details/137062282) · [知乎](https://zhuanlan.zhihu.com/p/689217297) · [微信](https://mp.weixin.qq.com/s/MgIN2-WptF8pJGnkerhHaQ) |
| Spring Boot页面国际化（spring-boot-i18n） | [CSDN](https://blog.csdn.net/dkbnull/article/details/137202903) · [知乎](https://zhuanlan.zhihu.com/p/690021887) · [微信](https://mp.weixin.qq.com/s/UA5nVO8BQxeWUjGfW1wocg) |
| Spring Boot引入外部jar包并打包（spring-boot-jar） | [CSDN](https://blog.csdn.net/dkbnull/article/details/137441600) · [知乎](https://zhuanlan.zhihu.com/p/690993440) · [微信](https://mp.weixin.qq.com/s/nz5fmX3cogG0gX8UIm3nww) |
| Spring Boot整合Dubbo+ZooKeeper（spring-boot-dubbo-provider、spring-boot-dubbo-consumer） | [CSDN](https://blog.csdn.net/dkbnull/article/details/137616028) · [知乎](https://zhuanlan.zhihu.com/p/691814446) · [微信](https://mp.weixin.qq.com/s/yB5BZT8MZNGek589JL5jiw) |
| Spring Boot整合Elasticsearch（spring-boot-elasticsearch） | [CSDN](https://blog.csdn.net/dkbnull/article/details/137748709) · [知乎](https://zhuanlan.zhihu.com/p/692422846) · [微信](https://mp.weixin.qq.com/s/NZWn2veRE7uYeO2B5_czTA) |
| Spring Boot整合RabbitMQ（spring-boot-rabbitmq-producer、spring-boot-rabbitmq-consumer） | [CSDN](https://blog.csdn.net/dkbnull/article/details/137995945) · [知乎](https://zhuanlan.zhihu.com/p/693568872) · [微信](https://mp.weixin.qq.com/s/6xOiydFbnLCeTOT-6fRmFA) |
| Spring Boot跨域问题解决（spring-boot-cors） | [CSDN](https://blog.csdn.net/dkbnull/article/details/138294214) · [知乎](https://zhuanlan.zhihu.com/p/695342039) · [微信](https://mp.weixin.qq.com/s/sUdk5E6cptl0QaWK2qiXUw) |
| Spring Boot支持发送邮件（spring-boot-mail） | [CSDN](https://blog.csdn.net/dkbnull/article/details/138768207) · [知乎](https://zhuanlan.zhihu.com/p/697359156) · [微信](https://mp.weixin.qq.com/s/qGInYLdwgOblO9ebZdZCFg) |