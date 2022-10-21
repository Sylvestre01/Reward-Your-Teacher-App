package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;

import java.math.BigDecimal;

public interface RewardService {

    ApiResponse<String> rewardTeacher(CustomUserDetails currentUser, Long teacherId, BigDecimal amount);


}
