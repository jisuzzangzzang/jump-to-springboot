package com.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found") // DataNotFoundException 발생 시 어노테이션에 의해
																		   // 404 오류 (HttpStatus.NOT_FOUND) 나타남
public class DataNotFoundException extends RuntimeException { // RuntimeException을 상속하는 DataNotFoundException
	
	public static final long serialVersionUID = 1L;
	public DataNotFoundException(String message) {
		super(message);
	}
}
