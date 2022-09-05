package com.mysite.sbb;

public class BootStrapGuide {

}


// 템플릿에 스타일 적용할 때 주의할 점
	/*
	- static 디렉토리에 style.css 파일이 위치하지만 /static/style.css 대신 /style.css 로 사용해야함
	- 왜냐하면, static 디렉토리가 스태틱 파일들의 루트 디렉토리이기 때문
	*/


// 부트스트랩 적용
	/*
	- #temporals.format(날짜객체, 날짜포맷) : 날짜 객체를 날짜 포맷에 맞게 변환
	- class="container my-3", class="table", class="table-dark" -> 부트스트랩 스타일시트에 정의되어 있는 클래스들
	※ 부트스트랩에 대한 자세한 내용은 다음 URL 참조
	  https://getbootstrap.com/docs/5.1/getting-started/introduction/
	※ 부트스트랩 card 컴포넌트에 대한 자세한 내용은 다음 URL 참조
	  https://getbootstrap.com/docs/5.1/components/card/
	*/


	// 질문 상세 템플릿에 사용한 부트스트랩 클래스
		/*
		· card, card-body, card-text : 부트스트랩 Card 컴포넌트
		· badge : 부트스트랩 Badge 컴포넌트
		· form-control : 부트스트랩 Form 컴포넌트
		· border-bottom : 아래방향 테두리 선
		· my-3 : 상하 마진값 3
		· py-2 : 상하 패딩값 2
		· p-2 : 상하좌우 패딩값 2
		· d-flex justify-content-end : 컴포넌트의 우측 정렬
		· bg-light : 연회색 배경
		· text-dark : 검은색 글씨
		· text-start : 좌측 정렬
		· btn btn-primary : 부트스트랩 버튼 컴포넌트
		· style="white-space: pre-line;" : 글 내용의 줄 바꿈을 정상적으로 보여주기 위해 적용한 스타일
		*/


// alert alert-danger
	/*
     - 오류 -> 붉은색으로 표시
	*/


// 네비게이션 바
	/*
	- 메인 페이지로 돌아갈 수 있는 기능
	- 모든 화면 위쪽에 고정되어 있는 부트스트랩 컴포넌트
	  ※ https://getbootstrap.com/docs/5.1/components/navbar/
	- 모든 페이지에서 공통적으로 보여야 하는 부분 -> layout.html 템플릿(+)
	- navbar.html 파일은 다른 템플릿에서 중복되어 사용되지는 않지만, 독립된 하나의 템플릿으로 관리하는 것이
	  유지 보수에 유리하므로 분리하였다.
	*/