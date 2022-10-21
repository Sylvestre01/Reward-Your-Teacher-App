package com.decagon.rewardyourteacherapi11bjavapodf2.controllers;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UpdateUserProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CurrentUser;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.UpdateUserProfileService;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UpdateUserProfileService updateUserProfileService;

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CurrentUser CustomUserDetails currentUser, @RequestHeader("Authorization") String bearToken) {
        return new ResponseEntity<>(userService.logout(currentUser, bearToken), HttpStatus.OK);
    }

    @PutMapping("/students/update")
    public ResponseEntity<String> updateUserProfile(@RequestBody UpdateUserProfileDto updateUserProfileDto){
       updateUserProfileService.updateUserProfile(updateUserProfileDto);
       return new ResponseEntity<>("Update Successful",HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<ApiResponse<User>> getAUser(@PathVariable("id") Long id){
        ApiResponse<User> user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
