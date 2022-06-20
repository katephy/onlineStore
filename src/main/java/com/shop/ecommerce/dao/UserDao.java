package com.shop.ecommerce.dao;

import com.shop.ecommerce.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    /**
     *
     * @param userCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<User> queryUserList(@Param("userCondition") User userCondition, @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);

    /**
     *
     * @param userCondition
     * @return
     */
    int queryUserCount(@Param("userCondition") User userCondition);

    /**
     *
     * @param userId
     * @return
     */
    User queryUserById(long userId);


    /**
     *
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     *
     * @param user
     * @return
     */
    int updateUser(User user);



}
