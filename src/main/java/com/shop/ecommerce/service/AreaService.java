package com.shop.ecommerce.service;

import com.shop.ecommerce.dto.AreaExecution;
import com.shop.ecommerce.entity.Area;

import java.util.List;

public interface AreaService {
    /**
     * get all areas
     * @return
     */
    List<Area> getAreaList();

    /**
     * add new area
     * @return
     */
     AreaExecution addArea(Area area);

    /**
     * modify area parameters
     * @param area
     * @return
     */
     AreaExecution modifyArea(Area area);




}
