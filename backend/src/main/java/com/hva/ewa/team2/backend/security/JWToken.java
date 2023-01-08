package com.hva.ewa.team2.backend.security;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import com.hva.ewa.team2.backend.Config;
import com.hva.ewa.team2.backend.domain.models.user.User;
import io.jsonwebtoken.*;

public class JWToken {

    private static final String JWT_ISSUER_CLAIM = "iss";
    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS_CLAIM = "ipa";
    public static final String JWT_ATTRIBUTE_NAME = "JWTokenInfo";

    private Integer userId;
    private User.Role role;
    private String ipAddress;

    public JWToken(Integer userId, User.Role role) {
        this.userId = userId;
        this.role = role;
    }

    public String encode(String issuer, String passphrase, int expiration) {
        Key key = getKey(passphrase);

        return Jwts.builder()
                .claim(JWT_USERID_CLAIM, this.userId)
                .claim(JWT_ROLE_CLAIM, this.role)
                .claim(JWT_IPADDRESS_CLAIM, this.ipAddress != null ? this.ipAddress : "1.1.1.1")
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    public static JWToken decode(String token, String issuer, String passphrase)
            throws ExpiredJwtException, MalformedJwtException {
        // Validate the token string and extract the claims
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token);
        Claims claims = jws.getBody();

        if (!claims.get(JWT_ISSUER_CLAIM).toString().equals(issuer)) {
            throw new MalformedJwtException("Invalid issuer");
        }
        // build our token from the extracted claims
        JWToken jwToken = new JWToken(
                Integer.valueOf(claims.get(JWT_USERID_CLAIM).toString()),
                User.Role.valueOf(claims.get(JWT_ROLE_CLAIM).toString())
        );
        jwToken.setIpAddress((String) claims.get(JWT_IPADDRESS_CLAIM));
        return jwToken;
    }

    public JWToken(Integer userId, User.Role role, String sourceIpAddress) {
        this(userId, role);
        this.setIpAddress(sourceIpAddress);
    }

    public static String getIpAddress(HttpServletRequest request) {
        // obtain the source IP-address of the current request
        String ipAddress = null;
        ipAddress = request.getHeader(Config.IP_FORWARDED_FOR);
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}