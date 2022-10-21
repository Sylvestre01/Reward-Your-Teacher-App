package com.decagon.rewardyourteacherapi11bjavapodf2.EventListeners;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class OnUserLogoutSuccessListener extends ApplicationEvent {

    @Serial
    private static final Long serialVersionID = 1L;

    private final String email;

    private final String token;

    private final Date timeOfTime;

    public OnUserLogoutSuccessListener(String email, String token) {
        super(email);
        this.email = email;
        this.token = token;
        this.timeOfTime = Date.from(Instant.now());
    }
}
