# XiaoQiang

### XiaoQiang是什么

​	该组件分为Client端和Server端。Client端提供了分布式系统中的方法异常重试、JVM性能监控接口等功能。Server端以图表的形式提供了Client端的方法异常重试和JVM信息的监控。XiaoQiang的愿景是让代码像”小强一样健壮“。

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

- #### 1. 项目构建与本地发布

  ```
  $ git clone git@github.com:leo-zz/XiaoQiang.git
  #将Client组件发布到本地maven仓库
  $ cd client/
  $ mvn install
  #将Server组件发布到本地maven仓库
  $ cd ..
  $ cd server/
  $ mvn install
  ```

- #### 2. Client端的使用

  - ##### 2.1 pom依赖的引入

  ```xml
  <dependency>
        <groupId>com.xiaoqiang</groupId>
        <artifactId>client</artifactId>
        <version>0.0.1-SNAPSHOT</version>
  </dependency>
  ```

  - ##### 2.2 yaml参数配置

  ```yaml
  xiaoqiang:
    xiaoQiangServerURL: localhost:5140
    instanceName: xiaoqTest
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
      onlyMyPackage: false
  ```

  - ##### 2.3 注解配置

    - @EnableXiaoQiangClient

    ​        在启动类上增加注解@EnableXiaoQiangClient，声明该项目使用XiaoQiangClient提供的方法异常重试、JVM性能监控接口功能。

    - @XiaoQiangRetry

    ​        在类上或方法上增加注解@XiaoQiangRetry，声明类中所有方法或指定方法开启异常的监控和重试管理。**重试方法要求具备幂等特性，否则方法的重复执行会影响业务逻辑。**

  - ##### 2.4 启动使用

    ​	以CPU监控信息为例，启动“http://localhost:8080/monitor/cpu” 可以访问该JVM的CPU数据。同理，通过“http://localhost:8080/monitor/class”、 “http://localhost:8080/monitor/gc”等链接，可以分别访问类、垃圾回收、内存、运行时数据区、线程相关的信息。

- #### 3. Server端的使用

  - ##### 3.1 新建SpringBoot项目

    ​	新建maven项目。

  - ##### 3.2 pom依赖的引入

    ```xml
    <dependency>
        <groupId>com.xiaoqiang</groupId>
        <artifactId>server</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    ```

  - ##### 3.3 yaml参数配置

    ```
    server:
      port: 5140
    ```

  - ##### 3.4 注解配置

    - @EnableXiaoQiangServer

    ​        在启动类上增加注解@EnableXiaoQiangServer，声明该项目为XiaoQiangServer组件，可以实现注册Client的JVM信息、方法异常信息的监控和管理。

  - ##### 3.5 开始使用

    ​	访问http://localhost:5140/ 可以访问XiaoQiang的监控主页。

    ​	Demo代码参考：[Client端](https://github.com/leo-zz/XiaoQiang/tree/master/demo/client)和[Server端](https://github.com/leo-zz/XiaoQiang/tree/master/demo/server)。