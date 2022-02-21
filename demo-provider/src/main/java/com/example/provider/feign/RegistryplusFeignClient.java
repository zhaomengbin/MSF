package com.example.provider.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/1/27
 * @since 1.0.0
 */
@FeignClient(name = "registryplus",path = "/registryplus")
public interface RegistryplusFeignClient {

    @GetMapping(value = {"/clusters"})
    String getClusters();

     @RequestMapping("/okhttp/back/{id}")
     String back(@PathVariable String id) ;



}