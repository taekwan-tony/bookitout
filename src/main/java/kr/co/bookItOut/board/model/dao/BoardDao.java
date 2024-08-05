package kr.co.bookItOut.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.board.model.dto.Board;
import kr.co.bookItOut.board.model.dto.BoardComment;
import kr.co.bookItOut.board.model.dto.BoardCommentRowMapper;
import kr.co.bookItOut.board.model.dto.BoardFileRowMapper;
import kr.co.bookItOut.board.model.dto.BoardF‎ile;
import kr.co.bookItOut.board.model.dto.BoardRowMapper;

@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardRowMapper boardRowMapper;
	@Autowired
	private BoardFileRowMapper boardFileRowMapper;
	@Autowired
	private BoardCommentRowMapper boardCommentRowMapper;
	public List selectBoardList(int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from board order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, boardRowMapper, params);
		return list;
	}
	public int selectBoardTotalCount() {
		String query = "select count(*) from board";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	public int insertBoard(Board b) {
		String query = "insert into board values(board_seq.nextval,?,?,?,0,to_char(sysdate,'YYYY-MM-DD'))";
		Object[] params = {b.getBoardTitle(),b.getMemberId(),b.getBoardContent()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectBoardNo() {
		String query = "select max(board_no) from board";
		int boardNo = jdbc.queryForObject(query, Integer.class);
		return boardNo;
	}
	public int insertBoard(BoardF‎ile boardF‎ile) {
		String query = "insert into board_file values(board_file_seq.nextval,?,?,?)";
		Object[] params = {boardF‎ile.getBoardNo(),boardF‎ile.getFilename(),boardF‎ile.getFilepath()};
		int result = jdbc.update(query,params);
		return result;
	}
	public Board selectOneBoard(int boardNo) {
		String query = "select * from board where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, boardRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Board)list.get(0);
		}
	}
	public int updateReadCount(int boardNo) {
		String query = "update board set read_count = read_count+1 where board_no=?";
		Object[] params = {boardNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public List selectBoardFile(int boardNo) {
		String query = "select * from board_file where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, boardFileRowMapper, params);
		return list;
	}
	public List<BoardComment> selectCommentList(int boardNo, int memberNo) {
		String query = "select bc.*,\r\n" + 
				"(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,\r\n" + 
				"(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like\r\n" + 
				"from board_comment nc\r\n" + 
				"where board_ref=? and board_comment_ref is null order by 1";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, boardCommentRowMapper, params);
		return list;
	}
	public List selectReCommentList(int boardNo, int memberNo) {
		String query = "select bc.*,\r\n" + 
				"(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,\r\n" + 
				"(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like\r\n" + 
				"from board_comment nc\r\n" + 
				"where board_ref=? and board_comment_ref is not null order by 1";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, boardCommentRowMapper, params);
		return list;
	}
	public List searchBoard(String type, String keyword, int start, int end) {
		String query = "";
		if(type.equals("title")) {
			query = "select * from (select rownum as rnum, n.* from (select * from board where board_title like '%'||?||'%' order by 1 desc)n) where rnum between ? and ? ";
		}else if(type.equals("writer")) {
			query = "select * from (select rownum as rnum, n.* from (select * from board where member_id like '%'||?||'%' order by 1 desc)n) where rnum between ? and ? ";
		}else if(type.equals("titleContent")) {
			query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' or board_title like '%'||?||'%' order by 1 desc)n) where rnum between ? and ? ";
			Object[] params = {keyword,keyword,start,end};
			List list = jdbc.query(query, boardRowMapper, params);
			return list;
		}else if(type.equals("content")) {
			query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' order by 1 desc)n) where rnum between ? and ? ";
		}
		Object[] params = {keyword,start,end};
		List list = jdbc.query(query, boardRowMapper, params);
		return list;
	}
	public int deleteBoard(int boardNo) {
		String query = "delete from board where board_no=?";
		Object[] params = {boardNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int updateBoard(Board b) {
		String query = "update board set board_title=?, board_content=?, read_count = read_count-1 where board_no=?";
		Object[] params = {b.getBoardTitle(),b.getBoardContent(),b.getBoardNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	public BoardF‎ile selectOneBoardFile(int fileNo) {
		String query = "select * from board_file where file_no=?";
		Object[] params = {fileNo};
		List list=jdbc.query(query, boardFileRowMapper,params);
		return (BoardF‎ile)list.get(0);
	}
	public int deleteBoardFile(int fileNo) {
		String query = "delete from board_file where file_no=?";
		Object[] params = {fileNo};
		int result = jdbc.update(query,params);
		return result;
	}
}
