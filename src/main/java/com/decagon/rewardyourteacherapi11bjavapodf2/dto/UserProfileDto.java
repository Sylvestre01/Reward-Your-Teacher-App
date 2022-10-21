package com.decagon.rewardyourteacherapi11bjavapodf2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private Long id;
    private String name;
    private String School;
    private String email;
    private String telephone;
    private String yearsOfService;

    public UserProfileDto(Long id, String name, String school, String email, String telephone) {
        this.id = id;
        this.name = name;
        School = school;
        this.email = email;
        this.telephone = telephone;
    }
}

