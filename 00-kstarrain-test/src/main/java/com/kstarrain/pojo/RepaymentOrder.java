package com.kstarrain.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RepaymentOrder implements Serializable{

	private static final long serialVersionUID = 1L;
	

	/** 订单号 */
	private String orderNo;

	private RepayOrderStatusEnum status;

	private String filePath;
	
		
	
		
}