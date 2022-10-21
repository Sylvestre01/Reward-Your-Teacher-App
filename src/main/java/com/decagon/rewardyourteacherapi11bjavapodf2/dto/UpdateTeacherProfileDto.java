package com.decagon.rewardyourteacherapi11bjavapodf2.dto;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.SchoolType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeacherProfileDto {
    private String name;
    private String email;
    private String school;
    private String yearsOfService;
    private List<SubjectDto> subjectList;
    private SchoolType schoolType;
    private String telephone;

}
