package com.example.framework.comm;

import com.example.framework.comm.annotation.ComponentInfo;
import com.example.framework.comm.config.InitConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/17
 * @since 1.0.0
 */
@Configuration
@Import({
        InitConfig.class
})
@ComponentInfo(name = "framework-starter",version = "1.0.0")
public class FrameworkAutoConfiguration {

}