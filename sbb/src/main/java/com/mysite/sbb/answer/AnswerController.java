package com.mysite.sbb.answer;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer") // URL 프리픽스 /answer로 고정
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	
	@PostMapping("/create/{id}") // createAnswer호출되도록 맵핑
	public String createAnswer(Model model, @PathVariable("id") Integer id, 
			@Valid AnswerForm answerForm, BindingResult bindingResult) {
		Question question = this.questionService.getQuestion(id);
		if (bindingResult.hasErrors()) { // 검증에 실패할 경우
			model.addAttribute("question", question); // Question 객체가 필요하므로 model 객체에 Question 객체를 저장
			return "question_detail";	 // question_detail 템플릿 렌더링								 
		}
		this.answerService.create(question, answerForm.getContent()); // create 메소드 호출, 답변 저장
		return String.format("redirect:/question/detail/%s", id);
	}

}



// @PostMapping
   /*
   - @RequestMapping과 동일하게 맵핑을 담당하는 역할
   - POST 요청만 받아들일 경우에 사용
   - 만약 위 URL을 GET방식으로 요청할 경우 오류 발생
   - @PostMapping(value="/create/{id}") 대신 @PostMapping("/create/{id}") 처럼 value는 생략 가능
   */


// @RequestParam
   /*
   - createAnswer의 매개변수
   - 템플릿에서 답변으로 입력한 내용(content)을 얻기 위해 추가
   - 템플릿의 답변 내용에 해당하는 textarea의 name 속성명이 content이기 때문에 변수명을 content로 사용
     (다른 변수명 사용시 오류 발생)
   - createAnswer 메소드의 URL 매핑 /create/{id}에서 {id}는 질문의 id 이므로,
	 이 id 값으로 질문을 조회하고 없을 경우에는 404 오류 발생
   - 하지만 아직 답변을 저장하는 코드를 작성하지 않고, 일단 다음과 같은 주석으로 답변을 저장해야 함을 나타냄
   */