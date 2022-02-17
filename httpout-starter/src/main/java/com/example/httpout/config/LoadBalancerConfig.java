package com.example.httpout.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * 〈设置负载均衡算法〉
 *
 *  使用注解 @LoadBalancerClient(value = "registryplus", configuration = LoadBalancerConfig.class) ，只能为指定服务创建负载均衡clinet实例
 *
 *  说明： @LoadBalancerClient or @LoadBalancerClients 无需再跟@Configuration结合使用
 * @author zhaomengbin
 * @create 2022/2/17
 * @since 1.0.0
 */
public class LoadBalancerConfig {

    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
                                                            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(loadBalancerClientFactory
                .getLazyProvider(name, ServiceInstanceListSupplier.class),
                name);
    }
}