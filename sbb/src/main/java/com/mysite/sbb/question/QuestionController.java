package com.mysite.sbb.question;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.answer.AnswerForm;

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
    
    @RequestMapping("/list")			   // URL 맵핑 시 value 매개변수는 생략 가능
    public String list(Model model) {
    	List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList); // 모델 객체에 값을 담아줌
   	    // Model 객체는 따로 생성할 필요 없이 컨트롤러 메소드의 매개변수로 지정하기만 하면, 자동으로 Model 객체를 생성함
        return "question_list"; // 템플릿에서 값을 사용
    }

    @RequestMapping(value = "/detail/{id}")
 // @ResponseBody 템플릿을 사용하기 때문에 기존에 사용했던 어노테이션 제거
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) { // 질문 상세 페이지에 대한 URL 맵핑 추가
    	Question question = this.questionService.getQuestion(id);
    	model.addAttribute("question", question);
    	return "question_detail";
          
    }
    
    @GetMapping("/create") // 질문 등록하기
    public String questionCreate(QuestionForm questionForm) {
    	return "question_form";
    }
    
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
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
    	this.questionService.create(questionForm.getSubject(), questionForm.getContent());
    	return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
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