package com.example.httpout.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * 〈对feign的配置进行初始化〉
 *
 * @author zhaomengbin
 * @create 2022/2/17
 * @since 1.0.0
 */
public class CustomApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        Properties firstProps = new Properties();
        firstProps.put("feign.httpclient.enabled", false);
        firstProps.put("feign.okhttp.enabled", true);
        firstProps.put("ribbon.httpclient.enabled", false);
        firstProps.put("ribbon.okhttp.enabled", true);
//        firstProps.put("feign.hystrix.enable",false);
        environment.getPropertySources().addFirst(new PropertiesPropertySource("httpin-starter", firstProps));

    }
}