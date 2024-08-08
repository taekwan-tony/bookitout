package kr.co.bookItOut.cart.model.dao;

import java.util.List;

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
	public List selectAllCart() {
		String query = "SELECT cart_no, book_no, book_img, book_name, book_price, book_cart_count, member_no FROM cart_tbl JOIN book using (book_no)";
		
		List list = jdbc.query(query, cartRowMapper);
		return list;
	}
}
