package com.xy.dao;

import org.apache.ibatis.annotations.Param;
import com.xy.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {

    /**
     *
     * @param seckillId
     * @param kilTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date kilTime);

    Seckill queryById(long seckillId);

    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
