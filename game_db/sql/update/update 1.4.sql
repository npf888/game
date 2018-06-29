set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';


USE `texas`;


-- 客户端资源版本
DROP TABLE IF EXISTS `t_robot_complement`;
CREATE TABLE `t_robot_complement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源版本',
  `robotId` bigint(20) NOT NULL COMMENT '版本号',
  `complement` bigint(20) NOT NULL COMMENT '补签数',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
