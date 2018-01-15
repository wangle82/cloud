package com.zhj.consumer.config;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ServerListFilter;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhang on 2017/12/3.
 * 可以定制对应的bean  来重写默认的
 * {@link ILoadBalancer}, {@link ServerListFilter}, {@link IRule}.
 */
@Configuration
public class CustomRobbinConfig {
}
