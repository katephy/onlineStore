package com.shop.ecommerce.utils;

public class PathUtil {
    private static String seperator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "C:/project/ztgg-image";
        } else {
            basePath = "/Users/kate/Desktop/imgs";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = "upload/images/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }

    public static String getShopCategoryPath() {
        String imagePath = "upload/images/item/shopCategory/";
        return imagePath.replace("/", seperator);
    }
}
