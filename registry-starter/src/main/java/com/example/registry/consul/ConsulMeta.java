package com.example.registry.consul;

/**
 * 〈Consul服务注册-metadata〉
 *
 * @author zhaomengbin
 * @create 2022/2/17
 * @since 1.0.0
 */
public interface ConsulMeta {
    /**
     * 集群名称
     */
    String CLUSTER_NAME="clusterName";
    /**
     * 访问类型
     *  internal 内部应用
     *  external 外部应用
     */
    String ACCESS_TYPE="accessType";
    /**
     * 应用类型
     *   springboot
     *   python
     *   ui
     *   flink
     */
    String APP_TYPE="appType";
    String DEPLOY_VERSION="version";
    String DEPLOY_GROUP="group";
    String WEIGHT="weight";
    String REGISTRY_TIME="registryTime";

}