package com.optimagrowth.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimagrowth.member.dto.JoinDto;
import com.optimagrowth.member.dto.LoginDto;
import com.optimagrowth.member.dto.MemberEntity;
import com.optimagrowth.member.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	MemberRepository memberRepository;
	
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
	
	public boolean login(LoginDto loginDto) {
		// TODO. 스프링 시큐리티 적용 시 패스워드 일치 여부를 확인하는 방식으로 변경
		return memberRepository.existsByMemberIdAndMemberPassword(loginDto.getMemberId(), loginDto.getMemberPassword());
	}
}
