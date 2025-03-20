package com.pplbo.shippingservice.repository;

import com.pplbo.shippingservice.model.OrderEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderEventRepository extends MongoRepository<OrderEvent,String> {
}
