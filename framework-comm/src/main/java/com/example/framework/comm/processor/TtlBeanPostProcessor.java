package com.example.framework.comm.processor;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.example.framework.comm.properties.TtlProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/10
 * @since 1.0.0
 */
public class TtlBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private TtlProperties ttlProperties;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //如果ttl功能开启，使用TtlExecutors对bean进行包装。
        if (ttlProperties.isEnable()) {
            if (bean instanceof ExecutorService) {
                return TtlExecutors.getTtlExecutorService((ExecutorService) bean);
            } else if (bean instanceof ThreadPoolTaskExecutor) {
                return TtlExecutors.getTtlScheduledExecutorService((ScheduledExecutorService) bean);
            }
        }
        return bean;
    }
}