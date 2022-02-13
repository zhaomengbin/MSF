package com.example.amqp.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/3
 * @since 1.0.0
 */
@Slf4j
public class RabbitReturnsCallBack implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        Message message=null;
        String exchange="";
        String routingkey="";
        int replyCode=0;
        String replyText="";
        if(returned!=null){
            message=returned.getMessage();
            exchange=returned.getExchange();
            routingkey=returned.getRoutingKey();
            replyCode=returned.getReplyCode();
            replyText=returned.getReplyText();
        }
        log.info("[MsgReturned] fail, reply-code={}, reply-text={}, exchange={}, routingkey={}, messeage={}",replyCode,replyText,exchange,routingkey,message);

    }
}