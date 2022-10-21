package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;
import com.decagon.rewardyourteacherapi11bjavapodf2.dto.PaymentRequest;
import com.decagon.rewardyourteacherapi11bjavapodf2.enums.NotificationType;
import com.decagon.rewardyourteacherapi11bjavapodf2.enums.TransactionType;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.WalletNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Transaction;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Wallet;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.TransactionRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.UserRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.WalletRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.PaymentResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.TransactionResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.VerifyTransactionResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.NotificationService;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.TransactionService;
import com.decagon.rewardyourteacherapi11bjavapodf2.utils.AuthDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;
    private final AuthDetails authDetails;
    private final HashMap<String,String> referenceTracker = new HashMap<>();
    @Value("${secret_key}")
    private String PAYSTACK_SECRET_KEY;
    @Value("${paystack_url}")
    private String PAYSTACK_BASE_URL;
    @Value("${verification_url}")
    private String PAYSTACK_VERIFY_URL;
    @Override
    public PaymentResponse initDeposit(PaymentRequest paymentRequest) throws Exception {
        User user = userRepository.findUserByEmail(authDetails.getAuthorizedUserEmail()).orElseThrow(() -> new UserNotFoundException(authDetails.getAuthorizedUserEmail()));
        PaymentResponse paymentResponse;
        paymentRequest.setAmount(paymentRequest.getAmount() * 100);
        paymentRequest.setEmail(user.getEmail());
        try {
            Gson gson = new Gson();
            StringEntity entity = new StringEntity(gson.toJson(paymentRequest));
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_BASE_URL);
            post.setEntity(entity);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
            StringBuilder result = new StringBuilder();
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = responseReader.readLine()) != null) {
                    System.out.println(line);
                    result.append(line);
                }
            } else {
                throw new AuthenticationException("Error occurred while initializing transaction");
            }
            ObjectMapper mapper = new ObjectMapper();
            paymentResponse = mapper.readValue(result.toString(), PaymentResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while initializing transaction");
        }
        referenceTracker.put(paymentResponse.getData().getReference(), user.getEmail());
        return paymentResponse;
    }
    @Override
    public VerifyTransactionResponse verifyTransaction(String reference) {
        Transaction transaction = new Transaction();
        VerifyTransactionResponse payStackResponse = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY_URL + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Error Occurred while connecting to PayStack URL");
            }
            ObjectMapper mapper = new ObjectMapper();
            payStackResponse = mapper.readValue(result.toString(), VerifyTransactionResponse.class);
            if (payStackResponse == null || payStackResponse.getStatus().equals("false")) {
                throw new Exception("An error occurred while  verifying payment");
            } else if (payStackResponse.getData().getStatus().equals("success")) {
                String email = referenceTracker.get(reference);
                fundWallet( email , payStackResponse.getData().getAmount().divide(BigDecimal.valueOf(100)));
                referenceTracker.remove(reference);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return payStackResponse;
    }

    @Override
    public ApiResponse<List<TransactionResponse>> findAllTransactionByReceiver() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = ((UserDetails) principal).getUsername();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("User with em " + userEmail + " not found"));
        List<Transaction> transactions = transactionRepository.findTop5ByReceiverAndTransactionTypeOrderByCreateDateDesc(user,TransactionType.CREDIT);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction transaction: transactions){
            TransactionResponse transactionResponse = new TransactionResponse(transaction);
            transactionResponses.add(transactionResponse);
        }
        return new ApiResponse<>("success", LocalDateTime.now(), transactionResponses);
    }


    private void fundWallet(String email, BigDecimal amount) {
        Wallet wallet = walletRepository.findWalletByUserEmail(email).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
        wallet.setAmount(wallet.getAmount().add(amount));
        walletRepository.save(wallet);
        String description = "You funded your wallet with ₦" + amount;
        notificationService.saveTransactionNotification(saveNewTransaction(description, email, email), NotificationType.FUND_PERSONAL_WALLET);
    }
    private Transaction saveNewTransaction(String desc, String senderEmail, String receiverEmail) {
        User sender = userRepository.findUserByEmail(senderEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
        User receiver = userRepository.findUserByEmail(receiverEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setDescription(desc);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        return transactionRepository.save(transaction);
    }

    @Override
    public ApiResponse<BigDecimal> totalMoneySentByStudent(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = ((UserDetails) principal).getUsername();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("User with em " + userEmail + " not found"));
        List<Transaction> transactions = transactionRepository.findAllTransactionBySenderAndTransactionType(user, TransactionType.DEBIT);
        if (transactions.isEmpty()){
            return new ApiResponse<>("User have not made any transactions", LocalDateTime.now());
        }
        BigDecimal amount = new BigDecimal(0);
        for (Transaction transaction : transactions){
            if (transaction.getTransactionType().equals(TransactionType.DEBIT)){
                amount = amount.add(transaction.getAmount());
            }
        }
        return new ApiResponse<>("Success", LocalDateTime.now(), amount);
    }
}