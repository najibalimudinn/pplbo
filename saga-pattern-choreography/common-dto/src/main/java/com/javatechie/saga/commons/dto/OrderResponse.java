package com.javatechie.saga.commons.dto;

import com.javatechie.saga.commons.event.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Integer userId;
    private Integer productId;
    private Integer amount;
    private String orderId;
    private OrderStatus orderStatus;
}
