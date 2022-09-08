package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	public Answer create(Question question, String content, SiteUser author) { // 답변 생성을 위해 create 메소드 추가
					// 입력으로 받은 question과 content를 사용하여 Answer 객체를 생성, 저장
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author); // 답변 저장 시 author 속성에 세팅, 답변 작성 시 작성자도 함께 저장
		this.answerRepository.save(answer);
		return answer;
		
	}
	
	public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    
    public void delete(Answer answer) {
    	this.answerRepository.delete(answer);
    }
    
    public void vote(Answer answer, SiteUser siteUser) { // 추천인 저장 vote 메소드 추가
    	answer.getVoter().add(siteUser);
    	this.answerRepository.save(answer);
    }
}
