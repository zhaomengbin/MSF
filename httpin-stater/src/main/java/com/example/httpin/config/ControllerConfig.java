package com.example.httpin.config;

import com.example.httpin.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/16
 * @since 1.0.0
 */
@Configuration
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@Import({GlobalExceptionHandler.class})
public class ControllerConfig {

}