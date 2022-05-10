package com.shop.ecommerce.service.impl;

import com.shop.ecommerce.dao.AreaDao;
import com.shop.ecommerce.dto.AreaExecution;
import com.shop.ecommerce.entity.Area;
import com.shop.ecommerce.enums.AreaState;
import com.shop.ecommerce.exceptions.AreaOperationException;
import com.shop.ecommerce.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }

    @Override
    public AreaExecution addArea(Area area) {
        if (area.getAreaName() != null && !"".equals(area.getAreaName())) {
            area.setTimeCreated(new Date());
            area.setTimeUpdated(new Date());
            try {
                int effectedNum = areaDao.insertArea(area);
                if (effectedNum > 0) {
                    return new AreaExecution(AreaState.SUCCESS, area);
                } else {
                    return new AreaExecution(AreaState.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new AreaOperationException("add area operation failed:" + e.toString());
            }
        } else {
            return new AreaExecution(AreaState.EMPTY);
        }
    }

    @Override
    public AreaExecution modifyArea(Area area) {

        if (area.getAreaId() != null && area.getAreaId() > 0) {
            area.setTimeUpdated(new Date());
            try {
                int effectedNum = areaDao.updateArea(area);
                if (effectedNum > 0) {
                    return new AreaExecution(AreaState.SUCCESS, area);
                } else {
                    return new AreaExecution(AreaState.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new AreaOperationException("modify area operations failed" + e.toString());
            }
        } else {
            return new AreaExecution(AreaState.EMPTY);
        }
    }


}
