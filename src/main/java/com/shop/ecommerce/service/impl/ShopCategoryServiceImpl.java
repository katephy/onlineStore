package com.shop.ecommerce.service.impl;

import com.shop.ecommerce.dao.ShopCategoryDao;
import com.shop.ecommerce.dto.ImageHolder;
import com.shop.ecommerce.dto.ShopCategoryExecution;
import com.shop.ecommerce.entity.ShopCategory;
import com.shop.ecommerce.enums.ShopCategoryState;
import com.shop.ecommerce.exceptions.ShopCategoryOperationException;
import com.shop.ecommerce.service.ShopCategoryService;
import com.shop.ecommerce.utils.ImageUtil;
import com.shop.ecommerce.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }

    @Override
    public ShopCategory getShopCategoryById(Long shopCategoryId) {
        return shopCategoryDao.queryShopCategoryById(shopCategoryId);
    }

    @Override
    @Transactional
    public ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) {
        if (shopCategory != null) {
            shopCategory.setTimeCreated(new Date());
            shopCategory.setTimeUpdated(new Date());
            if (thumbnail != null) {
                addThumbnail(shopCategory, thumbnail);
            }
            try {
                int effectedNum = shopCategoryDao.insertShopCategory(shopCategory);
                if (effectedNum > 0) {
                    return new ShopCategoryExecution(ShopCategoryState.SUCCESS, shopCategory);
                } else {
                    return new ShopCategoryExecution(ShopCategoryState.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new ShopCategoryOperationException("failed to add shop category:" + e.toString());
            }
        } else {
            return new ShopCategoryExecution(ShopCategoryState.EMPTY);
        }
    }

    @Override
    @Transactional
    public ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) {
        if (shopCategory.getShopCategoryId() != null && shopCategory.getShopCategoryId() > 0) {
            shopCategory.setTimeUpdated(new Date());
            if (thumbnail != null) {
                ShopCategory tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategory.getShopCategoryId());
                if (tempShopCategory.getShopCategoryImg() != null) {
                    ImageUtil.deleteFileOrPath(tempShopCategory.getShopCategoryImg());
                }
                addThumbnail(shopCategory, thumbnail);
            }
            try {
                int effectedNum = shopCategoryDao.updateShopCategory(shopCategory);
                if (effectedNum > 0) {
                    return new ShopCategoryExecution(ShopCategoryState.SUCCESS, shopCategory);
                } else {
                    return new ShopCategoryExecution(ShopCategoryState.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new ShopCategoryOperationException("failed to update shop:" + e.toString());
            }
        } else {
            return new ShopCategoryExecution(ShopCategoryState.EMPTY);
        }
    }

    /**
     * 存储图片
     *
     * @param shopCategory
     * @param thumbnail
     */
    private void addThumbnail(ShopCategory shopCategory, ImageHolder thumbnail) {
        String dest = PathUtil.getShopCategoryPath();
        String thumbnailAddr = ImageUtil.generateNormalImg(thumbnail, dest);
        shopCategory.setShopCategoryImg(thumbnailAddr);
    }


}
