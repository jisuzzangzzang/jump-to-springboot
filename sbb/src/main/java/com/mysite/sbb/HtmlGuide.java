package com.mysite.sbb;

public class HtmlGuide {

}

// 표준 HTML의 구조
   /*
    - html, head, body
	- CSS 파일은 head 엘리먼트 안에 링크 되어야 함
	- head 엘리먼트 안에는 meta, title 엘리먼트 포함
	- <table> (... 생략 ...) </table>  <!-- table 엘리먼트 -->
	
	<!doctype html>
	<html lang="ko">
	<head>
	    <!-- Required meta tags -->
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <!-- Bootstrap CSS -->
	    <link rel="stylesheet" type="text/css" th:href="@{/bootstrap.min.css}">
	    <!-- sbb CSS -->
	    <link rel="stylesheet" type="text/css" th:href="@{/style.css}">
	    <title>Hello, sbb!</title>
	</head>
	<body>
	(... 생략 ...)
	</body>
	</html>
    */


// 템플릿 상속
   /*
	- 템플릿 파일을 모두 HTML 구조로 변경 시 body 엘리먼트 바깥 부분(head 엘리먼트 등)은 모두 같은 내용으로 중복
	- CSS 파일 이름이 변경되거나, 새로운 CSS 파일이 추가될 때마다 모든 템플릿 파일을 일일이 수정해야함
	- 타임리프는 템플릿 상속 기능 제공
	- 템플릿 상속이란 기본 틀이 되는 템플릿을 먼저 작성, 다른 템플릿에서 그 템플릿을 상속해 사용함
    */


// layout.html 템플릿
   /*
   - 모든 템플릿이 상속해야 하는 템플릿으로 표준 HTML 문서의 기본 틀
   - body 엘리먼트 안의 <th:block layout:fragment="content"></th:block> 부분이 
   	 layout.html을 상속한 템플릿에서 개별적으로 구현해야 하는 영역이 됨
   - layout.html 템플릿 상속 시 <th:block layout:fragment="content"></th:block> 해당되는 부분만 작성해도 표준 HTML 문서 됨
   */


// question_list.html
   /*
   - 부트스트랩 스타일 링크 삭제 (부모 템플릿인 layout.html 템플릿에서 이미 부트스트랩 스타일 링크되어있음)
   - layout:decorate : 템플릿의 레이아웃(부모 템플릿)으로 사용할 템플릿 설정
   - 부모 템플릿의 th:block 엘리먼트의 내용이 자식 템플릿의 div 엘리먼트 내용으로 교체됨
   
   - 이전 페이지가 없는 경우 "이전" 링크가 비활성화(disabled) 되도록 함 (다음페이지도 마찬가지)
   - 페이지 리스트를 루프를 돌며 해당 페이지로 이동할 수 있는 링크 생성
   - 이 때, 루프 도중의 페이지가 현재 페이지와 같을 경우 active 클래스 적용하여 강조표시(선택표시) 해줌
   - 타임리프의 th:classappend="조건식 ? 클래스값" -> 조건식이 참인경우 클래스값을 class 속성에 추가
   - #numbers.sequence(시작, 끝) : 시작 번호부터 끝 번호까지의 루프를 만들어내는 타임리프 유틸리티
   - 페이지 리스트를 보기 좋게 표시하기 위해 부트스트랩의 pagination 컴포넌트 이용 (pagination, page-item, page-link)
     ※ https://getbootstrap.com/docs/5.1/components/pagination/
   
   - 템플릿에 사용된 주요 페이지 기능
     · 이전 페이지가 없으면 비활성화 : th:classappend="${!paging.hasPrevious} ? 'disabled'"
     · 다음 페이지가 없으면 비활성화 : th:classappend="${!paging.hasNext} ? 'disabled'"
     · 이전 페이지 링크 : th:href="@{|?page=${paging.number-1}|}"
     · 다음 페이지 링크 : th:href="@{|?page=${paging.number+1}|}"
     · 페이지 리스트 루프 : th:each="page: ${#numbers.sequence(0, paging.totalPages-1)}"
     · 현재 페이지와 같으면 active 적용 : th:classappend="${page == paging.number} ? 'active'"
     
   - 게시물 번호 공식 만들기
     · 번호 = 전체 게시물 갯수 - (현재 페이지 * 페이지당 게시물 갯수) - 나열 인덱스
     · 번호 : 최종 표시될 게시물 번호
     · 전체 게시물 갯수 : 데이터베이스에 저장된 게시물 전체 갯수
     · 현재 페이지 : 페이징에서 현재 선택한 페이지
     			   (만약 페이지가 1부터 시작한다면 1을 빼주어야 한다. 하지만 스프링부트의 페이징은 0부터 시작하므로 1을 뺄 필요 X)
     · 페이지당 게시물 갯수 : 한 페이지당 보여줄 게시물의 갯수
     · 나열 인덱스 : for 문 안의 게시물 순서 (나열 인덱스는 현재 페이지에서 표시할 수 있는 게시물의 인덱스,
     									 10개를 표시하는 페이지에서는 0~9, 2개를 표시하는 페이지에서는 0~1로 반복)
     									 
   - 템플릿에 사용된 공식의 정보
     · paging.getTotalElements : 전체 개시물 갯수
     · paging.number : 현재 페이지 번호
     · paging.size : 페이지 당 게시물 갯수
     · loop.index : 나열 인덱스 (0부터 시작)
     
   - 답변이 있는지 조사 : th:if="${#lists.size(question.answerList) > 0}"
     답변의 갯수 표시 : th:text="${#lists.size(question.answerList)}"
     
   - #list.size(이터러블객체)는 이터러블 객체의 사이즈를 반환하는 타임리프의 유틸리티
   
   - <td><span th:if="${question.author != null}" th:text="${question.author.username}"></span></td>
     · 질문의 글쓴이 표시
     · 작성자의 정보 없이 저장된 이전의 질문들은 author 속성에 해당되는 데이터가 없으므로 null이 아닌 경우만 표시하도록
     · 테이블 내용을 가운데 정렬하도록 tr 엘리먼트에 text-center 클래스 추가
     · 제목을 왼쪽 정렬하도록 text-start 클래스 추가
   */


