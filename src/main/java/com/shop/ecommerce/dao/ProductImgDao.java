package com.shop.ecommerce.dao;

import com.shop.ecommerce.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    /**
     * query product image by product Id
     *
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);

    /**
     * Batch insert
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * delete product image by productId
     *
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);
}
