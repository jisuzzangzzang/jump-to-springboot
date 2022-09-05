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
   */


// question_detail.html
   /*
   - th:object 추가 -> 답변 등록 폼의 속성이 AnswerForm을 사용
   - 검증에 실패할 경우 오류 메세지 출력 -> #fields.hasAnyErrors(), #fields.allErrors()
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
