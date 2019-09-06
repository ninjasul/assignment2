package com.assignment.support.security;

import com.assignment.support.service.AccountService;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JwtTokenProvider {

    public static final String TOKEN_PREFIX = "Bearer ";
    private static final long TOKEN_VALID_MILLISEC = 60000L;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Autowired
    private AccountService accountService;

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + TOKEN_VALID_MILLISEC))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        return Optional.of(request)
                        .map(req -> req.getHeader(HttpHeaders.AUTHORIZATION))
                        .map(header -> header.replace(TOKEN_PREFIX, ""))
                        .orElse(null);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claims.getBody().getExpiration().compareTo(new Date()) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = accountService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }
}