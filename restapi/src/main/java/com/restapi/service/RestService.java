package com.restapi.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.restapi.dto.RestDto;

@Service
public class RestService {

	@Autowired
	RestTemplate restTemplate;

	/**
	 * rest api endpoint for order create
	 * 
	 * @param restDto
	 */
	public void orderShipmentCreate(RestDto restDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<RestDto> entity = new HttpEntity<RestDto>(restDto, headers);
		restTemplate.exchange(
		         "http://localhost:8090/order/create", HttpMethod.POST, entity, String.class);
	}

}
