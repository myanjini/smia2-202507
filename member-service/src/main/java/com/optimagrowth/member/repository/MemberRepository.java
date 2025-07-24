package com.optimagrowth.member.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.optimagrowth.member.dto.MemberEntity;

@Repository
public interface MemberRepository extends CrudRepository<MemberEntity, String> {
	// memberId와 memberPassword가 일치하는 경우 Optional로 반환
	Optional<MemberEntity> findByMemberIdAndMemberPassword(String memberId, String memberPassword);
	
	// 존재 여부만 확인
	boolean existsByMemberIdAndMemberPassword(String memberId, String memberPassword);
}
