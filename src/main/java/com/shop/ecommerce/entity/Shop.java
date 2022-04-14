package com.shop.ecommerce.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class Shop {
    private Long shopId;
    private String shopName;
    private String shopDesc;
    private String shopAddr;
    private String phone;
    private String shopImg;
    private Integer priority;
    private Integer enableStatus;
    private Area area;
    private User owner;
    private ShopCategory shopCategory;
    private Date timeCreated;
    private Date timeUpdated;
}
