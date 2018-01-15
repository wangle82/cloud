package com.zhj.consumer.rest;

import com.zhj.domain.User;
import com.zhj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by zhanghongjun on 2017/11/14.
 */
@RestController
public class UserRestApiController {
    private  static final Logger logger= LoggerFactory.getLogger(UserRestApiController.class);

    @Autowired
    private UserService userService;

    /**
     * @param name 请求参数名为“name”的数据
     * @return 如果保存成功的话，返回{@link User},否则返回<code>null</code>
     */
    @PostMapping("/user/save")
    public User saveUser(@RequestBody User user) {

        if (userService.save(user)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * 罗列所有的用户数据
     * @return 所有的用户数据
     */
    @GetMapping("/user/list")
    public Collection<User> list() {
//        logger.info("服务消费方 /user/list");
        return userService.findAll();
    }

}

