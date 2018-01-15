package com.zhj.consumer.service;

import com.zhj.domain.User;
import com.zhj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Created by zhanghongjun on 2017/11/14.
 */
@Service
public class UserServiceProxy implements UserService {
    private static final Logger logger= LoggerFactory.getLogger(UserServiceProxy.class);

    // 告诉eureka 要发现的服务名
    private static final String PROVIDER_SERVER_URL_PREFIX = "http://user-service-provider";

    /**
     * 通过 REST API 代理到服务器提供者
     */
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean save(User user) {
        User returnValue =
                restTemplate.postForObject(PROVIDER_SERVER_URL_PREFIX + "/user/save", user, User.class);
        return returnValue != null;
    }

    @Override
    public Collection<User> findAll() {
//        logger.info("服务消费方调用后端服务 resttemplate /user/list start");
        Collection<User> users= restTemplate.getForObject(PROVIDER_SERVER_URL_PREFIX + "/user/list", Collection.class);
//        logger.info("服务消费方调用后端服务 resttemplate /user/list end");
        return users;
    }
}

