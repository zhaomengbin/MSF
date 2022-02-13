package com.example.framework.comm.reporter;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/10
 * @since 1.0.0
 */
@Data
@Builder
public class InputData {
    private Type type;
    @Builder.Default
    private Map<String,Object> data =new HashMap<>();

    public enum Type{
        FEIGN,
        COMPONENT,
        MQ_RABBIT,
        MQ_ROCKET,
        MQ_KAFKA,
        MYSQL,
        REDIS,
        OSS,
        MQ_SCHEMA,
        MQ_SCHEMA_SAMPLE;
    }

}