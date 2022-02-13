package com.example.amqp;

import com.example.amqp.config.RabbitConfig;
import com.example.framework.comm.annotation.ComponentInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/11
 * @since 1.0.0
 */
@Configuration
@Import(
        RabbitConfig.class
)
@ComponentInfo(name = "amqp-starter", version = "1.0.0")
public class AmqpAutoConfiguration {

}