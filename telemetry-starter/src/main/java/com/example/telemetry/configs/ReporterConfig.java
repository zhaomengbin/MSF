package com.example.telemetry.configs;

import com.example.telemetry.reporter.Reproter;
import com.example.telemetry.reporter.collector.ComponentCollector;
import com.example.telemetry.reporter.policy.HttpReproter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/16
 * @since 1.0.0
 */
@Configuration
public class ReporterConfig {

    @Bean
    public Reproter reproter(){
        return new HttpReproter();
    }

    @Bean
    public ComponentCollector componentCollector(Reproter reproter){
        ComponentCollector componentCollector=new ComponentCollector();
        componentCollector.setReproter(reproter);
        return componentCollector;
    }

}