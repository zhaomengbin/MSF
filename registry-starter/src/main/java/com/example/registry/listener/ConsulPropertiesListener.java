package com.example.registry.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Properties;

/**
 * @author wukaiqiang
 */
public class ConsulPropertiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment configurableEnvironment = event.getEnvironment();
        Properties firstProp = new Properties();
        Properties lastProp = new Properties();
//        if (Env.local.equals(EnvUtil.getEnv())) {
//            firstProp.put("spring.cloud.service-registry.auto-registration.enabled", Boolean.FALSE);
//            lastProp.put("spring.cloud.consul.host", "consul.ali-bj-dev01.shuheo.net");
//            lastProp.put("spring.cloud.consul.port", 80);
//        } else {
//            firstProp.put("spring.cloud.service-registry.auto-registration.enabled", Boolean.TRUE);
//            firstProp.put("spring.cloud.consul.host", configurableEnvironment.getProperty("caijiajia.host"));
//        }
//
//        if (!CollectionUtils.isEmpty(firstProp)) {
//            configurableEnvironment.getPropertySources()
//                    .addFirst(new PropertiesPropertySource("customConsulFirstProperties", firstProp));
//        }
//        if (!CollectionUtils.isEmpty(lastProp)) {
//            configurableEnvironment.getPropertySources()
//                    .addLast(new PropertiesPropertySource("customConsulLastProperties", lastProp));
//        }

    }
}