package com.zhj.consumer;

import com.zhj.consumer.config.CustomRobbinConfig;
import com.zhj.consumer.feignclient.FeignUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhanghongjun on 2017/11/14.
 *
 *
 * 负载均衡其他东西：
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard //启用管理界面/hystrix    实现对/hystrix.stream 端点的监控
@EnableFeignClients(clients = FeignUserService.class) // 对FeignClient注解启用代理  手动指定 是因为该注解不在默认的scanpackage
@RibbonClient(value = "user-service-provider", configuration = CustomRobbinConfig.class) //可以定制对应的robiin默认实现
public class UserClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserClientApplication.class, args);
    }

    // 配置 RestTemplate
    // 添加LoadBalancerInterceptor，这样才可以"http://user-service-provider/user/list"这种服务名形式调用，而不用指定ip端口
    //  该拦截器最终会委派RibbonLoadBalancerClient来根据服务名进行负载路由
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        // 1 构造RestTemplate时，可以指定ClientHttpRequestFactory， 进行不同httpclient的实现
        // 2 调用restTemplate.getForObject方法时,其实里面是封装了List<HttpMessageConverter<?>> messageConverters ，类似之前rest服务端的自动转换，将消息自动序列反序列化
        // 3 可以指定RestTemplate的拦截器ClientHttpRequestInterceptor，对请求响应进行定制
        RestTemplate restTemplate=new RestTemplate(new HttpComponentsClientHttpRequestFactory());
//        restTemplate.getForObject("http://url", Map.class);
//        restTemplate.setInterceptors(List<ClientHttpRequestInterceptor> interceptors);
        return restTemplate;
    }

}

