package kr.co.bookItOut.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardFileRowMapper implements RowMapper<BoardF‎ile>{

	@Override
	public BoardF‎ile mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardF‎ile boardF‎ile=new BoardF‎ile();
		boardF‎ile.setBoardNo(rs.getInt("BOARD_NO"));
		boardF‎ile.setFilename(rs.getString("FILENAME"));
		boardF‎ile.setFileNo(rs.getInt("FILE_NO"));
		boardF‎ile.setFilepath(rs.getString("FILEpath"));
		return boardF‎ile;
	}
	
}
