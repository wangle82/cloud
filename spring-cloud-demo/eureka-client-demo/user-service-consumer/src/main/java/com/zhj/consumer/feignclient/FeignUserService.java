package com.zhj.consumer.feignclient;

import com.zhj.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

/**
 * value: 要调用的服务应用名
 * fallback:熔断打开时回退实现 必须实现该接口，且声明为bean
 */
@FeignClient(value = "user-service-provider",fallback = FeignUserServiceFallback.class) //为当前的FeignClient指定回退类
public interface FeignUserService {
    /**
     * 保存用户
     *
     * @param user
     * @return 如果保存成功的话，返回<code>true</code>，
     * 否则返回<code>false</code>
     */
    @PostMapping("/user/save")
    boolean save(User user);

    /**
     * 查询所有的用户对象
     *
     * @return 不会返回<code>null</code>
     */
    @GetMapping("/user/list")
    Collection<User> findAll();



}
