package kr.co.bookItOut.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentMemberRowMapper implements RowMapper<BoardCommentMember> {
	@Override
	public BoardCommentMember mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardCommentMember bcm=new BoardCommentMember();
		bcm.setBoardCommentNo(rs.getInt("BOARD_COMMENT_No"));
		bcm.setMemberId(rs.getString("member_Id"));
		bcm.setMemberImg(rs.getString("member_Img"));
		return bcm;
	}
}