// 405 오류 (Method Not Allowed)
   /*
   - /question/create URL은 POST 방식으로 처리할 수 없음
   - POST 요청을 처리할 수 있도록 QuestionController 수정
   */


// question_form.html
   /*
   - #fields.hasAnyErrors -> true -> QuestionForm 검증이 실패한 경우
   - 검증에 실패한 오류 메세지 -> #fields.allErrors()
   - th:object -> 오류 표시 위한 타임리프 속성
   
   - th:replace -> 공통 템플릿을 템플릿 내에 삽입 가능
   - th:replace="form_errors :: formErrorsFragment" -> div 엘리먼트를 form.errors.html 파일의
   	 th:fragment 속성명이 formErrorsFragment인 엘리먼트로 교체하라는 의미
   	 
   - th:action="@{/question/create}" 삭제 -> action 속성 삭제하더라도 질문 등록 및 수정 기능이 정상 작동
   	 -> CSRF 값이 자동으로 생성되지 않기 때문에 CSRF 값을 설정하기 위한 hidden 형태의 input 엘리먼트 수동으로 추가
   - 폼 태그의 action 속성 없이 폼을 전송(submit)하면, 폼의 action은 현재 URL(브라우저에 표시되는 URL주소)를 기준으로 전송됨
     질문 등록시 브라우저에 표시되는 URL -> /question/create -> post로 폼 전송시 action 속성에 /question/create 설정
     질문 수정시 브라우저에 표시되는 URL -> /question/modify/2 -> post로 폼 전송시 action 속성에 /question/modify/2 설정
     
   - 답변 작성시 사용하는 폼 태그에도 action 속성 사용 X
   - 현재 호출된 URL로 폼 전송 -> th:action 속성이 없으므로 csrf 항목 수동 추가
   
   */


// question_detail.html
   /*
   - th:object 추가 -> 답변 등록 폼의 속성이 AnswerForm을 사용
   - 검증에 실패할 경우 오류 메세지 출력 -> #fields.hasAnyErrors(), #fields.allErrors()
   
   - 로그인 상태가 아닌 경우 textarea 태그에 disabled 속성을 적용하여 사용 X
     · sec:authorize="isAnonymous()" : 현재 로그아웃 상태
     · sec:authorize="isAuthenticated()" : 현재 로그인 상태
     
   - 수정 버튼은 로그인한 사용자와 글쓴이가 동일한 경우에만 노출되도록
     #authentication.getPrincipal().getUsername() == question.author.username 적용
     로그인한 사용자가 글쓴이와 다를 때 수정 버튼 노출 X
     · #authentication.getPrincipal() : Principal 객체를 리턴하는 타임리프의 유틸리티
     
   - 삭제 버튼은 수정 버튼과 달리 href 속성 값을 javascript:void(0)으로 설정
   - 삭제를 실행 할 URL 얻기 위해 th:data-uri 속성 추가
     (자바스크립트에서 클릭 이벤트 발생 시 this.dataset.uri과 같이 사용하여 그 값을 얻을 수 있음)
   - 삭제 버튼이 눌리는 이벤트 확인 할 수 있도록 class 속성에 delete 항목 추가
   - href에 삭제 URL 직접 사용하지 않고, 이러한 방식을 사용하는 이유는 삭제 버튼을 클릭했을 때
     "정말 삭제하시겠습니까?" 와 같은 확인 절차가 필요하기 때문

   - 질문 추천 버튼을 질문 수정 버튼 좌측에 추가
   - 버튼에는 추천수도 함께 보이도록 함
   - 추천 버튼을 클릭하면 href의 속성이 javascript:void(0)으로 되어 있기 때문에 아무런 동작 X
   - 하지만 class 속성에 "recommend"를 추가, 자바스크립트를 사용하여 data-uri에 정의된 URL 호출되도록 할 것
   - 이와 같은 방법을 사용하는 이유는 추천 버튼을 눌렀을 때 확인창을 통해 사용자의 확인을 구하기 위함임
   
   - 마크다운 컴포넌트 작성 시 -> th:utext 사용, th:text를 사용하면 HTML의 태그들이 이스케이프(escape)처리되어 그대로 보임
   */


