package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mysite.sbb.user.UserSecurityService;

import lombok.RequiredArgsConstructor;

	@RequiredArgsConstructor
	@Configuration // 스프링 환경설정 파일임을 의미하는 어노테이션
	@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 어노테이션
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	// QuestionController와 AnswerController에서 로그인 여부 판별하기 위해 사용했던 @PreAuthorize 어노테이션 사용하기 위해 반드시 필요

													
	public class SecurityConfig {
		
		private final UserSecurityService userSecurityService;
		
		@Bean
		public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/**").permitAll() // 인증되지 않은 모든 요청을 허락
																	 // 로그인하지 않아도 모든 페이지에 접근 가능
			.and() // http 객체 설정을 이어서 할 수 있게 하는 메소드
				.csrf().ignoringAntMatchers("/h2-console/**") // /h2-console/로 시작하는 URL은 CSRF 검증X
				
			.and()
				.headers()
				.addHeaderWriter(new XFrameOptionsHeaderWriter(
						XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
						// X-Frame-Options 헤더값을 sameorigin으로 설정하여 오류가 발생하지 않도록 함
						// sameorigin으로 설정시 frame에 포함된 페이지가 페이지를 제공하는 사이트와 동일한 경우,
						// 계속 사용할 수 있음.
			
			.and() // 스프링 시큐리티의 로그인 설정 담당
				.formLogin()
				.loginPage("/user/login") // 로그인 페이지의 URL
				.defaultSuccessUrl("/")   // 로그인 성공시 디폴트 페이지는 루트 URL(/)
			
			.and() 
				.logout() // 로그아웃 설정
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 URL
				.logoutSuccessUrl("/") // 로그아웃 성공 시 루트 URL(/)로 이동
				.invalidateHttpSession(true); // 로그아웃 시 생성된 세션 삭제
			
			return http.build();
		}
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Bean
		public AuthenticationManager authenticationManager // 스프링 시큐리티의 인증을 담당
			  // bean 생성 시 스프링 내부 동작으로 인해 위에서 작성한 UserSecurityService와 PasswordEncoder 자동 설정
			  (AuthenticationConfiguration authenticationConfiguration) throws Exception {
			return authenticationConfiguration.getAuthenticationManager();
		}
}
