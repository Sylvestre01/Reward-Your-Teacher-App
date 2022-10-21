package com.decagon.rewardyourteacherapi11bjavapodf2.security;

import com.decagon.rewardyourteacherapi11bjavapodf2.cache.LoggedOutJwtTokenCache;
import com.decagon.rewardyourteacherapi11bjavapodf2.EventListeners.OnUserLogoutSuccessEvent;
import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.InvalidTokenRequestException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service @Slf4j
public class JwtUtil {

    @Value("${jwt.signing.key.secret:decagonsquad11bpode}")
    private  String secret ;
   @Autowired
   @Lazy
    private LoggedOutJwtTokenCache tokenBlackListCache;

  //  @Value("${jwt.token.expiration.in.seconds}")
    // todo : reduce the expirationTime to the barest minimum say 5 mins.
    private final  int expirationTime = 1000 * 60 * 60 * 10;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        // todo : check if token is blacklisted -> if no process. else return false
        validateTokenIsNotForALoggedOutDevice(token);
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateToken(String token) {
        // todo : check if token is blacklisted -> if no process. else return false
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            validateTokenIsNotForALoggedOutDevice(token);
            return true;
        }catch(MalformedJwtException e){
            log.error("invalid jwt token -> message: {}", e.getMessage() ) ;
        }catch(ExpiredJwtException e){
            log.error("Expired jwt token -> message: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("Unsupported jwt token -> message: {}", e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("Jwt claims string is empty -> message: {}", e.getMessage());
        }
        return false;
    }


    private void validateTokenIsNotForALoggedOutDevice(String authToken) {
        OnUserLogoutSuccessEvent previouslyLoggedOutEvent = tokenBlackListCache.getLogoutEventForToken(authToken);
        if (previouslyLoggedOutEvent != null) {
            String userEmail = previouslyLoggedOutEvent.getUserEmail();
            Date logoutEventDate = previouslyLoggedOutEvent.getEventTime();
            String errorMessage = String.format("Token corresponds to an already logged out user [%s] at [%s]. Please login again", userEmail, logoutEventDate);
            throw new InvalidTokenRequestException("JWT", authToken, errorMessage);
        }
    }
}
