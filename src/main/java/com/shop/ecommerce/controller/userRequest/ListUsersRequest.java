package com.shop.ecommerce.controller.userRequest;

import com.shop.ecommerce.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListUsersRequest {
    User userCondition;
    int pageIndex;
    int pageSize;
}
