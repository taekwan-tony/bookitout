package kr.co.bookItOut.admin.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminCenterBookRowMapper implements RowMapper<AdminCenterBook>{

	@Override
	public AdminCenterBook mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdminCenterBook acb = new AdminCenterBook();
		acb.setAdminNo(rs.getInt("admin_no"));
		acb.setBookGenre(rs.getString("book_genre"));
		acb.setBookName(rs.getString("book_name"));
		acb.setBookNo(rs.getInt("book_no"));
		acb.setBookPrice(rs.getInt("book_price"));
		acb.setBookPublisher(rs.getString("book_publisher"));
		acb.setBookType(rs.getString("book_type"));
		acb.setBookWriter(rs.getString("book_writer"));
		acb.setCenterBookCount(rs.getInt("center_book_count"));
		acb.setCenterBookNo(rs.getInt("center_book_no"));
		acb.setEnrollDate(rs.getString("enroll_date"));
		acb.setPublicationDate(rs.getString("publication_date"));
		
		return acb;
	}
	

}
