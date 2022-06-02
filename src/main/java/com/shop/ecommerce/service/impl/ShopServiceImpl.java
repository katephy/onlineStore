package com.shop.ecommerce.service.impl;

import com.shop.ecommerce.dao.ShopDao;
import com.shop.ecommerce.dto.ImageHolder;
import com.shop.ecommerce.dto.ShopDto;
import com.shop.ecommerce.entity.Shop;
import com.shop.ecommerce.service.ShopService;
import com.shop.ecommerce.utils.ImageUtil;
import com.shop.ecommerce.utils.PageCalculator;
import com.shop.ecommerce.utils.PathUtil;
import com.shop.ecommerce.enums.ShopState;
import com.shop.ecommerce.exceptions.ShopOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    //atomic

    @Override
    @Transactional
    public ShopDto addShop(Shop shop, ImageHolder thumbnail) {
        // empty shop input
        if (shop == null) {
            return new ShopDto(ShopState.NULL_SHOP);
        }

        try {
            // set status to CHECK
            shop.setEnableStatus(0);
            shop.setTimeCreated(new Date());
            shop.setTimeUpdated(new Date());

            int effectedNum = shopDao.insertShop(shop);

            if (effectedNum <= 0) {
                throw new ShopOperationException("error creating new shop");
            } else {
                // success case: insert image
                if (thumbnail != null) {
                    // shop image
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    // update shop image
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("update ShopImg failed");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopDto(ShopState.CHECK, shop);
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }

    @Override
    public Shop getByShopId(long shopId) {

        return shopDao.queryByShopId(shopId);
    }

    @Override
    @Transactional
    public ShopDto modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopDto(ShopState.NULL_SHOP);
        } else {

            try {
                // process image if needed
                if (imageHolder != null) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    // delete old image if exist
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, imageHolder);
                }
                //update shop info
                shop.setTimeUpdated(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopDto(ShopState.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopDto(ShopState.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error:" + e.getMessage());
            }
        }
    }

    @Override
    public ShopDto getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopDto se = new ShopDto();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopState.INNER_ERROR.getState());
        }
        return se;
    }
}
