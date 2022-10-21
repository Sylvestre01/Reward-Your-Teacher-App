package com.decagon.rewardyourteacherapi11bjavapodf2.dto;

import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WalletDto {

    private BigDecimal amount;

    private User user;
}
