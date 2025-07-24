package com.optimagrowth.member.utils;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.optimagrowth.member.dto.MemberEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {
	private SecretKey hmacKey;
	private Long expirationTime;

	public JwtUtils(Environment env) {
		this.hmacKey = Keys.hmacShaKeyFor(env.getProperty("token.secret").getBytes());
		this.expirationTime = Long.parseLong(env.getProperty("token.expirationTime"));
	}

	public String generateToken(MemberEntity memberEntity) {
		Date now = new Date();

		String jwtToken = Jwts.builder().claim("name", memberEntity.getMemberName())
				.claim("email", memberEntity.getMemberEmail()).subject(memberEntity.getMemberEmail())
				.id(UUID.randomUUID().toString()).issuedAt(now)
				.expiration(new Date(now.getTime() + this.expirationTime)).signWith(this.hmacKey, Jwts.SIG.HS256)
				.compact();
		log.debug(jwtToken);

		return jwtToken;
	}

	private Claims getAllClaimsFromToken(String token) {
		Jws<Claims> jwt = Jwts.parser().verifyWith(this.hmacKey).build().parseSignedClaims(token);
		return jwt.getPayload();
	}

	private Date getExpirationDateFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.getExpiration();
	}

	private boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String getSubjectFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return claims.getSubject();
	}

	public boolean validateToken(String token, MemberEntity memberEntity) {
		// 토큰 유효기간 체크
		if (isTokenExpired(token)) {
			return false;
		}

		// 토큰 내용을 검증
		String subject = getSubjectFromToken(token);
		String email = memberEntity.getMemberEmail();

		return subject != null && email != null && subject.equals(email);
	}

}
