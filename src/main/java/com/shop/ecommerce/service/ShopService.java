package com.shop.ecommerce.service;

import com.shop.ecommerce.dto.ImageHolder;
import com.shop.ecommerce.dto.ShopExecution;
import com.shop.ecommerce.entity.Shop;
import com.shop.ecommerce.exceptions.ShopOperationException;

public interface ShopService {

    /**
     * register for a new shop, with image processing
     *
     * @param shop
     * @param imageHolder
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;

    /**
     * get shop by ID
     *
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * update shop Info
     *
     * @param shop
     * @param imageHolder
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;

    /**
     * return pagintaed shop list based on filters
     *
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
}
