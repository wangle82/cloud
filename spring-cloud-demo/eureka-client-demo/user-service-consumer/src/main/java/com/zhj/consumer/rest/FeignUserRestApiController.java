package com.zhj.consumer.rest;

import com.zhj.consumer.feignclient.FeignUserService;
import com.zhj.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by zhang on 2017/12/3.
 */
@RestController
public class FeignUserRestApiController {
    @Autowired
    private FeignUserService feignUserService;

    @PostMapping("/feign/user/save")
    public User saveUser(User user) {
         feignUserService.save(user);
         return  user;
    }

    @GetMapping(value = "/feign/user/list",produces = "application/json;charset=UTF-8")
    public Collection<User> list() {
        Collection<User> users= feignUserService.findAll();
        System.out.println(users);
        return users;
    }
}
