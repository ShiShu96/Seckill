package com.xy.dao;

import org.apache.ibatis.annotations.Param;
import com.xy.entity.SuccessKilled;

public interface SuccessKilledDao {
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
