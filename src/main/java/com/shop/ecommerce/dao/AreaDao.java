package com.shop.ecommerce.dao;

import com.shop.ecommerce.entity.Area;

import java.util.List;

public interface AreaDao {

    /**
     * this function returns a list of areas
     * @return areaList
     */
    List<Area> queryArea();
}
