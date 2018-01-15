package com.zhj.provider.dao;

import com.zhj.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhanghongjun on 2017/11/14.
 */
@Repository
public class UserRepository {

    private ConcurrentMap<Long, User> repository =
            new ConcurrentHashMap<>();

    private static final AtomicLong idGenerator =
            new AtomicLong(1);

    public Collection<User> findAll() {
        return repository.values();
    }

    public boolean save(User user) {
        Long id = idGenerator.incrementAndGet();
        user.setId(id);
        return repository.putIfAbsent(id, user) == null;
    }
    {
        User user=new User();
        user.setId(0l);
        user.setName("zhj");
        repository.putIfAbsent(0l,user);
    }
}

