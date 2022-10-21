package com.decagon.rewardyourteacherapi11bjavapodf2;

import com.decagon.rewardyourteacherapi11bjavapodf2.repository.SchoolRepository;
import com.decagon.rewardyourteacherapi11bjavapodf2.utils.SchoolData;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Bean;
import java.io.FileNotFoundException;


@EnableWebMvc
@SpringBootApplication
@AllArgsConstructor
public class RewardYourTeacherApi11BJavaPodF2Application {
    private final SchoolData schoolData;
    private final SchoolRepository schoolRepository;

    public static void main(String[] args) {
        SpringApplication.run(RewardYourTeacherApi11BJavaPodF2Application.class, args);

    }

    @Bean
    CommandLineRunner runner () throws FileNotFoundException {
        if(schoolRepository.findAll().size() < 1){
            schoolData.readCSVFileAndPopulateSchoolTable(("src/main/resources/School Data.csv"));}
        return null;
    }


}
