package com.shop.ecommerce.service;

import com.shop.ecommerce.dto.ImageHolder;
import com.shop.ecommerce.dto.ShopCategoryExecution;
import com.shop.ecommerce.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    /**
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

    ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder thumbnail);

    ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, ImageHolder thumbnail);

    ShopCategory getShopCategoryById(Long shopCategoryId);
}
