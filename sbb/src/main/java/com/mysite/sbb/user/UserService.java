package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository; // UserRepository 사용
	private final PasswordEncoder passwordEncoder; // 빈으로 등록한 PasswordEncoder 객체를 주입받아 사용
	
	public SiteUser create(String username, String email, String password) { // User 데이터 저장하는 create 메소드
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		/* 
		 - 객체를 new로 생성하는 것보다 Bean으로 등록해서 사용하는 것이 좋음.
		 - 암호화 방식을 변경하면 BCryptPasswordEncoder를 사용한 모든 프로그램을 일일이 찾아서 수정해야함
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 비밀번호는 보안을 위해 암호화하여 저장
	 // BCryptPasswordEncoder -> BCryptPasswordEncoder는 BCrypt 해싱 함수(BCrypt hashing function) 사용하여 비밀번호 암호화
	 // passwordEncoder -> BCryptPasswordEncoder의 인터페이스
		*/
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
		
	}
	
	public SiteUser getUser(String username) { // User 서비스를 통해 SiteUser 조회할 수 있는 getUser 메소드
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		
		if (siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
}
