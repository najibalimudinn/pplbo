package com.pplbo.common.mapper;

import com.pplbo.common.dto.OrderRequestDTO;
import com.pplbo.model.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderDTOtoEntityMapper {

    public Order map(OrderRequestDTO orderRequestDTO) {
        return
                Order.builder()
                        .customerId(orderRequestDTO.getCustomerId())
                        .name(orderRequestDTO.getName())
                        .productType(orderRequestDTO.getProductType())
                        .quantity(orderRequestDTO.getQuantity())
                        .price(orderRequestDTO.getPrice())
                        .orderDate(new Date())
                        .build();
    }
}
