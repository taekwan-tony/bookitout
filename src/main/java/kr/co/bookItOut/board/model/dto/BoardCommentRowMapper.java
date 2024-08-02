package kr.co.bookItOut.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentRowMapper implements RowMapper<BoardComment>{
	@Override
	public BoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardComment bc=new BoardComment();
		bc.setBoardCommentContent(rs.getString("BOARD_COMMENT_CONTENT"));
		bc.setBoardCommentDate(rs.getString("BOARD_COMMENT_DATE"));
		bc.setBoardCommentNo(rs.getInt("BOARD_COMMENT_NO"));
		bc.setBoardCommentRef(rs.getInt("BOARD_COMMENT_REF"));
		bc.setBoardRef(rs.getInt("BOARD_REF"));
		bc.setMemberId(rs.getString("MEMBER_ID"));
		return bc;
	}
}
