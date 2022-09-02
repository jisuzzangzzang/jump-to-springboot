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
   */


// question_detail.html
   /*
   - th:object 추가 -> 답변 등록 폼의 속성이 AnswerForm을 사용
   - 검증에 실패할 경우 오류 메세지 출력 -> #fields.hasAnyErrors(), #fields.allErrors()
   */