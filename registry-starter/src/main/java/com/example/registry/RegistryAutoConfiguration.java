package com.example.registry;

import com.example.framework.comm.annotation.ComponentInfo;
import com.example.registry.configs.RegistryConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/15
 * @since 1.0.0
 */
@Configuration
@Import({
        RegistryConfig.class
})
@ComponentInfo(name = "registry-starter",version = "1.0.0")
public class RegistryAutoConfiguration {

}