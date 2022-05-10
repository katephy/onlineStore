package com.shop.ecommerce.dao;

import com.shop.ecommerce.entity.Area;

import java.util.List;

public interface AreaDao {

    /**
     * this function returns a list of areas
     * @return areaList
     */
    List<Area> queryArea();


    /**
     * this function add new Area
     * @param area
     * @return new Area
     */
    int insertArea(Area area);


    /**
     * this function add new Area
     * @param area
     * @return
     */
    int updateArea(Area area);

    /**
     * this function delete new Area
     * @param areaId
     * @return
     */
    int deleteArea(long areaId);

    /**
     * this function delete a list of areas
     * @param areaIdList
     * @return
     */
    int batchDeleteArea(List<Long> areaIdList);


}