// Page 객체의 속성
	/*
	· paging.isEmpty :페이지 존재 여부 (게시물이 있으면 false, 없으면 true)
	· paging.totalElements : 전체 게시물 갯수
	· paging.totalPages : 전체 페이지 갯수
	· paging.size : 페이지당 보여줄 게시물 갯수
	· paging.number : 현재 페이지 번호
	· paging.hasPrevious : 이전 페이지 존재 여부
	· paging.hasNext : 다음 페이지 존재 여부
	*/


// navbar.html
   	/*
	- 사용자의 로그인 여부 : 타임리프의 sec:authorize 속성
	  · sec:authorize="isAnonymous()" : 로그인 되지 않은 경우에만 해당 엘리먼트가 표시되게 함
      · sec:authorize="isAuthenticated()" : 로그인 된 경우에만 해당 엘리먼트가 표시되게 함
      · 로그인을 안한 상태라면 sec:authorize="isAnonymous() -> ture -> 로그인 링크 표시
        로그인을 한 상태라면 sec:authorize="isAuthenticated() -> ture -> 로그아웃 링크 표시
    */

// 앵커
   	/*
	- 답글을 작성하거나 수정한 후에 항상 페이지 상단으로 스크롤이 이동
	- 본인이 작성한 답변 확인하려면 스크롤을 내려서 확인해야한다는 점
	- 답변을 추천한 경우에도 동일하게 발생
	- Ajax와 같은 비동기 통신 기술을 사용하여 해결 가능 -> 쉬운 방법으로 해결하는 방법이 앵커
	- URL 호출 시 원하는 위치로 이동시켜주는 앵커(anchor) 태그 사용
	- <a id="test></a> -> HTML 호출하는 URL 뒤에 #test 라고 붙여주면 해당 페이지가 호출되며 앵커로 스크롤 이동됨
    */

// 마크다운
   /*
	- 일반적인 텍스트 외에 글자를 진하게 표시하거나 링크를 추가하고 싶을 때 마크다운을 쓰면 쉽고 간단하게 표현할 수 있음
	
	< 리스트, 목록 >
    * 자바      -> ● 자바
	* 스프링부트 -> ● 스프링부트
	* 알고리즘   -> ● 알고리즘


	< 순서가 있는 목록 >
	1. 하나     -> 1. 하나
	1. 둘	   -> 2. 둘
	1. 셋	   -> 3. 셋
	
	
	< 강조 >
	- 강조할 텍스트 양쪽에 ** 넣어 감싸기
	  스프링부트는 **자바** 로 만들어진 웹 프레임워크이다. -> 스프링부트는 자바(강조됨)로 만들어진 웹 프레임워크이다.


	< 링크 >
	- [링크명](링크주소)
	  스프링 홈페이지는 [https://spring.io](https://spring.io) 입니다. -> 스프링 홈페이지는 https://spring.io 입니다. (링크 생성)


	< 소스코드 >
	``` 백쿼트(혹은 백틱) 3개를 연이어 붙여 위아래로 감싸면 생성 가능
	ex)
	
	```
	package com.mysite.sbb;
	
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.ResponseBody;
	
	@Controller
	public class HelloController {
	    @RequestMapping("/hello")
	    @ResponseBody
	    public String hello() {
	        return "Hello Spring Boot Board";
	    }
	}
	```

	< 인용 >
	- > 를 문장 맨 앞에 입력하고 1칸 띄어쓰기를 한 다음 인용구 입력
	  > 마크다운은 Github에서 사용하는 글쓰기 도구이다.


	※ 마크다운 문법 공식 문서 참고 : www.markdownguide.org/getting-started
	   - commonmark를 build.gradle(라이브러리 등록)에 추가할 때 버전을 지정해주어야함
	   - 내부적으로 관리하는 라이브러리에 포함되면 버전 정보가 필요없고, 포함되지 않으면 버전 정보가 필요함
	   - 즉, commonmark는 스프링부트가 내부적으로 관리하는 라이브러리가 아님
	   - 스프링부트가 관리하는 라이브러리의 경우 버전 정보를 명시하지 않으면 가장 궁합이 잘 맞는 버전으로 자동 선택해줌
	   - 라이브러리의 호환성을 생각한다면 버전 정보는 따로 입력하지 않는 편이 좋음
	   
	※ 마크다운 에디터 (마크다운 UI도구)
	   - https://simplemde.com/
	*/