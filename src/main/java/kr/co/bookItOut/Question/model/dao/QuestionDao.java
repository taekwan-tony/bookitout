package kr.co.bookItOut.Question.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.Question.model.dto.Question;
import kr.co.bookItOut.Question.model.dto.QuestionFile;
import kr.co.bookItOut.Question.model.dto.QuestionFileRowMapper;
import kr.co.bookItOut.Question.model.dto.QuestionRowMapper;

@Repository
public class QuestionDao {
	@Autowired
	private QuestionRowMapper questionRowMapper;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private QuestionFileRowMapper questionFileRowMapper;

	public int insertQuestion(Question q) {
		String query = "insert into customer_question values(customer_question_seq.nextval,?,?,?,null,?,?)";
		System.out.println(q);
		Object[] params = {q.getQuestionType(),q.getQuestionTitle(),q.getQuestionContent(),q.getMemberNo(),q.getQuestionEmail()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectQuestionNo(Question q) {
		String query = "select max(question_no) from customer_question";
		int questionNo = jdbc.queryForObject(query,Integer.class);

		
		return questionNo;
	}
	public int insertQuestionFile(QuestionFile qf) {
		String query ="insert into question_file values(question_file_seq.nextval,?,?,?)";
		Object[] params = {qf.getFilename(),qf.getFilepath(),qf.getQuestionNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
}
