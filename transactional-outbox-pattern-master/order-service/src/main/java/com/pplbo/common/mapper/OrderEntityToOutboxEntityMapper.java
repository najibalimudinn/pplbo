package com.pplbo.common.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplbo.model.Order;
import com.pplbo.model.Outbox;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderEntityToOutboxEntityMapper {

    public Outbox map(Order order) throws JsonProcessingException {
        return Outbox.builder()
                .aggregateId(order.getId().toString())
                .payload(new ObjectMapper().writeValueAsString(order))
                .createdAt(new Date())
                .processed(false)
                .build();
    }
}
