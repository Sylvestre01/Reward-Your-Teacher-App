package com.decagon.rewardyourteacherapi11bjavapodf2.mapper;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UserProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;

public class UserProfilePayloadToModel {

    public static User MapRequestToUser(UserProfileDto request){
        User user  = new User();
        if(request.getId() != null){
            user.setId(request.getId());
        }

        if(request.getName() != null){
            user.setName(request.getName());
        }
        if(request.getEmail() != null){
            user.setEmail(request.getEmail());
        }
        if(request.getSchool() != null){
            user.setSchool(request.getSchool());
        }
        if (request.getTelephone() != null){
            user.setTelephone(request.getTelephone());
        }
        return user;
    }

    public static UserProfileDto MapUserToDTO(User user){
        return new UserProfileDto(user.getId(), user.getName(), user.getSchool(), user.getEmail(), user.getTelephone());
    }
}

