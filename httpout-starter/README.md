# HTTP客户端

## 负载均衡
spring cloud 负载均衡的顶级接口：LoadBalancerClient

主要两种实现：
* RibbonLoadBalancerClient  （在netflix包下，于2020.0.x之后被spring cloud 移除）
* BlockingLoadBalancerClient (2020.0.x 之后默认实现)
![img.png](img.png)

