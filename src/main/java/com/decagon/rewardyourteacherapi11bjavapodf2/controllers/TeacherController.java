package com.decagon.rewardyourteacherapi11bjavapodf2.controllers;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UserProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UpdateTeacherProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ResponseService;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CurrentUser;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.TeacherService;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.UpdateTeacherProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TeacherController {
    private final TeacherService teacherService;
    private final ResponseService<ApiResponse<List<UserProfileDto>>> responseService;
    private final UpdateTeacherProfileService updateTeacherProfileService;


    @GetMapping("/teachers/search/{keyword}")
    public ResponseEntity<ApiResponse<List<UserProfileDto>>> searchTeacher(@PathVariable(value = "keyword")String keyword ) {
        return responseService.response(teacherService.searchTeacher(keyword), HttpStatus.OK);
    }

    @GetMapping(value =  "/schools/{school}/teachers")
    public ResponseEntity<ApiResponse> getAllTeachersBySchool( @PathVariable(value = "school") String school, @RequestParam("page") int page, @RequestParam("size") int size) {
       Page<UserProfileDto> teacherPage = teacherService.getAllTeachersBySchool(school, page, size);
        return new ResponseEntity<>(new ApiResponse<>("Success", LocalDateTime.now(), teacherPage), HttpStatus.OK);
    }

    @GetMapping(value =  "/schools/{school}/teachers/{name}")
    public ResponseEntity<ApiResponse> searchTeacherBySchool(@PathVariable("name") String name, @PathVariable(value = "school") String school, @RequestParam("page") int page, @RequestParam("size") int size) {
        Page<UserProfileDto> teacherPage = teacherService.searchTeacherBySchool(name, school, page, size);
        return new ResponseEntity<>(new ApiResponse<>("Success", LocalDateTime.now(), teacherPage), HttpStatus.OK);
    }

    @GetMapping("/teachers")
    public ResponseEntity<ApiResponse> getAllTeachers(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<UserProfileDto> teacherPage = teacherService.getAllTeachers(page, size);
        return new ResponseEntity<>(new ApiResponse<>("Success", LocalDateTime.now(), teacherPage), HttpStatus.OK);
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<?> viewTeacherProfile(@PathVariable(value ="id") Long id) {
        return new ResponseEntity<>(teacherService.viewProfile(id), HttpStatus.OK);
    }

    @PutMapping("/teachers/update/{id}")
    public ResponseEntity<String> updateTeacherProfile(@RequestBody UpdateTeacherProfileDto updateTeacherProfileDto, @PathVariable Long id){
        updateTeacherProfileService.updateTeacherProfile(updateTeacherProfileDto, id);
        return new ResponseEntity<>("Update Successful", HttpStatus.OK);
    }

    @PostMapping("/teachers/appreciate/{studentId}")
    public ResponseEntity<ApiResponse> teacherAppreciatesStudent(@CurrentUser CustomUserDetails currentUser, @PathVariable("studentId") Long id){
        return new ResponseEntity<>(teacherService.teacherAppreciatesStudent(currentUser, id), HttpStatus.OK);
    }
}
