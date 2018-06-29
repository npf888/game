CREATE TABLE `t_function` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `functionType` tinyint(3) DEFAULT NULL COMMENT '功能的类型',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `descrip` varchar(250) DEFAULT NULL COMMENT '描述',
  `pic` varchar(100) DEFAULT NULL COMMENT '功能图片',
  `startTime` datetime DEFAULT NULL COMMENT '买一赠一的功能 开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '买一赠一的功能 结束时间',
  `conditions` text COMMENT '条件',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

