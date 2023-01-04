package com.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.dto.RestDto;
import com.restapi.exception.CustomException;
import com.restapi.service.RestService;

@RestController
@RequestMapping("/orderShipment")
public class RestControllerClass {
	
	@Autowired
	private RestService restService;
	
	/**
	 * create order and shipment create by rest template call api
	 * @param restDto
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<?> orderShipmentCreate(@RequestBody RestDto restDto){
//		restService.orderShipmentCreate(restDto);
		return new ResponseEntity<>("Order And Shipment Completed", HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getMessage(){
		return new ResponseEntity<>("API Working", HttpStatus.OK);
	}
	
	@GetMapping("/throw-exception")
    public ResponseEntity<?> throwException(){
        throw new CustomException("My Exception");
    }

}
