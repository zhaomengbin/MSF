package com.example.amqp.config;

import com.example.amqp.amqp.*;
import com.example.amqp.consts.RabbitConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import javax.annotation.Resource;

;

/**
 * ConnectionFactory 有两种
 * 1.原生JAVA Rabbit Client
 * 2.Spring 抽象的接口，可以缓存connetion and channels 并复用。
 *
 *  Bean注入方式： 抽象接口+具体Rabbit作为实现
 *
 * @author zhaomengbin
 * @create 2022/1/28
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class RabbitConfig {
    @Resource
    private RabbitProperties rabbitProperties;

    @Bean
    public RabbitConfirmCallBack rabbitConfirmCallBack(){
        return new RabbitConfirmCallBack();
    }
    @Bean
    public RabbitReturnsCallBack rabbitReturnsCallBack(){
        return new RabbitReturnsCallBack();
    }
    @Bean
    public RabbitMessagePostProcessor rabbitMessagePostProcessor(){
        return new RabbitMessagePostProcessor();
    }

    @Bean
    public RabbitChannelListener rabbitChannelListener(){
        return  new RabbitChannelListener();
    }

    @Bean
    public RabbitConnectionListener rabbitConnectionListener(){
        return  new RabbitConnectionListener();
    }
    @Bean
    public RabbitRecoverListener rabbitRecoverListener(){
        return  new RabbitRecoverListener();
    }

//    @Bean
//    public DirectExchange directExchange(){
//        return new DirectExchange(RabbitConsts.EXCHANGE_DIRECT_TEST);
//    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(RabbitConsts.EXCHANGE_FANOUT_TEST);
    }
//    @Bean
//    public TopicExchange topicExchange(){
//        return new TopicExchange(RabbitConsts.EXCHANGE_TOPIC_TEST);
//    }

//    @Bean
//    public Queue directQueue(){
//        return new Queue(RabbitConsts.QUEUE_DIRECT_TEST,Boolean.TRUE);
//    }
//
//    @Bean
//    public Queue topicQueue(){
//        return new Queue(RabbitConsts.QUEUE_TOPIC_TEST,Boolean.TRUE);
//    }

    @Bean
    public Queue fanoutQueue(){
        return new Queue(RabbitConsts.QUEUE_FANOUT_TEST,Boolean.TRUE);
    }
//   @Bean
//   public Binding  directBind(){
//       return  BindingBuilder.bind(directQueue()).to(directExchange()).with("");
//   }
    @Bean
    public Binding fanoutBind(){
        Binding binding= BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());//binding key
        return binding;
    }
//    @Bean
//    public Binding topicBind(){
//        Binding binding= BindingBuilder.bind(topicQueue()).to(topicExchange()).with("*.orange.*");//binding key
//        return binding;
//    }

    @Bean
    public SimplePropertyValueConnectionNameStrategy cns() {
        return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
    }

    @Bean
    public CachingConnectionFactory ccf(ConnectionNameStrategy cns,
                                        RabbitConnectionListener rabbitConnectionListener,
                                        RabbitChannelListener rabbitChannelListener,
                                        RabbitRecoverListener rabbitRecoverListener){
        CachingConnectionFactory ccf= new CachingConnectionFactory();
        ccf.setAddresses(rabbitProperties.getAddresses());
        ccf.setVirtualHost(rabbitProperties.getVirtualHost());
        ccf.setUsername(rabbitProperties.getUsername());
        ccf.setPassword(rabbitProperties.getPassword());
        ccf.setConnectionTimeout(3000);
        ccf.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        ccf.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        ccf.setPublisherReturns(true);
        ccf.setConnectionNameStrategy(cns);
        ccf.addConnectionListener(rabbitConnectionListener);
        ccf.addChannelListener(rabbitChannelListener);
        ccf.setRecoveryListener(rabbitRecoverListener);
        return  ccf;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory ccf,
                                         RabbitConfirmCallBack rabbitConfirmCallBack,
                                         RabbitReturnsCallBack rabbitReturnsCallBack,
                                         RabbitMessagePostProcessor rabbitMessagePostProcessor) {
        RabbitTemplate template = new RabbitTemplate(ccf);
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        template.setRetryTemplate(retryTemplate);
        template.setMandatory(true);
        template.addBeforePublishPostProcessors(rabbitMessagePostProcessor);
        template.setConfirmCallback(rabbitConfirmCallBack);
        template.setReturnsCallback(rabbitReturnsCallBack);
        template.setUsePublisherConnection(true);
        return template;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory ccf){
        return new RabbitAdmin(ccf);
    }


//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(CachingConnectionFactory ccf, RabbitMessageListener rabbitMessageListener){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(ccf);
//        container.setQueueNames(RabbitConsts.QUEUE_FANOUT_TEST);
//        //启动时，自动创建channel数量
//        container.setConcurrentConsumers(3);
//        container.setStartConsumerMinInterval(1);
//        container.setStopConsumerMinInterval(60000);
//        //必须大于concurrentConsumers
//        container.setMaxConcurrentConsumers(10);
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        //必须设置listener
//        container.setMessageListener(rabbitMessageListener);
//        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
//            @Override
//            public String createConsumerTag(String queue) {
//                return "queue:"+queue+"-consumer:demo-"+ UUID.randomUUID();
//            }
//        });
//        return container;
//    }

}