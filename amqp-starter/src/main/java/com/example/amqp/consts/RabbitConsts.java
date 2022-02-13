package com.example.amqp.consts;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/3
 * @since 1.0.0
 */
public interface RabbitConsts {

     String HEADER_PUBLISH_TIME="x-publish-time";
     String HEADER_CLUSTER="x-cluster";
     String HEADER_PUBLISH_RETRY_TIMES="x-publish-try-times";
     String HEADER_CONSUMER_RETRY_TIMES="x-consumer-try-times";

     String QUEUE_DIRECT_TEST="demo.queue.direct";
     String QUEUE_FANOUT_TEST="demo.queue.fanout";
     String QUEUE_TOPIC_TEST="demo.queue.topic";
     String EXCHANGE_DIRECT_TEST="demo.exchange.direct";
     String EXCHANGE_FANOUT_TEST="demo.exchange.fanout";
     String EXCHANGE_TOPIC_TEST="demo.exchange.topic";


}