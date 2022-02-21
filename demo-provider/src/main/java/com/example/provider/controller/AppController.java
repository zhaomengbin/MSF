package com.example.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务发现方式
 * 1.负载均衡
 * 	1.1 RestTemplate + @LoadBalanced (依赖spring-cloud-starter-loadbalancer)
 * 2.DiscoveryClient
 */
@Slf4j
@RestController
public class AppController {

	@GetMapping("/hello")
	public String print() {
		return "Hi, I am provider";
	}

}