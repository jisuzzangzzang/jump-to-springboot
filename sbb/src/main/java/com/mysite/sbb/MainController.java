package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 컨트롤러
public class MainController {

	@RequestMapping("/sbb")	// URL과의 매핑 담당
	@ResponseBody			// URL 요청에 대한 응답으로 문자열을 리턴
	public String index() {
		// 문자열을 브라우저에 출력하기 위해 함수의 리턴값을 String으로 변경
		// return "index"; // "index" 문자열 리턴
		return "안녕하세요 sbb에 오신 것을 환영합니다.";
		
		
		// ORM (개발자가 쿼리를 직접 작성하지 않아도 데이터베이스의 데이터 처리 가능)
/*
		   - 쿼리 작성 시
		   insert into question (subject, content) values ('안녕하세요', '가입 인사드립니다^^');
		   insert into question (subject, content) values ('질문 있습니다', 'ORM이 궁금합니다^^');

		   - 자바 코드로 작성 시
		   Question q1 = new Question();
		   q1.setSubject("안녕하세요");
		   q1.setContent("가입 인사드립니다^^");
		   this.questionRepository.save(q1);
		   
		   Question q2 = new Question();
		   q1.setSubject("질문 있습니다");
		   q1.setContent("ORM이 궁금합니다^^");
		   this.questionRepository.save(q2);
		   
		   - ORM의 장점
		  	 · 데이터 베이스 종류에 상관 없이 일관된 코드 유지 가능 (유지·보수 편리)
		     · 내부에서 안전한 SQL 쿼리를 자동으로 생성해주므로 개발자가 달라도 통일된 쿼리 작성 가능
		     · 오류 발생률 줄일 수 있음
*/
		
		
		// JPA (Java Persistence API를 사용하여 데이터베이스를 처리)
/*
		   - 자바 진영에서 ORM (Object-Relational Mapping)의 기술 표준으로 사용하는 인터페이스 모음
		   - 인터페이스이므로, 구현하는 실제 클래스가 필요함
		   - 대표적으로는 하이버네이트(Hibernate)
*/
	}
}

