package com.example.demo.feign;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/1/27
 * @since 1.0.0
 */

@LoadBalancerClient()
@FeignClient(name = "provider",path = "/provider")
public interface ProviderFeignClient {

     @RequestMapping("/hello")
     String print() ;



}