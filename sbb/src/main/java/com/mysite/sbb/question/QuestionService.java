package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 				 // 스프링부트가 서비스로 인식하기 위해 어노테이션 작성
public class QuestionService {
	
	private final QuestionRepository questionRepository; // 생성자 방식으로 DI 규칙에 의해 주입
	
	public List<Question> getList() {	// 질문 목록 조회하여 리턴하는 getList 메소드 추가
		return this.questionRepository.findAll();
	}
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String subject, String content) { // 질문 데이터 저장 기능 추가
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q);
	}
}




// 서비스가 필요한 이유
   /*
   1. 모듈화
	  - 어떤 컨트롤러가 여러개의 레포지토리를 사용하여 데이터를 조회한 후 가공, 리턴한다고 가정
		-> 서비스로 만들어두면 컨트롤러에서 해당 서비스를 호출하여 사용
		-> 서비스로 만들지 않고 컨트롤러에서 구현하려 하면 해당 기능을 필요로 하는 모든 컨트롤러를 동일한 기능을 중복으로 구현해야함
		
   2. 보안
	  - 컨트롤러는 레포지토리 없이 서비스를 통해서만 데이터베이스에 접근하도록 구현하는 것이 안전
	  - 해킹을 통해 컨트롤러를 제어할 수 있게 되더라도 레포지토리에 직접 접근이 불가능
	  
   3. 엔티티 객체와 DTO 객체의 변환
   	  - Question, Answer (엔티티 클래스) : 데이터베이스와 직접 맞닿아있는 클래스
   	    -> 컨트롤러나 타임리프 같은 템플릿 엔진에 전달하여 사용하는 것은 좋지 않음
   	       속성을 변경하여 비즈니스적인 요구를 처리해야 하는 경우, 엔티티를 직접 사용하여 속성 변경 시
   	       테이블 컬럼이 변경되어 엉망이 될 수도 있기 때문
   	  - 엔티티 클래스는 컨트롤러에서 사용할 수 없게끔 설계하는 것이 바람직함
   	  - Question, Answer 대신 사용할 DTO(Data Transfer Object) 클래스,
   	  	앤티티 객체를 DTO객체로 변환하는 작업 필요 -> 서비스
   	  - 서비스는 컨트롤러와 레포지토리의 중간자적 입장에서 엔티티 객체와 DTO 객체를 변환하여 양방향에 전달하는 역할을 함
   	  	
    * 이 책에서는 별도의 DTO를 만들지 않고 엔티티 객체를 컨트롤러와 타임리프에서 그대로 사용함
    * 실제 업무 프로그램 작성 시 엔티티 클래스를 대신할 DTO 클래스를 만들어 사용 권장
    * 앞으로 작성할 컨트롤러들도 레포지토리를 직접 사용하지 않고 Controller -> Service -> Repository 구조로 데이터 처리 할 것임
   */