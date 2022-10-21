package com.decagon.rewardyourteacherapi11bjavapodf2.mapper;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UserProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Teacher;



public class TeacherMapper {

    public static Teacher MapRequestToUser(UserProfileDto request){
        var teacher  = new Teacher();
        if(request.getId() != null){
            teacher.setId(request.getId());
        }

        if(request.getName() != null){
            teacher.setName(request.getName());
        }
        if(request.getEmail() != null){
            teacher.setEmail(request.getEmail());
        }
        if(request.getSchool() != null){
            teacher.setSchool(request.getSchool());
        }
        if (request.getTelephone() != null){
            teacher.setTelephone(request.getTelephone());
        }
        if (request.getYearsOfService() != null){
            teacher.setYearsOfService(request.getYearsOfService());
        }
        return teacher;
    }

    public static UserProfileDto MapUserToDTO(Teacher teacher){
        return new UserProfileDto(teacher.getId(), teacher.getName(), teacher.getSchool(), teacher.getEmail(), teacher.getTelephone(), teacher.getYearsOfService());
    }
}

