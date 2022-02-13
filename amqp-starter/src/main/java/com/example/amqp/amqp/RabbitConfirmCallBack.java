package com.example.amqp.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/3
 * @since 1.0.0
 */
@Slf4j
public class RabbitConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        ReturnedMessage returnedMessage=correlationData.getReturned();
        Message message=null;
        String exchange="";
        String routingkey="";
        int replyCode=0;
        String replyText="";
        if(returnedMessage!=null){
            message=returnedMessage.getMessage();
            exchange=returnedMessage.getExchange();
            routingkey=returnedMessage.getRoutingKey();
            replyCode=returnedMessage.getReplyCode();
            replyText=returnedMessage.getReplyText();
        }
        if(ack){
            log.info("[MsgOut] success, reply-code={}, reply-text={}, exchange={}, routingkey={}, messeage={}",ack,replyCode,replyText,exchange,routingkey,message);
        }else {
            log.error("[MsgOut] fail, reply-code={}, reply-text={}, exchange={}, routingkey={}, messeage={}",ack,replyCode,replyText,exchange,routingkey,message);
        }


    }
}