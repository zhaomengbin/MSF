package com.example.telemetry;

import com.example.framework.comm.annotation.ComponentInfo;
import com.example.telemetry.configs.ReporterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Hello world!
 *
 */
@Configuration
@Import({
        ReporterConfig.class
})
@ComponentInfo(name = "telemetry-starter",version = "1.0.0")
public class TelemetryAutoConfiguration
{
}
