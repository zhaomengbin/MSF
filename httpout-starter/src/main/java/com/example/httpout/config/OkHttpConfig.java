package com.example.httpout.config;

import com.alibaba.csp.sentinel.adapter.okhttp.SentinelOkHttpConfig;
import com.alibaba.csp.sentinel.adapter.okhttp.SentinelOkHttpInterceptor;
import com.example.httpout.cooike.CookieJarService;
import com.example.httpout.interceptor.OkHttpInterceptor;
import com.example.httpout.sentinel.DemoOkHttpResourceExtractor;
import com.example.httpout.sentinel.DemoOkhttpFallBack;
import com.example.httpout.sentinel.RuleManager;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/1/28
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties({FeignProperties.class})
public class OkHttpConfig {

    @Bean
    public OkHttpInterceptor okHttpInterceptor(){
        return new OkHttpInterceptor();
    }

    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

    private SentinelOkHttpConfig sentinelOkHttpConfig(){
        return new SentinelOkHttpConfig(new DemoOkHttpResourceExtractor(), new DemoOkhttpFallBack());
    }

    @Bean
    public SentinelOkHttpInterceptor sentinelOkHttpInterceptor(){
        RuleManager.initFlowRules();
        return new SentinelOkHttpInterceptor(sentinelOkHttpConfig());
    }

    @Bean
    public OkHttpClient okHttpClient(FeignProperties feignProperties,OkHttpInterceptor okHttpInterceptor,SentinelOkHttpInterceptor sentinelOkHttpInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(okHttpInterceptor)
                .addInterceptor(sentinelOkHttpInterceptor)
                .cookieJar(BooleanUtils.isTrue(feignProperties.getEnableCookie()) ? new CookieJarService(feignProperties.getMaxHostCookieSize()) : CookieJar.NO_COOKIES)
                .build();
    }

}