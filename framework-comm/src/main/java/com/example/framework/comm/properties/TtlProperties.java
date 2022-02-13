package com.example.framework.comm.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/11
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.ttl")
public class TtlProperties {
    private boolean enable;

}