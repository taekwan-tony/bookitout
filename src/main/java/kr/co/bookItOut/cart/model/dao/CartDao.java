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
	
	public int insertCart(int bookNo, int memberNo) {
		String query = "insert into cart_tbl values (CART_SEQ.nextval,?,1,?)";
		Object[] params = {bookNo, memberNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectCart(int bookNo, int memberNo) {
		String query = "select cart_no from cart_tbl where book_no=? and member_no=?";
		Object[] params = {bookNo, memberNo};
		int result = jdbc.queryForObject(query, Integer.class, params);
		return result;
	}
	public int selectCartCount(int bookNo, int memberNo) {
		String query = "select count(*) from cart_tbl where book_no=? and member_no=?";
		Object[] params = {bookNo, memberNo};
		int result = jdbc.queryForObject(query, Integer.class, params);
		return result;
	}
}

