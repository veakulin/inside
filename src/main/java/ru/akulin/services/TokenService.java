package ru.akulin.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TokenService {

    private final Key key;

    public TokenService() {
        // Приготовим ключик. Конечно, его надо хранить в каком-то надёжном месте, но проект-то тестовый
        byte[] keyBytes = new byte[32];
        for (byte i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = i;
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String login) {

        Date date = Date.from(LocalDate.now().plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims decodeToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
