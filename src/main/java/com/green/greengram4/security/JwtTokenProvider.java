package com.green.greengram4.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final String secret;
    private final String headerSchemeName;
    private final String tokenType;
    private Key key;

    public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret
                        , @Value("${springboot.jwt.header-scheme-name}") String headerSchemeName
                        , @Value("${springboot.jwt.token-type}") String tokenType
    ) {
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
        this.tokenType = tokenType;
    }

    @PostConstruct
    public void init() {
        log.info("secret: {}", secret);
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        log.info("keyBytes: {}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(MyPrincipal principal, long tokenValidMs) {
        return Jwts.builder()
                .claims(createClaims(principal))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidMs))
                .signWith(this.key)
                .compact();
    }

    private Claims createClaims(MyPrincipal principal) {
        return Jwts.claims()
                .add("iuser", principal.getIuser())
                .build();
    }

    public String resolveToken(HttpServletRequest req) {
        String auth = req.getHeader(headerSchemeName);
        if(auth == null) { return null; }

        //Bearer Askladjflaksjfw309jasdklfj
        if(auth.startsWith(tokenType)) {
            return auth.substring(tokenType.length()).trim();
        }
        return null;
        //return auth == null ? null : auth.startsWith();
    }
}
