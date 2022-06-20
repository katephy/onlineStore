package com.shop.ecommerce.dto;

import com.shop.ecommerce.entity.User;
import com.shop.ecommerce.enums.UserState;

import java.util.List;

public class UserExecution {

    private int state;

    private String stateInfo;

    private int count;

    private User user;

    private List<User> userList;

    public UserExecution() {

    }

    public UserExecution(UserState userState) {
        this.state = userState.getState();
        this.stateInfo = userState.getStateInfo();
    }

    public UserExecution(UserState stateEnum, User user) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.user = user;
    }

    public UserExecution(UserState stateEnum, List<User> userList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.userList = userList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
