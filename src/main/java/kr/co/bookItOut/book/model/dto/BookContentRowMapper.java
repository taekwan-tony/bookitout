package kr.co.bookItOut.book.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookContentRowMapper implements RowMapper<BookContent> {

	@Override
	public BookContent mapRow(ResultSet rs, int rowNum) throws SQLException {
		BookContent content = new BookContent();
		content.setBoardCommentContent(rs.getString("board_comment_content"));
		content.setBoardCommentDate(rs.getNString("board_comment_date"));
		content.setBoardNoRef(rs.getInt("board_no_ref"));
		content.setBookCommentNo(rs.getInt("book_comment_no"));
		content.setBookNo(rs.getInt("book_no"));
		content.setMemberNo(rs.getInt("member_no"));
		return content;
	}

}
