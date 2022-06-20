package com.shop.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.ecommerce.dto.ImageHolder;
import com.shop.ecommerce.dto.ShopExecution;
import com.shop.ecommerce.entity.Area;
import com.shop.ecommerce.entity.Shop;
import com.shop.ecommerce.entity.ShopCategory;
import com.shop.ecommerce.entity.User;
import com.shop.ecommerce.enums.ShopState;
import com.shop.ecommerce.exceptions.ShopOperationException;
import com.shop.ecommerce.service.AreaService;
import com.shop.ecommerce.service.ShopCategoryService;
import com.shop.ecommerce.service.ShopService;

import com.shop.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopController {

    @Autowired
    ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(@RequestParam(value = "shop") String shopStr, @RequestParam(value = "shopImg") MultipartFile shopImg) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // map shopStr to shop
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // register shop
        if (shop != null && shopImg != null) {
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                ShopExecution shopExecution = shopService.addShop(shop,imageHolder);
                if (shopExecution.getState() == ShopState.CHECK.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "can't insert empty shop");
            return modelMap;
        }
    }

    @RequestMapping(value = "/getshopbyid/{shopId}", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(@PathVariable Long shopId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(@RequestParam(value = "shop") String shopStr, @RequestParam(value = "shopImg") MultipartFile shopImg) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // map shopStr to shop
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }


        // 2.modify shop info
        if (shop != null && shop.getShopId() != null) {
            ShopExecution shopExecution;
            try {
                if (shopImg == null) {
                    shopExecution = shopService.modifyShop(shop, null);
                } else {
                    ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                    shopExecution = shopService.modifyShop(shop, imageHolder);
                }
                if (shopExecution.getState() == ShopState.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
            return modelMap;
        }
    }

    @RequestMapping(value = "/getshoplist/{userId}", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(@PathVariable Long userId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            User user = userService.getUserById(userId);
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution shopExecution = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", shopExecution.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }
}