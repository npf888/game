#在texas 数据库中

alter table t_mail_info add column deleted tinyint(1) comment "删除标志位 0 未删除，1 已删除";