package com.decagon.rewardyourteacherapi11bjavapodf2.controllers;

import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.ProfilePictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class ProfilePictureController {

    private final ProfilePictureService profilePictureService;

    @PutMapping("/upload-picture")
    public ResponseEntity<ApiResponse> uploadProfilePicture(@RequestPart MultipartFile image) throws IOException {
        ApiResponse apiResponse = profilePictureService.uploadProfilePicture(image);
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
