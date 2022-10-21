package com.decagon.rewardyourteacherapi11bjavapodf2.response;


import com.decagon.rewardyourteacherapi11bjavapodf2.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;
    private String description;
    private BigDecimal amount;
    private String createDate;

    private String senderName;
    private String senderEmail;
    private String senderPhoneNumber;
    private String senderSchool;

    public TransactionResponse(Transaction transaction){
        id = transaction.getId();
        description = transaction.getDescription();
        amount = transaction.getAmount();
        createDate = transaction.getCreateDate();
        senderEmail = transaction.getSender().getEmail();
        senderName = transaction.getSender().getName();
        senderPhoneNumber = transaction.getSender().getTelephone();
        senderSchool = transaction.getSender().getSchool();
    }


}
