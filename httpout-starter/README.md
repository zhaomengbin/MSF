# Http客户端
* Feign (主要)
* RestTemplate (辅助)

说明：一般以OkHttp作为Http Client，具备连接池功能，可以提升性能。
      RetryLoadBalancerInterceptor 控制负载均衡是否重试


## Feign 是如何整合 OkHttp ，LoadBalancer ?
首先，spring-cloud 2021.x使用BlockingLoadBalancerClient作为默认的负载均衡实现。

若spring.cloud.loadbalancer.retry.enabled=true(默认)，则使用RetryableFeignBlockingLoadBalancerClient；

若spring.cloud.loadbalancer.retry.enabled=false，则使用FeignBlockingLoadBalancerClient；

两者都持有BlockingLoadBalancerClient 对象。

其次，启用okhttp时
* feign.okhttp.enabled=true
* feign.httpclient.enabled=false 

BlockingLoadBalancerClient根据choose返回的最近serviceInstance封装http请求时，通过OkHttpClient发送请求。

## Feign / LoadBalancer / OkHttpClient / Sentinel 多种超时参数有何关系？
* feign包下的Request.Options类中的Timeout定义会覆盖OkHttpClient的超时定义。（详见OkHttpClient.class)
  * Feign: connectTimeout=10s, readTimeout=60s (默认)
  * OkHttpClient: connectTimout=10s, readTimeout=10s, writeTimeout=10s （默认）
    
实验
### Feign 与OkHttpClient
      public feign.Response execute(feign.Request input, feign.Request.Options options)
      throws IOException {
    okhttp3.OkHttpClient requestScoped;
    if (delegate.connectTimeoutMillis() != options.connectTimeoutMillis()
        || delegate.readTimeoutMillis() != options.readTimeoutMillis()
        || delegate.followRedirects() != options.isFollowRedirects()) {
      requestScoped = delegate.newBuilder()
          .connectTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
          .readTimeout(options.readTimeoutMillis(), TimeUnit.MILLISECONDS)
          .followRedirects(options.isFollowRedirects())
          .build();
    } else {
      requestScoped = delegate;
    }
    Request request = toOkHttpRequest(input);
    Response response = requestScoped.newCall(request).execute();
    return toFeignResponse(response, input).toBuilder().request(input).build();
      }
      }
      



#源码分析
翻了源码，发现在FeignLoadBalancerAutoConfiguration中 @Import OkHttpFeignLoadBalancerConfiguration.class，里面有如下代码：

	@Bean
	@ConditionalOnMissingBean
	@Conditional(OnRetryNotEnabledCondition.class)
	public Client feignClient(okhttp3.OkHttpClient okHttpClient, LoadBalancerClient loadBalancerClient,
			LoadBalancerClientFactory loadBalancerClientFactory) {
		OkHttpClient delegate = new OkHttpClient(okHttpClient);
		return new FeignBlockingLoadBalancerClient(delegate, loadBalancerClient, loadBalancerClientFactory);
	}

    @Bean
	@ConditionalOnMissingBean
	@ConditionalOnClass(name = "org.springframework.retry.support.RetryTemplate")
	@ConditionalOnBean(LoadBalancedRetryFactory.class)
	@ConditionalOnProperty(value = "spring.cloud.loadbalancer.retry.enabled", havingValue = "true",
			matchIfMissing = true)
	public Client feignRetryClient(LoadBalancerClient loadBalancerClient, okhttp3.OkHttpClient okHttpClient,
			LoadBalancedRetryFactory loadBalancedRetryFactory, LoadBalancerClientFactory loadBalancerClientFactory) {
		OkHttpClient delegate = new OkHttpClient(okHttpClient);
		return new RetryableFeignBlockingLoadBalancerClient(delegate, loadBalancerClient, loadBalancedRetryFactory,
				loadBalancerClientFactory);
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
* 构造真实URL地址
   参阅：FeignBlockingLoadBalancerClient.class源码
      
      String reconstructedUrl = loadBalancerClient.reconstructURI(instance, originalUri).toString();



### ReactiveLoadBalancer 具体实现是什么？
进一步源码分析，发现ReactiveLoadBalancer是一个接口，目前提供两种实现:
* RandomLoadBalancer
* RoundRobinLoadBalancer (官方默认)

[切换负载均衡算法指南](https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#switching-between-the-load-balancing-algorithms)



##路由策略

服务列表 -> 服务分类 -> 策略编排 -


sleuth包下的TracingFeignClient 对Feign的request装配Trace信息
主要配置
* FeignClientProperties
* FeignHttpClientProperties
* FeignEncoderProperties



