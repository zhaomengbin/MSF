package com.example.provider.service;

import com.example.amqp.consts.RabbitConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/1/28
 * @since 1.0.0
 */
@Slf4j
@Component
public class RabbitService implements SmartInitializingSingleton {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void afterSingletonsInstantiated() {
//        amqpAdmin.declareQueue(new Queue(QUEUE_TEST));
    }

    public String send(String info){
        CorrelationData cd1 = new CorrelationData();
        rabbitTemplate.convertAndSend(RabbitConsts.EXCHANGE_FANOUT_TEST,"",info,cd1);
       return "send ok";
    }
}