package com.shop.ecommerce.enums;

public enum ShopState {

    CHECK(0, "verifying info provided"),
    OFFLINE(-1, "the shop is offline because of violation"),
    SUCCESS(1, "the shop is created successfully"),
    PASS(2, "the info provided is verified"),
    INNER_ERROR(-1001,"internal system error"),
    NULL_SHOPID(-1002, "ShopId is null"),
    NULL_SHOP(-1003, "shop is null");

    private int state;
    private String stateInfo;

    // private constructor
    private ShopState(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopState stateOf(int state) {
        for (ShopState stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
