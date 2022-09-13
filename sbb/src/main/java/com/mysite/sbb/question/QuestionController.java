package com.mysite.sbb.question;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor	// questionRepository 속성 포함하는 생성자 생성
							// Lombok이 제공하는 어노테이션, final이 붙은 속성을 포함하는 생성자 자동 생성
							// (ex, @Getter, @Setter)

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
    private final QuestionService questionService; // 레포지토리 대신 서비스를 사용하도록 수정함
    											   // 생성자 방식으로 DI 규칙에 의해 주입
    
    private final UserService userService;
    
    @RequestMapping("/list")			   // URL 맵핑 시 value 매개변수는 생략 가능
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, 
    								@RequestParam(value="kw", defaultValue="") String kw) {
    							 // http://localhost:8080/question/list?page=0 처럼 GET 방식으로 요청된 URL에서
    						     // 페이지 값을 가져오기 위해 매개변수가 list 메소드에 추가됨
    							 // URL에 페이지 파라미터 page가 전달되지 않은 경우 디폴트 값으로 0이 되도록 설정
    							 // * 스프링 부트의 페이징은 첫 페이지 번호가 1이 아닌 0
    					
    							 // 검색어에 해당하는 kw 파라미터 추가, 디폴트 값으로 빈 문자열 설정
    	Page<Question> paging = this.questionService.getList(page, kw);
        model.addAttribute("paging", paging); // 모델 객체에 값을 담아줌
   	    // Model 객체는 따로 생성할 필요 없이 컨트롤러 메소드의 매개변수로 지정하기만 하면, 자동으로 Model 객체를 생성함
        model.addAttribute("kw", kw);
        // 화면에서 입력한 검색어를 화면에 유지하기 위해 kw 값 저장
        return "question_list"; // 템플릿에서 값을 사용
    }

    @RequestMapping(value = "/detail/{id}")
 // @ResponseBody 템플릿을 사용하기 때문에 기존에 사용했던 어노테이션 제거
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) { // 질문 상세 페이지에 대한 URL 맵핑 추가
    	Question question = this.questionService.getQuestion(id);
    	model.addAttribute("question", question);
    	return "question_detail";
          
    }
    
    @PreAuthorize("isAuthenticated()") // 로그인이 필요한 메소드에 어노테이션 적용
    @GetMapping("/create") // 질문 등록하기
    public String questionCreate(QuestionForm questionForm) {
    	return "question_form";
    }
    
    @PreAuthorize("isAuthenticated()")  // 로그인이 필요한 메소드에 어노테이션 적용
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
    	// subject, content 대신 QuestionForm 객체로 변경
    	// subject, content 항목을 지닌 폼 전송 -> 자동으로 바인딩
    	if (bindingResult.hasErrors()) {
    		return "question_form";
    	}
    		   // @GetMapping 시 사용했던 questionCreate 메소드명과 동일하게 사용 가능
    		   // 단, 매개변수의 형태가 다른 경우에 가능 (메소드 오버로딩)
    		   // questionCreate 메소드는 화면에서 입력한 제목(subject)과 내용(content)을 매개변수로 받음
    	       // 이 때, 질문 등록 템플릿에서 필드 항목으로 사용했던 subject, content의 이름과 동일하게 해야함
    		   // bindingResult.hasErrors() 호출 -> 오류가 있는 경우 폼을 작성하는 화면 렌더링, 
    	       // 								   오류가 없으면 질문 등록 진행
    	SiteUser siteUser = this.userService.getUser(principal.getName());
    	this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
    	return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
    	Question question = this.questionService.getQuestion(id);
    	
    	if(!question.getAuthor().getUsername().equals(principal.getName())) { // 로그인한 사용자와 질문의 작성자 동일하지 않으면
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다."); // 오류 발생, 출력
    	}
    	questionForm.setSubject(question.getSubject()); // 수정할 질문과 내용을 화면에 보여주기 위해 객체에 값을 담아 템플릿으로 전달
    	questionForm.setContent(question.getContent()); // 이 과정이 없으면 화면에 제목, 내용의 값이 없어 비워져보인다.
    	return "question_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,
    							 @PathVariable("id") Integer id) { // questionForm의 데이터 검증
    	if(bindingResult.hasErrors()) {
    		return "question_form";
    	}
    	Question question = this.questionService.getQuestion(id);
    	if (!question.getAuthor().getUsername().equals(principal.getName())) { // 로그인한 사용자와 질문의 작성자 동일 검증
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
    	}
    	this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
    	// QuestionService에서 작성한 modify 메소드 호출 -> 질문 데이터 수정
    	return String.format("redirect:/question/detail/%s", id); // 수정 완료 시 질문 상세 화면을 다시 호출
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}") // URL로 전달받은 id값을 사용
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) { // 로그인한 사용자와 질문 작성자가 동일할 경우
    																				    // 서비스의 delete 메소드로 질문을 삭제
    	Question question = this.questionService.getQuestion(id); // Question 데이터 조회 후 
    	
    	if (!question.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
    	}
    	
    	this.questionService.delete(question); // 삭제 후 질문 목록 화면으로 돌아갈 수 있도록 루트 페이지로 리다이렉트
    	return "redirect:/";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String quesitonVote(Principal principal, @PathVariable("id") Integer id) {
    											 // @PathVariable 적용 -> 추천은 로그인 한 사람만 가능해야하므로
    	Question question = this.questionService.getQuestion(id);
    	SiteUser siteUser = this.userService.getUser(principal.getName());
    	this.questionService.vote(question, siteUser); // 추천인 호출하여 추천인을 저장
    	return String.format("redirect:/question/detail/%s", id); // 오류가 없으면 질문 상세화면으로 리다이렉트
    }
    
}

