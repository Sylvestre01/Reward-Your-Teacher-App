package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;

public interface WalletService {


    ApiResponse<String> getWalletBalance(CustomUserDetails currentUser);



}

