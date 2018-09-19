-- noinspection SqlNoDataSourceInspectionForFile

--procedure for kill execution

use seckill;
DELIMITER $$

create procedure execute_seckill
  (in v_seckill_id bigint, in v_phone bigint,
    in v_kill_time timestamp , out r_result int)
    BEGIN
    DECLARE insert_count int DEFAULT 0;
    start TRANSACTION;
    insert ignore into success_killed
          (seckill_id, user_phone, create_time)
          values (v_seckill_id, v_phone, v_kill_time);
    select row_count() into insert_count;
    IF(insert_count=0) then
        rollback;
        set r_result=-1;
    ELSEIF(insert_count<0) then
        rollback;
        set r_result = -2;
    ELSE
        update seckill
        set number = number -1
        where seckill_id = v_seckill_id
        and end_time>v_kill_time
        and start_time<v_kill_time
        and number>0;
        select row_count() into insert_count;
        IF(insert_count=0) THEN
             rollback ;
			 set r_result=0;
        ELSEIF(insert_count<0) then
               rollback;
               set r_result=-2;
        ELSE
             commit ;
              set r_result=1;
        END IF;
        END IF;
END;
    $$

DELIMITER ;
set @r_result=-3;
call execute_seckill(1000,19917632467,now(), @r_result);

select @r_result



