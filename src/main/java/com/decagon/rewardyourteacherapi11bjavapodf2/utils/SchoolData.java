package com.decagon.rewardyourteacherapi11bjavapodf2.utils;

import com.decagon.rewardyourteacherapi11bjavapodf2.model.School;
import com.decagon.rewardyourteacherapi11bjavapodf2.repository.SchoolRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.*;
@Data
@RequiredArgsConstructor
@Service
public class SchoolData {
    private final SchoolRepository schoolRepository;
    public void readCSVFileAndPopulateSchoolTable(String csv) throws FileNotFoundException {
        String data = csv;
        File file = new File(data);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        try {
            while ((line = bufferedReader.readLine())!=null)
            {
                String[] lineArray = line.split(",");
                School school = new School(lineArray[0], lineArray[1], lineArray[2]);
                schoolRepository.save(school);
            }
            System.out.println("All data uploaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

