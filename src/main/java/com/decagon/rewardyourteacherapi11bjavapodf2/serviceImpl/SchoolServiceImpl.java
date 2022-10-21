package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.SchoolDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.SchoolNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.School;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.SchoolRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;

    @Override
    public ApiResponse<School> addSchool(SchoolDto schoolDto) {
        School school = new School();
        school.setName(schoolDto.getName());
        school.setAddress(schoolDto.getAddress());
        school.setSchoolType(schoolDto.getSchoolType());
        schoolRepository.save(school);
        return new ApiResponse<>("Success", LocalDateTime.now(), school);
    }

    @Override
    public ApiResponse updateSchool(SchoolDto schoolDto, Long id) {
        School existingSchool = schoolRepository.findById(id).orElseThrow(()-> new SchoolNotFoundException("School with " + id + " not found"));
            existingSchool.setName(schoolDto.getName());
            existingSchool.setAddress(schoolDto.getAddress());
            existingSchool.setSchoolType(schoolDto.getSchoolType());
            schoolRepository.save(existingSchool);
            return new ApiResponse<>("School successfully updated" , LocalDateTime.now(), existingSchool);
    }

    @Override
    public Page<School> selectAllSchools( int page, int size) {
        if(page < 0){ page = 0;}
        if(size < 1){ size = 1;}
        Pageable pageable = PageRequest.of(page, size, (Sort.by("name")));
        Page<School> schools = schoolRepository.findAll(pageable);
        if (schools.isEmpty()){
            int lastPageNum = schools.getTotalPages() - 1 ;
            pageable = PageRequest.of(lastPageNum, size, (Sort.by("name")));
            return schoolRepository.findAll(pageable);
        }
        return schools;
    }

    @Override
    public Page<School> searchSchool(String keyword, int page, int size) {
        if(page < 0){ page = 0;}
        if(size < 1){ size = 1;}
        Pageable pageable = PageRequest.of(page, size, (Sort.by("name")));
        Page<School> schools = schoolRepository.findAllByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, pageable);
        if (schools.isEmpty()){
            page = schools.getTotalPages() - 1 ;
            if(page < 0){ page = 0;}
            if(size < 1){ size = 1;}
            pageable = PageRequest.of(page, size, (Sort.by("name")));
            return schoolRepository.findAllByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, pageable);
        }
        return schools;
    }


}
