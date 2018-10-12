package com.xy.service;

import com.xy.dto.Exposer;
import com.xy.dto.SeckillExecution;
import com.xy.entity.Seckill;
import com.xy.exception.RepeatKillException;
import com.xy.exception.SeckillCloseException;
import com.xy.exception.SeckillException;

import java.rmi.server.ExportException;
import java.util.List;

import java.util.List;

public interface SeckillService {
    /***
     * 查询所有秒杀记录
     */

    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启输出url
     * 否则
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, SeckillCloseException, RepeatKillException;
    
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException;

}
