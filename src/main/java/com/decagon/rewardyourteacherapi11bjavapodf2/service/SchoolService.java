package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.SchoolDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.School;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import org.springframework.data.domain.Page;


public interface SchoolService {

    ApiResponse addSchool(SchoolDto schoolDto);

    ApiResponse updateSchool(SchoolDto schoolDto , Long Id);

    Page<School> selectAllSchools(int page, int size);

    Page<School> searchSchool(String keyword, int page, int size);

}
