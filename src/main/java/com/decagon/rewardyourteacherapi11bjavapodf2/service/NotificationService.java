package com.decagon.rewardyourteacherapi11bjavapodf2.service;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.NotificationType;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Notification;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Transaction;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;

import java.util.List;

public interface NotificationService {

     Notification saveNotification(User user, String message, NotificationType notificationType);


     ApiResponse<List<Notification>> getUserNotifications(CustomUserDetails currentUser);

     Notification saveTransactionNotification(Transaction transaction, NotificationType notificationType);
}
