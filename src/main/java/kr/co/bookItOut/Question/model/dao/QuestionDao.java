package kr.co.bookItOut.Question.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.Question.model.dto.QuestionRowMapper;

@Repository
public class QuestionDao {
	@Autowired
	private QuestionRowMapper questionRowMapper;
	@Autowired
	private JdbcTemplate jdbc;
	
}
