package com.example.registry.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.scheduling.TaskScheduler;

/**
 * 〈服务注册入口〉
 *
 * 说明：TaskScheduler 是内置的线程池大小=1的调度器，可以复用
 *
 * @author zhaomengbin
 * @create 2022/2/16
 * @since 1.0.0
 */
@Slf4j
public class AutoRegistration implements InitializingBean {
    public static final Long REGISTRY_DURATION=20000L;

    private ServiceRegistry serviceRegistry;
    private Registration registration;

    public AutoRegistration(ServiceRegistry serviceRegistry,Registration registration){
        this.serviceRegistry=serviceRegistry;
        this.registration=registration;
    }

    @Autowired
    private TaskScheduler taskScheduler;
    /**
     * 基于不同注册中心，不同实现。
     */



    @Override
    public void afterPropertiesSet() throws Exception {
        // 启动注册
//        serviceRegistry.register(null);
        // 周期注册
        this.cycleRegistor();
    }

    /**
     * 周期注册
     */
    private void cycleRegistor(){
        taskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info(" 注册consul服务");
                serviceRegistry.register(registration);
            }
        },REGISTRY_DURATION);
    }


}