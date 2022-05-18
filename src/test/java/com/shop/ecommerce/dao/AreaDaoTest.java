package com.shop.ecommerce.dao;


import com.shop.ecommerce.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class AreaDaoTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryAreaList();
        Assert.assertEquals(1, areaList.size());
    }


}
