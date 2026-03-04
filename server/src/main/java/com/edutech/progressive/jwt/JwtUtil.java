package com.edutech.progressive.jwt;
 
import com.edutech.progressive.entity.User;
import com.edutech.progressive.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
 
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
@Component
public class JwtUtil {
 
    private final UserRepository userRepository;
 
    private final String secret = "secretKey0000_super_secret_change_me_for_prod";
    private final int expiration = 86400;
 
    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
 
    public String generateToken(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                    .compact();
        }
        return generateTokenFor(user);
    }
 
    public String generateTokenFor(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRole());
        claims.put("userId", user.getUserId());
        claims.put("patientId", user.getPatient() != null ? user.getPatient().getPatientId() : null);
        claims.put("doctorId", user.getDoctor() != null ? user.getDoctor().getDoctorId() : null);
 
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
 
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
 
    private boolean isTokenExpired(String token) {
        final Date expirationDate = extractAllClaims(token).getExpiration();
        return expirationDate.before(new Date());
    }
 
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractAllClaims(token).getSubject();
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}