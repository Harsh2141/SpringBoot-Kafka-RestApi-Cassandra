package com.shipment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipment.dto.ShipmentDto;
import com.shipment.service.ShipmentService;

@RestController
@RequestMapping("shipment")
public class ShipmentController {
	
	@Autowired
	private ShipmentService shipmentService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createShipment(@RequestBody ShipmentDto shipmentDto){
		return new ResponseEntity<>(shipmentService.createShipment(shipmentDto), HttpStatus.OK);
	}
	
	@GetMapping("getAll")
	public ResponseEntity<?> getAllShipmentsList(){
		return new ResponseEntity<>(shipmentService.getAllShipmentsList(), HttpStatus.OK);
	}
	
	/**
	 * kafka for create ship ment
	 * @param payload
	 */
	@KafkaListener(topics = "${kafka.bootstrap-topic}", groupId = "${kafka.bootstrap-groupId}")
	public void receive(String payload) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ShipmentDto shipmentDto = objectMapper.readValue(payload, ShipmentDto.class);
			shipmentService.createShipment(shipmentDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
