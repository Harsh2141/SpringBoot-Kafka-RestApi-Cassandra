package com.order.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.order.dto.OrderDto;
import com.order.entity.CounterTest;
import com.order.entity.Order;
import com.order.repository.CounterTestRepository;
import com.order.repository.OrderBaseRepository;
import com.order.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private OrderBaseRepository orderBaseRepository;
	
	@Value("${kafka.bootstrap-topic}")
	private String bootstrapTopic;
	
	/**
	 * create order
	 * @param orderDto
	 * @return
	 */
	public String createOrder(OrderDto orderDto) {
		Order order = this.convertDtoToEntity(orderDto);
		order.setCreatedDate(new Date());
		order.setUpdatedDate(null);
		this.orderRepository.save(order);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(orderDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
//		kafkaTemplate.send(bootstrapTopic, json);

		return "Order Created SuccessFully";
	}
	
	/**
	 * get all order
	 * @return
	 */
	public List<OrderDto> getAllOrdersList(){
		Iterable<Order> orders= this.orderRepository.findAll();
		List<OrderDto> orderDtos = new ArrayList<OrderDto>();
		for(Order order:orders) {
			orderDtos.add(this.convertEntityToDto(order));
		}
		return orderDtos;
	}
	
	/**
	 * convert dto to entity
	 * @param orderDto
	 * @return
	 */
	private Order convertDtoToEntity(OrderDto orderDto) {
		Order order = new Order();
		BeanUtils.copyProperties(orderDto, order);
		return order;
	}
	
	/**
	 * convert entity to dto
	 * @param order
	 * @return
	 */
	private OrderDto convertEntityToDto(Order order) {
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);
		return orderDto;
	}
	
	
	public List<OrderDto> getAllOrdersListByOrderBaseRepository(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderType", "pending");
		map.put("orderId", 100);
//		map.put("\"ordertype\"", "pending");
//		System.out.println(this.orderBaseRepository.findListByNaturalId(Order.class, "orderId",100L));
		List<Order> orders= this.orderBaseRepository.findListByMultipleNaturalId(Order.class, map);
//		List<Order> orders= this.orderBaseRepository.findBetweenValue(Order.class, 5,10,"orderId");
		
		List<OrderDto> orderDtos = new ArrayList<OrderDto>();
		for(Order order:orders) {
			orderDtos.add(this.convertEntityToDto(order));
		}
		return orderDtos;
	}
	static volatile int var =0; 
	public void getDataByCallableThread() throws InterruptedException, ExecutionException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderType", "pending");
		map.put("orderId", 5);
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		List<Callable<Order>> taskList = new ArrayList<>();
		
		for(int i=0;i<50;i++) {
			
			Callable<Order> callable = () -> {
				
				List<Order> orders= this.orderBaseRepository.findListByMultipleNaturalId(Order.class, map);
				if(orders.size()>0) {
					return orders.get(0);
				}
				return null;
			};
			taskList.add(callable);
		}
		
		List<Future<Order>> future = executorService.invokeAll(taskList);
		for(Future<Order> order:future) {
			System.out.println(order.get());
		}
	}
	
	@Autowired
	private CounterTestRepository counterTestRepository;

	public void counterTest() {
//		CounterTest c=new CounterTest();
//		c.setTablename("test1");
//		c.setCounter(BigInteger.valueOf(1));
		counterTestRepository.update1(null);
		
	}
}
