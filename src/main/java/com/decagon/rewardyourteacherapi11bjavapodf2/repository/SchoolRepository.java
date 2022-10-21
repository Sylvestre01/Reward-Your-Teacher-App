package com.decagon.rewardyourteacherapi11bjavapodf2.repository;

import com.decagon.rewardyourteacherapi11bjavapodf2.model.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SchoolRepository extends JpaRepository<School, Long> {

    School findById(School schoolById);

    Page<School> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM schools s WHERE s.address ILIKE %:key% or s.name ILike %:key%", nativeQuery = true)
    Page<School> findAllByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(@Param("key") String keyword, Pageable pageable);
}
