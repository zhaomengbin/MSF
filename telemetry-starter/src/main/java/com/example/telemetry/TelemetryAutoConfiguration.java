package com.example.telemetry;

import com.example.framework.comm.annotation.ComponentInfo;
import com.example.telemetry.configs.SwaggerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
