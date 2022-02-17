package com.example.registry.configs;

import com.example.registry.center.consul.ConsulCustomizer;
import com.example.registry.registration.AutoRegistration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈TODO 通过ConditionOn 判断使用何种注册中心〉
 *
 * @author zhaomengbin
 * @create 2022/2/16
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled", matchIfMissing = true)
@AutoConfigureAfter(ConsulRegistration.class)
public class RegistrationConfig {



    @Bean
    public AutoRegistration autoRegistration(ServiceRegistry serviceRegistry, Registration registration) {
        return new AutoRegistration(serviceRegistry, registration);
    }

    /**
     * consul服务注册定制器
     * @return
     */
    @Bean
    public ConsulCustomizer consulCustomizer() {
        return new ConsulCustomizer();
    }


}