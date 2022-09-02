package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
									// Jpa 인터페이스를 상속 (제네릭스)
									// 레포지토리의 대상이 되는 <엔티티의 타입, 엔티티의 PK 속성 타입> 지정 (규칙)
									// Question 엔티티의 Primary Key(PK) 속성인 id의 타입은 Integer
	Question findBySubject(String subject); // findBySubject 메소드를 이용하기 위해 작성
	Question findBySubjectAndContent(String subject, String content); // findBySubjectAndContent 메소드를 이용하기 위해 작성
	List<Question> findBySubjectLike(String subject); // 리스트 findBySubjectLike 메소드를 이용하기 위해 작성
}


// 레포지토리
   /*
    - 엔티티만으로는 데이터베이스에 데이터를 저장하거나 조회할 수 없음
    - 데이터 처리를 위해서는 실제 데이터베이스와 연동하는 JPA 레포지토리가 필요
    - 레포지토리란?
      · 엔티티에 의해 생성된 데이터베이스 테이블에 접근하는 메소드들 (findAll, save 등) 을 사용하기 위한 인터페이스
      · 데이터 처리를 위해서는 CRUD(Create, Read, Update, Delete)가 필요
        -> 이러한 CRUD를 어떻게 처리할지 정의하는 계층이 레포지토리
    */
