package com.decagon.rewardyourteacherapi11bjavapodf2.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.http.parser.Authorization;

import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class Data {
    private BigDecimal amount;
    private String currency;
    private String transaction_date;
    private String status;
    private String reference;
    private String domain;
    private String gateway_response;
    private String message;
    private String channel;
    private String ip_address;
    private String fees;
    private String plan;
    private String paid_at;
    @JsonIgnore
    private Authorization authorization;
}


