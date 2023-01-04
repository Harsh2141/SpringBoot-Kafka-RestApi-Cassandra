package com.shipment.entity;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("Practice_Shipment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {

	@PrimaryKey
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
	
}
