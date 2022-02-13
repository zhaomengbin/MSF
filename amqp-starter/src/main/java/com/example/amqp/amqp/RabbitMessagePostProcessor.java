package com.example.amqp.amqp;

import com.example.amqp.consts.RabbitConsts;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/4
 * @since 1.0.0
 */
public class RabbitMessagePostProcessor implements MessagePostProcessor {

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
       MessageProperties messageProperties= message.getMessageProperties();
       messageProperties.getHeaders().put(RabbitConsts.HEADER_PUBLISH_TIME,System.currentTimeMillis());
       messageProperties.setAppId("demo");
        return message;
    }
}