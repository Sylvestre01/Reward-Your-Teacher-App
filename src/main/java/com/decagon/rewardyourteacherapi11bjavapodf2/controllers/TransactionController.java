package com.decagon.rewardyourteacherapi11bjavapodf2.controllers;
import com.decagon.rewardyourteacherapi11bjavapodf2.dto.PaymentRequest;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Transaction;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.PaymentResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.TransactionResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private PaymentResponse paymentResponse = new PaymentResponse();
    @PostMapping(value = "/deposit")
    public ResponseEntity<?> deposit(@RequestBody PaymentRequest paymentRequest) throws Exception {
        paymentResponse = transactionService.initDeposit(paymentRequest);
        return new ResponseEntity<>( paymentResponse, HttpStatus.OK);
    }
    @GetMapping(value = "/callback")
    public void payStackResponse(String reference, HttpServletResponse response) throws Exception {
        transactionService.verifyTransaction(reference);
        response.sendRedirect("http://localhost:3000/dashboard");
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> retrieveAllReceiverTransactions() {
        return new ResponseEntity<>(transactionService.findAllTransactionByReceiver(), HttpStatus.OK);
    }

    @GetMapping("/student/moneysent")
    public ResponseEntity<?> getTotalMoneySentByStudent(){
        return new ResponseEntity<>(transactionService.totalMoneySentByStudent(),HttpStatus.OK);
    }
}