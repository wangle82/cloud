package com.zhj.consumer.feignclient;

import com.zhj.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zhang on 2017/12/3.
 *为对应的FeignUserService 指定回退类 ，当异常或超时发生时，将执行回退类中对应的方法
 * 如果需要访问回退时的异常原因，需要利用FeignClient#fallbackFactory属性
 *
 * 必须暴露为springbean 同时激活feign.hystrix.enabled=true 配置 方可失败回退
 */
@Component
public class FeignUserServiceFallback implements FeignUserService {
    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public Collection<User> findAll() {
        return new ArrayList<>();
    }
}
