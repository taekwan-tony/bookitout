package kr.co.bookItOut.Question.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.Question.model.dao.QuestionDao;

@Service
public class QuestionService {
	@Autowired
	private QuestionDao questionDao;
}
