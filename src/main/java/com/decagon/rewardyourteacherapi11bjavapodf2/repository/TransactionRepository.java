package com.decagon.rewardyourteacherapi11bjavapodf2.repository;

import com.decagon.rewardyourteacherapi11bjavapodf2.enums.TransactionType;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Transaction;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transaction WHERE user_id = ?1 ORDER BY create_date DESC ", nativeQuery = true)
    List<Transaction> findUserTransactionHistory(Long userId);

    List<Transaction> findTop5ByReceiverAndTransactionTypeOrderByCreateDateDesc(User user, TransactionType type);
    List<Transaction> findAllTransactionBySenderAndTransactionType(User user, TransactionType type);





}
