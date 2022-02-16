package com.example.registry.configs;

import com.example.registry.registration.AutoRegistration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.serviceregistry.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
public class RegistryConfig {


    @Bean
    @ConditionalOnProperty(value = "spring.cloud.consul.discovery.register", matchIfMissing = true)
    public ConsulAutoRegistration consulRegistration(
            AutoServiceRegistrationProperties autoServiceRegistrationProperties, ConsulDiscoveryProperties properties,
            ApplicationContext applicationContext,
            ObjectProvider<List<ConsulRegistrationCustomizer>> registrationCustomizers,
            ObjectProvider<List<ConsulManagementRegistrationCustomizer>> managementRegistrationCustomizers,
            HeartbeatProperties heartbeatProperties) {

        properties.setServiceName("PPQQ");
        properties.setHealthCheckInterval("3");
        return ConsulAutoRegistration.registration(autoServiceRegistrationProperties, properties, applicationContext,
                registrationCustomizers.getIfAvailable(), managementRegistrationCustomizers.getIfAvailable(),
                heartbeatProperties);
    }

    @Bean
    public AutoRegistration autoRegistration(ServiceRegistry serviceRegistry, Registration registration){
        return new AutoRegistration(serviceRegistry,registration);
    }

}