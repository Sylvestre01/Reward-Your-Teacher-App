package com.decagon.rewardyourteacherapi11bjavapodf2.controllers;

import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.CurrentUser;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RewardTeacherController {

    private final RewardService rewardService;

    @PostMapping(value = "/students/reward")
    public ResponseEntity<ApiResponse> rewardTeacher(@CurrentUser CustomUserDetails currentuser,
                                                     @RequestParam("teacherId") Long teacherId,
                                                     @RequestParam("amount") BigDecimal amount){
        return ResponseEntity.ok(rewardService.rewardTeacher(currentuser, teacherId, amount));

    }
}
