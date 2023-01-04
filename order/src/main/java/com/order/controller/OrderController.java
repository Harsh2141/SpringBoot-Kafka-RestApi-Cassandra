package com.order.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.order.dto.OrderDto;
import com.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${kafka.bootstrap-topic}")
	private String bootstrapTopic;
	
	/**
	 * create order and call order service end point
	 * @param orderDto
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto){
		return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllOrdersList(){
		return new ResponseEntity<>(orderService.getAllOrdersListByOrderBaseRepository(), HttpStatus.OK);
	}
	
	@PostMapping("/send")
	public ResponseEntity<?> sendMessage(){
		kafkaTemplate.send(bootstrapTopic, "hello");
		return new ResponseEntity<>("send", HttpStatus.OK);
	}
	
	public static void main(String[] args) {
		StringBuilder abc= new StringBuilder();
		for(int i =1 ;i<=100 ;i++) {
			abc.append(i + ", ");
		}
		System.out.println(abc);
	}
	
	@GetMapping("/getCallableThread")
	public ResponseEntity<?> getDataByCallableThread(){
		try {
			orderService.getDataByCallableThread();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>("Process Done", HttpStatus.OK);
	}
	
	@GetMapping("/counterTest")
	public void counterTest() {
		orderService.counterTest();
	}
}
