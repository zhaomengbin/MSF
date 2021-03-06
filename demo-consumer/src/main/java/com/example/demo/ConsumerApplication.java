package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *  本地启动 consul agent  -config-file=tmp/client.json
 */

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.demo.feign")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
