package kr.co.bookItOut.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentLikeCountRowMapper implements RowMapper<BoardCommentLikeCount>{
	@Override
	public BoardCommentLikeCount mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardCommentLikeCount boardCommentLikeCount=new BoardCommentLikeCount();
		boardCommentLikeCount.setBoardCommentNo(rs.getInt("BOARD_COMMENT_No"));
		boardCommentLikeCount.setLikeCount(rs.getInt("likeCount"));
		return boardCommentLikeCount;
	}
}
