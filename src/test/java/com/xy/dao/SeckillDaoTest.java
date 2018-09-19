package com.xy.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.xy.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")

public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testReduceNumber() {
        Date date = new Date();
        int updatecount = seckillDao.reduceNumber(1000L, date);
        System.out.println("updatecount = " + updatecount);


    }

    @Test
    public void testQueryById() {
        try{
            long seckillId = 1000;
            Seckill sec = seckillDao.queryById(seckillId);
            System.out.println(sec.getName());
            System.out.println(sec);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void testQueryAll() {
        int offset = 1;
        int limit = 3;
        List<Seckill> list = new ArrayList<Seckill>();
        list = seckillDao.queryAll(offset, limit);
        for(Seckill s: list)
        System.out.println(s);


    }
}