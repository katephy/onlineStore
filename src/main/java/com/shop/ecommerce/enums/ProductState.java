package com.shop.ecommerce.enums;

public enum ProductState {
    ILLEGAL(-1, "illegal"),
    SOLD(0, "sold out"),
    SUCCESS(1, "success"),
    INNER_ERROR(-1001, "failure"),
    EMPTY(-1002, "empty product");

    private int state;

    private String stateInfo;

    ProductState(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProductState stateOf(int index) {
        for (ProductState state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

}
