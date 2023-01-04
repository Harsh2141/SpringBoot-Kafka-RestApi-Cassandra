package com.shipment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipment.dto.ShipmentDto;
import com.shipment.entity.Shipment;
import com.shipment.repository.ShipmentRepository;

@Service
public class ShipmentService {
	
	@Autowired
	private ShipmentRepository shipmentRepository;
	
	/**
	 * create shipment
	 * @param shipmentDto
	 * @return
	 */
	public String createShipment(ShipmentDto shipmentDto) {
		Shipment shipment = this.convertDtoToEntity(shipmentDto);
		shipment.setCreatedDate(new Date());
		shipment.setUpdatedDate(null);
		this.shipmentRepository.save(shipment);
		return "Shipment Created SuccessFully";
	}
	
	/**
	 * get all shipment
	 * @return
	 */
	public List<ShipmentDto> getAllShipmentsList(){
		Iterable<Shipment> shipments= this.shipmentRepository.findAll();
		List<ShipmentDto> shipmentDtos = new ArrayList<ShipmentDto>();
		for(Shipment shipment:shipments) {
			shipmentDtos.add(this.convertEntityToDto(shipment));
		}
		return shipmentDtos;
	}
	
	/**
	 * convert dto to entity
	 * @param shipmentDto
	 * @return
	 */
	private Shipment convertDtoToEntity(ShipmentDto shipmentDto) {
		Shipment shipment = new Shipment();
		BeanUtils.copyProperties(shipmentDto, shipment);
		return shipment;
	}
	
	/**
	 * convert entity to dto
	 * @param shipment
	 * @return
	 */
	private ShipmentDto convertEntityToDto(Shipment shipment) {
		ShipmentDto shipmentDto = new ShipmentDto();
		BeanUtils.copyProperties(shipment, shipmentDto);
		return shipmentDto;
	}

}
