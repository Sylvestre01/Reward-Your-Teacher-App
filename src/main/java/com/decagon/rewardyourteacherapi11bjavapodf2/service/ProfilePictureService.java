package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfilePictureService {

    ApiResponse<?> uploadProfilePicture(MultipartFile image) throws IOException;
}
