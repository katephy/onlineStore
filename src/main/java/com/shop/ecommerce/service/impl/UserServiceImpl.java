package com.shop.ecommerce.service.impl;

import com.shop.ecommerce.dao.UserDao;
import com.shop.ecommerce.dto.UserExecution;
import com.shop.ecommerce.entity.User;
import com.shop.ecommerce.enums.UserState;
import com.shop.ecommerce.exceptions.UserOperationException;
import com.shop.ecommerce.service.UserService;
import com.shop.ecommerce.utils.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long userId) {
        return userDao.queryUserById(userId);
    }

    @Override
    public UserExecution getUserList(User userCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<User> userList = userDao.queryUserList(userCondition, rowIndex, pageSize);
        int count = userDao.queryUserCount(userCondition);
        UserExecution userExecution = new UserExecution();
        if (userList != null) {
            userExecution.setCount(count);
            userExecution.setUserList(userList);
        } else {
            userExecution.setState(UserState.INNER_ERROR.getState());
        }
        return userExecution;
    }

    @Override
    public UserExecution updateUser(User userCondition) {
        if(userCondition == null || userCondition.getUserId() == null) {
            return new UserExecution(UserState.EMPTY);
        } else {
            try {
                int num = userDao.updateUser(userCondition);
                if (num <= 0) {
                    return new UserExecution(UserState.INNER_ERROR);
                } else {
                    userCondition = userDao.queryUserById(userCondition.getUserId());
                    return new UserExecution(UserState.SUCCESS, userCondition);
                }
            } catch (Exception e) {
                throw new UserOperationException("update user error: " + e.getMessage());
            }
        }
    }

    @Override
    public UserExecution addUser(User user) {
        int num = userDao.insertUser(user);
        if (num <= 0) {
            throw new UserOperationException("add user failed");
        } else {
            return new UserExecution(UserState.SUCCESS);
        }
    }
}
