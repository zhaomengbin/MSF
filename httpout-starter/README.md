# Http客户端
* Feign (主要)
* RestTemplate (辅助)
    * RestTemplate -> BlockingLoadBalancerClient.execute(获得具体ServiceInstance) ->LoadBalancerRequestFactory.createRequest【封装请求，即将服务名替换为真实的IP或域名】
说明：一般以OkHttp作为Http Client，具备连接池功能，可以提升性能。
      RetryLoadBalancerInterceptor 控制负载均衡是否重试



## Feign 是如何整合 OkHttp ，LoadBalancer ?
翻了源码，发现在FeignLoadBalancerAutoConfiguration中 @Import OkHttpFeignLoadBalancerConfiguration.class，里面有如下代码：

	@Bean
	@ConditionalOnMissingBean
	@Conditional(OnRetryNotEnabledCondition.class)
	public Client feignClient(okhttp3.OkHttpClient okHttpClient, LoadBalancerClient loadBalancerClient,
			LoadBalancerClientFactory loadBalancerClientFactory) {
		OkHttpClient delegate = new OkHttpClient(okHttpClient);
		return new FeignBlockingLoadBalancerClient(delegate, loadBalancerClient, loadBalancerClientFactory);
	}


## Feign又是使用哪种LoadBalancer的具体实现呢？
**顶层接口LoadBalancerClient** 主要两种实现：
* BlockingLoadBalancerClient (Spring Cloud Hoxton.M2之 后默认实现)
* RibbonLoadBalancerClient  （在netflix包下，于Spring Cloud Hoxton.M2之后被spring cloud 移除）

当properties设置spring.cloud.loadbalancer.enabled=true(默认)时，
在类BlockingLoadBalancerClientAutoConfiguration实现自动注入

    @Bean
	@ConditionalOnBean(LoadBalancerClientFactory.class)
	@ConditionalOnMissingBean
	public LoadBalancerClient blockingLoadBalancerClient(LoadBalancerClientFactory loadBalancerClientFactory) {
		return new BlockingLoadBalancerClient(loadBalancerClientFactory);
	}

## BlockingLoadBalancerClient 做了哪些事？
1. 根据请求的目标serviceId，从LoadBalancerClientFactory获取对应的LoadBalancerClient实例。
2. 根据负载均衡策略，返回一个ServiceInstance


    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
    ReactiveLoadBalancer<ServiceInstance> loadBalancer = loadBalancerClientFactory.getInstance(serviceId);
    if (loadBalancer == null) {
    return null;
    }
    Response<ServiceInstance> loadBalancerResponse = Mono.from(loadBalancer.choose(request)).block();
    if (loadBalancerResponse == null) {
    return null;
    }
    return loadBalancerResponse.getServer();
    }
### ReactiveLoadBalancer 具体实现是什么？
进一步源码分析，发现ReactiveLoadBalancer是一个接口，目前提供两种实现:
* RandomLoadBalancer
* RoundRobinLoadBalancer (官方默认)

[切换负载均衡算法指南](https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#switching-between-the-load-balancing-algorithms)

    

主要配置
* FeignClientProperties
* FeignHttpClientProperties
* FeignEncoderProperties



