package com.example.framework.comm.config;

import com.example.framework.comm.global.AppInfo;
import com.example.framework.comm.global.BulidInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/17
 * @since 1.0.0
 */
@Configuration
public class InitConfig {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.address:localhost}")
    private String podIp;
    @Value("${server.port:8080}")
    private Integer podPort;
    @Value("${host.address:localhost}")
    private String hostIp;
    @Value("${host.name:localhost}")
    private String hostName;

    @Bean
    public AppInfo appInfo(){
        return AppInfo.builder()
                .appName(appName)
                .podIp(podIp)
                .podPort(podPort)
                .hostIp(hostIp)
                .hostName(hostName)
                .build();
    }
    @Bean
    public BulidInfo bulidInfo(){
        return new BulidInfo();
    }


}