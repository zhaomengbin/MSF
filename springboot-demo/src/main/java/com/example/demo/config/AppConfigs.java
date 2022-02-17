package com.example.demo.config;

import com.example.httpout.config.LoadBalancerConfig;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/1/27
 * @since 1.0.0
 */
@Configuration
@LoadBalancerClient(configuration = LoadBalancerConfig.class)
public class AppConfigs {
    @LoadBalanced
    @Bean
    public RestTemplate loadbalancedRestTemplate() {
        return new RestTemplate();
    }

}