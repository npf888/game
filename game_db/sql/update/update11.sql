CREATE TABLE `t_slot_list` (
  `id` bigint(20) NOT NULL,
  `langDesc` varchar(120) DEFAULT NULL COMMENT '老虎机名称',
  `payLinesNum` int(3) DEFAULT NULL,
  `bet1` int(11) DEFAULT NULL,
  `bet2` int(11) DEFAULT NULL,
  `bet3` int(11) DEFAULT NULL,
  `bet4` int(11) DEFAULT NULL,
  `bet5` int(11) DEFAULT NULL,
  `type` int(3) DEFAULT NULL COMMENT '老虎机编号',
  `bet1Lv` int(6) DEFAULT NULL,
  `bet2Lv` int(6) DEFAULT NULL,
  `bet3Lv` int(6) DEFAULT NULL,
  `bet4Lv` int(6) DEFAULT NULL,
  `bet5Lv` int(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

