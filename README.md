# XiaoQiang

### XiaoQiang是什么

​	该组件提供了方法异常监控与重试、JVM性能监控等功能。它的愿景是让代码像”小强一样健壮“。

### XiaoQiang的特点

- JVM中CPU、内存、垃圾回收、线程、类等信息的监控
- 异常方法的监控与重试
- 基于Echarts实现监控信息的图表展示
- Web控制台、yaml文件、注解属性多途径配置监控参数
- 简单易用，配置灵活

### 功能截图



​	CPU、内存的性能监控



​	垃圾回收的监控



​	方法异常的监控

### Hello XiaoQiang!

- 项目构建与本地发布

  ```
  $ git clone git@github.com:leo-zz/XiaoQiang.git
  #发布Client
  $ cd client/
  $ mvn install
  #发布Server
  $ cd ..
  $ cd server/
  $ mvn install
  ```

- Client端的使用

  - pom依赖的引入

  ```xml
  <dependency>
        <groupId>com.xiaoqiang</groupId>
        <artifactId>client</artifactId>
        <version>0.0.1-SNAPSHOT</version>
  </dependency>
  ```

  - yaml参数配置

  ```yaml
  xiaoqiang:
    monitor:
      class: true
      cpu: true
      gc: true
      memory: true
      runtime: true
      thread: true
    retry:
      Count: 3
      Delay: 500
      onlyMyPackage: true
  ```

  - 注解配置

    - @EnableXiaoQiangClient

    在启动类上增加注解@EnableXiaoQiangClient，声明该项目使用XiaoQiangClient组件进行JVM信息、方法异常信息的监控和管理。

    - @XiaoQiangRetry

    在类上或方法上增加注解@XiaoQiangRetry，声明类中所有方法或指定方法开启异常的监控和重试管理。注意，重试方法要求具备幂等特性，否则方法的重复执行会影响业务逻辑。

  - 启动使用

    ​	以CPU监控信息为例，启动http://localhost:8080/monitor/cpu可以访问该JVM的CPU数据。同理，通过“monitor/class”、“”、“”、“”、“”、“”、“”，可以分别访问

    ​	以方法异常信息为例，启动

- Server端的使用

  - 新建SpringBoot项目

  - pom依赖的引入
  - 注解配置
    - @EnableXiaoQiangServer
  - 开始使用