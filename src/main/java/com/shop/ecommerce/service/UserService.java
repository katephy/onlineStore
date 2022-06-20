package com.shop.ecommerce.service;

import com.shop.ecommerce.dto.UserExecution;
import com.shop.ecommerce.entity.User;

public interface UserService {

    User getUserById(Long userId);

    UserExecution getUserList(User userCondition, int pageIndex, int pageSize);

    UserExecution updateUser(User userCondition);

    UserExecution addUser(User user);

}
