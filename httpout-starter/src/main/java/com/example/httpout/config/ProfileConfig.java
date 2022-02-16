package com.example.httpout.config;

import com.example.httpout.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/15
 * @since 1.0.0
 */
@Configuration
public class ProfileConfig {

    @Bean
    @Profile({"dev"})
    public Student student(){
        return new Student();
    }
}