package kr.co.bookItOut.cart.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CartSelPayRowMapper implements RowMapper<Cart>{
	@Override
	public Cart mapRow(ResultSet rs, int rowNum) throws SQLException{
		Cart c = new Cart();
		c.setCartNo(rs.getInt("cart_no"));
		c.setBookNo(rs.getInt("book_no"));
		c.setBookCartCount(rs.getInt("book_cart_count"));
		c.setMemberNo(rs.getInt("member_no"));
		c.setBookName(rs.getString("book_name"));
		c.setBookPrice(rs.getInt("book_price"));
		c.setBookImg(rs.getString("book_img"));
		return c;
	}
}
