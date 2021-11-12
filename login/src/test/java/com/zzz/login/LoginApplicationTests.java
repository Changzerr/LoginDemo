package com.zzz.login;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
class LoginApplicationTests {

    @Test
    void contextLoads() {
    }
    private String SING = "adADafnjk21^TJSVFD%$JFGE&^%DGHJDF^&min";
    @Test
    public void jwt(){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("username","tom")
                .claim("role","admin")
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256,SING)
                .compact();

        System.out.println(jwtToken);
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsImV4cCI6MTYzNjc4NTc5MiwianRpIjoiYmM5YjgxZjMtYTk1NC00NDg2LWIwZDQtNjEwNDU0NWI5MzMzIn0.Hviz_uOTcYjF_-Wmj1KrzbJdEixB89XuVkMg0CwKO3A
    }

    @Test
    public void parse() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsImV4cCI6MTYzNjc4NTc5MiwianRpIjoiYmM5YjgxZjMtYTk1NC00NDg2LWIwZDQtNjEwNDU0NWI5MzMzIn0.Hviz_uOTcYjF_-Wmj1KrzbJdEixB89XuVkMg0CwKO3A";
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(SING).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());

    }
}
