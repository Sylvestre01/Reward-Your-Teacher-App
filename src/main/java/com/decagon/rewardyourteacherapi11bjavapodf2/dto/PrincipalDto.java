package com.decagon.rewardyourteacherapi11bjavapodf2.dto;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.Role;
import com.decagon.rewardyourteacherapi11bjavapodf2.enums.SchoolType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrincipalDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String token;
    private String school;
    private String telephone;
    private String yearsOfService;
    private SchoolType schoolType;

    public PrincipalDto(Long id, String name, String email, Role role, String token, String school, String telephone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
        this.school = school;
        this.telephone = telephone;
    }

    public PrincipalDto(Long id, String name, String email, Role role, String token, String school, String telephone, String yearsOfService, SchoolType schoolType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
        this.school = school;
        this.telephone = telephone;
        this.yearsOfService = yearsOfService;
        this.schoolType = schoolType;
    }
}
