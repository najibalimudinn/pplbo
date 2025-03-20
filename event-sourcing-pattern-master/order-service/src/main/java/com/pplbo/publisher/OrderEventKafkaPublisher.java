package com.pplbo.publisher;

import com.pplbo.orderservice.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventKafkaPublisher {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${order.event.topic-name}")
    private String topicName;

    public void sendOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send(topicName, orderEvent);
    }
}
