package com.mysite.sbb.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	@Size(min = 3, max = 25) // 길이가 3~25 사이여야 한다는 검증조건 (문자열의 길이가 해당되는지 검증)
	@NotEmpty(message = "사용자 ID는 필수 입력 항목입니다.")
	private String username;
	
	@NotEmpty(message = "비밀번호는 필수 입력 항목입니다.") // 회원가입 시 입력한 비밀번호가 정확한지 확인하기 위해 2개의 필드가 필요
	private String password1;
	
	@NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
	private String password2;
	
	@NotEmpty(message = "이메일은 필수 입력 항목입니다.")
	@Email	// 해당 속성 값이 이메일형식과 일치하는지 검증
	private String email;

}
