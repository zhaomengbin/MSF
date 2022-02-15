package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.RabbitService;
import com.example.framework.comm.exception.ClientException;
import com.example.framework.comm.global.ReturnCode;
import com.example.demo.feign.DemoFeignClient;
import com.example.demo.feign.SwaggerplusFeignClient;
import com.example.demo.vo.ReqBean;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 服务发现方式
 * 1.负载均衡
 * 	1.1 RestTemplate + @LoadBalanced (依赖spring-cloud-starter-loadbalancer)
 * 2.DiscoveryClient
 */
@Slf4j
@RestController
public class ExController {

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

	@GetMapping("/exc/0")
	public void ex0() {
	   int result = 10/0;
	}

	@GetMapping("/exc/1")
	public void ex() {
		throw new ClientException(ReturnCode.InvalidParam);
	}


	@GetMapping("/exc/2")
	public void ex2() {
		throw new ClientException(ReturnCode.NotFound.name(),ReturnCode.NotFound.value());
	}

	@GetMapping("/exc/3")
	public void ex3() {
		throw new ClientException(500,ReturnCode.NotFound.name(),ReturnCode.NotFound.value());
	}


	@GetMapping("/exm/1")
	public void exm(@RequestParam("name") String name) {
		System.err.println(name);
	}
	@GetMapping("/exm/2/{id}")
	public void exm2(@PathVariable("id") int id,@RequestParam("name") String name) {
		System.err.println(id+":"+name);
	}

	@PostMapping("/exv/1")
	public String exv(@Validated @RequestBody ReqBean reqBean) {
		return JSON.toJSONString(reqBean);
	}




}