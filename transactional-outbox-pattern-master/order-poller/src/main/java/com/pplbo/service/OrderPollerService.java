package com.pplbo.service;

import com.pplbo.model.Outbox;
import com.pplbo.publisher.MessagePublisher;
import com.pplbo.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPollerService {

    private final OutboxRepository outboxRepository;

    private final MessagePublisher messagePublisher;

    @Scheduled(fixedRate = 6000)
    public void pollOutboxAndPublish() {
        //1. fetch unprocessed record
        List<Outbox> unprocessedRecords = outboxRepository.findByProcessedFalse();
        log.info("Unprocessed records: {}", unprocessedRecords.size());

        //2. publish record to kafka/queue
        unprocessedRecords.forEach(outbox -> {
            try {
                messagePublisher.publish(outbox.getPayload());
                outbox.setProcessed(true);
                outboxRepository.save(outbox);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }
}
