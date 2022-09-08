package com.mysite.sbb.user;

import lombok.Getter;

@Getter // 상수 자료형이므로 Setter없이 Getter만 사용 가능하도록 작성
public enum UserRole { // ADMIN, USER 2개의 권한을 갖는 UserRole (사용자에게 권한 부여)
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	private UserRole(String value) {
		this.value = value;
	}
	
	private String value;

}
