package com.zhj.provider.service;

import com.zhj.domain.User;
import com.zhj.provider.dao.UserRepository;
import com.zhj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Random;

/**
 * Created by zhanghongjun on 2017/11/14.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
    private static Random random=new Random();
    @Autowired
    private UserRepository userRepository;

//    @PersistenceContext
//    private EntityManager entityManager;

    @Override
    public boolean save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Collection<User> findAll() { // 模拟服务异常
//        logger.info("后端业务层处理 /user/list:");
//        logger.info("后端业务层处理 /user/list:");
//        logger.info("后端业务层处理 /user/list:");
//        logger.info("后端业务层处理 /user/list:");
        if(random.nextInt()%2==1){
            throw new RuntimeException("fdaf");
        }else{

            return userRepository.findAll();
        }

    }
}

