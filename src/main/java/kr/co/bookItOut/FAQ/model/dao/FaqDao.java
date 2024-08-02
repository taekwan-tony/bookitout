package kr.co.bookItOut.FAQ.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.FAQ.model.dto.FaqRowMapper;

@Repository
public class FaqDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private FaqRowMapper faqRowMapper;
}
