package com.example.httpin.config;

import com.example.httpin.filter.HttpLogFilter;
import com.example.httpin.filter.ParameterFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/1/28
 * @since 1.0.0
 */
@Configuration
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
public class FilterConfig {

    @Bean(name="utf8CharacterEncodingFilter")
    public CharacterEncodingFilter characterEncodingFilter() {
        OrderedCharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceRequestEncoding(true);
        filter.setForceResponseEncoding(true);
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filter;
    }

    @Bean
    public FilterRegistrationBean  httpLogFilter(){
        FilterRegistrationBean<HttpLogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HttpLogFilter());
        registration.addUrlPatterns("/*");
        registration.setName("HttpLogFilter");
        registration.setOrder(4);
        return registration;
    }


    @Bean
    public FilterRegistrationBean  parametherFilter(){
        FilterRegistrationBean<ParameterFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ParameterFilter());
        registration.addUrlPatterns("/*");
        registration.setName("ParameterFilter");
        registration.setOrder(3);
        return registration;
    }

}