package com.example.httpin.init;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * 〈URI路径自动添加 /context〉
 *  由spring.factories加载
 * @author zhaomengbin
 * @create 2022/2/8
 * @since 1.0.0
 */
public class CustomerApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment= applicationContext.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        Properties fristProps = new Properties();
        fristProps.put("server.servlet.context-path", "/" + appName);
        environment.getPropertySources().addFirst(new PropertiesPropertySource("first-mvc-starter", fristProps));
        // 以下是spring mvc tomcat生产环境配置，设为last，允许应用自己调优覆盖
        Properties lastProps = new Properties();
        lastProps.put("server.compression.enabled", "true");
        // 2KB=2*1024 Bytes
        lastProps.put("server.compression.min-response-size", "2048");
        lastProps.put("server.compression.mime-types", "text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json,application/xml");
        lastProps.put("server.connection-timeout", 35000); //default 30 seconds
        lastProps.put("server.tomcat.max-threads", 500);
        lastProps.put("server.tomcat.min-spare-threads", 25);
        environment.getPropertySources().addLast(new PropertiesPropertySource("last-mvc-starter", lastProps));

    }
}