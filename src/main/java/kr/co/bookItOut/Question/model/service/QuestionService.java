package kr.co.bookItOut.Question.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.Question.model.dao.QuestionDao;
import kr.co.bookItOut.Question.model.dto.Question;
import kr.co.bookItOut.Question.model.dto.QuestionFile;

@Service
public class QuestionService {
	@Autowired
	private QuestionDao questionDao;

	@Transactional
	public int insertQuestion(Question q, List<QuestionFile> fileList) {
		int result = questionDao.insertQuestion(q);
		if(result>0) {
			int questionNo = questionDao.selectQuestionNo(q);
			for(QuestionFile qf : fileList) {
				qf.setQuestionNo(questionNo);;
				result+= questionDao.insertQuestionFile(qf);
			}
		}
		return result;
	}
}
