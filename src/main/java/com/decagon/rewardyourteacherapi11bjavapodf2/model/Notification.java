package com.decagon.rewardyourteacherapi11bjavapodf2.model;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.NotificationType;
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
@Table(name = "notifications")
public class Notification extends BaseClass{

    private String notificationBody;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;



}
