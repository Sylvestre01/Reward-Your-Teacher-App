package com.decagon.rewardyourteacherapi11bjavapodf2.dto;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.TransactionType;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;

import java.math.BigDecimal;

public class TransactionDto {
    private TransactionType transactionType;
    private BigDecimal amount;
    private User user;
}
