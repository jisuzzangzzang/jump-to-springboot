package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	// 해당 클래스의 컨트롤러 기능 수행
public class HelloController {
	@RequestMapping("/hello") // http://localhost:8080/hello URL 요청 발생 -> hello 메소드실행
							  // URL과 hello 메소드 맵핑 역할
							  // URL명과 메소드명은 동일할 필요 X
	@ResponseBody		      // hello 메소드의 응답 결과가 문자열 그 자체임을 나타냄
	
	public String hello() {
		return "Hello Spring Boot Board";
	}

}
