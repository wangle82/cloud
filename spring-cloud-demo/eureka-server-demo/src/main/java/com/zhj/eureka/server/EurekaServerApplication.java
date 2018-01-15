package com.zhj.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by zhanghongjun on 2017/11/14.
 * 通过 http://localhost:9090/ 直接查看注册发现中心的管理控制台
 *
 * 1  eureka客户端的高可用： 客户端在注册时 eureka.client.serviceUrl.defaultZone 配置多个注册服务器地址 (此时只会选择其中之一注册，注册服务器间不同步 类似failover)
 * 2  eureka服务端的高可用:  注册服务器之间相互注册，注册信息会彼此同步 也是通过该配置项eureka.client.serviceUrl.defaultZone实现
 *          简单模拟两个eureka server:可以配置多profile,启动时候命令行指令 --spring.profiles.active=peer1
 *          启动后，和其他普通的应用一样，会在注册节点下看到EUREKA-SERVER服务节点下，所有的注册服务器实例
 *          同时会在"General Info"#"registered-replicas"  中看到当前注册服务器 向其他注册服务器注册的地址
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

