package com.pplbo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pplbo.common.mapper.OrderEntityToOutboxEntityMapper;
import com.pplbo.model.Outbox;
import com.pplbo.repository.OrderRepository;
import com.pplbo.common.dto.OrderRequestDTO;
import com.pplbo.common.mapper.OrderDTOtoEntityMapper;
import com.pplbo.model.Order;
import com.pplbo.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    private final OrderDTOtoEntityMapper orderDTOtoEntityMapper;
    private final OrderEntityToOutboxEntityMapper orderEntityToOutboxEntityMapper;

    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO) throws JsonProcessingException {

        Order order = orderDTOtoEntityMapper.map(orderRequestDTO);
        order = orderRepository.save(order);

        Outbox outbox = orderEntityToOutboxEntityMapper.map(order);
        outboxRepository.save(outbox);

        return order;
    }
}
