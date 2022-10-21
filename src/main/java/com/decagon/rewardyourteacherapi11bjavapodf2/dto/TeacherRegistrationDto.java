package com.decagon.rewardyourteacherapi11bjavapodf2.dto;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.SchoolType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRegistrationDto extends UserDto{
    @NotBlank(message = "Field is required")
    private String yearsOfService;
    @NotBlank(message = "Field is required")
    private List<String> subjectList;
    @NotBlank(message = "Field is required")
    private SchoolType schoolType;


}
