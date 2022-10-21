package com.decagon.rewardyourteacherapi11bjavapodf2.repository;

import com.decagon.rewardyourteacherapi11bjavapodf2.model.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllBySchool(String school, Pageable pageable);

    Teacher findByEmail(String email);

}
