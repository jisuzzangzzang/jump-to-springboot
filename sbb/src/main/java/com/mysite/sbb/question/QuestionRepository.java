package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
									// Jpa 인터페이스를 상속 (제네릭스)
									// 레포지토리의 대상이 되는 <엔티티의 타입, 엔티티의 PK 속성 타입> 지정 (규칙)
									// Question 엔티티의 Primary Key(PK) 속성인 id의 타입은 Integer
	Question findBySubject(String subject); // findBySubject 메소드를 이용하기 위해 작성
	Question findBySubjectAndContent(String subject, String content); // findBySubjectAndContent 메소드를 이용하기 위해 작성
	List<Question> findBySubjectLike(String subject); // 리스트 findBySubjectLike 메소드를 이용하기 위해 작성
	Page<Question> findAll(Pageable pageable); // Pageable 객체를 입력으로 받아 Page<Quesiton> 타입 객체를 리턴하는 findAll 메소드
	Page<Question> findAll(Specification<Question> spec, Pageable pageable); // Question 엔티티 조회하는 findAll 메소드 선언

	@Query("select "
			+ "distinct q "
			+ "from Question q "
			+ "left outer join SiteUser u1 on q.author=u1 "
			+ "left outer join Answer a on a.question=q "
			+ "left outer join SiteUser u2 on a.author=u2 "
			+ "where "
			+ "  q.subject like %:kw% "
			+ "  or q.content like %:kw% "
			+ "  or u1.username like %:kw% "
			+ "  or a.content like %:kw% "
			+ "  or u2.username like %:kw% ")
	Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
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


// @Query
   /*
   - @Query 어노테이션 적용된 FindAllByKeyword 메소드 추가 (위에서 알아본 쿼리를 @Query에 구현)
   - @Query를 작성할 때에는 반드시 테이블 기준이 아닌 엔티티 기준으로 작성
   - site_user와 같은 테이블명 대신 SiteUser 처럼 엔티티명을 사용해야함
   - 조인문에서 보듯이 q.author_id=u1.id와 같은 컬럼명 대신 q.author=u1 처럼 엔티티의 속성명을 사용해야함
   - 파라미터로 전달할 kw 문자열은 메소드의 매개변수에 @Param("kw")처럼 어노테이션을 사용해야함
   - 검색어를 의미하는 kw 문자열은 @Query 안에서 :kw로 참조됨
   */
