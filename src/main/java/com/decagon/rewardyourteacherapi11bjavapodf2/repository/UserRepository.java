package com.decagon.rewardyourteacherapi11bjavapodf2.repository;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.Role;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Teacher;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    User getUserByEmail(String email);

    Optional<User> findUserById(Long id);
    User findByIdAndRole (Long id, Role role);

    Page<User> findAllByRole(Role role, Pageable pageable);

    List<User> findByRoleAndNameContainingIgnoreCase(Role role , String keyword);

    Page<Teacher> findAllBySchoolAndRole(String school, Role role, Pageable pageable);

   Page<Teacher> findAllByNameContainingIgnoreCaseAndSchoolAndRole(String keyword, String school, Role role, Pageable pageable);
}
