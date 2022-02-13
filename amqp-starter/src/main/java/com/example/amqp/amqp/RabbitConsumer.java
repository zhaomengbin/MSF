package com.example.amqp.amqp;

import com.example.amqp.consts.RabbitConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/3
 * @since 1.0.0
 */
@Slf4j
@Component
public class RabbitConsumer{
    @RabbitListener(queues = RabbitConsts.QUEUE_FANOUT_TEST)
    public void receiver(String msg){
        log.info("receiver msg:{}",1/0);
    }

}