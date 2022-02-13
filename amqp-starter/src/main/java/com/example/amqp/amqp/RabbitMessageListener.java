package com.example.amqp.amqp;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/4
 * @since 1.0.0
 */

@Slf4j
@Component
public class RabbitMessageListener extends AbstractAdaptableMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        log.info("[MsgIn] {}",message);
    }



    @Override
    public void onMessageBatch(List<Message> messages, Channel channel) {
        log.info("[MsgBatchIn] {}",messages);
    }
}