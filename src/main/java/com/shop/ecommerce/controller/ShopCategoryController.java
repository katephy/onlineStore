package com.shop.ecommerce.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.ecommerce.dto.ImageHolder;
import com.shop.ecommerce.dto.ShopCategoryExecution;
import com.shop.ecommerce.entity.ShopCategory;
import com.shop.ecommerce.enums.ShopCategoryState;
import com.shop.ecommerce.enums.ShopState;
import com.shop.ecommerce.exceptions.ShopCategoryOperationException;
import com.shop.ecommerce.service.ShopCategoryService;
import com.shop.ecommerce.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class ShopCategoryController {

    @Autowired
    ShopCategoryService shopCategoryService;

    @RequestMapping(value = "/listShopCategories", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopCategorys() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> list = new ArrayList<ShopCategory>();
        try {
            //first level
            list = shopCategoryService.getShopCategoryList(null);
            //second level
            list.addAll(shopCategoryService.getShopCategoryList(new ShopCategory()));
            modelMap.put(Constants.PAGE_SIZE, list);
            modelMap.put(Constants.TOTAL, list.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    @RequestMapping(value = "/list1stlevelshopcategories", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> list1stLevelShopCategories() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> list = new ArrayList<ShopCategory>();
        try {
            list = shopCategoryService.getShopCategoryList(null);
            modelMap.put(Constants.PAGE_SIZE, list);
            modelMap.put(Constants.TOTAL, list.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    @RequestMapping(value = "/list2ndlevelshopcategories", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> list2ndLevelShopCategories() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> list = new ArrayList<ShopCategory>();
        try {
            list = shopCategoryService.getShopCategoryList(new ShopCategory());
            modelMap.put(Constants.PAGE_SIZE, list);
            modelMap.put(Constants.TOTAL, list.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    @RequestMapping(value ="/addshopcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addShopCategory(@RequestParam(value = "shopCategory") String shopCategoryStr,
                                                @RequestParam(value = "shopCategoryImg") MultipartFile shopCategoryImg) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ShopCategory shopCategory = null;
        try {
            shopCategory = mapper.readValue(shopCategoryStr, ShopCategory.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (shopCategory != null && shopCategoryImg != null) {
            try {
                ImageHolder imageHolder = new ImageHolder(shopCategoryImg.getOriginalFilename(), shopCategoryImg.getInputStream());
                ShopCategoryExecution shopCategoryExecution = shopCategoryService.addShopCategory(shopCategory, imageHolder);
                if (shopCategoryExecution.getState() == ShopState.CHECK.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopCategoryExecution.getStateInfo());
                }
            } catch (ShopCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "can't add empty shopCategory");
            return modelMap;
        }
    }

    @RequestMapping(value = "/modifyshopcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShopCategory(@RequestParam(value = "shopCategory") String shopCategoryStr,
                                                   @RequestParam(value = "shopCategoryImg") MultipartFile shopCategoryImg) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        ShopCategory shopCategory = null;
        try {
            shopCategory = mapper.readValue(shopCategoryStr, ShopCategory.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (shopCategory != null && shopCategory.getShopCategoryId() != null) {
            try {
                ImageHolder imageHolder = null;
                if (shopCategoryImg != null && shopCategoryImg.getSize() > 0) {
                    imageHolder = new ImageHolder(shopCategoryImg.getOriginalFilename(), shopCategoryImg.getInputStream());
                }
                ShopCategoryExecution ae = shopCategoryService.modifyShopCategory(shopCategory, imageHolder);
                if (ae.getState() == ShopCategoryState.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "please enter the shop category");
        }
        return modelMap;
    }


}
