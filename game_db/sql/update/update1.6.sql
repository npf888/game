set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';


USE `texas`;


-- 首页公告
DROP TABLE IF EXISTS `t_index_notice`;
CREATE TABLE `t_index_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cn` varchar(5000) NOT NULL COMMENT 'cn',
  `en` varchar(5000) NOT NULL COMMENT 'en',
  `tw` varchar(5000) NOT NULL COMMENT 'tw',
  `open` int(11) NOT NULL COMMENT '开启',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into  t_index_notice VALUES (1,"","","",0,0,0);