package com.mysite.sbb.answer;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer") // URL 프리픽스 /answer로 고정
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}") // createAnswer호출되도록 맵핑
	public String createAnswer(Model model, @PathVariable("id") Integer id, 
			@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		// principal 객체를 통해 사용자 명을 얻은 뒤, 사용자 명을 통해 SiteUser 객체를 얻어 답변을 등록하는
		// AnswerService의 create 메소드에 전달하여 답변을 저장
		if (bindingResult.hasErrors()) { // 검증에 실패할 경우
			model.addAttribute("question", question); // Question 객체가 필요하므로 model 객체에 Question 객체를 저장
			return "question_detail";	 // question_detail 템플릿 렌더링								 
		}
		Answer answer = this.answerService.create(question, answerForm.getContent(), siteUser);
		// this.answerService.create(question, answerForm.getContent(), siteUser); // create 메소드 호출, 답변 저장
		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}") // 버튼 클릭시 요청되는 GET 방식의 /answer/modify/답변ID 형태의 URL
	public String asnwerModify(AnswerForm answerFrom, @PathVariable("id") Integer id, Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		answerFrom.setContent(answer.getContent());
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
							   @PathVariable("id") Integer id, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "answer_form";
		}
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		this.answerService.modify(answer, answerForm.getContent());
		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		this.answerService.delete(answer);
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
		// 답변 삭제하면 해당 답변이 있던 질문 상세화면으로 리다이렉트
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Integer id) {
											      // 로그인한 사람만 추천 가능
		Answer answer = this.answerService.getAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.answerService.vote(answer, siteUser); // vote 메소드를 호출하여 추천인을 저장
		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
		// 오류가 없다면 질문 상세화면으로 리다이렉트
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