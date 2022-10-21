package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;

import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.UserRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.ProfilePictureService;
import com.decagon.rewardyourteacherapi11bjavapodf2.utils.CloudUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfilePictureImpl implements ProfilePictureService {

    private final UserRepository userRepository;

    private final CloudUtils cloudUtils;

    @Override
    public ApiResponse<?> uploadProfilePicture(MultipartFile image) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = ((UserDetails) principal).getUsername();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("User with email" + userEmail + " not found"));
        user.setProfilePicture(cloudUtils.uploadImage(image));
        userRepository.save(user);
        return new ApiResponse<>("Profile picture uploaded", LocalDateTime.now());
    }
}
