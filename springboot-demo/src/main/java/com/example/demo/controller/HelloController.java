package com.example.demo.controller;

import com.example.demo.service.RabbitService;
import com.example.framework.comm.threadlocal.ParameterThreadLocal;
import com.example.demo.feign.DemoFeignClient;
import com.example.demo.feign.SwaggerplusFeignClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 服务发现方式
 * 1.负载均衡
 * 	1.1 RestTemplate + @LoadBalanced (依赖spring-cloud-starter-loadbalancer)
 * 2.DiscoveryClient
 */
@Slf4j
@RestController
public class HelloController {

	@Autowired
	private OkHttpClient okHttpClient;
	private int capacity = 10_000_000;
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private DemoFeignClient demoFeignClient;
	@Autowired
	private SwaggerplusFeignClient swaggerplusFeignClient;
	@Resource
	private RabbitService rabbitService;


	@GetMapping("/lb")
	public String getServiceName(@RequestParam("serviceName") String serviceName) {
		return this.getFirstProduct(serviceName);
	}

	@GetMapping("/ttl")
	public String printTTL() {
		String result= (String) ParameterThreadLocal.getValue("p_u");
		log.info("打印指定公共参数：{}",result);
		ParameterThreadLocal.ttlMap.forEach( (key,value) ->{
			log.info("打印全部公共参数： key={},value={}",key,value.get());
		});
		return result;
	}

	@GetMapping("/sentinel/{id}")
	public void feignBack(@PathVariable("id") String id) {
	Request request=	new Request.Builder().method("GET",null).url("http://localhost:8080/okhttp/back/999").build();
			for(int i=0;i<30;i++){
				Response response= null;
				try {
					response = okHttpClient.newCall(request).execute();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.err.println("req:"+i+",code:"+response.code());
				if(i%3==0){
					System.err.println("req:"+i/0);

				}
			}

	}

	@RequestMapping("/okhttp/back/{id}")
	public String back(@PathVariable String id) {
		return "Welcome Back! " + id;
	}

	/**
	 * 添加spring-cloud-starter-loadbalancer时，自动启用spring-cloud-starter-loadbalancer 作为负载均衡client
	 *
	 * @param serviceName
	 * @return
	 */
	@GetMapping("/feign")
	public String feign(@RequestParam("serviceName") String serviceName) {
		return 	swaggerplusFeignClient.getSwaggerJson(serviceName,"");
	}
	@GetMapping("/amqp")
	public String send(@RequestParam("serviceName") String serviceName) {
		return rabbitService.send(serviceName);
	}

	@GetMapping("/")
	public List<ServiceInstance> index(@RequestParam("serviceName") String serviceName) {
		return this.serviceUrl(serviceName);
	}
	public String getFirstProduct(String serviceName) {
		try {
			return this.restTemplate.getForObject(String.format("http://%s/%s/clusters",serviceName,serviceName), String.class);
		} catch (RestClientException e) {
			log.error("异常",e);
			throw e;
		}
	}
	public List<ServiceInstance> serviceUrl(String serviceName) {
		List<ServiceInstance> list = discoveryClient.getInstances(serviceName);
//		if (list != null && list.size() > 0 ) {
//			return list.get(0).getUri().toString();
//		}
		return list;
	}

}