package com.example.httpin;

import com.example.httpin.config.ControllerConfig;
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
        ControllerConfig.class
})
public class HttpInAutoConfiguration {

}
