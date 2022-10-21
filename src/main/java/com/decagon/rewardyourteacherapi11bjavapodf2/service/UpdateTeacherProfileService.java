package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.UpdateTeacherProfileDto;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.UpdateTeacherProfileResponse;

public interface UpdateTeacherProfileService {

    UpdateTeacherProfileResponse updateTeacherProfile (UpdateTeacherProfileDto updateTeacherProfileDto, Long id);
}
