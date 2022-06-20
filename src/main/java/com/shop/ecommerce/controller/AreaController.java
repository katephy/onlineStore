package com.shop.ecommerce.controller;

import com.shop.ecommerce.dto.AreaExecution;
import com.shop.ecommerce.entity.Area;
import com.shop.ecommerce.enums.AreaState;
import com.shop.ecommerce.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    private String testHello() {
        return "HelloWorld from spring controller";
    }

    @RequestMapping(value = "/listarea", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listArea() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Area> list = new ArrayList<>();
        try {
            list = areaService.getAreaList();
            modelMap.put("data", list);
            modelMap.put("total", list.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    @RequestMapping(value = "/addarea", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addArea(@RequestBody Area area) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (area != null && area.getAreaName() != null) {
            try {
                AreaExecution ae = areaService.addArea(area);
                if (ae.getState() == AreaState.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Please enter the area info");
        }
        return modelMap;
    }


    @RequestMapping(value = "/modifyarea", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyArea(@RequestBody Area area) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (area != null && area.getAreaId() != null) {
            try {
                AreaExecution ae = areaService.modifyArea(area);
                if (ae.getState() == AreaState.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "no area info was found in Database");
        }
        return modelMap;
    }

    @RequestMapping(value = "/delete/area/{id}", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyArea(@PathVariable Long id) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            AreaExecution ae = areaService.removeArea(id);
            if (ae.getState() == AreaState.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", ae.getStateInfo());
            }
        } catch (RuntimeException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        return modelMap;
    }




}
