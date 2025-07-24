package com.optimagrowth.member.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "member")
public class MemberEntity {
	@Id
	@Column(name = "member_id", nullable = false)
	private String memberId;
	
	@Column(name = "member_password", nullable = false)
	private String memberPassword;
	
	@Column(name = "member_name", nullable = false)
	private String memberName;
	
	@Column(name = "member_email", nullable = false)
	private String memberEmail;
}
