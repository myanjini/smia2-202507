package com.optimagrowth.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.member.dto.JoinDto;
import com.optimagrowth.member.dto.LoginDto;
import com.optimagrowth.member.dto.MemberEntity;
import com.optimagrowth.member.service.MemberService;

@RestController
public class MemberController {
	@Autowired
	MemberService memberService;
	
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
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
		boolean isLogin = memberService.login(loginDto);
		if (isLogin) {
			return ResponseEntity.status(HttpStatus.OK).body("정상적으로 로그인되었습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 사용자가 없습니다.");
		}
	}	
}
