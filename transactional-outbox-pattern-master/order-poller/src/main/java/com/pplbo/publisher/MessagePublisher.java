package com.pplbo.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class MessagePublisher {

    @Value("${order.poller.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String payload) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, payload);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + payload + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Sent message=[" + payload + "] with error=[" + ex.getMessage() + "]");
            }
        });
    }
}
