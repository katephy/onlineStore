package com.shop.ecommerce.service;

import com.shop.ecommerce.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    /**
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

}
