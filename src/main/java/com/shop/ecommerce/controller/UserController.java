package com.shop.ecommerce.controller;

import com.shop.ecommerce.controller.userRequest.ListUsersRequest;
import com.shop.ecommerce.dto.UserExecution;
import com.shop.ecommerce.entity.User;
import com.shop.ecommerce.enums.UserState;
import com.shop.ecommerce.service.UserService;
import com.shop.ecommerce.utils.Constants;
import com.shop.ecommerce.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/superadmin")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/listusers", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> listUsers(@RequestBody ListUsersRequest listUsersRequest) {
        Map<String, Object> modelMap = new HashMap<>();
        UserExecution userExecution = null;
        int pageIndex = listUsersRequest.getPageIndex();
        int pageSize = listUsersRequest.getPageSize();
        User userCondition = listUsersRequest.getUserCondition();
        if (pageIndex > 0 && pageSize > 0) {
            try {
                userExecution = userService.getUserList(userCondition, pageIndex, pageSize);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
            if (userExecution.getUserList() != null) {
                modelMap.put(Constants.PAGE_SIZE, userExecution.getUserList());
                modelMap.put(Constants.TOTAL, userExecution.getCount());
                modelMap.put("success", true);
            } else {
                modelMap.put(Constants.PAGE_SIZE, new ArrayList<User>());
                modelMap.put(Constants.TOTAL, 0);
                modelMap.put("success", true);
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty response");
            return modelMap;
        }
    }


    /**
     *  update user status
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyuser", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyUser(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long userId = HttpServletRequestUtil.getLong(request, "userId");
        int enableStatus = HttpServletRequestUtil.getInt(request, "enableStatus");
        if (userId >= 0 && enableStatus >= 0) {
            try {
                User user = new User();
                user.setUserId(userId);
                user.setEnableStatus(enableStatus);
                UserExecution userExecution = userService.updateUser(user);
                if (userExecution.getState() == UserState.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", userExecution.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "please enter the userId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addUser(@RequestBody User user) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (user != null && user.getName() != null) {
            try {
                UserExecution userExecution = userService.addUser(user);
                if (userExecution.getState() == UserState.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", userExecution.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Please enter the user info");
        }
        return modelMap;
    }


}
