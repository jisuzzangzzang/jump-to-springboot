package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

// import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;


@SpringBootTest // SbbApplicationTests 클래스가 스프링부트 테스트 클래스임을 의미
class SbbApplicationTests {
	
	@Autowired 	// 스프링의 DI 기능으로 questionRepository 객체를 스프링이 자동으로 생성해줌
						 // DI (Dependency Injection) - 스프링이 객체를 대신 생성하여 주입
	private QuestionRepository questionRepository;
	
	// @Autowired
	// private AnswerRepository answerRepository; // 답변 데이터 처리를 위해서 답변 레포지토리가 필요하므로
											   // AnswerRepository 객체를 @Autowired 어노테이션 주입

	@Transactional // 메소드가 종료될 때 까지 DB 세션 유지
	@Test		// testJpa 메소드가 테스트 메소드임을 나타냄
	void testJpa() {
				
		
		// 데이터 저장하기
/*		
		Question q1 = new Question();		// 엔티티 객체 생성
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);	// 첫번째 질문 저장 (데이터베이스)
		
		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);	// 두번째 질문 저장
*/		
		
		
		
		// 테이블에 저장된 모든 데이터 조회 (findAll, assertEquals)
/*		
		List<Question> all = this.questionRepository.findAll();
												  // findAll -> 모든 데이터 조회
		assertEquals(2, all.size());			  // JUnit의 assertEquals(기대값, 실제값)이 동일한지 조사
												  // 동일하지 않다면 테스트는 실패
		
		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject()); // 첫번째 데이터의 제목이 일치하는지 조사
*/
		
		
		
		// Question의 엔티티의 Id값으로 데이터를 조회 (findById)
/*		
		Optional<Question> oq = this.questionRepository.findById(1);
													 // findById의 리턴 타입은 Question이 아닌 Optional
													 // Optional은 null을 유연하게 처리하기 위해 사용하는 클래스
		if(oq.isPresent()) {						 // isPresent로 null이 아닌지를 확인한 후에
			Question q = oq.get();					 // get으로 실제 Question 객체 값을 얻어야함
			assertEquals("sbb가 무엇인가요?", q.getSubject());
*/

		
		// Question 엔티티의 subject 값으로 데이터를 조회 (findBySubject)
/*
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
*/
		
		
		// 제목과 내용을 함께 조회 (findBySubjectAndContent)
/*		
		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
*/
		
		
		// 제목에 특정 문자열이 포함되어 있는 데이터 조회 (findBySubjectLike)
/*		
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
																	   // sbb%: sbb로 시작하는 문자열
																	   // %sbb: sbb로 끝나는 문자열
																	   // %sbb%: sbb를 포함하는 문자열
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
*/
		
		
		
		// 질문 데이터 수정
/*		
		Optional<Question> oq = this.questionRepository.findById(1); // 질문 데이터 조회
		assertTrue(oq.isPresent()); // 값이 true인지 테스트
		Question q = oq.get();
		q.setSubject("수정된 제목"); // 수정된 제목이라는 값으로 수정
		this.questionRepository.save(q); // 변경된 데이터를 저장하기 위한 save 메소드
*/		
		
		
		// 질문 데이터 삭제
/*		
        assertEquals(2, this.questionRepository.count()); // count() -> 레포지토리의 총 데이터 건수 리턴
        												  // 삭제 전 데이터 건수 2가 맞는지 확인
        Optional<Question> oq = this.questionRepository.findById(1); // 첫번째 질문 삭제
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count()); // 삭제 후 데이터 건수가 1이 맞는지 확인
*/
        
		
        // 답변 데이터 생성 후 저장
/*		
		Optional<Question> oq = this.questionRepository.findById(2); // 답변 데이터 생성 위한 질문 데이터 id가 2인 질문 가져옴
		assertTrue(oq.isPresent());
		Question q = oq.get();
		
		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);	// 어떤 질문의 답변인지 알기 위해서 Question 객체 필요
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
*/
		
		
		// Id값을 이용해 데이터를 조회
/*
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
*/
		
		
		// 답변에 연결된 질문 찾기 vs 질문에 달린 답변 찾기
/*		
		- 필요한 시점에 데이터를 가져오는 방식(Lazy)
		- q 객체를 조회할 때 답변 리스트를 모두 가져오는 방식(Eager)
		- @OneToMany, @ManyToOne 어노테이션의 옵션으로 fetch=FetchType.LAZY 또는 fetch=FetchType.EAGER 처럼
		  가져오는 방식을 설정할 수 있는데 이 프로젝트에서는 항상 디폴트 값을 사용할 것임
*/		  
		
			Optional<Question> oq = this.questionRepository.findById(2); // DB세션이 끊어져서 오류 발생
			assertTrue(oq.isPresent());
			Question q = oq.get();
			
			List<Answer> answerList = q.getAnswerList(); // 이 때 답변 데이터 리스트를 가져옴
			
			assertEquals(1,  answerList.size());
			assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}



// @Autowired
/*
	- 객체를 주입하기 위해 사용하는 스프링의 어노테이션
	- 객체를 주입하는 방식에는 Setter 또는 생성자를 사용하는 방식이 있음
	- 순환 참조 문제와 같은 이유로 @Autowired 어노테이션보다 생성자를 통한 객체 주입 방식이 권장됨
	- 테스트 코드의 경우, 생성자를 통한 객체의 주입이 불가능
	- 테스트 코드 작성시에만 @Autowired 사용, 실제 코드 작성시 생성자를 통한 객체 주입 방식 사용
*/


// JUnit
/*
	- 테스트 코드를 작성하고 작성한 테스트코드를 실행하기 위해 사용하는 자바의 테스트 프레임워크
*/


// 인터페이스에 findBySubject라는 선언만 하고 구현은 하지 않았는데 실행이 되는 이유
/*
    - JpaRepository를 상속한 QuestionRepository 객체가 생성될 때 벌어짐
      (DI에 의해 스프링이 자동으로 QuestionRepository 객체 생성) -> 프록시 패턴 사용
      즉, 레포지토리 객체의 메소드가 실행될 때 JPA가 해당 메소드명을 분석하여 쿼리를 만들고 실행
    - findBy + 엔티티의 속성명(ex.findBySubject)과 같은 레포지토리 메소드를 작성하면
	  해당 속성의 값으로 데이터를 조회할 수 있음
*/


// 레포지토리의 메소드의 역할과 메소드 종류
/*
    - 데이터를 조회하는 쿼리문의 where 조건 결정
    - 단, 응답 결과가 여러건인 경우에는 레포지토리 메소드의 리턴 타입을
      Question이 아닌 List<Question>으로 해야함
    - 쿼리 생성 규칙의 공식 문서 URL
      ※ https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    
    - 메소드의 종류
      · And : findBySubjectAndContent(String subject, String content)
              여러 컬럼을 and로 검색
              
      · Or : findBySubjectOrContent(String subject, String content) 
              여러 컬럼을 or로 검색
              
      · Between : findByCreateDateBetween(LocalDateTime fromDate, LocalDateTime toDate)	
                  컬럼을 between으로 검색
              
      · LessThan : findByIdLessThan(Integer id)
                   작은 항목 검색
              
      · GreaterThanEqual : findByIdGraterThanEqual(Integer id)
                           크거나 같은 항목 검색
              
      · Like : findBySubjectLike(String subject)
               like 검색
              
      ·	In : findBySubjectIn(String[] subjects)
             여러 값 중에 하나인 항목 검색
              
      · OrderBy : findBySubjectOrderByCreateDateAsc(String subject)        
                  검색 결과를 정렬하여 전달
*/