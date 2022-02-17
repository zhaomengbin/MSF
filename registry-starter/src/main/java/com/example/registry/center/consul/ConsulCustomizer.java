package com.example.registry.center.consul;

import com.ecwid.consul.v1.agent.model.NewService;
import com.example.framework.comm.global.AppInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/17
 * @since 1.0.0
 */
public class ConsulCustomizer implements ConsulRegistrationCustomizer {

    @Autowired
    private AppInfo appInfo;
    @Value("${spring.cloud.consul.discovery.healthCheckInterval:10s}")
    private String healthCheckInterval;

    @Override
    public void customize(ConsulRegistration registration) {
        NewService.Check check = new NewService.Check();
        check.setHttp(healthCheckUrl());
        check.setInterval(healthCheckInterval);
        check.setDeregisterCriticalServiceAfter("10m");
        registration.getService().setId(instanceId());
        registration.getService().setCheck(check);
        registration.getService().setMeta(consulMetadata());
    }

    /**
     * 设置instanceId
     * @return
     */
    private  String instanceId(){
        return String.format("%s-%s-%s",appInfo.getAppName(),appInfo.getPodIp(),appInfo.getPodPort());

    }
    /**
     * 设置健康检查地址
     * @return
     */
    private  String healthCheckUrl(){
        return String.format("http://%s:%s/%s/actuator/health",appInfo.getPodIp(),appInfo.getPodPort(),appInfo.getAppName());
    }

    /**
     * 元数据来源：应用基础信息、构建信息、环境变量信息
     * @return
     */
    private Map<String,String> consulMetadata(){
        Map<String,String> map =new ConcurrentHashMap<>();
        if(StringUtils.isNotBlank(appInfo.getClusterName())){
            map.put(ConsulMeta.CLUSTER_NAME,appInfo.getClusterName());
        }
        map.put(ConsulMeta.ACCESS_TYPE,"internal");
        map.put(ConsulMeta.REGISTRY_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        map.put(ConsulMeta.APP_TYPE,"springboot");
        map.put(ConsulMeta.DEPLOY_VERSION,""); //TODO
        map.put(ConsulMeta.DEPLOY_GROUP,"");//TODO
        map.put(ConsulMeta.WEIGHT,"");//TODO
        return map;
    }

}