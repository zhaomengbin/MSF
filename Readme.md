# Getting Started
### maven依赖管理
* 依赖jar版本优先级
  * 直接依赖 > 间接依赖
  * 相同路径，先声明 > 后声明
  * 父pom 版本 > 间接依赖版本
 
* 是否引入父pom依赖 ？
  * 若父pom jar包<optional>true</optional>,子类希望引入依赖，需要显示声明
 
* 多模块jar依赖构建
  * 被依赖的jar pom 必须<packing>jar</packing> 否则会报类不存在。
  * 
### 服务注册
#### consul
每个Node节点都需要一个守护进程consul agent进程，负责服务的健康检查、服务的注册、服务的查询、本地服务信息缓存等。

*1. 启动consul agent*

`
 congsul agent -config-file=/consul/client.json
`

*2. 服务自动注册*

应用启动后，自动注册到consul中心

### Feign相关
#### 启用OkHttp方式
*1. 引入依赖*

    <dependency>
        <groupId>io.github.openfeign</groupId>
        <artifactId>feign-okhttp</artifactId>
    </dependency>
*2. properties配置*

* feign.okhttp.enabled=true
* feign.httpclient.enabled=false

*3. 自定义OkHttpClient*

详见【 OkHttpConfig.java 】

### Sleuth相关
#### TraceWebFilter
自动创建TraceWebFilter且优先级最高，由TraceWebServletConfiguration完成bean的注入。

    @Bean
    FilterRegistrationBean traceWebFilter(BeanFactory beanFactory, SleuthWebProperties webProperties) {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new LazyTracingFilter(beanFactory));
    filterRegistrationBean.setDispatcherTypes(DispatcherType.ASYNC, DispatcherType.ERROR, DispatcherType.FORWARD,
    DispatcherType.INCLUDE, DispatcherType.REQUEST);
    filterRegistrationBean.setOrder(webProperties.getFilterOrder());
    return filterRegistrationBean;
    }
#### 日志打印
 若应用不设置日志打印格式，默认按TraceEnvironmentPostProcessor处理。

#### span采集ingore列表
* 注入健康检查、心跳检测、高频率短周期性查询等span忽略不采集

### Spring AMQP
#### 包含两个模块
* spring-amqp  (org.springframework.amqp.core) 顶层抽象，便于对接不同供应商
* spring-rabbit 只是前者的一个具体实现