package com.example.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *  本地启动 consul agent  -config-file=tmp/client.json
 */

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class ProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}

}
