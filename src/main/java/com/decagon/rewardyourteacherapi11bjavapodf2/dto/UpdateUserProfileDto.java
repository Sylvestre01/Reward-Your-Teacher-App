package com.decagon.rewardyourteacherapi11bjavapodf2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileDto {
    private String name;
    private String email;
    private String telephone;
    private String school;

}
