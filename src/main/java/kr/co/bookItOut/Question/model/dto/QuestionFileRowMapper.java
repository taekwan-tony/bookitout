package kr.co.bookItOut.Question.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionFileRowMapper implements RowMapper<QuestionFile>{

	@Override
	public QuestionFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuestionFile qf = new QuestionFile();
		qf.setFilename(rs.getString("filename"));
		qf.setFilepath(rs.getString("filepath"));
		qf.setQuestionFileNo(rs.getInt("question_file_no"));
		qf.setQuestionNo(rs.getInt("question_no"));
		return qf;
	}
	
}
