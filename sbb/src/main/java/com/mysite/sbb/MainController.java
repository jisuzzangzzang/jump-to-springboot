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
	}
	
    @RequestMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
}


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


// Root URL
/*
   - http://localhost:8080 처럼 도메인 명과 포트 뒤에 아무것도 붙이지 않은 URL
*/


// redirect
/*
   - redirect:<URL> : URL로 리다이렉트 (리다이렉트는 완전히 새로운 URL로 요청됨)
   - forward:<URL> : URL로 포워드 (포워드는 기존 요청 값들이 유지된 상태로 URL이 전환됨)
*/     


// templates > question_list.html (+주석 작성시 오류 발생으로 여기에 작성함)
/*
   - th:each="question : ${questionList}" (타임리프 템플릿 엔진)
   - QuestionController의 list 메소드에서 조회한 질문 목록 데이터를
	  "questionList"라는 이름으로 Model 객체에 저장
   - 타임리프는 Model 객체에 저장된 값을 읽을 수 있음 -> 템플릿에서 questionList를 사용할 수 있게 됨
   - <tr>..<tr> 엘리먼트를 questionList의 갯수만큼 반복하여 출력하는 역할.
     questionList에 저장된 데이터를 하나씩 꺼내 question 객체에 대입 -> 반복구간 내에서 사용 가능
     (ex. Java for each문)
*/


// 자주 사용하는 타임리프의 속성
/*
   1. 분기문 속성
      - question 객체가 null이 아닌 경우 해당 엘리먼트 표시
	  - th:if="${question != null}"
   
   2. 반복문 속성
      - 반복횟수만큼 해당 엘리먼트를 반복하여 표시. 자바의 for each문과 유사
      - th:each="question : ${questionList}"
      - th:each="question, loop : ${questionList}"
        -> 추가한 loop 객체를 이용하여 루프 내에서 다음과 같은 속성을 사용할  수 있음
	       · loop.index - 반복 순서, 0부터 1씩 증가
	       · loop.count - 반복 순서, 1부터 1씩 증가
	       · loop.size - 반복 객체의 요소 갯수 (예: questionList의 요소 갯수)
	       · loop.first - 루프의 첫번째 순서인 경우 true
	       · loop.last - 루프의 마지막 순서인 경우 true
	       · loop.odd - 루프의 홀수번째 순서인 경우 true
	       · loop.even - 루프의 짝수번째 순서인 경우 true
	       · loop.current - 현재 대입된 객체 (예: 위의 경우 question과 동일)
	  	 
   3. 텍스트 속성
      - th:text=값 속성은 해당 엘리먼트의 텍스트로 "값"을 출력
      - th:text="${question.subject}"
      - 텍스트는 th:text 속성 대신 다음처럼 대괄호를 사용하여 값을 직접 출력할 수 있다.		
        <tr th:each="question : ${questionList}">
        <td>[[${question.subject}]]</td>
        <td>[[${question.createDate}]]</td>
        </tr>  	 
*/


// 질문 상세 링크 추가하기
   /*
   - 타임리프의 링크 주소는 th:href 속성 사용
   	 @{ URL 주소 }
   	 /question/detail/ 과 ${question.id} 가 조합되어 /question/detail/${question.id}
     이 때, 좌우에 | 문자 없이 사용하면 오류 발생
     
   - 문자열과 자바 객체의 값을 더할 때에는 반드시 좌우를 | 로 감싸 주어야 함
	 <a th:href="@{|/question/detail/${question.id}|}" th:text="${question.subject}"></a>
   */


// 질문 상세 페이지에 답변 표시하기
   /*
   - #lists.size(question.answerList)} -> 답변의 갯수
   - #lists.size(이터러블객체) -> 유틸리티의 객체로 길이를 반환
   - 답변은 question 객체의 answerList를 순회하여 <li> 엘리먼트로 표시
   */


// Spring Boot Validation 설치 후 사용할 수 있는 어노테이션
   /*
   · @Size : 문자 길이 제한
   · @NotNull : Null 허용 X
   · @NotEmpty : Null 또는 빈 문자열("") 허용 X
   · @Past : 과거 날짜만 가능
   · @Future : 미래 날짜만 가능
   · @FutureOrPresent : 미래 또는 오늘 날짜만 가능
   · @Max : 최대값
   · @Min : 최소값
   · @Pattern : 정규식으로 검증
   · 보다 많은 기능은 다음 URL 참고(https://beanvalidation.org/)
   */
