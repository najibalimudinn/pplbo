package com.javatechie.saga.order.config;

import com.javatechie.saga.commons.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EventConsumerConfig {

    private final OrderStatusUpdateHandler orderStatusUpdateHandler;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer()
    {
        log.info("EventConsumerConfig paymentEventConsumer");
        return (paymentEvent) -> orderStatusUpdateHandler.updateOrder(paymentEvent.getPaymentRequest().getOrderId(), purchaseOrder -> {
            purchaseOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
        });
    }
}
