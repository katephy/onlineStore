package com.shop.ecommerce.enums;

public enum AreaState {

    ILLEGAL(-1, "illegal"),
    SUCCESS(0, "success"),
    INNER_ERROR(-1001, "failure"),
    EMPTY(-1002, "empty area info");

    private int state;

    private String stateInfo;

    AreaState(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static AreaState stateOf(int index) {
        for (AreaState state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
