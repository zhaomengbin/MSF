package com.example.provider.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/1/27
 * @since 1.0.0
 */
@FeignClient(name = "swaggerplus",path = "/swaggerplus")
public interface SwaggerplusFeignClient {

    @GetMapping(value = {"/swagger-api/{appName}"})
    String getSwaggerJson(@PathVariable("appName") String appName,@RequestParam(value = "version", required = false, defaultValue = "") String version);

     @RequestMapping("/okhttp/back/{id}")
     String back(@PathVariable String id) ;



}