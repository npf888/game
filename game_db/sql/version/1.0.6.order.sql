alter table t_human_recharge_order add column level bigint(20) default 0 COMMENT "下单的时候用户 的等级";
alter table t_human_recharge_order add column gold bigint(20) default 0 COMMENT "下单的时候用户 的金币";

alter table t_notice add column isDelete tinyint(1) default 0 COMMENT "是否删除，0：未删除     1：已删除";