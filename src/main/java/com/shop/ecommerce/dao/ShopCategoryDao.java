package com.shop.ecommerce.dao;

import com.shop.ecommerce.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    /**
     * return a list of shop categories
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);

    /**
     *
     * @param shopCategoryId
     * @return
     */
    ShopCategory queryShopCategoryById(long shopCategoryId);

    /**
     *
     * @param shopCategoryIdList
     * @return
     */
    List<ShopCategory> queryShopCategoryByIds(List<Long> shopCategoryIdList);

    /**
     *
     * @param shopCategory
     * @return
     */
    int insertShopCategory(ShopCategory shopCategory);

    /**
     *
     * @param shopCategory
     * @return
     */
    int updateShopCategory(ShopCategory shopCategory);

    /**
     *
     * @param shopCategoryId
     * @return
     */
    int deleteShopCategory(long shopCategoryId);


    /**
     *
     * @param shopCategoryIdList
     * @return
     */
    int batchDeleteShopCategory(List<Long> shopCategoryIdList);

}
