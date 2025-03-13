package com.javatechie.saga.payment.repository;

import com.javatechie.saga.payment.model.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {
}
