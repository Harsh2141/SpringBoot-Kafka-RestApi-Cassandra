package com.shipment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shipment.entity.Shipment;

@Repository
public interface ShipmentRepository extends CrudRepository<Shipment, Long>{

}
