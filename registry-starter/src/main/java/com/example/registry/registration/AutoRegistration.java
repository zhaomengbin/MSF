package com.example.registry.registration;

import com.example.registry.center.consul.ConsulMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.scheduling.TaskScheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public static final Long REGISTRY_DURATION=60000L;

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
     * 每次注册从服务端获取最新应用信息（权重等）
     */
    private void cycleRegistor(){
        taskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(registration instanceof ConsulAutoRegistration){
                    log.info(" 周期性注册consul服务");
                    ConsulAutoRegistration consulAutoRegistration= (ConsulAutoRegistration)registration;
                    consulAutoRegistration.getService().getMeta().put(ConsulMeta.REGISTRY_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                    //TODO 读取应用在集群中最新状态，重新更新MetaData
                    serviceRegistry.register(consulAutoRegistration);
                }
            }
        },REGISTRY_DURATION);
    }


}