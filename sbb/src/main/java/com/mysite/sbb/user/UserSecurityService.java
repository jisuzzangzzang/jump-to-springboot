package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
		  // UserSecurityService : 스프링 시큐리티 로그인 처리의 핵심 부분
		  // UserDetailsService : loadUserByUsername 메소드를 구현하도록 강제하는 인터페이스
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
					// 사용자명으로 비밀번호를 조회하여 리턴하는 메소드
		Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
		
		if (_siteUser.isEmpty()) { // 사용자명에 해당되는 데이터가 없을 경우 오류 발생
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		SiteUser siteUser = _siteUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if ("admin".equals(username)) { // admian경우 admin 권한 부여
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {						// 그 외 user 권한 부여
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities); // user 객체 생성하여 리턴
	}

}
