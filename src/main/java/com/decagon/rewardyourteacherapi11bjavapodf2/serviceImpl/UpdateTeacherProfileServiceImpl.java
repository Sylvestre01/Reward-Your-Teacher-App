package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.SubjectDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UpdateTeacherProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Subject;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Teacher;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.TeacherRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.UpdateTeacherProfileResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.UpdateTeacherProfileService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateTeacherProfileServiceImpl implements UpdateTeacherProfileService {
    private  final TeacherRepository teacherRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UpdateTeacherProfileResponse updateTeacherProfile(UpdateTeacherProfileDto updateTeacherProfileDto, Long id){
      Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
       if(optionalTeacher.isEmpty()){
           throw new UserNotFoundException("Teacher Not Found");
       }
        Teacher teacher = optionalTeacher.get();
        teacher.setName(updateTeacherProfileDto.getName());
        teacher.setEmail(updateTeacherProfileDto.getEmail());
        teacher.setSchool(updateTeacherProfileDto.getSchool());
        teacher.setYearsOfService(updateTeacherProfileDto.getYearsOfService());
        teacher.setSchoolType(updateTeacherProfileDto.getSchoolType());
        List<Subject> subjects = teacher.getSubject();
        List<SubjectDto> subjectDtos = updateTeacherProfileDto.getSubjectList();
        Map<Long, SubjectDto> mapSubjetReqIdsToReq = new HashMap<>();
            for(SubjectDto subjectDto: subjectDtos ){
              mapSubjetReqIdsToReq.put(subjectDto.getId(),subjectDto);
        }
        subjects.forEach(subject -> {
            if(mapSubjetReqIdsToReq.containsKey(subject.getId())){
                this.populateSubject(mapSubjetReqIdsToReq.get(subject.getId()),subject);
            }
        });

        teacherRepository.save(teacher);
        return new UpdateTeacherProfileResponse("Update Successful", LocalDateTime.now());
    }

    private void populateSubject(SubjectDto subjectDto, Subject subject) {
        subject.setNameOfSubject(subjectDto.getNameOfSubject());
    }

}
