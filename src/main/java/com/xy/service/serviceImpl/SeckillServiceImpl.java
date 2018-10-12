package com.xy.service.serviceImpl;

import com.xy.dao.SeckillDao;
import com.xy.dao.SuccessKilledDao;
import com.xy.dao.cache.RedisDAO;
import com.xy.dto.Exposer;
import com.xy.dto.SeckillExecution;
import com.xy.entity.Seckill;
import com.xy.entity.SuccessKilled;
import com.xy.enums.SeckillStatEnum;
import com.xy.exception.RepeatKillException;
import com.xy.exception.SeckillCloseException;
import com.xy.exception.SeckillException;
import com.xy.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    private RedisDAO redisDAO;

    private final String salt = "erteyruisodhgfdsdgurhogjdsfthJ3YUJRI";

    public List<Seckill> getSeckillList() {

        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {

        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //一致性建立在超时的基础上
        //缓存优化
        //1.从Redis里获取seckill
        Seckill seckill = redisDAO.getSeckill(seckillId);
        if (seckill == null) {
//            2.访问数据库
            seckill = seckillDao.queryById(seckillId);
            //如果超过最大连接数
            if (seckill == null)
                return new Exposer(false, seckillId);
            else {
//                3 放入Redis
                redisDAO.putSeckill(seckill);
            }
        }


        Date start = seckill.getStartTime();
        Date end = seckill.getEndTime();
        Date now = new Date();


        if (now.getTime() < start.getTime() || now.getTime() > end.getTime())
            return new Exposer(false, seckillId, now.getTime(), start.getTime(), end.getTime());

        String md5 = getMD5(seckillId);

        return new Exposer(true, seckillId, md5);
    }

    public String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;


    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException {
        if (md5 == null || !md5.equals(getMD5(seckillId)))
            throw new SeckillException("data rewrite");
        //减库存+记录购买行为
        Date now = new Date();
        try {
            //记录购买行为，
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                throw new RepeatKillException("重复秒杀");

            } else {
                int updateCount = seckillDao.reduceNumber(seckillId, now);
                if (updateCount <= 0) {
                    //没有更新到记录，秒杀结束，rollback
                    throw new SeckillCloseException("秒杀结束");

                } else {
                    //秒杀成功，commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }

        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error" + e.getMessage());
        }


    }
    
    @Override
	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			return new SeckillExecution(seckillId, SeckillStateEnum.DATA_REWRITE);
		}
		Date killTime = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		// 执行存储过程，result被赋值
		try {
			seckillDao.killByProcedure(map);
			// 获取result
			int result = MapUtils.getInteger(map, "result", -2);
			if (result == 1) {
				SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, sk);
			} else {
				return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
		}
	}
}
