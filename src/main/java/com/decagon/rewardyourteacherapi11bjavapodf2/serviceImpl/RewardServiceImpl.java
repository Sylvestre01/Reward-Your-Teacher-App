package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.EmailDetails;
import com.decagon.rewardyourteacherapi11bjavapodf2.enums.NotificationType;
import com.decagon.rewardyourteacherapi11bjavapodf2.enums.Role;
import com.decagon.rewardyourteacherapi11bjavapodf2.enums.TransactionType;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.InsufficientAmountException;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.WalletNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Notification;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Transaction;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Wallet;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.NotificationRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.TransactionRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.UserRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.WalletRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.CustomUserDetails;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.EmailService;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    private final NotificationRepository notificationRepository;

    private final TransactionRepository transactionRepository;

    private final EmailService emailService;

    @Override
    public ApiResponse<String>rewardTeacher(CustomUserDetails currentUser, Long teacherId, BigDecimal amount) {
            User user = userRepository.findUserByEmail(currentUser.getUsername()).orElseThrow(()-> new UserNotFoundException(currentUser.getUsername() + " not found"));
            Wallet userWallet = walletRepository.findWalletByUser(user).orElseThrow(()-> new WalletNotFoundException("Wallet Not Found"));

            User teacher = userRepository.findByIdAndRole(teacherId, Role.TEACHER);
            Optional<Wallet> teacherWallet = Optional.ofNullable(walletRepository.findWalletByUser(teacher).orElseThrow(() -> new WalletNotFoundException("Wallet Not Found")));

            if(userWallet.getAmount().compareTo(amount) >= 0){
                userWallet.setAmount(userWallet.getAmount().subtract(amount));
                teacherWallet.get().setAmount(teacherWallet.get().getAmount().add(amount));
                walletRepository.save(userWallet);
                walletRepository.save(teacherWallet.get());
                String messageToStudent = "You sent ₦" + amount + " to " +   teacher.getName();
                String messageToTeacher = user.getName() + " sent you ₦" + amount;
                notificationRepository.save(new Notification(messageToStudent, NotificationType.DEBIT, user));
                notificationRepository.save(new Notification(messageToTeacher,NotificationType.CREDIT, teacher));

                EmailDetails userEmailDetailsStudent = new EmailDetails(currentUser.getUsername(), messageToStudent + "\nKind regards, \n@Reward App", "Debit Alert");
                EmailDetails userEmailDetailsTeacher = new EmailDetails(teacher.getEmail(), messageToTeacher + "\nKind regards, \n@Reward App", "Credit Alert");
                emailService.sendSimpleMail(userEmailDetailsStudent);
                emailService.sendSimpleMail(userEmailDetailsTeacher);

                Transaction transaction = new Transaction();
                transaction.setDescription(messageToTeacher);
                transaction.setTransactionType(TransactionType.CREDIT);
                transaction.setAmount(amount);
                transaction.setSender(user);
                transaction.setReceiver(teacher);
                transactionRepository.save(transaction);

                Transaction transaction1 = new Transaction(1L, TransactionType.DEBIT, messageToStudent, amount, teacher, user);
                transactionRepository.save(transaction1);

                return new ApiResponse<>("Transfer successful", LocalDateTime.now(), messageToStudent);
            }
            else{
                throw new InsufficientAmountException("Insufficient Fund");
            }


        }
    }

