package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@CreatedDate
	private LocalDateTime createDate;
	
	@ManyToOne
	private Question question; // 답변 엔티티에서 질문 엔티티를 참조하기 위해 추가
							   // 답변 객체(answer)를 통해 질문 객체의 제목을 알고 싶다면
							   // answer.getQuestion().getSubject()처럼 접근 가능
							   // 하지만 속성만 추가 X, 연결된 속성이라는 것을 명시적으로 표시해줘야함
							   // @ManyToOne 어노테이션 추가
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate; // 답변의 수정 일시 속성 추가
	
	@ManyToMany // 속성을 생성하면 새로운 테이블을 생성하여 데이터를 관리
				// 테이블에는 서로 연관된 엔티티의 고유번호(id) 2개가 프라이머리 키로 되어 있기 때문에 다대다(N:N) 관계가 성립
	Set<SiteUser> voter;
}

// @ManyToOne
   /*
    - 답변은 하나의 질문에 여러개가 달릴 수 있는 구조
      따라서 답변은 Many, 질문은 One
    - @ManyToOne (= N:1)
    - Answer 엔티티의 question 속성과 Question 엔티티가 서로 연결됨
      (실제 데이터베이스에서는 ForeignKey 관계 생성)
    - @ManyToOne 어노테이션은 부모 자식 관계 구조에서 사용
      (여기서 부모는 Question, 자식은 Answer)
   */


// Question 엔티티에서 Answer 엔티티를 참조하는 방법
   /*
    - 1:N의 관계
    - @OneToMany 어노테이션 사용
	- Question 하나에 Answer는 여러개이므로
	  추가할 답변의 속성은 List 형태로 구성
   */