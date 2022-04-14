package com.shop.ecommerce.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Area {
    private Long userId;
    private String name;
    private String profileImg;
    private String email;
    private String gender;
    private Integer enableStatus;// 0: not active, 1: active
    private Integer userType; // 1: customer, 2: merchant, 3: admin
    private Date timeCreated;
    private Date timeUpdated;

}
