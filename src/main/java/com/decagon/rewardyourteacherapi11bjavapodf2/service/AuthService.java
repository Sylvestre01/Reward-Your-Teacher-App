package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.LoginDTO;
import com.decagon.rewardyourteacherapi11bjavapodf2.dto.PrincipalDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.dto.TeacherRegistrationDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UserDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.UserRegistrationResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {
    ApiResponse<PrincipalDto> login(LoginDTO loginDTO);
    UserRegistrationResponse registerUser(UserDto userDto);
    UserRegistrationResponse registerTeacher(TeacherRegistrationDto teacherDto) throws IOException;

    ApiResponse<PrincipalDto> authenticateOAuth2User(OAuth2UserInfo auth2UserInfo);

    String verifyAccount(String token);
}
