package com.optimagrowth.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimagrowth.member.dto.JoinDto;
import com.optimagrowth.member.dto.LoginDto;
import com.optimagrowth.member.dto.MemberEntity;
import com.optimagrowth.member.repository.MemberRepository;
import com.optimagrowth.member.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	@Autowired
	MemberRepository memberRepository;

	@Autowired
	JwtUtils jwtUtils;
	
	public MemberEntity join(JoinDto joinDto) {
		// 패스워드 일치 여부를 체크
		if (!joinDto.isPasswordEquals()) 
			return null;
		
		// TODO. 패스워드는 암호화해서 저장
		MemberEntity entity = new MemberEntity();
		entity.setMemberId(joinDto.getMemberId());
		entity.setMemberPassword(joinDto.getMemberPassword());
		entity.setMemberName(joinDto.getMemberName());
		entity.setMemberEmail(joinDto.getMemberEmail());
		
		return memberRepository.save(entity);
	}
	
	public Optional<MemberEntity> login(LoginDto loginDto) {
		// TODO. 스프링 시큐리티 적용 시 패스워드 일치 여부를 확인하는 방식으로 변경
		// return memberRepository.existsByMemberIdAndMemberPassword(loginDto.getMemberId(), loginDto.getMemberPassword());
		return memberRepository.findByMemberIdAndMemberPassword(loginDto.getMemberId(), loginDto.getMemberPassword());
	}
	
	public boolean validate(HttpServletRequest request, String memberId) {
		// userId에 해당하는 사용자 정보를 조회
		Optional<MemberEntity> member = memberRepository.findById(memberId);
		if (!member.isPresent())
			return false;
		
		// 요청 헤더에서 Authorization 헤더의 값을 추출
		String authorization = request.getHeader("Authorization");
		if (authorization == null) 
			return false;
		
		// Bearer로 시작하는 확인
		if (!authorization.startsWith("Bearer"))
			return false;
		
		// JWT 토큰을 추출
		String jwt = authorization.substring(7);
		log.debug(jwt);
		
		return jwtUtils.validateToken(jwt, member.get()); 		
	}
}
