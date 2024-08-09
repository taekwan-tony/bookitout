package kr.co.bookItOut.pay.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.pay.model.dto.PayRowMapper;

@Repository
public class PayDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private PayRowMapper payRowMapper;
}
