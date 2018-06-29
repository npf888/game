set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';


USE `texas`;

alter table t_human_misc add fbInvitePack text DEFAULT NULL;
alter table t_human_misc add fbInviteRewardPack varchar(200) DEFAULT NULL;
alter table t_human_misc add fbReward int(11) DEFAULT 0;
