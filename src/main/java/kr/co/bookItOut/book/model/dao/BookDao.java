package kr.co.bookItOut.book.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.book.model.dto.BookRowMapper;

@Repository
public class BookDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private BookRowMapper bookRowMapper;
}
