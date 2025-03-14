/*
Navicat MySQL Data Transfer

Source Server         : 47.139.148.109
Source Server Version : 50740
Source Host           : 43.139.148.109:3306
Source Database       : source-test

Target Server Type    : MYSQL
Target Server Version : 50740
File Encoding         : 65001

Date: 2023-10-25 14:20:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cms_article
-- ----------------------------
DROP TABLE IF EXISTS `cms_article`;
CREATE TABLE `cms_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_type` varchar(50) NOT NULL COMMENT '类型',
  `small_title` varchar(100) NOT NULL COMMENT '简短标题',
  `big_title` varchar(200) DEFAULT NULL COMMENT '完整标题',
  `face_url` varchar(200) DEFAULT NULL COMMENT '封面图',
  `face_thum` varchar(200) DEFAULT NULL COMMENT '缩略图',
  `article_content` mediumtext COMMENT '内容',
  `article_source` varchar(50) DEFAULT NULL COMMENT '来源',
  `sort_no` int(4) DEFAULT NULL COMMENT '排序号',
  `star_Num` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=297 DEFAULT CHARSET=utf8 COMMENT='内容管理';

-- ----------------------------
-- Records of cms_article
-- ----------------------------

-- ----------------------------
-- Table structure for cms_developer
-- ----------------------------

DROP TABLE IF EXISTS `cms_developer`;
CREATE TABLE `cms_developer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `career` varchar(20) NOT NULL COMMENT '职业',
  `intro` varchar(255) NOT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成员管理';

-- ----------------------------
-- Table structure for cms_feedback
-- ----------------------------
DROP TABLE IF EXISTS `cms_feedback`;
CREATE TABLE `cms_feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `feed_type` varchar(255) DEFAULT NULL COMMENT '类别',
  `feed_title` varchar(255) DEFAULT NULL COMMENT '标题',
  `feed_content` varchar(255) DEFAULT NULL COMMENT '详情',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of cms_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for cms_link
-- ----------------------------
DROP TABLE IF EXISTS `cms_link`;
CREATE TABLE `cms_link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `link_Name` varchar(255) NOT NULL COMMENT '站点名称',
  `link_Url` varchar(255) NOT NULL COMMENT '站点链接',
  `link_Logo` varchar(255) NOT NULL COMMENT '站点Logo',
  `link_Show` varchar(255) NOT NULL COMMENT '展示地址',
  `link_Way` varchar(255) NOT NULL COMMENT '联系方式',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of cms_link
-- ----------------------------

-- ----------------------------
-- Table structure for cms_msg
-- ----------------------------
DROP TABLE IF EXISTS `cms_msg`;
CREATE TABLE `cms_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `msg_type` int(1) NOT NULL COMMENT '消息类型',
  `msg_content` varchar(2000) NOT NULL COMMENT '内容',
  `msg_from_session` varchar(50) DEFAULT NULL COMMENT '来源会话',
  `msg_from_user_id` varchar(50) DEFAULT NULL COMMENT '来源用户',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `msg_to_session` varchar(50) DEFAULT NULL COMMENT '目标会话',
  `msg_to_user_id` varchar(50) DEFAULT NULL COMMENT '目标用户',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息管理';

-- ----------------------------
-- Records of cms_msg
-- ----------------------------

-- ----------------------------
-- Table structure for cms_service_item
-- ----------------------------
DROP TABLE IF EXISTS `cms_service_item`;
CREATE TABLE `cms_service_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(20) DEFAULT NULL COMMENT '类别',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` mediumblob COMMENT '内容',
  `state` char(1) DEFAULT NULL COMMENT '启用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of cms_service_item
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表';

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表字段';

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for house_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `house_evaluate`;
CREATE TABLE `house_evaluate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `house_id` bigint(20) NOT NULL COMMENT '房屋id',
  `eval_user_id` bigint(20) NOT NULL COMMENT '评价人id',
  `eval_user` varchar(255) DEFAULT NULL COMMENT '评价人',
  `evaluate` text NOT NULL COMMENT '评价',
  `state` int(1) NOT NULL COMMENT '状态 1显示 2删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='房源评价';

-- ----------------------------
-- Records of house_evaluate
-- ----------------------------

-- ----------------------------
-- Table structure for house_feature
-- ----------------------------
DROP TABLE IF EXISTS `house_feature`;
CREATE TABLE `house_feature` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `house_id` bigint(20) NOT NULL,
  `feature` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=608 DEFAULT CHARSET=utf8 COMMENT='房源特色';

-- ----------------------------
-- Records of house_feature
-- ----------------------------

-- ----------------------------
-- Table structure for house_heart
-- ----------------------------
DROP TABLE IF EXISTS `house_heart`;
CREATE TABLE `house_heart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `house_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=551 DEFAULT CHARSET=utf8 COMMENT='房源特色';

-- ----------------------------
-- Records of house_heart
-- ----------------------------

-- ----------------------------
-- Table structure for house_image
-- ----------------------------
DROP TABLE IF EXISTS `house_image`;
CREATE TABLE `house_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `house_id` bigint(20) DEFAULT NULL,
  `image_name` varchar(50) DEFAULT NULL,
  `image_path` varchar(200) DEFAULT NULL,
  `img_url` varchar(200) DEFAULT NULL,
  `image_size` float(11,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=357 DEFAULT CHARSET=utf8 COMMENT='房源图片';

-- ----------------------------
-- Records of house_image
-- ----------------------------

-- ----------------------------
-- Table structure for house_room
-- ----------------------------
DROP TABLE IF EXISTS `house_room`;
CREATE TABLE `house_room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(1) NOT NULL COMMENT '0整租/1合租',
  `house_num` varchar(2) DEFAULT NULL COMMENT '居室',
  `toilet_num` varchar(2) DEFAULT NULL COMMENT '卫生间',
  `house_area` float(6,2) DEFAULT NULL COMMENT '整套面积',
  `room_type` int(1) DEFAULT NULL COMMENT '1主卧,1次卧',
  `room_code` varchar(10) DEFAULT NULL COMMENT '房间号',
  `room_area` float(6,0) DEFAULT NULL COMMENT '房屋面积',
  `direction` varchar(2) DEFAULT NULL COMMENT '朝向',
  `price` decimal(10,2) DEFAULT NULL COMMENT '租金',
  `floor` varchar(10) DEFAULT NULL COMMENT '楼层',
  `step_type` varchar(10) DEFAULT NULL COMMENT '楼房类型',
  `start_date` datetime DEFAULT NULL COMMENT '起租日期',
  `introduce` varchar(255) DEFAULT NULL COMMENT '房源描述',
  `owner_name` varchar(20) DEFAULT NULL COMMENT '房东姓名',
  `ower_phone` varchar(11) DEFAULT NULL COMMENT '房东电话',
  `village_id` bigint(20) DEFAULT NULL COMMENT '小区id',
  `village_name` varchar(20) DEFAULT NULL COMMENT '小区名称',
  `address` varchar(100) DEFAULT NULL COMMENT '具体地址',
  `house_no` varchar(50) DEFAULT NULL COMMENT '房牌号',
  `pay_type` varchar(10) DEFAULT NULL COMMENT '付款方式',
  `publish_id` bigint(20) DEFAULT NULL COMMENT '发布人id',
  `state` int(1) DEFAULT NULL COMMENT '0：待出租，1：已出租,2:下架',
  `create_time` datetime DEFAULT NULL,
  `create_name` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_name` varchar(20) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `house_Hall` varchar(2) DEFAULT NULL,
  `code` varchar(32) DEFAULT NULL,
  `face_Url` varchar(200) NOT NULL,
  `decoration` varchar(50) DEFAULT NULL,
  `agent_Name` varchar(20) DEFAULT NULL,
  `agent_Phone` varchar(20) DEFAULT NULL,
  `agent_User_Id` bigint(20) DEFAULT NULL,
  `agent_Avatar` varchar(255) DEFAULT NULL,
  `longitude` varchar(20) DEFAULT NULL,
  `latitude` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_house` (`publish_id`,`village_id`,`address`,`house_no`,`price`,`ower_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8 COMMENT='房源详情';

-- ----------------------------
-- Records of house_room
-- ----------------------------

-- ----------------------------
-- Table structure for house_user
-- ----------------------------
DROP TABLE IF EXISTS `house_user`;
CREATE TABLE `house_user` (
  `id` bigint(20) NOT NULL,
  `house_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `publish_id` bigint(20) NOT NULL,
  `actual_price` decimal(10,2) NOT NULL,
  `state` int(1) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出租信息';

-- ----------------------------
-- Records of house_user
-- ----------------------------

-- ----------------------------
-- Table structure for house_village
-- ----------------------------
DROP TABLE IF EXISTS `house_village`;
CREATE TABLE `house_village` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `province` varchar(10) NOT NULL COMMENT '省',
  `city` varchar(20) NOT NULL COMMENT '市',
  `area` varchar(50) NOT NULL COMMENT '区',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `code` varchar(50) DEFAULT NULL COMMENT '代号',
  `year` int(4) DEFAULT NULL COMMENT '年份',
  `type` varchar(20) DEFAULT NULL COMMENT '建筑类型',
  `green` float(5,2) DEFAULT NULL COMMENT '绿化率',
  `introduce` varchar(200) DEFAULT NULL COMMENT '介绍',
  `way_state` int(1) DEFAULT NULL COMMENT '靠近地铁',
  `way_code` varchar(20) DEFAULT NULL COMMENT '地铁线路',
  `way_space` int(8) DEFAULT NULL COMMENT '地铁距离',
  `lon` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `Lat` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `state` int(1) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='房源小区';

-- ----------------------------
-- Records of house_village
-- ----------------------------

-- ----------------------------
-- Table structure for mall_address
-- ----------------------------
DROP TABLE IF EXISTS `mall_address`;
CREATE TABLE `mall_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(50) NOT NULL COMMENT '收货人',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `address` varchar(50) NOT NULL COMMENT '地址',
  `is_default` char(1) NOT NULL COMMENT '默认地址',
  `normal_disable` char(1) NOT NULL DEFAULT '' COMMENT '是否启用',
  `sort_no` int(4) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COMMENT='收货地址';

-- ----------------------------
-- Records of mall_address
-- ----------------------------

-- ----------------------------
-- Table structure for mall_advert
-- ----------------------------
DROP TABLE IF EXISTS `mall_advert`;
CREATE TABLE `mall_advert` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `advert_type` int(1) NOT NULL COMMENT '广告类别',
  `advert_name` varchar(50) NOT NULL COMMENT '广告名称',
  `advert_address` varchar(50) NOT NULL COMMENT '链接地址',
  `advert_url` varchar(200) NOT NULL COMMENT '广告图片',
  `sort_no` int(4) NOT NULL COMMENT '排序',
  `normal_disable` char(1) NOT NULL COMMENT '是否启用',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COMMENT='广告管理';

-- ----------------------------
-- Records of mall_advert
-- ----------------------------

-- ----------------------------
-- Table structure for mall_classify
-- ----------------------------
DROP TABLE IF EXISTS `mall_classify`;
CREATE TABLE `mall_classify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父id',
  `ancestors` varchar(50) NOT NULL DEFAULT '' COMMENT '祖级列表',
  `classify_name` varchar(30) NOT NULL DEFAULT '' COMMENT '分类名称',
  `classify_url` varchar(200) NOT NULL COMMENT '分类图片',
  `sort_no` int(4) DEFAULT '0' COMMENT '排序',
  `normal_disable` char(1) DEFAULT '0' COMMENT '是否启用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8mb4 COMMENT='分类管理';

-- ----------------------------
-- Records of mall_classify
-- ----------------------------

-- ----------------------------
-- Table structure for mall_goods
-- ----------------------------
DROP TABLE IF EXISTS `mall_goods`;
CREATE TABLE `mall_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `goods_classify` varchar(50) NOT NULL COMMENT '商品分类',
  `goods_name` varchar(50) NOT NULL COMMENT '商品名称',
  `goods_feature` varchar(50) DEFAULT NULL COMMENT '特色',
  `new_price` decimal(9,2) NOT NULL COMMENT '销售价',
  `old_price` decimal(9,2) DEFAULT NULL COMMENT '划线价',
  `goods_stock` int(9) NOT NULL COMMENT '库存',
  `goods_face_url` varchar(200) NOT NULL COMMENT '主图',
  `goods_item_url` varchar(2000) NOT NULL COMMENT '轮播图',
  `article_content` mediumtext CHARACTER SET utf8 NOT NULL COMMENT '图文详情',
  `sort_no` int(4) DEFAULT '0' COMMENT '排序',
  `status` char(1) DEFAULT NULL COMMENT '是否上架',
  `hot_Status` char(1) DEFAULT NULL COMMENT '是否热点',
  `goods_view` int(9) DEFAULT '0',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COMMENT='商品管理';

-- ----------------------------
-- Records of mall_goods
-- ----------------------------

-- ----------------------------
-- Table structure for mall_goods_spec
-- ----------------------------
DROP TABLE IF EXISTS `mall_goods_spec`;
CREATE TABLE `mall_goods_spec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `spec_name` varchar(50) NOT NULL COMMENT '规格名称',
  `spec_url` varchar(200) NOT NULL COMMENT '规格图片',
  `spec_price` decimal(9,2) NOT NULL COMMENT '销售价',
  `spec_stock` int(9) NOT NULL COMMENT '库存',
  `sort_no` int(4) DEFAULT '0' COMMENT '排序',
  `normal_disable` char(1) NOT NULL COMMENT '是否启用',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格';

-- ----------------------------
-- Records of mall_goods_spec
-- ----------------------------

-- ----------------------------
-- Table structure for mall_navigate
-- ----------------------------
DROP TABLE IF EXISTS `mall_navigate`;
CREATE TABLE `mall_navigate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `navigate_name` varchar(50) NOT NULL COMMENT '导航名称',
  `navigate_address` varchar(50) NOT NULL COMMENT '链接地址',
  `navigate_url` varchar(200) NOT NULL COMMENT '导航图标',
  `sort_no` int(4) NOT NULL COMMENT '排序',
  `normal_disable` char(1) NOT NULL COMMENT '是否启用',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COMMENT='导航管理';

-- ----------------------------
-- Records of mall_navigate
-- ----------------------------

-- ----------------------------
-- Table structure for mall_order
-- ----------------------------
DROP TABLE IF EXISTS `mall_order`;
CREATE TABLE `mall_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_code` varchar(20) NOT NULL COMMENT '订单号',
  `order_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `pay_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `pay_method` char(1) DEFAULT NULL COMMENT '支付方式',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_status` char(1) NOT NULL COMMENT '支持状态',
  `dis_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '优惠金额',
  `order_status` char(1) DEFAULT NULL COMMENT '订单状态',
  `sort_no` int(4) NOT NULL COMMENT '排序',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `receiver` varchar(20) DEFAULT NULL COMMENT '收货人',
  `receiver_Phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `receiver_Address` varchar(50) DEFAULT NULL COMMENT '收货地址',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8mb4 COMMENT='订单信息';

-- ----------------------------
-- Records of mall_order
-- ----------------------------

-- ----------------------------
-- Table structure for mall_order_goods
-- ----------------------------
DROP TABLE IF EXISTS `mall_order_goods`;
CREATE TABLE `mall_order_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `title` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '卖点',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `value` int(255) DEFAULT NULL COMMENT '数量',
  `image` varchar(255) DEFAULT NULL COMMENT '主图',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='订单商品关系表';

-- ----------------------------
-- Records of mall_order_goods
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Blob类型的触发器表';

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日历信息表';

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Cron类型的触发器表';

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', '0/15 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', '0/20 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(13) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(13) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='已触发的触发器表';

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务详细信息表';

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', null, 'cn.source.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001E636E2E736F757263652E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720027636E2E736F757263652E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017DAD2E5AA078707400007070707400013174000E302F3130202A202A202A202A203F74001172795461736B2E72794E6F506172616D7374000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000001740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', null, 'cn.source.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001E636E2E736F757263652E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720027636E2E736F757263652E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017DAD2E5AA078707400007070707400013174000E302F3135202A202A202A202A203F74001572795461736B2E7279506172616D7328277279272974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000002740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E69C89E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', null, 'cn.source.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001E636E2E736F757263652E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720027636E2E736F757263652E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017DAD2E5AA078707400007070707400013174000E302F3230202A202A202A202A203F74003872795461736B2E72794D756C7469706C65506172616D7328277279272C20747275652C20323030304C2C203331362E3530442C203130302974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000003740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E5A49AE58F82EFBC8974000133740001317800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储的悲观锁信息表';

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'TRIGGER_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'STATE_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='暂停的触发器表';

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(13) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(13) NOT NULL COMMENT '检查间隔时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调度器状态表';

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RuoyiScheduler', 'VM-12-6-centos1675129483457', '1677402008862', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(7) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(12) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(10) NOT NULL COMMENT '已经触发的次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简单触发器的信息表';

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='同步机制的行锁表';

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(13) DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(13) DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(13) NOT NULL COMMENT '开始时间',
  `end_time` bigint(13) DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(2) DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='触发器详细信息表';

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', 'TASK_CLASS_NAME1', 'DEFAULT', null, '1675689110000', '-1', '5', 'PAUSED', 'CRON', '1675689102000', '0', null, '2', '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', 'TASK_CLASS_NAME2', 'DEFAULT', null, '1675689105000', '-1', '5', 'PAUSED', 'CRON', '1675689103000', '0', null, '2', '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', 'TASK_CLASS_NAME3', 'DEFAULT', null, '1675689120000', '-1', '5', 'PAUSED', 'CRON', '1675689104000', '0', null, '2', '');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2021-12-12 13:47:15', 'admin', '2022-03-01 10:42:46', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES ('2', '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2021-12-12 13:47:16', '', null, '初始化密码 123456');
INSERT INTO `sys_config` VALUES ('3', '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2021-12-12 13:47:16', 'admin', '2022-02-15 16:55:53', '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES ('4', '账号自助-验证码开关', 'sys.account.captchaOnOff', 'true', 'Y', 'admin', '2021-12-12 13:47:16', '', null, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES ('5', '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2021-12-12 13:47:16', 'admin', '2022-02-12 20:13:42', '是否开启注册用户功能（true开启，false关闭）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('100', '0', '0', '集团公司', '0', '詹Sir', '18720989281', '261648947@qq.com', '0', '0', 'admin', '2021-12-12 13:47:05', 'admin', '2022-09-20 16:04:44');
INSERT INTO `sys_dept` VALUES ('101', '100', '0,100', '杭州科技有限公司', '1', '詹Sir', '18720989281', '261648947@qq.com', '0', '0', 'admin', '2021-12-12 13:47:05', 'admin', '2022-09-20 16:04:40');
INSERT INTO `sys_dept` VALUES ('102', '100', '0,100', '长沙分公司', '2', '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2021-12-12 13:47:05', '', null);
INSERT INTO `sys_dept` VALUES ('103', '101', '0,100,101', '研发部', '1', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-12-12 13:47:05', 'admin', '2022-02-23 15:37:56');
INSERT INTO `sys_dept` VALUES ('104', '101', '0,100,101', '市场部门', '2', '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2021-12-12 13:47:05', '', null);
INSERT INTO `sys_dept` VALUES ('105', '101', '0,100,101', '测试部门', '3', '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2021-12-12 13:47:05', '', null);
INSERT INTO `sys_dept` VALUES ('106', '101', '0,100,101', '财务部门', '4', '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2021-12-12 13:47:05', '', null);
INSERT INTO `sys_dept` VALUES ('107', '101', '0,100,101', '运维部门', '5', '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2021-12-12 13:47:05', '', null);
INSERT INTO `sys_dept` VALUES ('108', '102', '0,100,102', '市场部门', '1', '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2021-12-12 13:47:05', '', null);
INSERT INTO `sys_dept` VALUES ('109', '102', '0,100,102', '财务部门', '2', '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2021-12-12 13:47:05', '', null);
INSERT INTO `sys_dept` VALUES ('110', '101', '0,100,101', '运营部', '2', null, null, null, '0', '0', 'admin', '2022-02-15 17:00:54', '', null);
INSERT INTO `sys_dept` VALUES ('111', '101', '0,100,101', '财务部', '2', null, null, null, '0', '0', 'admin', '2022-02-15 17:01:03', '', null);
INSERT INTO `sys_dept` VALUES ('112', '101', '0,100,101', '产品部', '4', '詹Sir', '18720989281', '261648947@qq.com', '0', '0', 'admin', '2022-02-23 15:24:35', 'admin', '2022-02-23 15:27:29');
INSERT INTO `sys_dept` VALUES ('113', '100', '0,100', '河南科技有限公司', '2', '司先生', '18720989281', null, '0', '2', 'admin', '2022-07-22 11:48:37', 'admin', '2022-09-20 16:04:29');
INSERT INTO `sys_dept` VALUES ('114', '113', '0,100,113', '经纪人', '1', '司先生', '18720989281', null, '0', '2', 'admin', '2022-07-22 11:49:38', '', null);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES ('1', '1', '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2021-12-12 13:47:14', '', null, '性别男');
INSERT INTO `sys_dict_data` VALUES ('2', '2', '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2021-12-12 13:47:14', '', null, '性别女');
INSERT INTO `sys_dict_data` VALUES ('3', '3', '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2021-12-12 13:47:14', '', null, '性别未知');
INSERT INTO `sys_dict_data` VALUES ('4', '1', '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2021-12-12 13:47:15', '', null, '显示菜单');
INSERT INTO `sys_dict_data` VALUES ('5', '2', '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES ('6', '1', '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2021-12-12 13:47:15', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('7', '2', '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('8', '1', '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2021-12-12 13:47:15', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('9', '2', '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('10', '1', '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2021-12-12 13:47:15', '', null, '默认分组');
INSERT INTO `sys_dict_data` VALUES ('11', '2', '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '系统分组');
INSERT INTO `sys_dict_data` VALUES ('12', '0', '是', '0', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2021-12-12 13:47:15', 'admin', '2022-05-03 19:08:43', '系统默认是');
INSERT INTO `sys_dict_data` VALUES ('13', '1', '否', '1', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2021-12-12 13:47:15', 'admin', '2022-05-03 19:08:47', '系统默认否');
INSERT INTO `sys_dict_data` VALUES ('14', '1', '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2021-12-12 13:47:15', '', null, '通知');
INSERT INTO `sys_dict_data` VALUES ('15', '2', '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '公告');
INSERT INTO `sys_dict_data` VALUES ('16', '1', '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2021-12-12 13:47:15', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('17', '2', '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '关闭状态');
INSERT INTO `sys_dict_data` VALUES ('18', '1', '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '新增操作');
INSERT INTO `sys_dict_data` VALUES ('19', '2', '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '修改操作');
INSERT INTO `sys_dict_data` VALUES ('20', '3', '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '删除操作');
INSERT INTO `sys_dict_data` VALUES ('21', '4', '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '授权操作');
INSERT INTO `sys_dict_data` VALUES ('22', '5', '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '导出操作');
INSERT INTO `sys_dict_data` VALUES ('23', '6', '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '导入操作');
INSERT INTO `sys_dict_data` VALUES ('24', '7', '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '强退操作');
INSERT INTO `sys_dict_data` VALUES ('25', '8', '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '生成操作');
INSERT INTO `sys_dict_data` VALUES ('26', '9', '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '清空操作');
INSERT INTO `sys_dict_data` VALUES ('27', '1', '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('28', '2', '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2021-12-12 13:47:15', '', null, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('29', '1', '开源项目', '开源项目', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-02-15 17:28:57', 'admin', '2022-04-14 21:50:59', null);
INSERT INTO `sys_dict_data` VALUES ('30', '11', '程序人生', '程序人生', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-02-15 17:29:08', 'admin', '2022-03-01 10:37:05', null);
INSERT INTO `sys_dict_data` VALUES ('36', '0', '开源字节', '开源字节', 'sys_article_source', null, 'default', 'N', '0', 'admin', '2022-02-15 17:33:45', 'admin', '2022-03-17 16:15:01', null);
INSERT INTO `sys_dict_data` VALUES ('38', '1', '用户协议', '用户协议', 'sys_service_type', null, 'default', 'N', '0', 'admin', '2022-02-15 17:35:37', 'admin', '2022-03-18 23:19:11', null);
INSERT INTO `sys_dict_data` VALUES ('39', '3', '代理合作', '代理合作', 'sys_service_type', null, 'default', 'N', '0', 'admin', '2022-02-15 17:35:51', 'admin', '2022-03-08 09:45:06', null);
INSERT INTO `sys_dict_data` VALUES ('40', '2', '隐私协议', '隐私协议', 'sys_service_type', null, 'default', 'N', '0', 'admin', '2022-02-23 16:00:14', 'admin', '2022-03-08 09:45:02', null);
INSERT INTO `sys_dict_data` VALUES ('42', '13', '技术博客', '技术博客', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-02-24 09:44:28', 'admin', '2022-04-22 13:52:05', null);
INSERT INTO `sys_dict_data` VALUES ('45', '2', '小程序开发', '小程序开发', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-02-28 17:08:12', 'admin', '2022-03-01 13:56:59', null);
INSERT INTO `sys_dict_data` VALUES ('46', '3', 'App开发', 'App开发', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-02-28 17:08:31', 'admin', '2022-03-01 13:57:04', null);
INSERT INTO `sys_dict_data` VALUES ('47', '4', '网站开发', '网站开发', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-02-28 17:08:41', 'admin', '2022-03-01 13:57:09', null);
INSERT INTO `sys_dict_data` VALUES ('48', '1', '方案', '方案', 'sys_feedback_type', null, 'default', 'N', '0', 'admin', '2022-02-28 17:09:35', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('50', '3', '需求', '需求', 'sys_feedback_type', null, 'default', 'N', '0', 'admin', '2022-02-28 17:10:08', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('51', '4', '缺陷', '缺陷', 'sys_feedback_type', null, 'default', 'N', '0', 'admin', '2022-02-28 17:10:21', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('52', '10', '低代码开发', '低代码开发', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-03-01 10:36:17', 'admin', '2022-04-12 10:44:14', null);
INSERT INTO `sys_dict_data` VALUES ('53', '5', 'SEO优化', 'SEO优化', 'sys_article_type', '', 'default', 'N', '0', 'admin', '2022-03-03 16:14:28', 'admin', '2022-03-03 16:14:38', null);
INSERT INTO `sys_dict_data` VALUES ('54', '6', '解决方案', '解决方案', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-03-03 16:15:44', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('55', '4', '友链申请', '友链申请', 'sys_service_type', null, 'default', 'N', '0', 'admin', '2022-03-07 09:57:06', 'admin', '2022-03-08 09:44:56', null);
INSERT INTO `sys_dict_data` VALUES ('56', '2', '反馈', '反馈', 'sys_feedback_type', null, 'default', 'N', '0', 'admin', '2022-03-12 22:22:42', 'admin', '2022-03-12 22:22:50', null);
INSERT INTO `sys_dict_data` VALUES ('57', '5', '免责声明', '免责声明', 'sys_service_type', null, 'default', 'N', '0', 'admin', '2022-03-17 16:09:25', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('58', '7', '产品思维', '产品思维', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-03-17 16:41:09', 'admin', '2022-03-26 09:48:00', null);
INSERT INTO `sys_dict_data` VALUES ('61', '12', '艺术人生', '艺术人生', 'sys_article_type', null, 'default', 'N', '0', 'admin', '2022-04-22 13:51:51', 'admin', '2022-04-22 13:51:57', null);
INSERT INTO `sys_dict_data` VALUES ('62', '0', '首页轮播图', '0', 'mall_advert_type', null, 'default', 'N', '0', 'admin', '2022-05-02 16:57:17', 'admin', '2022-05-03 21:22:08', null);
INSERT INTO `sys_dict_data` VALUES ('63', '1', '首页中部广告位', '1', 'mall_advert_type', null, 'default', 'N', '0', 'admin', '2022-05-02 16:57:25', 'admin', '2022-05-02 16:57:30', null);
INSERT INTO `sys_dict_data` VALUES ('64', '0', '上架', '0', 'mall_goods_shelf', null, 'default', 'N', '0', 'admin', '2022-05-02 17:40:43', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('65', '1', '下架', '1', 'mall_goods_shelf', null, 'default', 'N', '0', 'admin', '2022-05-02 17:40:52', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('66', '2', '热门推荐', '2', 'mall_advert_type', null, 'default', 'N', '0', 'admin', '2022-05-03 10:49:58', 'admin', '2022-05-03 10:50:06', null);
INSERT INTO `sys_dict_data` VALUES ('67', '1', '待付款', '1', 'mall_order_status', null, 'default', 'N', '0', 'admin', '2022-06-12 16:43:28', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('68', '2', '待发货', '2', 'mall_order_status', null, 'default', 'N', '0', 'admin', '2022-06-12 16:43:44', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('69', '3', '待收货', '3', 'mall_order_status', null, 'default', 'N', '0', 'admin', '2022-06-12 16:43:53', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('70', '4', '待评价', '4', 'mall_order_status', null, 'default', 'N', '0', 'admin', '2022-06-12 16:44:05', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('71', '5', '已完成', '5', 'mall_order_status', null, 'default', 'N', '0', 'admin', '2022-06-12 16:44:17', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('72', '6', '已关闭', '6', 'mall_order_status', null, 'default', 'N', '0', 'admin', '2022-06-12 16:44:31', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('73', '7', '已退款', '7', 'mall_order_status', null, 'default', 'N', '0', 'admin', '2022-06-12 16:44:50', 'admin', '2022-06-19 16:04:04', null);
INSERT INTO `sys_dict_data` VALUES ('74', '1', '微信', '1', 'mall_pay_method', null, 'default', 'N', '0', 'admin', '2022-06-19 14:55:55', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('75', '2', '支付宝', '2', 'mall_pay_method', null, 'default', 'N', '0', 'admin', '2022-06-19 14:56:08', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('76', '1', '待支付', '1', 'mall_pay_status', null, 'default', 'N', '0', 'admin', '2022-06-19 15:02:26', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('77', '2', '已支付', '2', 'mall_pay_status', null, 'default', 'N', '0', 'admin', '2022-06-19 15:02:34', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('78', '3', '已退款', '3', 'mall_pay_status', null, 'default', 'N', '0', 'admin', '2022-06-19 15:02:44', '', null, null);
INSERT INTO `sys_dict_data` VALUES ('79', '6', '租房避坑指南', '租房避坑指南', 'sys_service_type', null, 'default', 'N', '0', 'admin', '2022-07-13 11:26:55', '', null, null);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('1', '用户性别', 'sys_user_sex', '0', 'admin', '2021-12-12 13:47:14', '', null, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES ('2', '菜单状态', 'sys_show_hide', '0', 'admin', '2021-12-12 13:47:14', '', null, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES ('3', '系统开关', 'sys_normal_disable', '0', 'admin', '2021-12-12 13:47:14', '', null, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES ('4', '任务状态', 'sys_job_status', '0', 'admin', '2021-12-12 13:47:14', '', null, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES ('5', '任务分组', 'sys_job_group', '0', 'admin', '2021-12-12 13:47:14', '', null, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES ('6', '系统是否', 'sys_yes_no', '0', 'admin', '2021-12-12 13:47:14', '', null, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES ('7', '通知类型', 'sys_notice_type', '0', 'admin', '2021-12-12 13:47:14', '', null, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES ('8', '通知状态', 'sys_notice_status', '0', 'admin', '2021-12-12 13:47:14', '', null, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES ('9', '操作类型', 'sys_oper_type', '0', 'admin', '2021-12-12 13:47:14', '', null, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES ('10', '系统状态', 'sys_common_status', '0', 'admin', '2021-12-12 13:47:14', '', null, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES ('11', '文章来源', 'sys_article_source', '0', 'admin', '2022-02-15 17:26:13', 'admin', '2022-07-12 17:18:42', '文章版权说明');
INSERT INTO `sys_dict_type` VALUES ('12', '文章类型', 'sys_article_type', '0', 'admin', '2022-02-15 17:26:28', 'admin', '2022-07-12 17:18:48', '文章类型');
INSERT INTO `sys_dict_type` VALUES ('13', '服务条目', 'sys_service_type', '0', 'admin', '2022-02-15 17:27:22', 'admin', '2022-07-12 17:19:33', '通用说明设置');
INSERT INTO `sys_dict_type` VALUES ('14', '反馈类型', 'sys_feedback_type', '0', 'admin', '2022-02-28 17:09:24', 'admin', '2022-07-12 17:19:57', '反馈类型说明');
INSERT INTO `sys_dict_type` VALUES ('15', '广告类别', 'mall_advert_type', '0', 'admin', '2022-05-02 16:57:05', 'admin', '2022-07-12 17:20:12', 'APP广告位');
INSERT INTO `sys_dict_type` VALUES ('16', '是否上架', 'mall_goods_shelf', '0', 'admin', '2022-05-02 17:40:31', 'admin', '2022-07-12 17:20:20', '上架标识');
INSERT INTO `sys_dict_type` VALUES ('17', '订单状态', 'mall_order_status', '0', 'admin', '2022-06-12 16:42:45', 'admin', '2022-07-12 17:20:25', '订单状态');
INSERT INTO `sys_dict_type` VALUES ('18', '支付方式', 'mall_pay_method', '0', 'admin', '2022-06-19 14:55:21', 'admin', '2022-07-12 17:20:29', '支付方式');
INSERT INTO `sys_dict_type` VALUES ('19', '支付状态', 'mall_pay_status', '0', 'admin', '2022-06-19 15:02:00', 'admin', '2022-07-12 17:20:42', '支付状态');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2021-12-12 13:47:16', '', null, '');
INSERT INTO `sys_job` VALUES ('2', '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2021-12-12 13:47:16', '', null, '');
INSERT INTO `sys_job` VALUES ('3', '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2021-12-12 13:47:16', '', null, '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度日志表';

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统访问记录';

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(11) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(11) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(11) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1281 DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '0', '997', 'system', null, '', '1', '0', 'M', '0', '0', '', 'system', 'admin', '2021-12-12 13:47:07', 'admin', '2022-02-15 17:08:08', '系统管理目录');
INSERT INTO `sys_menu` VALUES ('2', '系统监控', '0', '997', 'monitor', null, '', '1', '0', 'M', '0', '0', '', 'monitor', 'admin', '2021-12-12 13:47:07', 'admin', '2022-02-15 17:01:51', '系统监控目录');
INSERT INTO `sys_menu` VALUES ('3', '系统工具', '0', '998', 'tool', null, '', '1', '0', 'M', '0', '0', '', 'tool', 'admin', '2021-12-12 13:47:07', 'admin', '2022-02-15 17:01:45', '系统工具目录');
INSERT INTO `sys_menu` VALUES ('4', '开源官网', '0', '999', 'https://sourcebyte.vip', null, '', '0', '0', 'M', '0', '0', '', 'guide', 'admin', '2021-12-12 13:47:07', 'admin', '2023-02-10 19:38:54', '若依官网地址');
INSERT INTO `sys_menu` VALUES ('100', '用户管理', '1', '1', 'user', 'system/user/index', '', '1', '0', 'C', '0', '0', 'system:user:list', 'user', 'admin', '2021-12-12 13:47:07', '', null, '用户管理菜单');
INSERT INTO `sys_menu` VALUES ('101', '角色管理', '1', '2', 'role', 'system/role/index', '', '1', '0', 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2021-12-12 13:47:07', '', null, '角色管理菜单');
INSERT INTO `sys_menu` VALUES ('102', '菜单管理', '1', '3', 'menu', 'system/menu/index', '', '1', '0', 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2021-12-12 13:47:07', '', null, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES ('103', '部门管理', '1', '4', 'dept', 'system/dept/index', '', '1', '0', 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2021-12-12 13:47:07', '', null, '部门管理菜单');
INSERT INTO `sys_menu` VALUES ('104', '岗位管理', '1', '5', 'post', 'system/post/index', '', '1', '0', 'C', '1', '1', 'system:post:list', 'post', 'admin', '2021-12-12 13:47:07', 'admin', '2022-05-02 21:12:59', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES ('105', '字典管理', '1', '6', 'dict', 'system/dict/index', '', '1', '0', 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2021-12-12 13:47:07', '', null, '字典管理菜单');
INSERT INTO `sys_menu` VALUES ('106', '参数设置', '1', '7', 'config', 'system/config/index', '', '1', '0', 'C', '1', '0', 'system:config:list', 'edit', 'admin', '2021-12-12 13:47:07', 'admin', '2022-04-11 21:56:09', '参数设置菜单');
INSERT INTO `sys_menu` VALUES ('107', '通知公告', '1', '8', 'notice', 'system/notice/index', '', '1', '0', 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2021-12-12 13:47:07', 'admin', '2022-02-23 15:21:23', '通知公告菜单');
INSERT INTO `sys_menu` VALUES ('108', '日志管理', '1', '9', 'log', '', '', '1', '0', 'M', '0', '0', '', 'log', 'admin', '2021-12-12 13:47:07', '', null, '日志管理菜单');
INSERT INTO `sys_menu` VALUES ('109', '在线用户', '2', '1', 'online', 'monitor/online/index', '', '1', '0', 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2021-12-12 13:47:07', '', null, '在线用户菜单');
INSERT INTO `sys_menu` VALUES ('110', '定时任务', '2', '2', 'job', 'monitor/job/index', '', '1', '0', 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2021-12-12 13:47:07', '', null, '定时任务菜单');
INSERT INTO `sys_menu` VALUES ('111', '数据监控', '2', '3', 'druid', 'monitor/druid/index', '', '1', '0', 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2021-12-12 13:47:07', '', null, '数据监控菜单');
INSERT INTO `sys_menu` VALUES ('112', '服务监控', '2', '4', 'server', 'monitor/server/index', '', '1', '0', 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2021-12-12 13:47:07', '', null, '服务监控菜单');
INSERT INTO `sys_menu` VALUES ('113', '缓存监控', '2', '5', 'cache', 'monitor/cache/index', '', '1', '0', 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2021-12-12 13:47:07', '', null, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES ('114', '表单构建', '3', '1', 'build', 'tool/build/index', '', '1', '0', 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2021-12-12 13:47:08', '', null, '表单构建菜单');
INSERT INTO `sys_menu` VALUES ('115', '代码生成', '3', '2', 'gen', 'tool/gen/index', '', '1', '0', 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2021-12-12 13:47:08', '', null, '代码生成菜单');
INSERT INTO `sys_menu` VALUES ('116', '系统接口', '3', '3', 'swagger', 'tool/swagger/index', '', '1', '0', 'C', '1', '1', 'tool:swagger:list', 'swagger', 'admin', '2021-12-12 13:47:08', 'admin', '2022-02-15 16:58:37', '系统接口菜单');
INSERT INTO `sys_menu` VALUES ('500', '操作日志', '108', '1', 'operlog', 'monitor/operlog/index', '', '1', '0', 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2021-12-12 13:47:08', '', null, '操作日志菜单');
INSERT INTO `sys_menu` VALUES ('501', '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', '', '1', '0', 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2021-12-12 13:47:08', '', null, '登录日志菜单');
INSERT INTO `sys_menu` VALUES ('1001', '用户查询', '100', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:user:query', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1002', '用户新增', '100', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:user:add', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1003', '用户修改', '100', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:user:edit', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1004', '用户删除', '100', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:user:remove', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1005', '用户导出', '100', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:user:export', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1006', '用户导入', '100', '6', '', '', '', '1', '0', 'F', '0', '0', 'system:user:import', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1007', '重置密码', '100', '7', '', '', '', '1', '0', 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1008', '角色查询', '101', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:role:query', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1009', '角色新增', '101', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:role:add', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1010', '角色修改', '101', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:role:edit', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1011', '角色删除', '101', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:role:remove', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1012', '角色导出', '101', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:role:export', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1013', '菜单查询', '102', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:query', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1014', '菜单新增', '102', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:add', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1015', '菜单修改', '102', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1016', '菜单删除', '102', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1017', '部门查询', '103', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:query', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1018', '部门新增', '103', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:add', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1019', '部门修改', '103', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1020', '部门删除', '103', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1021', '岗位查询', '104', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:post:query', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1022', '岗位新增', '104', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:post:add', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1023', '岗位修改', '104', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:post:edit', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1024', '岗位删除', '104', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:post:remove', '#', 'admin', '2021-12-12 13:47:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1025', '岗位导出', '104', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:post:export', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1026', '字典查询', '105', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:query', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1027', '字典新增', '105', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:add', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1028', '字典修改', '105', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1029', '字典删除', '105', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1030', '字典导出', '105', '5', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:export', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1031', '参数查询', '106', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:query', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1032', '参数新增', '106', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:add', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1033', '参数修改', '106', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:edit', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1034', '参数删除', '106', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:remove', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1035', '参数导出', '106', '5', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:export', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1036', '公告查询', '107', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:query', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1037', '公告新增', '107', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:add', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1038', '公告修改', '107', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1039', '公告删除', '107', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1040', '操作查询', '500', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1041', '操作删除', '500', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1042', '日志导出', '500', '4', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1043', '登录查询', '501', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1044', '登录删除', '501', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1045', '日志导出', '501', '3', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1046', '在线查询', '109', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1047', '批量强退', '109', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1048', '单条强退', '109', '3', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1049', '任务查询', '110', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1050', '任务新增', '110', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1051', '任务修改', '110', '3', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1052', '任务删除', '110', '4', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1053', '状态修改', '110', '5', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1054', '任务导出', '110', '7', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1055', '生成查询', '115', '1', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1056', '生成修改', '115', '2', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1057', '生成删除', '115', '3', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1058', '导入代码', '115', '2', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1059', '预览代码', '115', '4', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2021-12-12 13:47:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1060', '生成代码', '115', '5', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2021-12-12 13:47:10', '', null, '');
INSERT INTO `sys_menu` VALUES ('1061', '内容管理', '0', '2', '/cms', null, null, '1', '0', 'M', '0', '0', null, 'build', 'admin', '2022-02-15 17:03:02', '', null, '');
INSERT INTO `sys_menu` VALUES ('1063', '服务条目', '1061', '3', 'item', 'system/item/index', null, '1', '0', 'C', '0', '0', 'system:item:list', 'server', 'admin', '2022-02-15 17:06:29', 'admin', '2022-07-11 09:17:01', '');
INSERT INTO `sys_menu` VALUES ('1080', '消息管理', '1061', '4', 'message', null, null, '1', '0', 'M', '0', '0', '', 'message', 'admin', '2022-02-16 09:50:37', 'admin', '2022-07-11 09:17:08', '');
INSERT INTO `sys_menu` VALUES ('1087', '私域商城', '0', '6', 'mall', null, null, '1', '0', 'M', '0', '0', '', 'shopping', 'admin', '2022-02-23 14:29:26', 'admin', '2022-05-22 13:36:35', '');
INSERT INTO `sys_menu` VALUES ('1088', '商品管理', '1087', '1', 'goods', null, null, '1', '0', 'M', '0', '0', '', 'server', 'admin', '2022-02-23 14:29:47', 'admin', '2022-05-02 21:45:49', '');
INSERT INTO `sys_menu` VALUES ('1091', '评价管理', '1088', '3', '3', null, null, '1', '0', 'M', '1', '1', '', 'build', 'admin', '2022-02-23 14:46:27', 'admin', '2022-05-30 17:15:38', '');
INSERT INTO `sys_menu` VALUES ('1094', '促销管理', '1087', '3', '3', null, null, '1', '0', 'M', '1', '1', '', '#', 'admin', '2022-02-23 14:47:50', 'admin', '2022-05-30 17:14:38', '');
INSERT INTO `sys_menu` VALUES ('1095', '优惠卷', '1094', '1', '1', null, null, '1', '0', 'M', '0', '0', '', 'build', 'admin', '2022-02-23 14:48:06', 'admin', '2022-05-22 14:00:49', '');
INSERT INTO `sys_menu` VALUES ('1097', '财务管理', '1087', '4', '4', null, null, '1', '0', 'M', '1', '1', '', '#', 'admin', '2022-02-23 14:51:40', 'admin', '2022-05-30 17:14:58', '');
INSERT INTO `sys_menu` VALUES ('1098', '资金记录', '1097', '1', '1', null, null, '1', '0', 'M', '0', '0', '', 'build', 'admin', '2022-02-23 14:51:53', 'admin', '2022-05-22 14:00:56', '');
INSERT INTO `sys_menu` VALUES ('1099', '广告管理', '1087', '5', 'advert', null, null, '1', '0', 'M', '0', '0', '', 'chart', 'admin', '2022-02-23 14:52:19', 'admin', '2022-05-02 21:46:31', '');
INSERT INTO `sys_menu` VALUES ('1102', '地址管理', '1087', '6', '6', null, null, '1', '0', 'M', '0', '0', '', 'log', 'admin', '2022-02-23 14:55:34', 'admin', '2022-05-22 13:55:55', '');
INSERT INTO `sys_menu` VALUES ('1119', '查询', '1063', '1', '', null, null, '1', '0', 'F', '0', '0', 'system:item:query', '#', 'admin', '2022-02-25 21:39:57', '', null, '');
INSERT INTO `sys_menu` VALUES ('1120', '新增', '1063', '2', '', null, null, '1', '0', 'F', '0', '0', 'system:item:add', '#', 'admin', '2022-02-25 21:40:27', '', null, '');
INSERT INTO `sys_menu` VALUES ('1121', '修改', '1063', '3', '', null, null, '1', '0', 'F', '0', '0', 'system:item:edit', '#', 'admin', '2022-02-25 21:40:50', '', null, '');
INSERT INTO `sys_menu` VALUES ('1122', '删除', '1063', '4', '', null, null, '1', '0', 'F', '0', '0', 'system:item:remove', '#', 'admin', '2022-02-25 21:41:11', '', null, '');
INSERT INTO `sys_menu` VALUES ('1123', '导出', '1063', '5', '', null, null, '1', '0', 'F', '0', '0', 'system:item:export', '#', 'admin', '2022-02-25 21:41:32', '', null, '');
INSERT INTO `sys_menu` VALUES ('1129', '意见反馈', '1080', '1', 'feedback', 'system/feedback/index', null, '1', '0', 'C', '0', '0', 'system:feedback:list', 'education', 'admin', '2022-03-01 19:53:18', 'admin', '2022-03-01 21:11:19', '意见反馈菜单');
INSERT INTO `sys_menu` VALUES ('1130', '意见反馈查询', '1129', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:feedback:query', '#', 'admin', '2022-03-01 19:53:18', '', null, '');
INSERT INTO `sys_menu` VALUES ('1131', '意见反馈新增', '1129', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:feedback:add', '#', 'admin', '2022-03-01 19:53:18', '', null, '');
INSERT INTO `sys_menu` VALUES ('1132', '意见反馈修改', '1129', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:feedback:edit', '#', 'admin', '2022-03-01 19:53:18', '', null, '');
INSERT INTO `sys_menu` VALUES ('1133', '意见反馈删除', '1129', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:feedback:remove', '#', 'admin', '2022-03-01 19:53:18', '', null, '');
INSERT INTO `sys_menu` VALUES ('1134', '意见反馈导出', '1129', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:feedback:export', '#', 'admin', '2022-03-01 19:53:18', '', null, '');
INSERT INTO `sys_menu` VALUES ('1135', '文章管理', '1061', '1', 'article', 'system/article/index', null, '1', '0', 'C', '0', '0', 'system:article:list', 'clipboard', 'admin', '2022-03-01 21:02:47', 'admin', '2022-03-01 21:11:08', '文章管理菜单');
INSERT INTO `sys_menu` VALUES ('1136', '文章管理查询', '1135', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:article:query', '#', 'admin', '2022-03-01 21:02:47', '', null, '');
INSERT INTO `sys_menu` VALUES ('1137', '文章管理新增', '1135', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:article:add', '#', 'admin', '2022-03-01 21:02:47', '', null, '');
INSERT INTO `sys_menu` VALUES ('1138', '文章管理修改', '1135', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:article:edit', '#', 'admin', '2022-03-01 21:02:47', '', null, '');
INSERT INTO `sys_menu` VALUES ('1139', '文章管理删除', '1135', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:article:remove', '#', 'admin', '2022-03-01 21:02:47', '', null, '');
INSERT INTO `sys_menu` VALUES ('1140', '文章管理导出', '1135', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:article:export', '#', 'admin', '2022-03-01 21:02:47', '', null, '');
INSERT INTO `sys_menu` VALUES ('1143', '友情链接', '1061', '2', 'link', 'system/link/index', null, '1', '0', 'C', '0', '0', 'system:link:list', 'link', 'admin', '2022-03-12 22:10:04', 'admin', '2022-07-11 09:16:55', '友情链接菜单');
INSERT INTO `sys_menu` VALUES ('1144', '友情链接查询', '1143', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:link:query', '#', 'admin', '2022-03-12 22:10:04', '', null, '');
INSERT INTO `sys_menu` VALUES ('1145', '友情链接新增', '1143', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:link:add', '#', 'admin', '2022-03-12 22:10:04', '', null, '');
INSERT INTO `sys_menu` VALUES ('1146', '友情链接修改', '1143', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:link:edit', '#', 'admin', '2022-03-12 22:10:05', '', null, '');
INSERT INTO `sys_menu` VALUES ('1147', '友情链接删除', '1143', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:link:remove', '#', 'admin', '2022-03-12 22:10:05', '', null, '');
INSERT INTO `sys_menu` VALUES ('1148', '友情链接导出', '1143', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:link:export', '#', 'admin', '2022-03-12 22:10:05', '', null, '');
INSERT INTO `sys_menu` VALUES ('1150', '房源管理', '1162', '1', 'houseRoom', 'house/houseRoom/index', null, '1', '0', 'C', '0', '0', 'system:houseRoom:list', 'cu-room', 'admin', '2022-03-27 19:54:35', 'admin', '2022-04-26 17:42:12', '房源信息菜单');
INSERT INTO `sys_menu` VALUES ('1151', '房源信息查询', '1150', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseRoom:query', '#', 'admin', '2022-03-27 19:54:35', '', null, '');
INSERT INTO `sys_menu` VALUES ('1152', '房源信息新增', '1150', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseRoom:add', '#', 'admin', '2022-03-27 19:54:35', '', null, '');
INSERT INTO `sys_menu` VALUES ('1153', '房源信息修改', '1150', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseRoom:edit', '#', 'admin', '2022-03-27 19:54:35', '', null, '');
INSERT INTO `sys_menu` VALUES ('1154', '房源信息删除', '1150', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseRoom:remove', '#', 'admin', '2022-03-27 19:54:35', '', null, '');
INSERT INTO `sys_menu` VALUES ('1155', '房源信息导出', '1150', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseRoom:export', '#', 'admin', '2022-03-27 19:54:36', '', null, '');
INSERT INTO `sys_menu` VALUES ('1156', '小区管理', '1162', '2', 'houseVillage', 'house/houseVillage/index', null, '1', '0', 'C', '0', '0', 'system:houseVillage:list', 'cu-village', 'admin', '2022-03-27 19:54:39', 'admin', '2022-07-10 22:31:19', '房源小区菜单');
INSERT INTO `sys_menu` VALUES ('1157', '房源小区查询', '1156', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseVillage:query', '#', 'admin', '2022-03-27 19:54:39', '', null, '');
INSERT INTO `sys_menu` VALUES ('1158', '房源小区新增', '1156', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseVillage:add', '#', 'admin', '2022-03-27 19:54:39', '', null, '');
INSERT INTO `sys_menu` VALUES ('1159', '房源小区修改', '1156', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseVillage:edit', '#', 'admin', '2022-03-27 19:54:39', '', null, '');
INSERT INTO `sys_menu` VALUES ('1160', '房源小区删除', '1156', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseVillage:remove', '#', 'admin', '2022-03-27 19:54:39', '', null, '');
INSERT INTO `sys_menu` VALUES ('1161', '房源小区导出', '1156', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:houseVillage:export', '#', 'admin', '2022-03-27 19:54:39', '', null, '');
INSERT INTO `sys_menu` VALUES ('1162', '便民租房', '0', '3', '/house', null, null, '1', '0', 'M', '0', '0', '', 'cu-house', 'admin', '2022-03-27 19:57:04', 'admin', '2022-04-26 17:41:55', '');
INSERT INTO `sys_menu` VALUES ('1163', '历史消息', '1080', '2', 'msg', 'system/msg/index', null, '1', '0', 'C', '0', '0', 'system:msg:list', 'message', 'admin', '2022-04-03 20:40:01', 'admin', '2022-07-11 09:17:16', '消息管理菜单');
INSERT INTO `sys_menu` VALUES ('1164', '消息管理查询', '1163', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:msg:query', '#', 'admin', '2022-04-03 20:40:01', '', null, '');
INSERT INTO `sys_menu` VALUES ('1165', '消息管理新增', '1163', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:msg:add', '#', 'admin', '2022-04-03 20:40:01', '', null, '');
INSERT INTO `sys_menu` VALUES ('1166', '消息管理修改', '1163', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:msg:edit', '#', 'admin', '2022-04-03 20:40:01', '', null, '');
INSERT INTO `sys_menu` VALUES ('1167', '消息管理删除', '1163', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:msg:remove', '#', 'admin', '2022-04-03 20:40:01', '', null, '');
INSERT INTO `sys_menu` VALUES ('1168', '消息管理导出', '1163', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:msg:export', '#', 'admin', '2022-04-03 20:40:01', '', null, '');
INSERT INTO `sys_menu` VALUES ('1220', '广告管理', '1099', '2', 'advert', 'mall/advert/index', null, '1', '0', 'C', '0', '0', 'system:advert:list', 'build', 'admin', '2022-05-02 16:51:53', 'admin', '2022-05-02 16:55:28', '广告管理菜单');
INSERT INTO `sys_menu` VALUES ('1221', '广告管理查询', '1220', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:advert:query', '#', 'admin', '2022-05-02 16:51:53', '', null, '');
INSERT INTO `sys_menu` VALUES ('1222', '广告管理新增', '1220', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:advert:add', '#', 'admin', '2022-05-02 16:51:54', '', null, '');
INSERT INTO `sys_menu` VALUES ('1223', '广告管理修改', '1220', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:advert:edit', '#', 'admin', '2022-05-02 16:51:54', '', null, '');
INSERT INTO `sys_menu` VALUES ('1224', '广告管理删除', '1220', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:advert:remove', '#', 'admin', '2022-05-02 16:51:54', '', null, '');
INSERT INTO `sys_menu` VALUES ('1225', '广告管理导出', '1220', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:advert:export', '#', 'admin', '2022-05-02 16:51:54', '', null, '');
INSERT INTO `sys_menu` VALUES ('1226', '导航管理', '1099', '1', 'navigate', 'mall/navigate/index', null, '1', '0', 'C', '0', '0', 'system:navigate:list', 'build', 'admin', '2022-05-02 16:52:08', 'admin', '2022-05-02 16:55:02', '导航管理菜单');
INSERT INTO `sys_menu` VALUES ('1227', '导航管理查询', '1226', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:navigate:query', '#', 'admin', '2022-05-02 16:52:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1228', '导航管理新增', '1226', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:navigate:add', '#', 'admin', '2022-05-02 16:52:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1229', '导航管理修改', '1226', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:navigate:edit', '#', 'admin', '2022-05-02 16:52:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1230', '导航管理删除', '1226', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:navigate:remove', '#', 'admin', '2022-05-02 16:52:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('1231', '导航管理导出', '1226', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:navigate:export', '#', 'admin', '2022-05-02 16:52:09', '', null, '');
INSERT INTO `sys_menu` VALUES ('1232', '分类管理', '1088', '2', 'classify', 'mall/classify/index', null, '1', '0', 'C', '0', '0', 'system:classify:list', 'build', 'admin', '2022-05-02 17:45:26', 'admin', '2022-05-02 17:47:09', '分类管理菜单');
INSERT INTO `sys_menu` VALUES ('1233', '分类管理查询', '1232', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:classify:query', '#', 'admin', '2022-05-02 17:45:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1234', '分类管理新增', '1232', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:classify:add', '#', 'admin', '2022-05-02 17:45:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1235', '分类管理修改', '1232', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:classify:edit', '#', 'admin', '2022-05-02 17:45:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1236', '分类管理删除', '1232', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:classify:remove', '#', 'admin', '2022-05-02 17:45:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1237', '分类管理导出', '1232', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:classify:export', '#', 'admin', '2022-05-02 17:45:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1238', '商品管理', '1088', '1', 'goods', 'mall/goods/index', null, '1', '0', 'C', '0', '0', 'system:goods:list', 'build', 'admin', '2022-05-02 17:45:39', 'admin', '2022-05-02 17:46:55', '商品管理菜单');
INSERT INTO `sys_menu` VALUES ('1239', '商品管理查询', '1238', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:goods:query', '#', 'admin', '2022-05-02 17:45:40', '', null, '');
INSERT INTO `sys_menu` VALUES ('1240', '商品管理新增', '1238', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:goods:add', '#', 'admin', '2022-05-02 17:45:40', '', null, '');
INSERT INTO `sys_menu` VALUES ('1241', '商品管理修改', '1238', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:goods:edit', '#', 'admin', '2022-05-02 17:45:40', '', null, '');
INSERT INTO `sys_menu` VALUES ('1242', '商品管理删除', '1238', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:goods:remove', '#', 'admin', '2022-05-02 17:45:40', '', null, '');
INSERT INTO `sys_menu` VALUES ('1243', '商品管理导出', '1238', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:goods:export', '#', 'admin', '2022-05-02 17:45:40', '', null, '');
INSERT INTO `sys_menu` VALUES ('1245', '订单满减', '1094', '2', '2', null, null, '1', '0', 'M', '0', '0', null, 'build', 'admin', '2022-05-22 14:00:40', '', null, '');
INSERT INTO `sys_menu` VALUES ('1246', '收货地址', '1102', '1', 'address', 'mall/address/index', null, '1', '0', 'C', '0', '0', 'system:address:list', 'build', 'admin', '2022-06-19 16:19:45', 'admin', '2022-06-19 16:22:29', '收货地址菜单');
INSERT INTO `sys_menu` VALUES ('1247', '收货地址查询', '1246', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:address:query', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1248', '收货地址新增', '1246', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:address:add', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1249', '收货地址修改', '1246', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:address:edit', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1250', '收货地址删除', '1246', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:address:remove', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1251', '收货地址导出', '1246', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:address:export', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1252', '订单信息', '1087', '2', 'order', 'mall/order/index', null, '1', '0', 'C', '0', '0', 'system:order:list', 'list', 'admin', '2022-06-19 16:19:46', 'admin', '2022-06-19 16:21:56', '订单信息菜单');
INSERT INTO `sys_menu` VALUES ('1253', '订单信息查询', '1252', '1', '#', '', null, '1', '0', 'F', '0', '0', 'system:order:query', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1254', '订单信息新增', '1252', '2', '#', '', null, '1', '0', 'F', '0', '0', 'system:order:add', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1255', '订单信息修改', '1252', '3', '#', '', null, '1', '0', 'F', '0', '0', 'system:order:edit', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1256', '订单信息删除', '1252', '4', '#', '', null, '1', '0', 'F', '0', '0', 'system:order:remove', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1257', '订单信息导出', '1252', '5', '#', '', null, '1', '0', 'F', '0', '0', 'system:order:export', '#', 'admin', '2022-06-19 16:19:46', '', null, '');
INSERT INTO `sys_menu` VALUES ('1258', '房源更多操作', '1150', '6', '', null, null, '1', '0', 'F', '0', '0', 'system:houseRoom:more', '#', 'admin', '2022-07-10 19:54:51', 'admin', '2022-07-10 19:55:29', '');
INSERT INTO `sys_menu` VALUES ('1308', '成员管理', '1061', 5, 'developer', 'system/developer/index', NULL, 1, 0, 'C', '0', '0', 'system:developer:list', 'user', 'admin', '2024-04-27 09:20:15', 'admin', '2024-04-27 09:21:32', '成员管理菜单');
INSERT INTO `sys_menu` VALUES ('1309', '成员管理查询', '1308', 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:developer:query', '#', 'admin', '2024-04-27 09:20:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES ('1310', '成员管理新增', '1308', 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:developer:add', '#', 'admin', '2024-04-27 09:20:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES ('1311', '成员管理修改', '1308', 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:developer:edit', '#', 'admin', '2024-04-27 09:20:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES ('1312', '成员管理删除', '1308', 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:developer:remove', '#', 'admin', '2024-04-27 09:20:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES ('1315', '成员管理导出', '1308', 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:developer:export', '#', 'admin', '2024-04-27 11:28:44', '', NULL, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('1', '需要技术支持的朋友可以联系我们', '2', 0x3C703EE68891E4BBACE4B893E6B3A8E68F90E4BE9BE4B893E4B89AE79A84E8BDAFE4BBB6E4BAA7E59381E5928CE68A80E69CAFE38082E4B8BAE59088E4BD9CE4BC99E4BCB4E68F90E4BE9BE5AE89E585A8E58FAFE99DA0E79A84E8BDAFE4BBB6E4BAA7E59381E4B88EE8A7A3E586B3E696B9E6A188EFBC8CE585B1E5908CE5B8AEE58AA9E7BB88E7ABAFE794A8E688B7E5AE9EE78EB0E4B89AE58AA1E5889BE696B0E38081E5BFABE9809FE58F91E5B195E38082203C2F703E, '0', 'admin', '2021-12-12 13:47:16', 'admin', '2022-09-20 16:54:39', '技术支持');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志记录';

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='岗位信息表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES ('1', 'ceo', '董事长', '1', '0', 'admin', '2021-12-12 13:47:06', '', null, '');
INSERT INTO `sys_post` VALUES ('2', 'manager', '项目经理', '2', '0', 'admin', '2021-12-12 13:47:06', 'admin', '2022-03-01 10:39:43', '');
INSERT INTO `sys_post` VALUES ('3', 'hr', '人力资源', '3', '0', 'admin', '2021-12-12 13:47:06', '', null, '');
INSERT INTO `sys_post` VALUES ('4', 'user', '普通员工', '4', '0', 'admin', '2021-12-12 13:47:06', '', null, '');
INSERT INTO `sys_post` VALUES ('5', 'dev', '开发工程师', '5', '0', 'admin', '2022-02-15 16:59:52', '', null, null);
INSERT INTO `sys_post` VALUES ('6', 'test', '测试工程师', '6', '0', 'admin', '2022-02-15 17:00:10', '', null, null);
INSERT INTO `sys_post` VALUES ('7', 'producter', '产品经理', '7', '0', 'admin', '2022-02-23 15:25:17', 'admin', '2022-03-01 10:39:28', null);
INSERT INTO `sys_post` VALUES ('8', 'register ', '注册用户', '10', '0', 'admin', '2022-03-20 18:22:20', '', null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', '1', '1', '1', '1', '0', '0', 'admin', '2021-12-12 13:47:06', '', null, '超级管理员');
INSERT INTO `sys_role` VALUES ('2', '体验用户', 'common', '2', '1', '1', '1', '0', '2', 'admin', '2021-12-12 13:47:06', 'admin', '2022-04-25 19:40:36', '普通角色');
INSERT INTO `sys_role` VALUES ('3', '查看用户', 'product', '3', '1', '1', '1', '0', '0', 'admin', '2022-02-23 15:29:08', 'admin', '2023-02-18 16:12:14', '演示账户');
INSERT INTO `sys_role` VALUES ('4', '开发工程师', 'dev', '4', '1', '0', '0', '0', '2', 'admin', '2022-02-23 15:36:30', 'admin', '2022-03-18 23:13:07', null);
INSERT INTO `sys_role` VALUES ('5', '注册用户', 'register ', '4', '1', '1', '1', '0', '0', 'admin', '2022-03-20 18:21:22', '', null, null);
INSERT INTO `sys_role` VALUES ('6', '租房用户', 'house', '5', '1', '1', '1', '0', '0', 'admin', '2022-04-18 14:49:55', 'admin', '2022-10-01 20:51:22', null);
INSERT INTO `sys_role` VALUES ('7', '电商用户', 'mall', '6', '1', '1', '1', '0', '0', 'admin', '2022-05-02 21:35:12', 'admin', '2022-06-19 16:47:02', null);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('3', '1');
INSERT INTO `sys_role_menu` VALUES ('3', '4');
INSERT INTO `sys_role_menu` VALUES ('3', '103');
INSERT INTO `sys_role_menu` VALUES ('3', '107');
INSERT INTO `sys_role_menu` VALUES ('3', '108');
INSERT INTO `sys_role_menu` VALUES ('3', '500');
INSERT INTO `sys_role_menu` VALUES ('3', '501');
INSERT INTO `sys_role_menu` VALUES ('3', '1017');
INSERT INTO `sys_role_menu` VALUES ('3', '1036');
INSERT INTO `sys_role_menu` VALUES ('3', '1040');
INSERT INTO `sys_role_menu` VALUES ('3', '1043');
INSERT INTO `sys_role_menu` VALUES ('3', '1061');
INSERT INTO `sys_role_menu` VALUES ('3', '1063');
INSERT INTO `sys_role_menu` VALUES ('3', '1087');
INSERT INTO `sys_role_menu` VALUES ('3', '1088');
INSERT INTO `sys_role_menu` VALUES ('3', '1099');
INSERT INTO `sys_role_menu` VALUES ('3', '1102');
INSERT INTO `sys_role_menu` VALUES ('3', '1119');
INSERT INTO `sys_role_menu` VALUES ('3', '1135');
INSERT INTO `sys_role_menu` VALUES ('3', '1136');
INSERT INTO `sys_role_menu` VALUES ('3', '1143');
INSERT INTO `sys_role_menu` VALUES ('3', '1144');
INSERT INTO `sys_role_menu` VALUES ('3', '1150');
INSERT INTO `sys_role_menu` VALUES ('3', '1151');
INSERT INTO `sys_role_menu` VALUES ('3', '1156');
INSERT INTO `sys_role_menu` VALUES ('3', '1157');
INSERT INTO `sys_role_menu` VALUES ('3', '1162');
INSERT INTO `sys_role_menu` VALUES ('3', '1220');
INSERT INTO `sys_role_menu` VALUES ('3', '1221');
INSERT INTO `sys_role_menu` VALUES ('3', '1222');
INSERT INTO `sys_role_menu` VALUES ('3', '1225');
INSERT INTO `sys_role_menu` VALUES ('3', '1226');
INSERT INTO `sys_role_menu` VALUES ('3', '1227');
INSERT INTO `sys_role_menu` VALUES ('3', '1228');
INSERT INTO `sys_role_menu` VALUES ('3', '1231');
INSERT INTO `sys_role_menu` VALUES ('3', '1232');
INSERT INTO `sys_role_menu` VALUES ('3', '1233');
INSERT INTO `sys_role_menu` VALUES ('3', '1234');
INSERT INTO `sys_role_menu` VALUES ('3', '1237');
INSERT INTO `sys_role_menu` VALUES ('3', '1238');
INSERT INTO `sys_role_menu` VALUES ('3', '1239');
INSERT INTO `sys_role_menu` VALUES ('3', '1240');
INSERT INTO `sys_role_menu` VALUES ('3', '1243');
INSERT INTO `sys_role_menu` VALUES ('3', '1246');
INSERT INTO `sys_role_menu` VALUES ('3', '1247');
INSERT INTO `sys_role_menu` VALUES ('3', '1248');
INSERT INTO `sys_role_menu` VALUES ('3', '1251');
INSERT INTO `sys_role_menu` VALUES ('3', '1252');
INSERT INTO `sys_role_menu` VALUES ('3', '1253');
INSERT INTO `sys_role_menu` VALUES ('3', '1254');
INSERT INTO `sys_role_menu` VALUES ('3', '1257');
INSERT INTO `sys_role_menu` VALUES ('5', '4');
INSERT INTO `sys_role_menu` VALUES ('6', '1150');
INSERT INTO `sys_role_menu` VALUES ('6', '1151');
INSERT INTO `sys_role_menu` VALUES ('6', '1152');
INSERT INTO `sys_role_menu` VALUES ('6', '1153');
INSERT INTO `sys_role_menu` VALUES ('6', '1154');
INSERT INTO `sys_role_menu` VALUES ('6', '1155');
INSERT INTO `sys_role_menu` VALUES ('6', '1156');
INSERT INTO `sys_role_menu` VALUES ('6', '1157');
INSERT INTO `sys_role_menu` VALUES ('6', '1158');
INSERT INTO `sys_role_menu` VALUES ('6', '1159');
INSERT INTO `sys_role_menu` VALUES ('6', '1160');
INSERT INTO `sys_role_menu` VALUES ('6', '1161');
INSERT INTO `sys_role_menu` VALUES ('6', '1162');
INSERT INTO `sys_role_menu` VALUES ('6', '1258');
INSERT INTO `sys_role_menu` VALUES ('7', '1087');
INSERT INTO `sys_role_menu` VALUES ('7', '1088');
INSERT INTO `sys_role_menu` VALUES ('7', '1099');
INSERT INTO `sys_role_menu` VALUES ('7', '1102');
INSERT INTO `sys_role_menu` VALUES ('7', '1220');
INSERT INTO `sys_role_menu` VALUES ('7', '1221');
INSERT INTO `sys_role_menu` VALUES ('7', '1222');
INSERT INTO `sys_role_menu` VALUES ('7', '1223');
INSERT INTO `sys_role_menu` VALUES ('7', '1224');
INSERT INTO `sys_role_menu` VALUES ('7', '1225');
INSERT INTO `sys_role_menu` VALUES ('7', '1226');
INSERT INTO `sys_role_menu` VALUES ('7', '1227');
INSERT INTO `sys_role_menu` VALUES ('7', '1228');
INSERT INTO `sys_role_menu` VALUES ('7', '1229');
INSERT INTO `sys_role_menu` VALUES ('7', '1230');
INSERT INTO `sys_role_menu` VALUES ('7', '1231');
INSERT INTO `sys_role_menu` VALUES ('7', '1232');
INSERT INTO `sys_role_menu` VALUES ('7', '1233');
INSERT INTO `sys_role_menu` VALUES ('7', '1234');
INSERT INTO `sys_role_menu` VALUES ('7', '1235');
INSERT INTO `sys_role_menu` VALUES ('7', '1236');
INSERT INTO `sys_role_menu` VALUES ('7', '1237');
INSERT INTO `sys_role_menu` VALUES ('7', '1238');
INSERT INTO `sys_role_menu` VALUES ('7', '1239');
INSERT INTO `sys_role_menu` VALUES ('7', '1240');
INSERT INTO `sys_role_menu` VALUES ('7', '1241');
INSERT INTO `sys_role_menu` VALUES ('7', '1242');
INSERT INTO `sys_role_menu` VALUES ('7', '1243');
INSERT INTO `sys_role_menu` VALUES ('7', '1246');
INSERT INTO `sys_role_menu` VALUES ('7', '1247');
INSERT INTO `sys_role_menu` VALUES ('7', '1248');
INSERT INTO `sys_role_menu` VALUES ('7', '1249');
INSERT INTO `sys_role_menu` VALUES ('7', '1250');
INSERT INTO `sys_role_menu` VALUES ('7', '1251');
INSERT INTO `sys_role_menu` VALUES ('7', '1252');
INSERT INTO `sys_role_menu` VALUES ('7', '1253');
INSERT INTO `sys_role_menu` VALUES ('7', '1254');
INSERT INTO `sys_role_menu` VALUES ('7', '1255');
INSERT INTO `sys_role_menu` VALUES ('7', '1256');
INSERT INTO `sys_role_menu` VALUES ('7', '1257');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '103', 'admin', '詹Sir', '00', '261648947@qq.com', '18720989280', '0', '', '$2a$10$hGa2NV8FchpdxBAhqPYx/eMm2JMEdu3EhfywF0RcUa4HPOCdjGyP.', '0', '0', '117.147.27.96', '2023-02-26 16:59:17', 'admin', '2021-12-12 13:47:06', '', '2023-02-26 16:59:17', '管理员');
INSERT INTO `sys_user` VALUES ('4', '112', 'demo', '开源', '00', '7777777@qq.com', '18823688995', '0', '/profile/avatar/2023/02/25/0d4f62d6-526e-4774-be33-cf71644af3d7.jpeg', '$2a$10$8r4QPWQW2o4WTBkBHZoiLOsrXOKwlHAL9/98KCaARHMkS25lfc2Ie', '0', '0', '120.229.38.247', '2023-02-26 16:58:55', 'admin', '2022-02-23 15:26:44', 'admin', '2023-02-26 16:58:55', null);
INSERT INTO `sys_user` VALUES ('10', '112', '18720989281', '字节', '00', '', '18720989281', '0', '/profile/upload/2023/02/06/ad95c112-43ad-4a24-9b4e-22ca1cdd5b16.png', '$2a$10$qfm5jpZtkPhTbLOPesjSCOR0AWKqNuGrVZjtuFx0hGN2MS.7JIkmm', '0', '0', '36.110.161.100', '2023-02-09 18:13:26', 'admin', '2022-03-20 12:14:47', 'admin', '2023-02-09 18:13:26', null);
INSERT INTO `sys_user` VALUES ('15', '112', 'house', '租房', '00', '', '18720989283', '1', '', '$2a$10$1VHqOjt.qhWlUQ2kJ65oUuTTzIzL.fwKb5Q3Np6jSPaqDbgmKxjuK', '0', '0', '117.148.95.192', '2023-02-10 22:26:26', 'admin', '2022-04-18 14:53:06', 'admin', '2023-02-10 22:26:26', null);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES ('1', '1');
INSERT INTO `sys_user_post` VALUES ('4', '7');
INSERT INTO `sys_user_post` VALUES ('10', '5');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('4', '3');
INSERT INTO `sys_user_role` VALUES ('10', '3');
INSERT INTO `sys_user_role` VALUES ('15', '6');
