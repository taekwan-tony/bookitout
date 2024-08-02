package kr.co.bookItOut.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.board.model.dto.Board;
import kr.co.bookItOut.board.model.dto.BoardF‎ile;
import kr.co.bookItOut.board.model.dto.BoardRowMapper;

@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardRowMapper boardRowMapper;
	public List selectBoardList(int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from board order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, boardRowMapper, params);
		return list;
	}
	public int selectBoardTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int insertBoard(Board b) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int selectBoardNo() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int insertBoard(BoardF‎ile boardF‎ile) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
