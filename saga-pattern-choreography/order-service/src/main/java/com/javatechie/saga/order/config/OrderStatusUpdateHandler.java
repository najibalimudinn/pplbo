package com.javatechie.saga.order.config;

import com.javatechie.saga.commons.dto.OrderRequest;
import com.javatechie.saga.commons.event.OrderStatus;
import com.javatechie.saga.commons.event.PaymentStatus;
import com.javatechie.saga.order.model.PurchaseOrder;
import com.javatechie.saga.order.repository.OrderRepository;
import com.javatechie.saga.order.service.OrderStatusPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OrderStatusUpdateHandler {

    private final OrderRepository orderRepository;

    private final OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public void updateOrder(Integer orderId, Consumer<PurchaseOrder> consumer)
    {
        log.info("Updating order with id {}", orderId);
        orderRepository.findById(orderId).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder purchaseOrder) {
        boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isPaymentComplete)
        {
            orderStatusPublisher.publishOrderEvent(convertEntityToOrderRequest(purchaseOrder), orderStatus);
        }
    }

    public OrderRequest convertEntityToOrderRequest(PurchaseOrder purchaseOrder)
    {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(purchaseOrder.getId());
        orderRequest.setUserId(purchaseOrder.getUserId());
        orderRequest.setAmount(purchaseOrder.getPrice());
        orderRequest.setProductId(purchaseOrder.getProductId());

        return orderRequest;
    }
}
