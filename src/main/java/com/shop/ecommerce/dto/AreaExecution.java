package com.shop.ecommerce.dto;

import com.shop.ecommerce.entity.Area;
import com.shop.ecommerce.enums.AreaState;

import java.util.List;

public class AreaExecution {

    private int state;

    private String stateInfo;

    private int count;

    private Area area;

    private List<Area> areaList;

    public AreaExecution() {
    }

    // area operations failed
    public AreaExecution(AreaState stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // area execution success
    public AreaExecution(AreaState stateEnum, Area area) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.area = area;
    }

    // batch area execution success
    public AreaExecution(AreaState stateEnum, List<Area> areaList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.areaList = areaList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }



}
