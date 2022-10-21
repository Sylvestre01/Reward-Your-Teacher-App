package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);
}
