package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UpdateUserProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.UserRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.UpdateUserProfileResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.UpdateUserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UpdateUserProfileServiceImpl implements UpdateUserProfileService {
    private  final UserRepository userRepository;
    public UpdateUserProfileResponse updateUserProfile(UpdateUserProfileDto updateUserProfileDto){
        User user = userRepository.getUserByEmail(updateUserProfileDto.getEmail());

        user.setName(updateUserProfileDto.getName());
        user.setTelephone(updateUserProfileDto.getTelephone());
        user.setSchool(updateUserProfileDto.getSchool());
        userRepository.save(user);
        return new UpdateUserProfileResponse("Update Successful", LocalDateTime.now());

    }


}
