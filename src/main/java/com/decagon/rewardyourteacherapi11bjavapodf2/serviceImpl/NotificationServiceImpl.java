package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.NotificationType;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Notification;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Transaction;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.NotificationRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.UserRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    @Override
    public Notification saveNotification(User user, String message, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setNotificationType(notificationType);
        notification.setNotificationBody(message);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification saveTransactionNotification(Transaction transaction, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setNotificationBody(transaction.getDescription());
        notification.setNotificationType(notificationType);
        notification.setUser(transaction.getReceiver());
        return notificationRepository.save(notification);
    }

    @Override
    public ApiResponse<List<Notification>> getUserNotifications(CustomUserDetails currentUser) {
        User user = userRepository.findUserByEmail(currentUser.getUsername()).orElseThrow(() -> new UserNotFoundException("User with email " + currentUser.getUsername() + " not found!"));
        List<Notification> notifications = notificationRepository.getNotificationsByUserOrderByCreateDateDesc(user);
        return new ApiResponse<>("notifications successfully retrieved", LocalDateTime.now(), notifications);

    }
}
