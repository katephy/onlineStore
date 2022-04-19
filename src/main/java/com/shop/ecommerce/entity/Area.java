package com.shop.ecommerce.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Area {
    private Long areaId;
    private String areaName;
    private int priority;
    private Date timeCreated;
    private Date timeUpdated;

}
