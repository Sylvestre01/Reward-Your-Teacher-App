package com.decagon.rewardyourteacherapi11bjavapodf2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String email;
    @Digits(integer = 9, fraction = 0)
    private Long amount;

}
