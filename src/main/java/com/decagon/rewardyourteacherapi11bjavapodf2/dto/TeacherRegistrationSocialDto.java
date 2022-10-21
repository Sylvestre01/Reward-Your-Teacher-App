package com.decagon.rewardyourteacherapi11bjavapodf2.dto;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.SchoolType;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Subject;
import lombok.Data;

import java.util.List;

@Data
public class TeacherRegistrationSocialDto {
    private String school;
    private String yearsOfService;
    private List<Subject> subjectList;
    private SchoolType schoolType;
    private String validId;
}
