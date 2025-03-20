package com.pplbo.orderservice.repository;

import com.pplbo.orderservice.model.OrderEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderEventRepository extends MongoRepository<OrderEvent, String> {
}
