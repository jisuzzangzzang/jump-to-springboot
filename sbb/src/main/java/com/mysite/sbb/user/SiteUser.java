package com.mysite.sbb.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser { // 스프링 시큐리티에 User 클래스가 있으므로 SiteUser로 함
						// 패키지명이 달라 사용할 수 있으나 패키지 오용으로 인한 오류가 발생할 수 있으므로 User 대신 사용
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true) // 유일한 값만 저장 (값을 중복되게 저장할 수 없음)
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;
	
}
