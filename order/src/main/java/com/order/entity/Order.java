package com.order.entity;




import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("practice_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	
	@PrimaryKey
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
	
}
