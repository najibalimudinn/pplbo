package com.javatechie.saga.payment.service;

import com.javatechie.saga.commons.dto.OrderRequest;
import com.javatechie.saga.commons.dto.PaymentRequest;
import com.javatechie.saga.commons.event.OrderEvent;
import com.javatechie.saga.commons.event.PaymentEvent;
import com.javatechie.saga.commons.event.PaymentStatus;
import com.javatechie.saga.payment.model.UserBalance;
import com.javatechie.saga.payment.model.UserTransaction;
import com.javatechie.saga.payment.repository.UserBalanceRepository;
import com.javatechie.saga.payment.repository.UserTransactionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserBalanceRepository userBalanceRepository;

    private final UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalanceInDB() {
        List<UserBalance> userBalances = Arrays.asList(
                new UserBalance(101, 1000),
                new UserBalance(102, 2000),
                new UserBalance(103, 3000),
                new UserBalance(104, 4000),
                new UserBalance(105, 5000)
        );

        userBalanceRepository.saveAll(userBalances);
    }

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequest orderRequest = orderEvent.getOrderRequest();
        PaymentRequest paymentRequest=new PaymentRequest(orderRequest.getOrderId(), orderRequest.getUserId(), orderRequest.getAmount());

        return userBalanceRepository.findById(orderRequest.getUserId())
                .filter(userBalance -> userBalance.getPrice()>orderRequest.getAmount())
                .map(userBalance -> {
                    userBalance.setPrice(userBalance.getPrice()-orderRequest.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderRequest.getOrderId(), orderRequest.getUserId(), orderRequest.getAmount()));
                    return new PaymentEvent(paymentRequest, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequest, PaymentStatus.PAYMENT_FAILED));
    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequest().getOrderId())
                .ifPresent(userTransaction -> {
                    userTransactionRepository.delete(userTransaction);
                    userTransactionRepository.findById(userTransaction.getUserId())
                            .ifPresent(userBalance -> {
                                userBalance.setAmount(userBalance.getAmount()+userBalance.getAmount());
                            });
                });
    }
}
