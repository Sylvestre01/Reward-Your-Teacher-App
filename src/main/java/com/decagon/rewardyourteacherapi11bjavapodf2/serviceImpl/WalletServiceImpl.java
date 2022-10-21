package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;


import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.WalletNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Wallet;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.UserRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.WalletRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<String> getWalletBalance(CustomUserDetails currentUser) {
        User user = userRepository.findUserByEmail(currentUser.getUsername()).orElseThrow(()-> new UserNotFoundException(currentUser.getUsername() + "not found"));
        Wallet wallet = walletRepository.findWalletByUser(user).orElseThrow(()-> new WalletNotFoundException("Wallet Not Found"));
        String balance = wallet.getAmount().toString();
        return new ApiResponse<>("success", LocalDateTime.now(), balance);
    }



}