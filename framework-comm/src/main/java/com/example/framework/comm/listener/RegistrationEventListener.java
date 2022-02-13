package com.example.framework.comm.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *   InstancePreRegisteredEvent :注册前
 *   InstanceRegisteredEvent : 注册后
 *
 * @author zhaomengbin
 * @create 2022/1/27
 * @since 1.0.0
 */
@Slf4j
@Component
public class RegistrationEventListener implements ApplicationListener<InstanceRegisteredEvent> {

    @Override
    public void onApplicationEvent(InstanceRegisteredEvent event) {
            log.info("注册实例:{}",event.toString());
    }
}