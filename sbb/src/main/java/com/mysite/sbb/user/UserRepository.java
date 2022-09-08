package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
												   // SiteUser의 PK의 타입은 Long
	Optional<SiteUser> findByUsername(String username); // 사용자를 조회하는 기능
}