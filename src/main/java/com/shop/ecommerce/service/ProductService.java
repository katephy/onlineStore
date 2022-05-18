package com.shop.ecommerce.service;

import com.shop.ecommerce.dto.ImageHolder;
import com.shop.ecommerce.dto.ProductExecution;
import com.shop.ecommerce.entity.Product;
import com.shop.ecommerce.exceptions.ProductOperationException;

import java.util.List;

public interface ProductService {
    /**
     *
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     *
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     *
     *
     * @param product
     * @param thumbnail
     * @param productImgs
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs)
            throws ProductOperationException;

    /**
     *
     *
     * @param product
     * @param thumbnail
     * @param productImgs
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs)
            throws ProductOperationException;
}
