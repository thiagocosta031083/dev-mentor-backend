package com.thiagocosta.devmentor.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private final Key key; private final long expiration;
    public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-millis}") long expiration){
        this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.expiration=expiration;}
    public String gerar(String email){Date now=new Date();return Jwts.builder().setSubject(email).setIssuedAt(now)
            .setExpiration(new Date(now.getTime()+expiration)).signWith(key,SignatureAlgorithm.HS256).compact();}
    public String extrairEmail(String token){return claims(token).getSubject();}
    public boolean valido(String token,String email){Claims c=claims(token);return email.equals(c.getSubject())&&c.getExpiration().after(new Date());}
    private Claims claims(String token){return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();}
}
