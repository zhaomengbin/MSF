package com.example.httpin;

import com.example.framework.comm.annotation.ComponentInfo;
import com.example.httpin.config.FilterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Hello world!
 *
 */
@Configuration
@Import({
        FilterConfig.class,
//        ControllerConfig.class
})
@ComponentInfo(name = "httpin-starter",version = "1.0.0")
public class HttpInAutoConfiguration {

}
