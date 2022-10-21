package com.decagon.rewardyourteacherapi11bjavapodf2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message extends BaseClass{

    @Column(length = 1000)
    private String content;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;


}
