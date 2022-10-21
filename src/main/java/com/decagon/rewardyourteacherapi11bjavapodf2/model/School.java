package com.decagon.rewardyourteacherapi11bjavapodf2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "schools")
public class School extends BaseClass{

    private String name;
    private String address;
    private String schoolType;



}

