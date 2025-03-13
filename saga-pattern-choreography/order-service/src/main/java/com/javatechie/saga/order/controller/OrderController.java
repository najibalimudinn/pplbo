package com.javatechie.saga.order.controller;

import com.javatechie.saga.commons.dto.OrderRequest;
import com.javatechie.saga.order.model.PurchaseOrder;
import com.javatechie.saga.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public PurchaseOrder createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping
    public List<PurchaseOrder> getAllOrders()
    {
        return orderService.getAllOrders();
    }
}
