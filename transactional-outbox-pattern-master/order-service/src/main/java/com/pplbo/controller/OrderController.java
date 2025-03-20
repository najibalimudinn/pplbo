package com.pplbo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pplbo.common.dto.OrderRequestDTO;
import com.pplbo.model.Order;
import com.pplbo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws JsonProcessingException {
        Order createOrder = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOrder);
    }
}
