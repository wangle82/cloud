package com.zhj.provider.dao;

import com.zhj.domain.User;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zhang on 2017/12/10.
 */
//@EnableTransactionManagement
@NoRepositoryBean
public interface UserJpaRepository extends PagingAndSortingRepository<User,Long> {
}
