CREATE TABLE `t_student` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `NAME` varchar(10) DEFAULT NULL COMMENT '姓名',
  `BIRTHDAY` date DEFAULT NULL COMMENT '生日',
  `MONEY` decimal(10,2) DEFAULT NULL COMMENT '余额',
  `CREATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `ALIVE_FLAG` varchar(1) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';



CREATE TABLE `t_goods` (
  `goods_id` varchar(32) NOT NULL COMMENT '商品id',
  `goods_name` varchar(32) DEFAULT '' COMMENT '商品名字',
  `stock` int(11) DEFAULT '0' COMMENT '商品库存',
  `status` varchar(1) NOT NULL DEFAULT '1' COMMENT '商品状态(1:上架  0:下架)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `alive_flag` varchar(1) DEFAULT NULL COMMENT '删除标记',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '数据版本号',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

INSERT INTO `t_goods` (`goods_id`, `goods_name`, `stock`, `status`, `create_date`, `update_date`, `alive_flag`, `version`)
VALUES ('d7fbbfd87a08408ba994dea6be435111', 'iphone8', 100, '1', '2019-1-20 12:13:44', '2019-1-22 12:02:47', '1', 1);


CREATE TABLE `t_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_code` varchar(32) DEFAULT '' COMMENT '订单编号',
  `goods_id` varchar(32) NOT NULL COMMENT '商品id',
  `goods_num` int(11) NOT NULL COMMENT '商品数量',
  `buyer_id` varchar(32) NOT NULL COMMENT '买家id',
  `trade_status` int(1) NOT NULL DEFAULT '1' COMMENT '交易状态(1:等待买家付款 2:买家已付款，等待卖家发货 3:卖家已发货，等待买家确认 4:交易成功 5:交易关闭)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `alive_flag` varchar(1) NOT NULL COMMENT '删除标记',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';