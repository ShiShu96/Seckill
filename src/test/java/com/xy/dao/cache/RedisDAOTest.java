package com.xy.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.xy.dao.SeckillDao;
import com.xy.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")

public class RedisDAOTest {

    @Autowired
    private RedisDAO redisDAO;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void getSeckill() {
        Seckill seckill;
        long seckillId = 1000;
        seckill = seckillDao.queryById(seckillId);
        String info = redisDAO.putSeckill(seckill);
        System.out.println("info" + info);

        seckill = redisDAO.getSeckill(seckillId);
        System.out.println(seckill);


    }

    @Test
    public void putSeckill() {
    }
}