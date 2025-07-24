package com.optimagrowth.member.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.member.dto.JoinDto;
import com.optimagrowth.member.dto.LoginDto;
import com.optimagrowth.member.dto.MemberEntity;
import com.optimagrowth.member.service.MemberService;
import com.optimagrowth.member.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {
	@Autowired
	MemberService memberService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody JoinDto joinDto) {
		MemberEntity entity = memberService.join(joinDto);
		if (entity == null) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("패스워드가 일치하지 않습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body("정상적으로 가입되었습니다.");
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
		Optional<MemberEntity> member = memberService.login(loginDto);
		if (member.isPresent()) {
			String token = jwtUtils.generateToken(member.get());
			response.setHeader("token", token);
			
			return ResponseEntity.status(HttpStatus.OK).body("정상적으로 로그인되었습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 사용자가 없습니다.");
		}
	}	
	
	@GetMapping("/validate/{memberId}")
	public ResponseEntity<String> validate(@PathVariable("memberId") String memberId, HttpServletRequest request) {
		boolean isValid = false;
		try {
			isValid = memberService.validate(request, memberId);
		} catch (Exception e) {
			log.error(e.toString());
		}
		if (isValid) {
			return ResponseEntity.status(HttpStatus.OK).body("VALID");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INVALID");
		}
	}
}
