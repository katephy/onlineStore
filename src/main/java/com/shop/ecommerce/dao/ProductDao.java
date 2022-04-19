package com.shop.ecommerce.dao;

import com.shop.ecommerce.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    /**
     * query product list by production condition and pagination
     *
     * @param productCondition
     * @param beginIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int beginIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * return total number of product
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * query product by productId
     *
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * add a new product
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * update product
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * update product category to null for all products
     *
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);

    /**
     * delete product
     *
     * @param productId
     * @return
     */
    int deleteProduct(@Param("productId") long productId, @Param("shopId") long shopId);
}
