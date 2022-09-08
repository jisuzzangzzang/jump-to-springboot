
// 엔티티 (데이터베이스 테이블과 매핑되는 자바 클래스)
	/*
	 * - SBB (질문과 답변을 할 수 있는 게시판 서비스)
	 * - 질문과 답변에 해당되는 엔티티가 있어야 함
	 * - 테이블과 매핑되는 클래스 (모델 or 도메인 모델)
	 */

// Question(질문) 엔티티
	/*
	 * - id : 질문의 고유의 번호
	 * - subject : 질문의 제목
	 * - content : 질문의 내용
	 * - create_data : 질문을 작성한 일시
	 */

// Answer(답변) 엔티티
	/*
	 * - id : 답변의 고유 번호
	 * - question : 질문 (어떤 질문의 답변인지 알아야하므로 질문 속성이 필요함)
	 * - content : 답변의 내용
	 * - create_date : 답변을 작성한 일시
	 */


package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity	// 엔티티로 만들기 위해 어노테이션 적용 (JPA가 엔티티로 인식)

public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	// 질문에서 답변을 참조하려면 question.getAnswerList() 호출
	// mappedBy : 참조 엔티티의 속성명 의미
	// 즉, Answer 엔티티에서 Question 엔티티를 참조한 속성명 question을 mappedBy에 전달
	
	@ManyToOne // 여러개의 질문이 한 명의 사용자에게 작성될 수 있으므로, @ManyToOne 관계 성립
	private SiteUser author;
	
	private LocalDateTime modifyDate; // 질문의 수정 일시 속성 추가
	
	@ManyToMany	// 질문과 추천인은 부모 자식관계가 아니고 대등한 관계기 때문에 @ManyToMany 어노테이션 사용
	Set<SiteUser> voter; // 추천인은 중복되면 안되기때문에 List가 아닌 Set으로 설정

}



// @Id
   /*
	- id 속성을 기본 키로 지정 (데이터베이스에 저장할 때 동일한 값으로 저장할 수 없음)
	- 기본 키로 한 이유 : 고유 번호는 엔티티에서 각 데이터를 구분하는 유효한 값으로 중복되면 X
	- 데이터베이스에서는 id와 같은 특징을 가진 속성을 기본 키 (primary key)라고 함
   */



// @GeneratedValue
   /*
	- 데이터를 저장할 때 해당 속성에 값을 따로 세팅하지 않아도 1씩 자동으로 증가하여 저장
	- strategy는 고유 번호를 생성하는 옵션
	- GenerationType.IDENTITY는 해당 컬럼만의 독립적인 시퀀스를 생성하여 번호를 증가시킬 때 사용
	- strategy 옵션을 생략할 경우?
	  · @GeneratedValue이 지정된 컬럼들이 모두 동일한 시퀀스로 번호를 생성함
      · 일정한 순서의 고유번호를 가질 수 없게 됨
      · 이러한 이유로 보통 GenerationType.IDENTITY를 많이 사용
   */



// @Column
   /*
	- 엔티티의 속성은 테이블의 컬럼명과 일치
	- 컬럼의 세부 설정을 위해 @Column 어노테이션 사용
	- length는 컬럼의 길이를 설정할 때 사용
	- columnDefinition은 컬럼의 속성을 정의할 때 사용
	- columnDefinition = "TEXT"은 "내용"처럼 글자 수를 제한할 수 없는 경우에 사용
	- 테이블 컬럼으로 인식하고 싶지 않은 경우
	  · 엔티티의 속성은 @Column 어노테이션 사용하지 않더라도 테이블 칼럼으로 인식함
      · @Transient 어노테이션 사용
   */



// 테이블의 컬럼명
   /*
	- 작성일시에 해당하는 createDate속성의 실제 테이블 컬럼명은 create_date
	- 대소문자 형태의 카멜케이스(Camel Case) 이름은 create_date처럼
	  모두 소문자로 변경되고 언더바(_)로 단어가 구분되어 실제 테이블 컬럼명이 된다.
   */



// 엔티티와 Setter
   /*
	- 엔티티는 Setter 메소드를 구현하지 않고 사용하기를 권장
	- 데이터베이스와 바로 연결되있으므로 데이터를 자유롭게 변경할 수 있는
	  Setter 메소드를 허용하는 것이 안전하지 않다고 판단함
	- 그럼 Setter 메소드 없이 어떻게 엔티티에 값을 저장할 수 있을까?
	  · 롬복의 @Builder 어노테이션을 통한 빌드패턴 사용
	  · 데이터를 변경해야 할 경우 해당되는 메소드를 엔티티에 추가하여 데이터 변경
	  · 다만 원활한 설명을 위해 엔티티에 Setter 메소드 추가하여 진행	
   */



// CascadeType.REMOVE
   /*
	- 질문 하나에는 여러개의 답변 작성 될 수 있음
	- 질문을 삭제하면 그에 달린 답변들도 모두 함께 삭제하기 위해
	  @OneToMany의 속성으로 cascade = CascadeType.REMOVE 사용 
   */