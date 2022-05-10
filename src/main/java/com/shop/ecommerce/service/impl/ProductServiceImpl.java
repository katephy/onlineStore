package com.shop.ecommerce.service.impl;

import com.shop.ecommerce.dao.ProductDao;
import com.shop.ecommerce.dao.ProductImgDao;
import com.shop.ecommerce.dto.ImageHolder;
import com.shop.ecommerce.dto.ProductExecution;
import com.shop.ecommerce.entity.Product;
import com.shop.ecommerce.entity.ProductImg;
import com.shop.ecommerce.service.ProductService;
import com.shop.ecommerce.utils.ImageUtil;
import com.shop.ecommerce.utils.PageCalculator;
import com.shop.ecommerce.utils.PathUtil;
import com.shop.ecommerce.enums.ProductState;
import com.shop.ecommerce.exceptions.ProductOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public ProductExecution getProductList(Product product, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(product, rowIndex, pageSize);
        int count = productDao.queryProductCount(product);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    // 1.process image
    // 2.insert into tb_product and get product_id back
    // 3.batch insert image to product_id
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
            throws ProductOperationException {
        // edge case
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {

            product.setTimeCreated(new Date());
            product.setTimeUpdated(new Date());

            product.setEnableStatus(1);

            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {

                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("addProduct fail");
                }
            } catch (Exception e) {
                throw new ProductOperationException("addProduct fail:" + e.toString());
            }

            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                addProductImgList(product, productImgHolderList);
            }
            return new ProductExecution(ProductState.SUCCESS, product);
        } else {

            return new ProductExecution(ProductState.EMPTY);
        }
    }

    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                          List<ImageHolder> productImgHolderList) throws ProductOperationException {

        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {

            product.setTimeUpdated(new Date());
            if (thumbnail != null) {
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgHolderList);
            }
            try {
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("modifyProduct failed");
                }
                return new ProductExecution(ProductState.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("modifyProduct failed:" + e.toString());
            }
        } else {
            return new ProductExecution(ProductState.EMPTY);
        }
    }

    /**
     *
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * batch insert
     *
     * @param product
     * @param productImgHolderList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        for (ImageHolder productImgHolder : productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setTimeCreated(new Date());
            productImgList.add(productImg);
        }

        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("addProductImgList failed");
                }
            } catch (Exception e) {
                throw new ProductOperationException("addProductImgList failed:" + e.toString());
            }
        }
    }

    /**
     *
     *
     * @param productId
     */
    private void deleteProductImgList(long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }
}