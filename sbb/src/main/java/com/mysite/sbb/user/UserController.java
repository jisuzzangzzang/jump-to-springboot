package com.mysite.sbb.user;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/signup") // GET으로 요청 시 회원 가입을 위한 템플릿을 렌더링
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup") // POST로 요청 시 회원가입 진행
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) { // 비밀번호1과 2가 동일한지 검증하는 로직
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다."); // 일치하지 않으면 오류 발생
									// "필드명", "오류코드", "에러메세지"
									// 대형 프로젝트인 경우 번역과 관리를 위해 오류코드를 잘 정의하여 사용해야 함
			return "signup_form";
		}
		
		try { // 사용자 ID 또는 이메일 주소가 동일할 경우 DataIntegrityViolationException 예외 발생
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		} catch (DataIntegrityViolationException e) { // DataIntegrityViolationException 예외 발생 시
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
		 // bindingResult.reject(오류코드, 오류메시지) -> 특정 필드의 오류가 아닌 일반적인 오류를 등록할 때 사용
			return "signup_form";
		} catch (Exception e) { // 다른 오류의 경우 해당 오류의 메세지를 출력하도록 함
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "singup_form";
		}
		
		userService.create(userCreateForm.getUsername(), userCreateForm.getEmail() , userCreateForm.getPassword1());
			return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form"; // login-from.html 렌더링하는 GET방식의 login 메소드 추가
							 // 실제 로그인을 진행하는 @PostMapping 방식의 메소드는 스프링 시큐리티가 대신 처리, 직접 구현 할 필요X
	}
}
