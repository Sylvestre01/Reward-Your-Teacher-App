package com.decagon.rewardyourteacherapi11bjavapodf2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject extends BaseClass{
    private String nameOfSubject;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teacher_id" , referencedColumnName = "id")
    private Teacher teacher;



}
