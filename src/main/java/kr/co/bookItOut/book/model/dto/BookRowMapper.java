package kr.co.bookItOut.book.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookRowMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book b = new Book();
		b.setBookNo(rs.getInt("book_no"));
		b.setBookName(rs.getNString("book_name"));
		b.setBookWriter(rs.getNString("book_writer"));
		b.setBookPrice(rs.getInt("book_price"));
		b.setBookPublisher(rs.getNString("book_publisher"));
		b.setPublicationDate(rs.getString("publication_date"));
		b.setEnrollDate(rs.getNString("enroll_date"));
		b.setBookImg(rs.getString("book_img"));
		b.setAdminNo(rs.getInt("admin_no"));
		b.setBookDetailContent(rs.getString("book_detail_content"));
		b.setBookDetailWriter(rs.getNString("book_detail_writer"));
		b.setBookDetailImg(rs.getNString("book_detail_img"));
		b.setBookType(rs.getString("book_type"));
		b.setBookGenre(rs.getString("book_genre"));
		b.setBookCount(rs.getInt("book_count"));
		return b;
	}

}
