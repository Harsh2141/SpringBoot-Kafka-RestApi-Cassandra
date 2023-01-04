package com.shipment.dto;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDto {

	private Long shipmentId;
	private Long orderId;
	private String shipmentKey;
	
	private String shipmentStatus;
	private String shipmentYype;
	private Long noOfItems;
	private Long itemQuantity;
	private Long tripId;
	private Date updatedDate;
	private Date createdDate;
	
	
	
	private Long userId;
	private String userAddress;
	private String orderType;
	private String orderStatus;
	private Long fcCode;
	private Long popCode;
	private Double orderValue;
}
