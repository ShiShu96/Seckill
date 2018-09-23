-- mysql 8.0
create database seckill;
use seckill;

create table seckill(
seckill_id bigint not null auto_increment comment "商品库存id",
name varchar (30) not null comment "商品名称",
number int comment "商品库存数量",
start_time timestamp not null comment "秒杀开始时间",
end_time timestamp not null comment "秒杀结束时间",
create_time timestamp not null default current_timestamp comment "秒杀创建时间",
primary key (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(creat_time)
)auto_increment = 1000 default charset = utf8 comment = "秒杀库存表"

insert into seckill (name, number, start_time, end_time)
values ("$100 for ipad", 100, "2015-01-01 00:00", "2015-01-02 00:00");

insert into seckill (name, number, start_time, end_time)
values ("$200 for iphonex", 400, "2015-01-01 00:00", "2015-01-02 00:00");

insert into seckill (name, number, start_time, end_time)
values ("$10 for ipod", 200, "2015-01-01 00:00", "2015-01-02 00:00");


create table success_killed(
seckill_id bigint not null auto_increment comment "秒杀商品id",
user_phone bigint not null commment "",
state tinyint not null default -1 comment "",
create_time timestamp not null comment "",
primary key (seckill_id, user_phome),
key idx_create_time(create_time)
)default charset = utf8 comment = "秒杀成功明细表"