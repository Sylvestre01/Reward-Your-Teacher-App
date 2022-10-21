package com.decagon.rewardyourteacherapi11bjavapodf2.service;


import com.decagon.rewardyourteacherapi11bjavapodf2.dto.OAuth2UserInfo;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;


public interface UserService {


    ApiResponse getOAuth2Teacher(OAuth2UserInfo oAuth2UserInfo);

    ApiResponse registerOAuth2Student(OAuth2UserInfo oAuth2UserInfo);

    ApiResponse<String> logout(CustomUserDetails activeUser, String token);

    ApiResponse<User> getUserById(Long id);

}
