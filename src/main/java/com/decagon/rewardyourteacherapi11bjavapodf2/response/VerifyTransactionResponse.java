package com.decagon.rewardyourteacherapi11bjavapodf2.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyTransactionResponse {

    private String status;
    private String message;
    private Data data;

}
