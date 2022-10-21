package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.PaymentRequest;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.PaymentResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.TransactionResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.VerifyTransactionResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Transaction;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    PaymentResponse initDeposit(PaymentRequest paymentRequest) throws Exception;

    VerifyTransactionResponse verifyTransaction(String reference) throws Exception;

    ApiResponse<List<TransactionResponse>> findAllTransactionByReceiver();

    ApiResponse<BigDecimal> totalMoneySentByStudent();
}
