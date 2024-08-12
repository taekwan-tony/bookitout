package kr.co.bookItOut.book.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminBookRowMapper implements RowMapper<AdminBook>{

	@Override
	public AdminBook mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdminBook ab = new AdminBook();
		ab.setBookNo(rs.getInt("BOOK_NO"));
		ab.setBookName(rs.getString("BOOK_NAME"));
		ab.setBookWriter(rs.getString("BOOK_WRITER"));
		ab.setBookPrice(rs.getInt("BOOK_PRICE"));
		ab.setBookPublisher(rs.getString("BOOK_PUBLISHER"));
		ab.setPublicationDate(rs.getString("PUBLICATION_DATE"));
		ab.setEnrollDate(rs.getString("ENROLL_DATE"));
		ab.setBookImg(rs.getString("BOOK_IMG"));
		ab.setAdminNo(rs.getInt("ADMIN_NO"));
		ab.setBookDetailContent(rs.getString("BOOK_DETAIL_CONTENT"));
		ab.setBookDetailWriter(rs.getString("BOOK_DETAIL_WRITER"));
		ab.setBookDetailImg(rs.getString("BOOK_DETAIL_IMG"));
		ab.setBookType(rs.getString("BOOK_TYPE"));
		ab.setBookGenre(rs.getString("BOOK_GENRE"));
		ab.setReadCount(rs.getInt("READ_COUNT"));
		ab.setCenterBookCount(rs.getInt("center_book_count"));
		
		return ab;
	}
	

}
