package com.javatechie.saga.order.service;

import com.javatechie.saga.commons.dto.OrderRequest;
import com.javatechie.saga.commons.event.OrderStatus;
import com.javatechie.saga.order.model.PurchaseOrder;
import com.javatechie.saga.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public PurchaseOrder createOrder(OrderRequest orderRequest)
    {
        PurchaseOrder order = orderRepository.save(convertDtoToModel(orderRequest));
        orderRequest.setOrderId(order.getId());
        // Produce Kafka event
        orderStatusPublisher.publishOrderEvent(orderRequest, order.getOrderStatus());
        return order;
    }

    public List<PurchaseOrder> getAllOrders()
    {
        return orderRepository.findAll();
    }

    private PurchaseOrder convertDtoToModel(OrderRequest orderRequest) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(orderRequest.getProductId());
        purchaseOrder.setUserId(orderRequest.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(orderRequest.getAmount());
        return purchaseOrder;
    }
}
