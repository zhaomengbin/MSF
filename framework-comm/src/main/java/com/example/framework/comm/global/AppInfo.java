package com.example.framework.comm.global;

import lombok.Data;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/8
 * @since 1.0.0
 */
@Data
public class AppInfo {
    private String appName;
    private String clusterName;
    private String hostIp;
    private String hostName;
    private String podIp;
    private Integer podPort;
    private Env env;
    protected String deployGroup;
    protected String department;
}