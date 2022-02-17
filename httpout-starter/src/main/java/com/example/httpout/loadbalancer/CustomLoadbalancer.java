package com.example.httpout.loadbalancer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈自定义负载均衡算法〉
 *
 * @author zhaomengbin
 * @create 2022/2/17
 * @since 1.0.0
 */
@Slf4j
public class CustomLoadbalancer implements ReactorServiceInstanceLoadBalancer {
    final AtomicInteger position;

    final String serviceId;

    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

	public CustomLoadbalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                   String serviceId) {
        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
    }

    public CustomLoadbalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                  String serviceId, int seedPosition) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(seedPosition);
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
            ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                    .getIfAvailable(NoopServiceInstanceListSupplier::new);
            return supplier.get(request).next()
                    .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances));
        }

        private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                List<ServiceInstance> serviceInstances) {
            Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances);
            if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
                ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
            }
            return serviceInstanceResponse;
        }

        private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
            if (instances.isEmpty()) {
                if (log.isWarnEnabled()) {
                    log.warn("No servers available for service: " + serviceId);
                }
                return new EmptyResponse();
            }
            // TODO: enforce order?
            int pos = Math.abs(this.position.incrementAndGet());

            ServiceInstance instance = instances.get(pos % instances.size());

            return new DefaultResponse(instance);
        }


    }