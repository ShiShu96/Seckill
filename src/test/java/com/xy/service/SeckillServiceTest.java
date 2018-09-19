package com.xy.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.xy.dto.Exposer;
import com.xy.dto.SeckillExecution;
import com.xy.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})

public class SeckillServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillService seckillService;

    @Test
    public void testGetSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
        for(Seckill s: list)
        System.out.println(s);
    }

    @Test
    public void testGetById() {
        long seckillId = 1000;
        Seckill s = seckillService.getById(seckillId);
        System.out.println(s);
    }

    @Test
    public void testExportSeckillUrl(){

        long seckillId = 1000;
        Exposer e = seckillService.exportSeckillUrl(seckillId);
        System.out.println(e);


    }


    @Test
    public void testExcuteSeckill() {
        long seckillId = 1000;
        long userPhone = 18515244719L;
        String md5 = "de63945022c5bfd4c97a930f38cb1970";
        SeckillExecution s = seckillService.executeSeckill(seckillId, userPhone, md5);
        System.out.println(s);

    }
}