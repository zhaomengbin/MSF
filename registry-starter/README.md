#服务注册
支持适配主流的服务注册中心，如consul 、nacos 、kubernets 等

**Required** 
* consul注册中心功能验证：安装好consul server集群 、本地安装并启动consul agent


## Spring Boot启动注册
原理：spring-cloud-commons包中有个AbstractAutoServiceRegistration类，负责服务注册。
以consul为例：调用ConsulAutoServiceRegistration.registry()接口。

## 如何扩展适配多注册中心？
AbstractAutoServiceRegistration类的内部方法基本protected的，不方便直接调用，可才使用如下两个顶级接口：

* private ServiceRegistry serviceRegistry;
* private Registration registration;

## 注册中心元数据定义
主要包含如下：
* 服务定义（Service Definition)
* 健康检查定义 （HealthCheck Definition)

一般情况下，需要对服务定义填充自定义的属性，如serviceName、HealthCheck等。
### consul
实现 ConsulRegistrationCustomizer接口，并注入IOC容器即可。
注：兼容支持原始Properties配置

## 设计要点
* 启动自动注册
   **
  * 获取服务注册的数据
* 运行定期注册 (避免高频注册，对服务端造成负担)
    * TaskScheduler 定时注册 
* 停止运行注销