package com.example.registry.configs;

import com.example.framework.comm.global.AppInfo;
import com.example.registry.consul.ConsulMeta;
import com.example.registry.registration.AutoRegistration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulManagementRegistrationCustomizer;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private AppInfo appInfo;

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.consul.discovery.register", matchIfMissing = true)
    public ConsulAutoRegistration consulRegistration(
            AutoServiceRegistrationProperties autoServiceRegistrationProperties, ConsulDiscoveryProperties properties,
            ApplicationContext applicationContext,
            ObjectProvider<List<ConsulRegistrationCustomizer>> registrationCustomizers,
            ObjectProvider<List<ConsulManagementRegistrationCustomizer>> managementRegistrationCustomizers,
            HeartbeatProperties heartbeatProperties) {
        properties.setInstanceId(instanceId());
        properties.setHealthCheckUrl(healthCheckUrl());
        properties.setMetadata(consulMetadata());
        return ConsulAutoRegistration.registration(autoServiceRegistrationProperties, properties, applicationContext,
                registrationCustomizers.getIfAvailable(), managementRegistrationCustomizers.getIfAvailable(),
                heartbeatProperties);
    }

    @Bean
    public AutoRegistration autoRegistration(ServiceRegistry serviceRegistry, Registration registration){
        return new AutoRegistration(serviceRegistry,registration);
    }


    /**
     * 设置instanceId
     * @return
     */
    private String instanceId(){
        return String.format("%s-%s-%s",appInfo.getAppName(),appInfo.getPodIp(),appInfo.getPodPort());

    }
    /**
     * 设置健康检查地址
     * @return
     */
    private String healthCheckUrl(){
        return String.format("http://%s:%s/%s/actuator/health",appInfo.getPodIp(),appInfo.getPodPort(),appInfo.getAppName());
    }

    /**
     * 元数据来源：应用基础信息、构建信息、环境变量信息
     * @return
     */
    private Map<String,String> consulMetadata(){
        Map<String,String> map =new HashMap<>();
        map.put(ConsulMeta.CLUSTER_NAME,appInfo.getClusterName());
        map.put(ConsulMeta.ACCESS_TYPE,"internal");
        map.put(ConsulMeta.REGISTRY_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        map.put(ConsulMeta.APP_TYPE,"springboot");
        map.put(ConsulMeta.DEPLOY_VERSION,""); //TODO
        map.put(ConsulMeta.DEPLOY_GROUP,"");//TODO
        map.put(ConsulMeta.WEIGHT,"");//TODO
        return map;
    }

}