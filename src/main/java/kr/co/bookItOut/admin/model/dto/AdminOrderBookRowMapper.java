package kr.co.bookItOut.admin.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminOrderBookRowMapper implements RowMapper<AdminOrderBook>{

	@Override
	public AdminOrderBook mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdminOrderBook aob = new AdminOrderBook();
		aob.setAdminNo(rs.getInt("admin_no"));
		aob.setAdminType(rs.getInt("admin_type"));
		aob.setBookGenre(rs.getString("book_genre"));
		aob.setBookName(rs.getString("book_name"));
		aob.setBookNo(rs.getInt("book_no"));
		aob.setBookPrice(rs.getInt("book_price"));
		aob.setBookType(rs.getString("book_type"));
		aob.setBookWriter(rs.getString("book_writer"));
		aob.setOrderCheck(rs.getInt("order_check"));
		aob.setOrderDate(rs.getString("order_date"));
		aob.setOrderNo(rs.getInt("order_no"));
		aob.setOrderQuntity(rs.getString("order_quntity"));
		aob.setPublicationDate(rs.getString("publication_date"));
		
		return aob;
	}
	

}
