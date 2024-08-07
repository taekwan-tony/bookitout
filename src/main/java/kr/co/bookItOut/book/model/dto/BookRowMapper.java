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
		b.setBookNo(rs.getInt("BOOK_NO"));
		b.setBookName(rs.getString("BOOK_NAME"));
		b.setBookWriter(rs.getString("BOOK_WRITER"));
		b.setBookPrice(rs.getInt("BOOK_PRICE"));
		b.setBookPublisher(rs.getString("BOOK_PUBLISHER"));
		b.setPublicationDate(rs.getString("PUBLICATION_DATE"));
		b.setEnrollDate(rs.getString("ENROLL_DATE"));
		b.setBookImg(rs.getString("BOOK_IMG"));
		b.setAdminNo(rs.getInt("ADMIN_NO"));
		b.setBookDetailContent(rs.getString("BOOK_DETAIL_CONTENT"));
		b.setBookDetailWriter(rs.getString("BOOK_DETAIL_WRITER"));
		b.setBookDetailImg(rs.getString("BOOK_DETAIL_IMG"));
		b.setBookType(rs.getString("BOOK_TYPE"));
		b.setBookGenre(rs.getString("BOOK_GENRE"));
		b.setBookCount(rs.getInt("READ_COUNT"));
		return b;
//		BOOK_NO
//		BOOK_NAME
//		BOOK_WRITER
//		BOOK_PRICE
//		BOOK_PUBLISHER
//		PUBLICATION_DATE
//		ENROLL_DATE
//		BOOK_IMG
//		ADMIN_NO
//		BOOK_DETAIL_CONTENT
//		BOOK_DETAIL_WRITER
//		BOOK_DETAIL_IMG
//		BOOK_TYPE
//		BOOK_GENRE
//		READ_COUNT
	}

}
