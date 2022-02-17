package com.example.httpout;

import com.example.framework.comm.annotation.ComponentInfo;
import com.example.httpout.config.LoadBalancerConfig;
import com.example.httpout.config.OkHttpConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Hello world!
 *
 */
@Configuration
@Import({OkHttpConfig.class,
        LoadBalancerConfig.class
})
@ComponentInfo(name = "httpout-starter",version = "1.0.0")
public class HttpOutAutoConfiguration
{
}
