package com.decagon.rewardyourteacherapi11bjavapodf2.controllers;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.SchoolDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/schools")
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping("/add")
    public ResponseEntity<?> addSchool(@RequestBody SchoolDto schoolDto){
        return  new ResponseEntity<>(schoolService.addSchool(schoolDto), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSchool(@PathVariable("id") Long id, @RequestBody SchoolDto schoolDto){
        return new ResponseEntity<>(schoolService.updateSchool(schoolDto, id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> selectAllSchools(@RequestParam("page") int page,@RequestParam("size") int size){
        return new ResponseEntity<>(schoolService.selectAllSchools(page, size), HttpStatus.OK);
    }

    @GetMapping("/all/{keyword}")
    public ResponseEntity<?> searchSchools(@PathVariable("keyword") String keyword, @RequestParam("page") int page,@RequestParam("size") int size){
        return new ResponseEntity<>(schoolService.searchSchool(keyword, page, size), HttpStatus.OK);
    }
}
