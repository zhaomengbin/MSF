package com.example.demo.config;

import com.example.amqp.consts.RabbitConsts;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/13
 * @since 1.0.0
 */
@Configuration
public class MQConfigs {
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


}