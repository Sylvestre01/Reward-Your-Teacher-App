package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UserProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.enums.NotificationType;
import com.decagon.rewardyourteacherapi11bjavapodf2.enums.Role;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.mapper.TeacherMapper;
import com.decagon.rewardyourteacherapi11bjavapodf2.mapper.UserProfilePayloadToModel;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Notification;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Teacher;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.NotificationRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.UserRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;

    @Override
    public Page<UserProfileDto> getAllTeachers(int page, int size) {
        if(page < 0){ page = 0;}
        if(size < 1){ size = 1;}
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<User> paged = userRepository.findAllByRole(Role.TEACHER, pageable);
        if (paged.isEmpty()){
            int lastPageNum = paged.getTotalPages() - 1 ;
            pageable = PageRequest.of(lastPageNum, size);
            paged =  userRepository.findAllByRole(Role.TEACHER, pageable);
        }
        List<UserProfileDto> teachers = paged.getContent().stream().map(UserProfilePayloadToModel::MapUserToDTO).collect(Collectors.toList());
        return new PageImpl<>(teachers, pageable, paged.getTotalElements());
    }

    @Override
    public Page<UserProfileDto> getAllTeachersBySchool(String school, int page, int size) {
        if(page < 0){ page = 0;}
        if(size < 1){ size = 1;}
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Teacher> paged = userRepository.findAllBySchoolAndRole(school, Role.TEACHER, pageable);
        if (paged.isEmpty()){
            page = paged.getTotalPages() - 1 ;
            if(page < 0){ page = 0;}
            if(size < 1){ size = 1;}
            pageable = PageRequest.of(page, size, Sort.by("name"));
            paged =  userRepository.findAllBySchoolAndRole(school, Role.TEACHER, pageable);
        }
        List<UserProfileDto> teachers = paged.getContent().stream().map(TeacherMapper::MapUserToDTO).collect(Collectors.toList());
        return new PageImpl<>(teachers, pageable, paged.getTotalElements());
    }

    @Override
    public Page<UserProfileDto> searchTeacherBySchool(String keyword, String school, int page, int size){
        if(page < 0){ page = 0;}
        if(size < 1){ size = 1;}
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Teacher> paged = userRepository.findAllByNameContainingIgnoreCaseAndSchoolAndRole(keyword, school, Role.TEACHER, pageable);
        if (paged.isEmpty()){
            page = paged.getTotalPages() - 1 ;
            if(page < 0){ page = 0;}
            if(size < 1){ size = 1;}
            pageable = PageRequest.of(page, size, Sort.by("name"));
            paged = userRepository.findAllByNameContainingIgnoreCaseAndSchoolAndRole(keyword, school, Role.TEACHER, pageable);
        }
        List<UserProfileDto> teachers = paged.getContent().stream().map(TeacherMapper::MapUserToDTO).collect(Collectors.toList());
        return new PageImpl<>(teachers, pageable, paged.getTotalElements());
    }

    @Override
    public ApiResponse<List<UserProfileDto>> searchTeacher(String name) {
        List<User> teachers = userRepository.findByRoleAndNameContainingIgnoreCase(Role.TEACHER , name);
        log.info("{}" , teachers);

        List<UserProfileDto> teacherProfiles = new ArrayList<>();
        for(User teacher: teachers){
            UserProfileDto teacherProfile = new UserProfileDto();
            teacherProfile.setName(teacher.getName());
            teacherProfile.setSchool(teacher.getSchool());
            teacherProfile.setEmail(teacher.getEmail());
            teacherProfile.setTelephone(teacher.getTelephone());
            teacherProfiles.add(teacherProfile);
        }
        return  new ApiResponse<>("success", LocalDateTime.now() , teacherProfiles);
    }

    @Override
    public ApiResponse<UserProfileDto> viewProfile(Long id) {
        UserProfileDto teacherProfile = new UserProfileDto() ;
        User teacher = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("user id not found"));
        teacherProfile.setName(teacher.getName());
        teacherProfile.setSchool(teacher.getSchool());
        teacherProfile.setEmail(teacher.getEmail());
        teacherProfile.setTelephone(teacher.getTelephone());
        return new ApiResponse<>("success",LocalDateTime.now(),teacherProfile);
    }

    @Override
    public ApiResponse<Notification> teacherAppreciatesStudent(CustomUserDetails currentUser, Long userId) {
        User teacher = userRepository.findUserByEmail(currentUser.getUsername()).get();
        User student = userRepository.findUserById(userId).get();
        String messageToStudent = String.format("%s appreciated you \uD83D\uDC4D", teacher.getName());
        Notification notification = new Notification(messageToStudent, NotificationType.APPRECIATION, student);
        notificationRepository.save(notification);
        return new ApiResponse<>("Success", LocalDateTime.now(), notification);
    }

}
