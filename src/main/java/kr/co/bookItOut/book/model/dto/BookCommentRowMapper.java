package kr.co.bookItOut.book.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookCommentRowMapper implements RowMapper<BookComment> {

	@Override
	public BookComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		BookComment bc = new BookComment();
		bc.setBookCommentNo(rs.getInt("book_comment_no"));
		bc.setBookCommentWriter(rs.getString("book_comment_writer"));
		bc.setBookCommentContent(rs.getString("book_comment_content"));
		bc.setBookCommentDate(rs.getNString("book_comment_date"));
		bc.setBookRef(rs.getInt("book_ref"));
		bc.setBookCommentRef(rs.getInt("book_comment_ref"));
		bc.setLikeCount(rs.getInt("like_count"));
		bc.setIsLike(rs.getInt("is_like"));
		
		return bc;
	}

}
