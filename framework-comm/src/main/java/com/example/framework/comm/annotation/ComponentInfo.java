package com.example.framework.comm.annotation;

import java.lang.annotation.*;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/11
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentInfo {
   String name();
   String version();
}