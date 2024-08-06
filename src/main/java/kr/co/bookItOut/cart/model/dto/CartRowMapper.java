package kr.co.bookItOut.cart.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
public class CartRowMapper implements RowMapper<Cart>{

	@Override
	public Cart mapRow(ResultSet rs, int rowNum) throws SQLException{
		Cart c = new Cart();
		c.setOrderNo(rs.getInt("order_no"));
		c.setOrderQuntity(rs.getInt("order_quntity"));
		c.setOrderDate(rs.getString("order_date"));
		c.setOrderCheck(rs.getInt("order_check"));
		c.setAdminNo(rs.getInt("admin_no"));
		c.setBookNo(rs.getInt("book_no"));
		return c;
	}
}
