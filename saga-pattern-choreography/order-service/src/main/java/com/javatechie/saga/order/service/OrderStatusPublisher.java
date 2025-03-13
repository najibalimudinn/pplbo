package com.javatechie.saga.order.service;

import com.javatechie.saga.commons.dto.OrderRequest;
import com.javatechie.saga.commons.event.OrderEvent;
import com.javatechie.saga.commons.event.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class OrderStatusPublisher {

    private final Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequest orderRequest, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequest, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
