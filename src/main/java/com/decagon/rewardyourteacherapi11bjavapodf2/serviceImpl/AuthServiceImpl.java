package com.decagon.rewardyourteacherapi11bjavapodf2.serviceImpl;

import com.decagon.rewardyourteacherapi11bjavapodf2.dto.*;
import com.decagon.rewardyourteacherapi11bjavapodf2.enums.Role;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.OAuth2AuthenticationException;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserAlreadyExistException;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.UserNotFoundException;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Subject;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Teacher;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.User;
import com.decagon.rewardyourteacherapi11bjavapodf2.model.Wallet;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.SubjectRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.TeacherRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.UserRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.WalletRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.UserRegistrationResponse;
import com.decagon.rewardyourteacherapi11bjavapodf2.security.JwtUtil;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.AuthService;
import com.decagon.rewardyourteacherapi11bjavapodf2.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static com.decagon.rewardyourteacherapi11bjavapodf2.utils.Constants.BASE_URL;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final TeacherRepository teacherRepository;

    private final SubjectRepository subjectRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;
    private final WalletRepository walletRepository;

    private final EmailService emailService;


    @Override
    public UserRegistrationResponse registerUser(UserDto userDto) {
        String email = userDto.getEmail();
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if(existingUser.isEmpty()){
            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setTelephone(userDto.getTelephone());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setSchool(userDto.getSchool());
            user.setRole(Role.STUDENT);
            userRepository.save(user);
            walletRepository.save( new Wallet(new BigDecimal("0"), user));
            return sendVerificationEmail(user);
        }else {
            throw new UserAlreadyExistException("User already exist");
        }
    }

    private UserRegistrationResponse sendVerificationEmail(User user){
        String token = jwtUtil.generateToken(user.getEmail());
        String msgBody = "Dear "+ user.getName() + ",\n\nThank you for registering to Reward Your Teacher App." +
                " Please verify your account with the link below. \n\n"
                + BASE_URL + "api/v1/auth/account-verification/" + token
                + "\n\nThank you!";
        String subject = "Verify User Account";
        EmailDetails emailDetails = new EmailDetails(user.getEmail(), msgBody, subject);
        emailService.sendSimpleMail(emailDetails);
        String success = "Registration successful. Please check email for verification";
        return new UserRegistrationResponse(success, LocalDateTime.now());
    }

    @Override
    public UserRegistrationResponse registerTeacher(TeacherRegistrationDto teacherDto)  {
        String email = teacherDto.getEmail();
        Optional<User> existingUser = userRepository.findUserByEmail(email);

        if(existingUser.isEmpty()){
            Teacher teacher = new Teacher();
            teacher.setName(teacherDto.getName());
            teacher.setEmail(teacherDto.getEmail());
            teacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
            teacher.setTelephone(teacherDto.getTelephone());
            teacher.setSchool(teacherDto.getSchool());
            teacher.setYearsOfService(teacherDto.getYearsOfService());
            teacher.setSchoolType(teacherDto.getSchoolType());
            teacher.setRole(Role.TEACHER);
            userRepository.save(teacher);
            walletRepository.save( new Wallet(new BigDecimal("0"), teacher));
            teacherDto.getSubjectList().forEach(subject -> {
                subjectRepository.save(new Subject(subject , teacher));
            });

            return sendVerificationEmail(teacher);
        }else{
            throw new UserAlreadyExistException("User already exist");
        }
    }

    @Override
    public ApiResponse<PrincipalDto> login(LoginDTO loginDTO) {
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
        } catch (AuthenticationException ex) {
            log.error(ex.getMessage());
            throw new UserNotFoundException("invalid username or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        User loggedInUser = userRepository.findUserByEmail(loginDTO.getEmail()).get();
        if (loggedInUser.getRole().equals(Role.STUDENT)) {
            return new ApiResponse<>("success", LocalDateTime.now(), new PrincipalDto(loggedInUser.getId(), loggedInUser.getName(), loggedInUser.getEmail(), loggedInUser.getRole(), jwtUtil.generateToken(loginDTO.getEmail()), loggedInUser.getSchool(), loggedInUser.getTelephone()));
        }else{
            Teacher loggedInTeacher = teacherRepository.findByEmail(loggedInUser.getEmail());
            return new ApiResponse<>("success", LocalDateTime.now(), new PrincipalDto(loggedInTeacher.getId(), loggedInTeacher.getName(), loggedInTeacher.getEmail(), loggedInTeacher.getRole(), jwtUtil.generateToken(loginDTO.getEmail()), loggedInTeacher.getSchool(), loggedInTeacher.getTelephone(), loggedInTeacher.getYearsOfService(), loggedInTeacher.getSchoolType()));
        }

    }
    @Override
    public ApiResponse<PrincipalDto> authenticateOAuth2User(OAuth2UserInfo auth2UserInfo) {
        User loggedInUser = userRepository.findUserByEmail(auth2UserInfo.getEmail()).orElseThrow(() ->
                new OAuth2AuthenticationException("Email not Found " + auth2UserInfo.getEmail())); //TODO
        String token = jwtUtil.generateToken(auth2UserInfo.getEmail());
        if (loggedInUser.getRole().equals(Role.STUDENT)) {
            return new ApiResponse<>("success", LocalDateTime.now(), new PrincipalDto(loggedInUser.getId(), loggedInUser.getName(), loggedInUser.getEmail(), loggedInUser.getRole(), token, loggedInUser.getSchool(), loggedInUser.getTelephone()));
        }else{
            Teacher loggedInTeacher = teacherRepository.findByEmail(loggedInUser.getEmail());
            return new ApiResponse<>("success", LocalDateTime.now(), new PrincipalDto(loggedInTeacher.getId(), loggedInTeacher.getName(), loggedInTeacher.getEmail(), loggedInTeacher.getRole(), token, loggedInTeacher.getSchool(), loggedInTeacher.getTelephone(), loggedInTeacher.getYearsOfService(), loggedInTeacher.getSchoolType()));
        }    }

    @Override
    public String verifyAccount(String token){
        boolean tokenIsValid = jwtUtil.validateToken(token);
        if (tokenIsValid){
            String email = jwtUtil.extractUsername(token);
            User user = userRepository.getUserByEmail(email);
            user.setAccountVerified(true);
            userRepository.save(user);
            return "Account activated successfully!" +
                    "\n\n\nPlease login with link below" +
                    "\n\n\n<a href='http://localhost:3000/login'>Login Here</a>";
        }else {
            return "Account not activated!";
        }
    }


}
