package kr.co.bookItOut.cart.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.cart.model.dto.CartRowMapper;

@Repository
public class CartDao {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private CartRowMapper cartRowMapper;
}