// 스프링의 의존성 주입(Dependency Injection) 방식 3가지
   /*
	- @Autowired : 속성에 어노테이션을 적용하여 객체를 주입
	- 생성자 : 생성자를 작성하여 객체를 주입 (권장 방식)
	- Setter - Setter 메소드를 작성하여 객체를 주입하는 방식
	  (메소드에 @Autowired 어노테이션 적용 필요)
	  (테스트코드(SbbApplicationTests.java)에서는 속성에 @Autowired 어노테이션을 사용하여 객체를 주입함))
    */


// @PathVariable
  /*
   - 요청 URL http://localhost:8080/question/detail/2 의 숫자 2처럼 변하는 id 값을 얻을 때에는
	 위와 같이 어노테이션을 사용해야함
   - 이 때, @RequestMapping(value = "/question/detail/{id}") 에서 사용한 id와 @PathVariable("id") 의 매개변수 이름이 동일해야 함
   */


// URL 프리픽스 (prefix)
  /*
   - 1. @RequestMapping("/question/list")
     2. @RequestMapping(value = "/question/detail/{id}")
   - URL의 프리픽스가 모두 /question 으로 시작함
   - 이런 경우 클래스명 위에 @RequestMapping("/question") 어노테이션을 추가하고
     메소드 단위에서는 /question을 생략한 그 뒷 부분만을 적으면 됨
   - 다만 QuestionController에서 사용하는 URL 맵핑은 항상 /question으로 시작해야하는 규칙성 생김
   - 컨트롤러의 클래스 단위의 URL 맵핑은 필수 사항 X, 컨트롤러의 성격에 맞게 사용하면 됨
   */


// @Valid
  /*
   - QuestionForm의 @NotEmpty, @Size 등으로 설정한 검증 기능이 동작
   - BindingResult 매개변수는 @Valid 어노테이션으로 인해 검증이 수행된 결과를 의미하는 객체
   - BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 함
   - 만약 2개의 매개변수의 위치가 정확하지 않다면 @Valid 에만 적용이 되어 입력값 검증 실패시 400 오류 발생
   */