package com.leverx.courseapp.security.jwt;

import com.leverx.courseapp.security.util.UserDetailsServiceImpl;
import com.leverx.courseapp.user.model.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long validitiTime;

    @NonNull
    @Autowired
    private final UserDetailsServiceImpl userDetailsService;



    @PostConstruct
    public void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, Collection<GrantedAuthority> authorities) {
        var claims = Jwts.claims().setSubject(username);
        claims.put("roles", authorities);
        var now = new Date();
        var validity = new Date(now.getTime() + validitiTime);
        var token = Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(SignatureAlgorithm.HS256, secret).compact();
        return token;
    }

    public Authentication getAuthentification(String token) {
        var userDetails = userDetailsService.loadUserByUsername(receiveUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String receiveUsername(String token) {
        var username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        return username;
    }

    public String resolveToken(HttpServletRequest request){
        var bearerToken = request.getHeader("Authruzstion");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        if (claims.getBody().getExpiration().before(new Date())) {
            return false;
        } else {
            return true;
        }

    }

}
