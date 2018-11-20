ALTER TABLE `loccitane`.`STAFF_COMMISSION` ADD COLUMN `extra_commission_rate` double not null default 0;
ALTER TABLE `loccitane`.`STAFF_COMMISSION` ADD COLUMN `extra_commission` double not null default 0;
ALTER TABLE `loccitane`.`STAFF_COMMISSION` ADD COLUMN `target_commission` double not null default 0;
ALTER TABLE `loccitane`.`STAFF_COMMISSION` ADD COLUMN `target_extra_commission_rate` double not null default 0;
ALTER TABLE `loccitane`.`STAFF_COMMISSION` ADD COLUMN `target_extra_commission` double not null default 0;

ALTER TABLE `loccitane`.`staff_clock_in_out` ADD `warning_msg` varchar(255) null;
-- 2018-11-19 17:25 created by ken
ALTER TABLE `loccitane`.`BOOK` ADD COLUMN `is_all_share_single_room` TINYINT(1) not null default 0 COMMENT 'booking对应的bookitem是否共用一个房间';

