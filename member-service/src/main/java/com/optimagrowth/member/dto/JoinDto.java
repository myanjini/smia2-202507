package com.optimagrowth.member.dto;

import lombok.Data;

@Data
public class JoinDto {
	private String memberId;
	private String memberPassword;
	private String memberPasswordConfirm;
	private String memberName;
	private String memberEmail;
	
	public boolean isPasswordEquals() {
		if (memberPassword != null)
			return memberPassword.equals(memberPasswordConfirm);
		return false;
	}
}
