package com.example.amqp.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.impl.AMQImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ChannelListener;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/5
 * @since 1.0.0
 */
@Slf4j
public class RabbitChannelListener implements ChannelListener {

    @Override
    public void onCreate(Channel channel, boolean transactional) {
        log.info("create rabbit channel:{}, transactional: {}",channel,transactional);
    }

    @Override
    public void onShutDown(ShutdownSignalException signal) {
        AMQImpl.Channel.Close close= (AMQImpl.Channel.Close) signal.getReason();
        //判断是channel断开还是connection断开
        if(signal.isHardError()){
            log.warn("Connection onShutDown replyCode:{} replyText:{} methodId:{} classId:{}",close.getReplyCode(),close.getReplyText(),close.getMethodId(),close.getClassId());
        }else {
            log.warn("Channel onShutDown replyCode:{} replyText:{} methodId:{} classId:{}",close.getReplyCode(),close.getReplyText(),close.getMethodId(),close.getClassId());
        }

    }
}