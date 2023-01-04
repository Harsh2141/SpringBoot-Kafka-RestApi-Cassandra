package com.order.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	
	private Long orderId;
	private Long userId;
	private String userAddress;
	private String orderType;
	private String orderStatus;
	private Long fcCode;
	private Long popCode;
	private Double orderValue;
	private Long noOfItems;
	private Long itemQuantity;
	private Date createdDate;
	private Date updatedDate;
	private Long shipmentId;
	private Long tripId;
	
	private String shipmentKey;
	private String shipmentStatus;
	private String shipmentYype;
}
