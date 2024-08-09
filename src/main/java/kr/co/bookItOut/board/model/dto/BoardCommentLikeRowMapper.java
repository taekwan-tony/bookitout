package kr.co.bookItOut.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentLikeRowMapper implements RowMapper<BoardCommentLike>{
	@Override
	public BoardCommentLike mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardCommentLike boardCommentLike=new BoardCommentLike();
		boardCommentLike.setBoardCommentNo(rs.getInt("BOARD_COMMENT_NO"));
		boardCommentLike.setMemberNo(rs.getInt("MEMBER_NO"));
		return boardCommentLike;
	}
}
