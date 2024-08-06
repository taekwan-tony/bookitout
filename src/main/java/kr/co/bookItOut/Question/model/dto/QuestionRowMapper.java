package kr.co.bookItOut.Question.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionRowMapper implements RowMapper<Question>{

	@Override
	public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
		Question q = new Question();
		q.setMemberNo(rs.getInt("member_no"));
		q.setQuestionAnswer(rs.getString("question_answer"));
		q.setQuestionContent(rs.getString("question_content"));
		q.setQuestionNo(rs.getInt("question_no"));
		q.setQuestionTitle(rs.getString("question_title"));
		q.setQuestionType(rs.getString("question_type"));
		q.setQuestionEmail(rs.getString("question_email"));
		return q;
	}
	
}
