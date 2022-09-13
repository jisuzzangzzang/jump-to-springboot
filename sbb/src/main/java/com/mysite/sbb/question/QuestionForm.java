package com.mysite.sbb.question;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
	
	@NotEmpty(message="제목은 필수 입력 항목입니다.")
	// 해당 값이 Null 또는 빈 문자열("")을 허용하지 않음
	// message -> 검증이 실패할 경우 화면에 표시할 오류
	@Size(max=200)
	// 최대 길이가 200 byte를 넘으면 안됨. 200 byte 초과 시 오류 발생
	private String subject;
	
	@NotEmpty(message="내용은 필수 입력 항목입니다.")
	private String content;
	
}
