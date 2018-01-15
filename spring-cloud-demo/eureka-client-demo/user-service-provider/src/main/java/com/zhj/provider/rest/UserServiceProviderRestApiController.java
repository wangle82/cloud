package com.zhj.provider.rest;

import com.zhj.domain.User;
import com.zhj.provider.service.UserServiceImpl;
import com.zhj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * {@link UserService 用户服务} 提供方 REST API {@link RestController}
 *
 */
@RestController
public class UserServiceProviderRestApiController {
    private static final Logger logger= LoggerFactory.getLogger(UserServiceProviderRestApiController.class);

    @Autowired
    private UserService userService;

    /**
     * @param user User
     * @return 如果保存成功的话，返回{@link User},否则返回<code>null</code>
     */
    @PostMapping("/user/save")
    public User saveUser(@RequestBody User user) {
        if (userService.save(user)) {
            System.out.println("UserService 服务方：保存用户成功！" + user);
            return user;
        } else {
            return null;
        }
    }

    /**
     * 罗列所有的用户数据
     *
     * @return 所有的用户数据
     */
    @GetMapping("/user/list")
    public Collection<User> list() {
//        logger.info("后端收到http请求 /user/list:");
        Collection<User> users= userService.findAll();
        return users;
    }

}

