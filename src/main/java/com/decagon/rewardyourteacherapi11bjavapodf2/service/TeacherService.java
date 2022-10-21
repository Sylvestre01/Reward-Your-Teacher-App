package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UserProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Notification;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TeacherService {
    Page<UserProfileDto> getAllTeachersBySchool(String school, int page, int size);

    Page<UserProfileDto> getAllTeachers(int page, int size);

    Page<UserProfileDto> searchTeacherBySchool(String keyword, String school, int page, int size);

    ApiResponse<List<UserProfileDto>> searchTeacher(String name);

    ApiResponse<UserProfileDto> viewProfile(Long id);

    ApiResponse<Notification> teacherAppreciatesStudent(CustomUserDetails currentUser, Long userId);

}
