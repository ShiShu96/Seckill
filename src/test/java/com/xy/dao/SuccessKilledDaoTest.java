package com.xy.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.xy.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")



public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() {
        long seckillId = 1000;
        long userPhone = 18811409939L;
        int insertcount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
        System.out.println("insertcount = " + insertcount);

    }

    @Test
    public void testQueryByIdWithSeckill() {
        long seckillId = 1000;
        long userPhone = 18811409939L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());


    }
}