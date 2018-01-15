package com.zhj.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }



    @Autowired
    private  ContextRefresher contextRefresher;
    @Autowired
    private  Environment environment;
    @Scheduled(fixedRate = 30 * 1000, initialDelay = 3 * 1000)
    public void autoRefresh() {

        Set<String> updatedPropertyNames = contextRefresher.refresh();

        updatedPropertyNames.forEach( propertyName ->
                System.err.printf("[Thread :%s] 当前配置已更新，具体 Key：%s , Value : %s \n",
                        Thread.currentThread().getName(),
                        propertyName,
                        environment.getProperty(propertyName)
                ));
    }

}

