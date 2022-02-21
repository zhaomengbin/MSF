package com.example.httpout.loadbalancer;

import com.example.framework.comm.consts.CommHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 〈灰度负载均衡〉
 *
 * @author zhaomengbin
 * @create 2022/2/17
 * @since 1.0.0
 */
@Slf4j
public class GrayRondRobinLoadbalancer extends RoundRobinLoadBalancer {
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    private  String serviceId;

    public GrayRondRobinLoadbalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        super(serviceInstanceListSupplierProvider, serviceId);
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(serviceInstances,request));
    }

    private Response<ServiceInstance> processInstanceResponse(List<ServiceInstance> serviceInstances,Request request) {
       DefaultRequestContext requestContext=(DefaultRequestContext) request.getContext();
        RequestData clientRequest=(RequestData) requestContext.getClientRequest();
        HttpHeaders httpHeaders=clientRequest.getHeaders();
        String grayVersion=httpHeaders.getFirst(CommHeader.HEADER_GRAY_VERSION);
        if(StringUtils.isBlank(grayVersion)){
            return super.choose(request).block();
        }
        log.info("请求灰度版本:{},{}",grayVersion,clientRequest.getUrl());
        for(ServiceInstance serviceInstance:serviceInstances){
            Map<String,String> meta=serviceInstance.getMetadata();
            String targetVersion=meta.getOrDefault(CommHeader.HEADER_GRAY_VERSION,"");
            if(StringUtils.equalsIgnoreCase(grayVersion,targetVersion)){
                return new DefaultResponse(serviceInstance);
            }
        }
        //降级策略
        return super.choose(request).block();
    }




}