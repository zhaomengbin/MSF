package com.example.httpout.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = FeignProperties.PROPERTY_PREFIX)
@Getter
@Setter
public class FeignProperties {

    public final static String PROPERTY_PREFIX = "feign";

    private int connectionTimeoutMilliSeconds = 5000;

    private int readTimeoutMilliSeconds = 300000;

    private Map<String, Integer> customizedConnectionTimeout = new HashMap<>();
    private Map<String, Integer> customizedReadTimeout = new HashMap<>();

    private Connectionpool connectionpool = new Connectionpool();

    private Boolean retryOnConnectionFailure = true;

    private Boolean enableCookie = false;

    private Integer maxHostCookieSize = 256;

    private Boolean followRedirects = true;

    private Boolean forwardAllHeaders = false;

    public static class Connectionpool {
        private int maxIdleConnections = 50;

        private int keepAliveDurationSeconds = 30;

        public int getMaxIdleConnections() {
            return maxIdleConnections;
        }

        public void setMaxIdleConnections(int maxIdleConnections) {
            this.maxIdleConnections = maxIdleConnections;
        }

        public int getKeepAliveDurationSeconds() {
            return keepAliveDurationSeconds;
        }

        public void setKeepAliveDurationSeconds(int keepAliveDurationSeconds) {
            // haproxy timeout = 60s, so here we cannot exceed 60s
            if (keepAliveDurationSeconds >= 60) {
                this.keepAliveDurationSeconds = 59;
            } else if (keepAliveDurationSeconds <= 0) {
                this.keepAliveDurationSeconds = 1;
            } else {
                this.keepAliveDurationSeconds = keepAliveDurationSeconds;
            }
        }


    }


}

