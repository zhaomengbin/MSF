package com.example.registry.consul;

import com.example.framework.comm.global.AppInfo;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/17
 * @since 1.0.0
 */

public class RegistrationHelper {
    private AppInfo appInfo;

    public RegistrationHelper(AppInfo appInfo){
        this.appInfo=appInfo;
    }

    public ConsulDiscoveryProperties fill(ConsulDiscoveryProperties properties){
        if(properties==null){
            return null;
        }
        properties.setInstanceId(instanceId());
        properties.setHealthCheckUrl(healthCheckUrl());
        properties.setMetadata(this.consulMetadata());
        return properties;
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
