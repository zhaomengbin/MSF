package com.example;

import com.example.framework.comm.annotation.ComponentInfo;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentInfo(name = "telemetry-starter",version = "1.0.0")
public class TelemetryAutoConfiguration
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
