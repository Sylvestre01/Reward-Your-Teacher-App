package com.decagon.rewardyourteacherapi11bjavapodf2.controllers;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.EmailDetails;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/api/v1/sendMail")
    public String sendMail(@RequestBody EmailDetails details){
        String status = emailService.sendSimpleMail(details);
        return status;
    }
}
